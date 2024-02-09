package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.request.EntryForms_GMF_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.EntryForms_GMF_Service;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EntryForms_GMF_Controller {
	@Autowired
	EntryForms_GMF_Service entryForms_GMF_Service;

	/*------- District Wise Budget Limits -------*/

	// Get Data
	@GetMapping("DistrictsBudgetLimits")
	public List<Map<String, String>> DistrictsBudgetLimits() {
		return entryForms_GMF_Service.DistrictsBudgetLimits();
	}

	// Head Office Data
	@GetMapping("HeadOfficeBudgetLimits")
	public List<Map<String, String>> HeadOfficeBudgetLimits() {
		return entryForms_GMF_Service.HeadOfficeBudgetLimits();
	}

	// District Data
	@GetMapping("getDistrictTotal")
	public List<Map<String, String>> getDistrictTotal() {
		return entryForms_GMF_Service.getDistrictTotal();
	}

	// Grand Total Data
	@GetMapping("GrandTotal")
	public List<Map<String, String>> getGrandTotal() {
		return entryForms_GMF_Service.getGrandTotal();
	}

	@PostMapping("UpdateDistrictBudgetLimits")
	public Map<String, Object> updateDistrictBudgetLimits(
			@RequestBody List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		return entryForms_GMF_Service.updateDistrictBudgetLimits(entryForms_GMF_Request);
	}

////*------- Bank Detail Confirmation --------*/

	@PostMapping("GetBankDetailsToBeConfirmByGmf")
	public List<Map<String, String>> GetBankDetailsToBeConfirmByGmf(
			@RequestBody EntryForms_GMF_Request entryForms_GMF_Request) {
		String code = entryForms_GMF_Request.getCode();
		return entryForms_GMF_Service.GetBankDetailsToBeConfirmByGmf(code);
	}

	@PostMapping("UpdateBankDetainlsConfirm")
	public Map<String, Object> UpdateBankDetainlsConfirm(
			@RequestBody List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		return entryForms_GMF_Service.UpdateBankDetainlsConfirm(entryForms_GMF_Request);
	}

//	/*------- District Wise Date Limits --------*/

	@GetMapping("DistrictBudgetDateLimits")
	public List<Map<String, String>> DistrictBudgetDateLimits() {
		return entryForms_GMF_Service.DistrictBudgetDateLimits();
	}

	@PostMapping("UpdateDistrictDateLimits")
	public Map<String, Object> updateDistrictDateLimits(
			@RequestBody List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		return entryForms_GMF_Service.updateDistrictDateLimits(entryForms_GMF_Request);
	}

	// EmployeeConfrimation
	@PostMapping("EmployeeConfirmation")
	public List<Map<String, String>> EmployeeConfirmation(@RequestBody EntryForms_GMF_Request entryForms_GMF_Request) {
		String code = entryForms_GMF_Request.getCode();
		return entryForms_GMF_Service.EmployeeConfirmation(code);
	}

	@PostMapping("UpdateEmpdetailsConfirm")
	public Map<String, Object> UpdateEmpdetailsConfirm(
			@RequestBody List<EntryForms_GMF_Request> entryForms_GMF_Request) {
		return entryForms_GMF_Service.UpdateEmpdetailsConfirm(entryForms_GMF_Request);
	}
}
