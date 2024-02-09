package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.ReportsServicesRepo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.jwt.JwtUtils;
import in_Apcfss.Apofms.api.ofmsapi.models.LoginRequest;
import in_Apcfss.Apofms.api.ofmsapi.request.ServiceIdsListReq;
import in_Apcfss.Apofms.api.ofmsapi.services.ReportsServicesService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class ReportsServicesServiceImpl implements ReportsServicesService {

	@Autowired
	ReportsServicesRepo reportsServicesRepo;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	UsersSecurityRepo usersSecurityRepo;
	@Value("${bezkoder.app.jwtSecret}")
	private String jwtSecret;

	public String getRoleNames() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		return reportsServicesRepo.getRoleNames(user_id);
	}

	public Map<String, Object> getServices() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {
			System.out.println("element" + element);
			List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getReports() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getReportNames(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getPayrollReports() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getPayrollReportNames(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getBRSForms() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getBRSForms(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getManagerApproveForms() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getManagerApproveForms(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getHousingReports() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getHousingReports(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getGeneralServices() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getGeneralServices(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getAdminServices() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();

		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id.add(role_names);

		for (String element : id) {

			List<Map<String, String>> service_names = reportsServicesRepo.getAdminServices(element);

			temp = new HashMap<String, Object>();
			temp.put("service_id", service_names);

			outer.add(temp);

		}

		if (outer.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	@Override
	public List<Map<String, Object>> changeUsername(LoginRequest loginRequest, HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String ip_address = request.getRemoteAddr();
		System.out.println("ip_address::" + ip_address);
		String prevUserId = loginRequest.getPrevUserId();
		System.out.println("prevUserId::" + prevUserId);
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("loginRequest.getUsername()" + loginRequest.getUsername());
		if (user_id.equals("ADMIN") || user_id.equals("CGGADMIN") || user_id.equals("CFSSADMIN")
				|| prevUserId.equals("ADMIN") || prevUserId.equals("CGGADMIN") || prevUserId.equals("CFSSADMIN")) {

			Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), null);

			System.out.println(loginRequest.getUsername() + "---------->" + "authentication login");
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String token = jwtUtils.generateToken(loginRequest.getUsername());
			System.out.println("TOKEN:::" + token);

			Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
			String username = claims.getSubject();

			System.out.println("USERNAME:::" + username);

			String user = reportsServicesRepo.getUser_ID(username);
			System.out.println("USER::" + user);

			if ((user != null || !user.equals(null) || !user.equals(""))
					&& (username == user || username.equals(user))) {

				List<Map<String, String>> SevicesList = usersSecurityRepo.getServiceIdsByUserId(username);
				System.out.println("SevicesList::" + SevicesList);

				int insert = usersSecurityRepo.insert_User_logined_in(username, ip_address);
				System.out.println("insert::" + insert);

				String role_id = usersSecurityRepo.getRoleId(username);
				System.out.println("Role_ID ::::" + role_id);

				map.put("responseToken", 1);
				map.put("responseMsg", "User Changed");
				map.put("token", token);
				map.put("user", username);

			} else {
				map.put("responseMsg", "Unauthorized");

			}
		} else {
			map.put("responseMsg", "Invalid USER_ID");
		}

		respose.add(map);
		return respose;
	}

	// ADDED PARENT IDS
	@Override
	public Map<String, Object> getParentReports() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> outer1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> id2 = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);

		id2.add(role_names);

		for (String element2 : id2) {

			List<String> service_ids = reportsServicesRepo.getServiceIdsForParentReports(element2);
			id.addAll(service_ids);
			System.out.println("service_ids" + service_ids);

			for (String element : id) {

				List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element, element2);

				temp = new HashMap<String, Object>();
				temp.put("service_id", service_names);
				System.out.println("service_names" + service_names);
				if (element.equals("874")) {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);
					temp.put("inner", sql);

					outer1.add(temp);
				} else {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);

					temp.put("inner", sql);

					outer.add(temp);
				}
			}
		}

		if (outer1.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer1);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;

	}

	@Override
	public Map<String, Object> getParentServices() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> outer1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> id2 = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id2.add(role_names);

		for (String element2 : id2) {

			List<String> service_ids = reportsServicesRepo.getServiceIdsForParentServices(element2);
			id.addAll(service_ids);

			for (String element : id) {

				List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element, element2);

				temp = new HashMap<String, Object>();
				temp.put("service_id", service_names);

				if (element.equals("874")) {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);
					temp.put("inner", sql);

					outer1.add(temp);
				} else {
					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);

					temp.put("inner", sql);

					outer.add(temp);
				}
			}
		}
		if (outer1.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer1);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getParentBrs() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> outer1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> id2 = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id2.add(role_names);

		for (String element2 : id2) {

			List<String> service_ids = reportsServicesRepo.getServiceIdsForParentBRS(element2);
			id.addAll(service_ids);

			for (String element : id) {

				List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element, element2);

				temp = new HashMap<String, Object>();
				temp.put("service_id", service_names);

				if (element.equals("874")) {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);
					temp.put("inner", sql);

					outer1.add(temp);
				} else {
					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);

					temp.put("inner", sql);

					outer.add(temp);
				}
			}
		}
		if (outer1.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer1);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	@Override
	public Map<String, Object> getParentPayrollReports() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("hii payroll");
		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> outer1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> id2 = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id2.add(role_names);

		for (String element2 : id2) {

			List<String> service_ids = reportsServicesRepo.getServiceIdsForParentPayrollReports(element2);
			id.addAll(service_ids);

			for (String element : id) {

				List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element, element2);

				temp = new HashMap<String, Object>();
				temp.put("service_id", service_names);

				if (element.equals("874")) {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);
					temp.put("inner", sql);

					outer1.add(temp);
				} else {
					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);

					temp.put("inner", sql);

					outer.add(temp);
				}
			}
		}
		if (outer1.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer1);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}

	public Map<String, Object> getParentApprovals() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("hii payroll");
		ArrayList<HashMap<String, Object>> outer = new ArrayList<HashMap<String, Object>>();
		ArrayList<HashMap<String, Object>> outer1 = new ArrayList<HashMap<String, Object>>();
		ArrayList<String> id2 = new ArrayList<String>();
		ArrayList<String> id = new ArrayList<String>();
		HashMap<String, Object> temp = null;

		Map<String, Object> responseMap = new LinkedHashMap<>();

		String role_names = reportsServicesRepo.getRoleNames(user_id);
		id2.add(role_names);

		for (String element2 : id2) {

			List<String> service_ids = reportsServicesRepo.getParentApprovals(element2);
			id.addAll(service_ids);

			for (String element : id) {

				List<Map<String, String>> service_names = reportsServicesRepo.getServiceNames(element, element2);

				temp = new HashMap<String, Object>();
				temp.put("service_id", service_names);

				if (element.equals("874")) {

					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);
					temp.put("inner", sql);

					outer1.add(temp);
				} else {
					List<Map<String, String>> sql = reportsServicesRepo.getServicesList(element, element2);

					temp.put("inner", sql);

					outer.add(temp);
				}
			}
		}
		if (outer1.size() > 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer1);
		} else if (outer.size() > 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Details Found Succesfully");
			responseMap.put("SDESC", outer);
		} else {
			responseMap.put("SCODE", "0");
			responseMap.put("SDESC", "Details Not Found");
		}
		return responseMap;
	}
	/*------------------------------------------------------ ADD SERVICE ------------------------------------------------------*/

	public List<Map<String, Object>> getParentIDsList() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String role_id = reportsServicesRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> parent = null;

		if (role_id.equals("1") || role_id.equals("8") || role_id.equals("15")) {

			parent = reportsServicesRepo.getParentIDsList();

			map.put("responseToken", 1);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("Parent_ID_List", parent);
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> getServicesList() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String role_id = reportsServicesRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> parent = null;

		if (role_id.equals("1") || role_id.equals("8") || role_id.equals("15")) {

			parent = reportsServicesRepo.getAddServiceServicesList();

			map.put("responseToken", 1);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("Parent_ID_List", parent);
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> getEditServiceData(int service_id) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String role_id = reportsServicesRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> parent = null;

		if (role_id.equals("1") || role_id.equals("8") || role_id.equals("15")) {

			parent = reportsServicesRepo.getEditServiceData(service_id);

			map.put("responseToken", 1);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("Parent_ID_List", parent);
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> updateServices(int parent_id, int service_id, int display_id, String target,
			String service_name) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String role_id = reportsServicesRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		if (role_id.equals("1") || role_id.equals("8") || role_id.equals("15")) {

			int insert_service_bck = reportsServicesRepo.insert_service_bck(service_id);
			System.out.println("insert_service_bck:::::" + insert_service_bck);

			if (insert_service_bck > 0) {

				int update_services = reportsServicesRepo.update_services(service_id, parent_id, display_id, target,
						service_name);
				System.out.println("update_services:::::" + update_services);

				if (update_services > 0) {
					map.put("responseToken", 1);
					map.put("responseMsg", "Updated Successsfully");
					map.put("responseToken", update_services);
				} else {
					map.put("responseToken", 0);
					map.put("responseMsg", "Not Updated");
				}
			}
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}
		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> deleteService(int service_id) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String role_id = reportsServicesRepo.getRoleId(user_id);
		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		if (role_id.equals("1") || role_id.equals("8") || role_id.equals("15")) {

			int update_Service_false = reportsServicesRepo.update_Service_false(service_id);
			System.out.println("update_Service_false::::" + update_Service_false);

			if (update_Service_false > 0) {

				map.put("responseToken", 1);
				map.put("responseMsg", "Deleted Successsfully");
				map.put("responseToken", update_Service_false);
			} else {
				map.put("responseToken", 0);
				map.put("responseMsg", "Not Deleted");
			}
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	/*---------------------------------------------------- ROLE SERVICE MAPPING ----------------------------------------------------*/

	public List<Map<String, Object>> getRolesList() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

//		String role_id = commonRepo.getRoleId(user_id);
//		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> parent = null;

		if (user_id.equals("ADMIN")) {

			parent = reportsServicesRepo.getRolesList();

			map.put("responseToken", 1);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("Parent_ID_List", parent);
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> getServicesListWithRoleID(int role_id) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

//		String role_id = commonRepo.getRoleId(user_id);
//		System.out.println("Role_ID ::::" + role_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		List<Map<String, Object>> parent = null;

		if (user_id.equals("ADMIN")) {

			parent = reportsServicesRepo.getServicesListWithRoleID(role_id);

			map.put("responseToken", 1);
			map.put("responseMsg", "Data fetched Successfully");
			map.put("Parent_ID_List", parent);
		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}

	public List<Map<String, Object>> insertRolesServices(LoginRequest loginRequest) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		if (user_id.equals("ADMIN")) {

			System.out.println("addServicesDetailsReq.getService_ids()::::" + loginRequest.getService_ids());

			int delete_roles = reportsServicesRepo.delete_roles(loginRequest.getRole_id());
			System.out.println("delete_roles::::" + delete_roles);

			List<ServiceIdsListReq> service_details = loginRequest.getService_ids();
			System.out.println("SERVICES:::::::" + service_details.size());

			int serviceIds = 0;
			int insert_roles = 0;
			for (int i = 0; i < service_details.size(); i++) {

				serviceIds = service_details.get(i).getService_id();
				System.out.println("serviceIds------->" + serviceIds);

				insert_roles += reportsServicesRepo.insert_roles(loginRequest.getRole_id(), serviceIds);
				System.out.println("insert_roles::::" + insert_roles);
			}

			if (insert_roles > 0) {
				map.put("responseToken", 1);
				map.put("responseMsg", "Data Inserted Successfully");
				map.put("Parent_ID_List", insert_roles);
			} else {
				map.put("responseToken", 0);
				map.put("responseMsg", "Not Inserted");
			}

		} else {
			map.put("responseToken", 2);
			map.put("responseMsg", "Invalid userId");
		}

		respose.add(map);
		return respose;
	}
}
