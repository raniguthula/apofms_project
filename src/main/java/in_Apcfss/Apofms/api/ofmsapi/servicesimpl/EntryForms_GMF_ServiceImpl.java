package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.EntryForms_GMF_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.EntryForms_GMF_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.EntryForms_GMF_Service;

@Service
public class EntryForms_GMF_ServiceImpl implements EntryForms_GMF_Service {

	@Autowired
	EntryForms_GMF_Repo entryForms_GMF_Repo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Override
	public List<Map<String, String>> DistrictsBudgetLimits() {

		return entryForms_GMF_Repo.DistrictsBudgetLimits();
	}

	@Override
	public List<Map<String, String>> HeadOfficeBudgetLimits() {

		return entryForms_GMF_Repo.HeadOfficeBudgetLimits();
	}

	@Override
	public List<Map<String, String>> getDistrictTotal() {

		return entryForms_GMF_Repo.getDistrictTotal();
	}

	@Override
	public List<Map<String, String>> getGrandTotal() {

		return entryForms_GMF_Repo.getGrandTotal();
	}

	public Map<String, Object> updateDistrictBudgetLimits(List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		System.out.println("security_id" + security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int update_budget_limits_insert = 0;
		int budget_limits_update = 0;

		try {
			for (EntryForms_GMF_Request budgetlimits : entryForms_GMF_Request) {
				String code = budgetlimits.getCode();
				update_budget_limits_insert = entryForms_GMF_Repo.Insert_update_budget_limits(code);
				System.out.println("update_budget_limits_insert" + update_budget_limits_insert);

				float regular = budgetlimits.getRegular();
				float conveyance = budgetlimits.getConveyance();
				float administative = budgetlimits.getAdministative();
				float others = budgetlimits.getOthers();
				float capital_exp = budgetlimits.getCapital_exp();
				float adv_staff = budgetlimits.getAdv_staff(); 

				// Print the values for debugging
				System.out.println("Regular: " + regular);
				System.out.println("Conveyance: " + conveyance);
				// ... and so on for other fields

				// You can add additional validation here to check the values

				budget_limits_update = entryForms_GMF_Repo.budget_limits_update(regular, conveyance, administative,
						others, capital_exp, adv_staff, code);
				System.out.println("budget_limits_update" + budget_limits_update);
			}

			if (update_budget_limits_insert != 0 && budget_limits_update != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Updated Successfully");
			} else {
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Error Updating Data");
			}
		} catch (Exception e) {
			responseMap.put("SCODE", "03");
			responseMap.put("SDESC", "Error: " + e.getMessage());
			e.printStackTrace();
		}

		return responseMap;
	}


	////*------- Bank Detail Confirmation --------*/
	@Override
	public List<Map<String, String>> GetBankDetailsToBeConfirmByGmf(String code) {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		List<Map<String, String>> bankdetails = null;
		if (code.equals("All")) {
			bankdetails = entryForms_GMF_Repo.GetBankDetailsToBeConfirmByGmf();
		} else {
			bankdetails = entryForms_GMF_Repo.GetBankDetailsToBeConfirmByGmf_dist(code);
		}
		return bankdetails;
	}

	public Map<String, Object> UpdateBankDetainlsConfirm(List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int Update = 0;
		int Delete = 0;
		for (EntryForms_GMF_Request bankdetails : entryForms_GMF_Request) {
			String status = bankdetails.getStatus();
			String banknameaccountno = bankdetails.getBanknameaccountno();
			if (status.equals("update")) {
				Update = entryForms_GMF_Repo.update_mandal_account(user_id, banknameaccountno);
			} else if (status.equals("delete")) {
				Delete = entryForms_GMF_Repo.delete_mandal_account(banknameaccountno);
			}
			if (Update != 0 || Delete != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "SucessfullY Updated");
			} else {
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");
			}

		}
		return responseMap;

	}

//	/*------- District Wise Date Limits --------*/
	@Override
	public List<Map<String, String>> DistrictBudgetDateLimits() {

		return entryForms_GMF_Repo.DistrictBudgetDateLimits();
	}

	@Override
	public Map<String, Object> updateDistrictDateLimits(List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		String security_id = usersSecurityRepo.GetSecurity_Id(userid);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int update_date_limits_insert = 0;
		int date_limits_update = 0;
		for (EntryForms_GMF_Request datelimits : entryForms_GMF_Request) {
			String code = datelimits.getCode();
			update_date_limits_insert = entryForms_GMF_Repo.Insert_update_date_limits(code);
			System.out.println("update_date_limits_insert" + update_date_limits_insert);
		
			String dl_sw = datelimits.getDl_sw();
			String dl_conveyance = datelimits.getDl_conveyance();
			String dl_reclib = datelimits.getDl_reclib();
			String dl_jvs = datelimits.getDl_jvs();
			date_limits_update = entryForms_GMF_Repo.date_limits_update(dl_sw, dl_conveyance, dl_reclib, dl_jvs, code);
			System.out.println("date_limits_update" + date_limits_update);

		}
		if (update_date_limits_insert != 0 && date_limits_update != 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Updated Sucessfully");
		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}
		return responseMap;
	}
//	EmployeeConfirmation
	@Override
	public List<Map<String, String>> EmployeeConfirmation(String code) {
		String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + userid);
		List<Map<String, String>> empdetails = null;
		if (code.equals("All")) {
			empdetails = entryForms_GMF_Repo.EmployeeConfirmation_All();
		} else {
			empdetails = entryForms_GMF_Repo.EmployeeConfirmation_dist(code);
		}
		return empdetails;
	}
	@Override
	public Map<String, Object> UpdateEmpdetailsConfirm(List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int Update = 0;
		int Delete = 0;
		for (EntryForms_GMF_Request bankdetails : entryForms_GMF_Request) {
			String status = bankdetails.getStatus();
			String emp_id = bankdetails.getEmp_id();
			String code=bankdetails.getCode();
			String designation_code=bankdetails.getCode();
			int confirmedemployees_insert=entryForms_GMF_Repo.confirmedemployees_insert(emp_id,code,designation_code);
		
			if (status.equals("update")) {
			
				Update = entryForms_GMF_Repo.empdetails_update(user_id, emp_id);
			} else if (status.equals("delete")) {
				Delete = entryForms_GMF_Repo.deleteemployees(emp_id);
			}
			if ( confirmedemployees_insert!=0 || Update != 0 || Delete != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "SucessfullY Updated");
			} else {
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");
			}

		}
		return responseMap;
	}
}
