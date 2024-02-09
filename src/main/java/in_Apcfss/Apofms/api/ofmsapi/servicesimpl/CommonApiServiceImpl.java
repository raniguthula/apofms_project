package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.CommonApiRepo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.controllers.UserLoginController;
import in_Apcfss.Apofms.api.ofmsapi.services.CommonApiService;

@Service
public class CommonApiServiceImpl  implements CommonApiService{

	@Autowired
	CommonApiRepo commonApiRepo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;
	public List<Map<String, String>> getDistrict_Names() {

		List<Map<String, String>> Dist_name = null;

		UserLoginController roleService = new UserLoginController();
		System.out.println("roleService:::roleService" + roleService);

		Map<String, Object> result = roleService.getRole_ids();
		System.out.println("result:::result" + roleService);
		String user_id = (String) result.get("user_id");
		System.out.println("user_id:::user_id" + user_id);

		String dist_code = user_id.substring(2, 4);
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> roles = (List<Map<String, Object>>) result.get("roles");

		System.out.println("roles in district names method" + roles);
		if (user_id.substring(0, 2).equals("DM")) {
			Dist_name = commonApiRepo.getDistrict_Name(dist_code);
		} else if (user_id.equals("APSHCL")) {
			System.out.println(".");
			Dist_name = new ArrayList<>();
			Map<String, String> districtMap = new HashMap<>();
			districtMap.put("dist_name", "APSHCL(H.O)");
			Dist_name.add(districtMap);
			return Dist_name;
		}

		return commonApiRepo.getDistrict_Name(dist_code);
	}

	public List<Map<String, String>> getDistrict_Name() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String dist_code = user_id.substring(2, 4);
		System.out.println("dist_code" + dist_code);
		List<Map<String, String>> Dist_name = null;
		if (user_id.substring(0, 2).equals("DM")) {
			Dist_name = commonApiRepo.getDistrict_Name(dist_code);
		} else if (user_id.equals("APSHCL")) {
			System.out.println(".");
			Dist_name = new ArrayList<>();
			Map<String, String> districtMap = new HashMap<>();
			districtMap.put("dist_name", "APSHCL(H.O)");
			Dist_name.add(districtMap);
			return Dist_name;
		}

		return commonApiRepo.getDistrict_Name(dist_code);
	}

	public List<Map<String, String>> getLoggedUser() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return commonApiRepo.getLoggedUser(user_id);

	}

	public List<Map<String, String>> getName_Of_The_Bank_DropDown() {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		return commonApiRepo.getName_Of_The_Bank_DropDown();
	}

	@Override
	public List<Map<String, String>> Bank_Name_And_Account_No_DropDown() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		List<Map<String, String>> banks = null;
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		if (security_id.startsWith("M")) {
			banks = commonApiRepo.Bank_Name_And_Account_No_DropDown_Man();
		} else {
			banks = commonApiRepo.Bank_Name_And_Account_No_DropDown(security_id);
		}
		return banks;
	}
	@Override
	public List<Map<String, String>> getHeadNames() {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);

		return commonApiRepo.getHeadNames();
	}

	@Override
	public List<Map<String, String>> HeadNamesPayments() {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		return commonApiRepo.HeadNamesPayments();
	}

	@Override
	public List<Map<String, String>> ClassificationBudget() {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		return commonApiRepo.ClassificationBudget(security_id);
	}

	@Override
	public List<Map<String, String>> getSubHeadNames(String headid) {
		List<Map<String, String>> headcase = null;
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		if (headid.equals("All") && !security_id.equals("00")) {
			headcase = commonApiRepo.getSubHeadNames_All(security_id);
		} else if (!headid.equals("All") && !security_id.equals("00")) {
			headcase = commonApiRepo.getSubHeadNames(headid, security_id);

		} else if (headid.equals("All") && security_id.equals("00")) {
			headcase = commonApiRepo.getSubHeadNames_Apshcl_All();
		} else if (!headid.equals("All") && security_id.equals("00")) {
			headcase = commonApiRepo.getSubHeadNames_Apshcl(headid);

		}
		return headcase;

	}
	@Override
	public List<Map<String, String>> CGG_District() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		if (security_id.equals("00") || security_id.equals("GM")) {
			return commonApiRepo.CGG_DistrictApshcl();
		} else {
			return commonApiRepo.CGG_District(security_id);

		}

	}
	
}
