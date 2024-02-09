package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.request.EntryForms_GMF_Request;

@Service
public interface EntryForms_GMF_Service {

	List<Map<String, String>> DistrictsBudgetLimits();

	List<Map<String, String>> HeadOfficeBudgetLimits();

	List<Map<String, String>> getDistrictTotal();

	List<Map<String, String>> getGrandTotal();

	Map<String, Object> updateDistrictBudgetLimits(List<EntryForms_GMF_Request> entryForms_GMF_Request);

////*------- Bank Detail Confirmation --------*/
	List<Map<String, String>> GetBankDetailsToBeConfirmByGmf(String code);

	Map<String, Object> UpdateBankDetainlsConfirm(List<EntryForms_GMF_Request> entryForms_GMF_Request);

//	/*------- District Wise Date Limits --------*/
	List<Map<String, String>> DistrictBudgetDateLimits();

	Map<String, Object> updateDistrictDateLimits(List<EntryForms_GMF_Request> entryForms_GMF_Request);

//	EmployeeConfirmation

	List<Map<String, String>> EmployeeConfirmation(String code);

	Map<String, Object> UpdateEmpdetailsConfirm(List<EntryForms_GMF_Request> entryForms_GMF_Request);

}
