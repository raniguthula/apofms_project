package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.jwt.JwtUtils;
import in_Apcfss.Apofms.api.ofmsapi.models.JwtResponse;
import in_Apcfss.Apofms.api.ofmsapi.models.LoginRequest;
import in_Apcfss.Apofms.api.ofmsapi.models.User;
import in_Apcfss.Apofms.api.ofmsapi.request.UserRequest;
import in_Apcfss.Apofms.api.ofmsapi.services.ReportsServicesService;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.CommonServiceImpl;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserLoginController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Autowired
	ReportsServicesService reportsServicesService;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/OfmsLogin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
			HttpServletRequest request) {
		JwtResponse response = new JwtResponse();

		String ip_address = request.getRemoteAddr();
		System.out.println("ip_address :::" + ip_address);
		String flag_password_change = usersSecurityRepo.getflag_password_change(loginRequest.getUsername());
		System.out.println(flag_password_change + "flag_password_change");

			try {

				System.out.println("username------------->" + loginRequest.getUsername());
				System.out.println("password------------->" + loginRequest.getPassword());

				String encodedPassword = encodePassword(loginRequest.getPassword()); 

				System.out.println("Encoded password: " + encodedPassword);
				int count_users_log = usersSecurityRepo.getcount_users_log(loginRequest.getUsername());
				System.out.println(count_users_log + ":::::count_users_log");

				if (count_users_log == 0) {
					int User_logined_in = usersSecurityRepo.insert_User_logined_in(loginRequest.getUsername(),
							ip_address);
					System.out.println("User_logined_in:::" + User_logined_in);

				} else {
					int update_userslog_login = usersSecurityRepo.update_userslog_login(loginRequest.getUsername(),
							ip_address);
					System.out.println("update_userslog_login" + update_userslog_login);
				}
				Authentication authentication = authenticationManager
						.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
								loginRequest.getPassword()));
 
				System.out.println("username------------->" + loginRequest.getUsername());
				System.out.println("password------------->" + encodedPassword);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				String jwt = jwtUtils.generateJwtToken(authentication);

				UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
				List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toList());

				System.out.println("login success for the user" + roles + "userDetails---------------->" + userDetails);
				User user = usersSecurityRepo.getById(loginRequest.getUsername());
				System.out.println("user---------------" + user);

				if (user == null) {
					response.setStatus("S02");
					response.setMsg("User does not exist");
					return ResponseEntity.ok(response);
				}

				return ResponseEntity.ok(new JwtResponse("S01", jwt, userDetails.getUsername(),flag_password_change, roles));

			} catch (BadCredentialsException e) {
				System.out.println("Authentication failed: invalid username or password");

				response = new JwtResponse();
				response.setStatus("S02");
				response.setMsg("Invalid username or password");
				return ResponseEntity.ok(response);
			}
		} 


	private String encodePassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();

			for (byte b : digest) {
				sb.append(String.format("%02x", b & 0xff));
			}

			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

	@PostMapping("/OfmsLogout")
	public List<Map<String, Object>> OfmsLogout(HttpServletRequest request) {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		String ip_address = request.getRemoteAddr();
		System.out.println("IP ADDRESS" + ip_address);
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id::::" + user_id);
		int User_logined_out = usersSecurityRepo.OfmsLogout(user_id, ip_address);

		if (User_logined_out != 0) {
			map.put("responseToken", 1);
			map.put("responseMsg", "Sucessfully Logged Out");

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", " Having Some Error");

		}
		respose.add(map);

		return respose;

	}

	@PostMapping("/changePassword")
	public Map<String, Object> changePassword(@RequestBody UserRequest userRequest, HttpServletRequest request) {

		String oldpassword = userRequest.getOldpassword();
		String newpassword = userRequest.getNewpassword();
		String confirm_newpassword = userRequest.getConfirm_newpassword();
		System.out.println("oldpassword" + oldpassword);
		System.out.println("newpassword" + newpassword);
		System.out.println("confirm_newpassword" + confirm_newpassword);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id::::" + user_id);
		String hashedoldpassword = getMD5Hash(oldpassword);
		System.out.println("hashedoldpassword" + hashedoldpassword);
		int userCount = usersSecurityRepo.userCount(user_id, hashedoldpassword);
		System.out.println("userCount" + userCount);
		if (userCount != 0) {
			if (oldpassword.equals(confirm_newpassword) || oldpassword.equals(newpassword)) {
				System.out.println("called");
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "New Password Should not be Same as Old password");
				return responseMap;
			} else if (!newpassword.equals(confirm_newpassword)) {
				responseMap.put("SCODE", "03");
				responseMap.put("SDESC", "Confirm NewPassword Should be Same as NewPasswrod");
				return responseMap;
			} else {

				String hashednewpassword = getMD5Hash(newpassword);
				System.out.println("hashednewpassword" + hashednewpassword);
				int UpdatePassword = usersSecurityRepo.UpdatePassword(hashednewpassword, user_id);
				if (UpdatePassword != 0) {
					responseMap.put("SCODE", "01");
					responseMap.put("SDESC", "updated Sucessfully");
					return responseMap;
				}
				responseMap.put("SCODE", "03");
				responseMap.put("SDESC", "Having an Error");
				return responseMap;
			}

		}

		else

		{
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Incorrect Old PassWord");
			return responseMap;

		}

	}

	private String getMD5Hash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] digest = md.digest();
			StringBuilder sb = new StringBuilder();
			for (byte b : digest) {
				sb.append(String.format("%02x", b));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// Handle the exception (e.g., log it)
			return null;
		}
	}

	@GetMapping("ServicesListBy_UserId")
	public List<Map<String, String>> ServicesListBy_UserId() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id::::" + user_id);
		if (user_id.startsWith("DM")) {
			return usersSecurityRepo.ServicesListBy_DM(user_id);
		} else
			return usersSecurityRepo.ServicesListBy_UserId(user_id);

	}

	 @GetMapping("getRole_ids")
	public Map<String, Object> getRole_ids() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("user_id::::" + user_id);
//		List<Map<String, Object>> roles = null;
		List<Map<String, Object>> roles = usersSecurityRepo.getRoleIdsByServiceIds(user_id);
		System.out.println("roles::::::roles" + roles);
		for (Map<String, Object> role : roles) {
			Integer rrr = (Integer) role.get("role_id");
			System.out.println("roleId::::" + rrr);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("user_id", user_id);
		result.put("roles", roles);
		return result;
	}

	// http://172.17.205.55:81/yjcapi/getRoleNames
	@GetMapping("/getRoleNames")
	public String getRoleNames() {
		return reportsServicesService.getRoleNames();
	}

	// http://172.17.205.55:81/yjcapi/getServices
	@PostMapping("/getServices")
	public Map<String, Object> getServices() {
		return reportsServicesService.getServices();
	}

	// http://172.17.205.55:81/yjcapi/getReports
	@PostMapping("/getReports")
	public Map<String, Object> getReports() {
		return reportsServicesService.getReports();
	}

	@PostMapping("/getPayrollReports")
	public Map<String, Object> getPayrollReports() {
		return reportsServicesService.getPayrollReports();
	}

//FOR APSHCL LOGIN
	@PostMapping("/getBRSForms")
	public Map<String, Object> getBRSForms() {
		return reportsServicesService.getBRSForms();
	}

	// FOR MANAGER LOGIN (ROLE_ID IS 6)
	@PostMapping("/getManagerApproveForms")
	public Map<String, Object> getManagerApproveForms() {
		return reportsServicesService.getManagerApproveForms();
	}

	@PostMapping("/getHousingReports")
	public Map<String, Object> getHousingReports() {
		return reportsServicesService.getHousingReports();
	}
	@PostMapping("/getGeneralServices")
	public Map<String, Object> getGeneralServices() {
		return reportsServicesService.getGeneralServices();
	}
	@PostMapping("/getAdminServices")
	public Map<String, Object> getAdminServices() {
		return reportsServicesService.getAdminServices();
	}
	
	@PostMapping("/changeUsername")
	public List<Map<String, Object>> changeUsername(@RequestBody LoginRequest loginRequest,HttpServletRequest request) {
		
		return reportsServicesService.changeUsername(loginRequest, request);
	}
	//ADDED PARENT IDS
	@PostMapping("/getParentReports")
	public Map<String, Object> getParentReports() {
		return reportsServicesService.getParentReports();
	} 
	@PostMapping("/getParentServices")
	public Map<String, Object> getParentServices() {
		return reportsServicesService.getParentServices();
	}
	@PostMapping("/getParentBrs")
	public Map<String, Object> getParentBrs() {
		return reportsServicesService.getParentBrs();
	}
	@PostMapping("/getParentPayrollReports")
	public Map<String, Object> getParentPayrollReports() {
		return reportsServicesService.getParentPayrollReports();
	}
	@PostMapping("/getParentApprovals")
	public Map<String, Object> getParentApprovals() {
		return reportsServicesService.getParentApprovals();
	}
	@GetMapping("/getParentIDsList")
	public List<Map<String, Object>> getParentIDsList() {
		return reportsServicesService.getParentIDsList();
	}
	@GetMapping("/getServicesList")
	public List<Map<String, Object>> getServicesList() {
		return reportsServicesService.getServicesList();
	}
	@GetMapping("/getEditServiceData")
	public List<Map<String, Object>> getEditServiceData(@RequestParam int service_id) {
		return reportsServicesService.getEditServiceData(service_id);
	}
	
	@PutMapping("/updateServices")
	public List<Map<String, Object>> updateServices(@RequestBody LoginRequest loginRequest) {
		
		int parent_id = loginRequest.getParent_id();
		int service_id = loginRequest.getService_id();
		int display_id = loginRequest.getDisplay_id();
		String target = loginRequest.getTarget();
		String service_name = loginRequest.getService_name();
		
		return reportsServicesService.updateServices(parent_id,service_id,display_id,target,service_name);
	}
	@PostMapping("/deleteService")
	public List<Map<String, Object>> deleteService(@RequestParam int service_id) {
		return reportsServicesService.deleteService(service_id);
	}
	@GetMapping("/getRolesList")
	public List<Map<String, Object>> getRolesList() {
		return reportsServicesService.getRolesList();
	}
	
	@GetMapping("/getServicesListWithRoleID")
	public List<Map<String, Object>> getServicesListWithRoleID(@RequestParam int role_id) {
		return reportsServicesService.getServicesListWithRoleID(role_id);
	}
	
	
	@PostMapping("/insertRolesServices")
	public List<Map<String, Object>> insertRolesServices(@RequestBody LoginRequest loginRequest) {
		
		return reportsServicesService.insertRolesServices(loginRequest);
	}
}
