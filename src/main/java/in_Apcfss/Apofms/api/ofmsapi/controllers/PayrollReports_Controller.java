package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.io.IOException;
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

import com.azure.core.annotation.Get;

import in_Apcfss.Apofms.api.ofmsapi.Execptions.ResourceNotFoundException;
import in_Apcfss.Apofms.api.ofmsapi.Response.VariationInCal_PaybillResponse;
import in_Apcfss.Apofms.api.ofmsapi.request.Generate_Confirm_Updatelist;
import in_Apcfss.Apofms.api.ofmsapi.request.Generate_paybill_Updatelist;
import in_Apcfss.Apofms.api.ofmsapi.request.List_PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.MonthlyPaybillRequest;
import in_Apcfss.Apofms.api.ofmsapi.request.PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.PayrollReports_Service;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class PayrollReports_Controller {

	@Autowired
	PayrollReports_Service payrollReports_Service;

//1ST Payroll Report
	@GetMapping("EmployeeTypeDropDown")
	public List<Map<String, String>> EmployeeTypeDropDown() {
		return payrollReports_Service.EmployeeTypeDropDown();

	}

	@PostMapping("ConfirmEmp_OnRolls_Report")
	public List<Map<String, String>> ConfirmEmp_OnRolls_Report(
			@RequestBody PayrollReports_Request payrollReports_Request) {
		int emptype_id = payrollReports_Request.getEmptype_id();
		String district = payrollReports_Request.getDistrict();
		return payrollReports_Service.ConfirmEmp_OnRolls_Report(emptype_id, district);

	}

	@PutMapping("ConfirmEmp_WorkStatus_Report")
	public Map<String, Object> ConfirmEmp_WorkStatus_Report(
			@RequestBody PayrollReports_Request payrollReports_Request) {
		List<List_PayrollReports_Request> employeeStatusList = payrollReports_Request.getEmployeeStatusList();
		return payrollReports_Service.ConfirmEmp_WorkStatus_Report(employeeStatusList);

	}

	// Query Written by own
	@PostMapping("ConfirmEmp_EmpName_Report")
	public List<Map<String, String>> ConfirmEmp_EmpName_Report(
			@RequestBody PayrollReports_Request payrollReports_Request) {
		int emptype_id = payrollReports_Request.getEmptype_id();
		String emp_id = payrollReports_Request.getEmp_id();
		String year = payrollReports_Request.getYear();
		String confirm_month = payrollReports_Request.getConfirm_month();
		String payment_type = payrollReports_Request.getPayment_type();
		return payrollReports_Service.ConfirmEmp_EmpName_Report(emptype_id, emp_id, year, confirm_month, payment_type);

	}

	@PutMapping("ConfirmEmp_Update_Report")
	public Map<String, Object> ConfirmEmp_Update_Report(@RequestBody PayrollReports_Request payrollReports_Request) {
		// Emp_details

		return payrollReports_Service.ConfirmEmp_Update_Report(payrollReports_Request);

	}
	// 2ND PayrollReport

	@GetMapping("DesignationDropDown")
	public List<Map<String, String>> DesignationDropDown() {
		return payrollReports_Service.DesignationDropDown();

	}

	@GetMapping("MonthsDropDown")
	public List<Map<String, String>> MonthsDropDown() {
		return payrollReports_Service.MonthsDropDown();

	}

	@GetMapping("YearsDropDown")
	public List<Map<String, String>> YearsDropDown() { 
		return payrollReports_Service.YearsDropDown();

	}

	@PostMapping("GetMonthlyPaybillConfirmationReport_PaybillGenerated")
	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String confirm_month = monthlyPaybillRequest.getConfirm_month();
		String year = monthlyPaybillRequest.getYear();
		return payrollReports_Service.GetMonthlyPaybillConfirmationReport(emp_type, payment_type, confirm_month, year);

	}

	@PostMapping("GetMonthlyPaybillConfirmationReport_AfterConfirm")
	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport_AfterConfirm(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String confirm_month = monthlyPaybillRequest.getConfirm_month();
		String year = monthlyPaybillRequest.getYear();
		return payrollReports_Service.GetMonthlyPaybillConfirmationReport_AfterConfirm(emp_type, payment_type,
				confirm_month, year);
	}

	@PostMapping("GetMonthlyPaybillConfirmationReport_beforeConfirm")
	public List<Map<String, String>> GetMonthlyPaybillConfirmationReport_beforeConfirm(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String confirm_month = monthlyPaybillRequest.getConfirm_month();
		String year = monthlyPaybillRequest.getYear();
		return payrollReports_Service.GetMonthlyPaybillConfirmationReport_beforeConfirm(emp_type, payment_type,
				confirm_month, year);

	}

//update gerented paybill from false to true when clickign on Genereate paybill button
	@PutMapping("paybillconfirm_UpdatList")
	public Map<String, Object> paybillconfirm_UpdatList(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		List<Generate_paybill_Updatelist> genertedPaybillList = monthlyPaybillRequest.getGenertedPaybillList();
		return payrollReports_Service.paybillconfirm_UpdatList(genertedPaybillList);

	}

//clicking on confirm button

//	{
//	     "emp_type": "1",
//	    "generateConfirmlist": [
//	        {
//	            "emp_id": "0119040957",
//	            "payment_type": "reg",
//	            "confirm_month": "01",
//	            "year": "2023"
//	        },
//	       {
//	            "emp_id": "0220040911",
//	            "payment_type": "reg",
//	            "confirm_month": "01",
//	            "year": "2023"
//	       }
//	    ]
//	}
	@PutMapping("confirm_UpdatList")
	public Map<String, Object> confirm_UpdatList(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		return payrollReports_Service.confirm_UpdatList(monthlyPaybillRequest);

	}

	// 3rd PayrollReport
	@PostMapping("GenertedMonthlyPaybill_Report")
	public List<Map<String, String>> GenertedMonthlyPaybill_Report(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String confirm_month = monthlyPaybillRequest.getConfirm_month();
		String year = monthlyPaybillRequest.getYear();
		return payrollReports_Service.GenertedMonthlyPaybill_Report(emp_type, payment_type, confirm_month, year);
	}

	// 4th payroll report
	@GetMapping("getEmpNamesbyType/{emp_type}")
	public List<Map<String, String>> getEmpNamesbyType(@PathVariable String emp_type) {
		return payrollReports_Service.getEmpNamesbyType(emp_type);
	}

	@PostMapping("getEmpNamesbyTypeandDistrict")
	public List<Map<String, String>> getEmpNamesbyTypeandDistrict(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String district = monthlyPaybillRequest.getDistrict();
		return payrollReports_Service.getEmpNamesbyTypeandDistrict(emp_type, district);
	}

	@PostMapping("empPayParticluarsReport")
	public List<Map<String, String>> empPayParticluarsReport(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String emp_id = monthlyPaybillRequest.getEmp_id();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String fromMonth = monthlyPaybillRequest.getFromMonth();
		String toMonth = monthlyPaybillRequest.getToMonth();
		String fromYear = monthlyPaybillRequest.getFromYear();
		String toYear = monthlyPaybillRequest.getToYear();
		return payrollReports_Service.empPayParticluarsReport(emp_id, emp_type, payment_type, fromMonth, toMonth,
				fromYear, toYear);
	}

//	@PostMapping(value = "/getEmp_paypdf")
//	public List<Map<String, String>> getEmp_paypdf(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest)
//			throws IOException, ResourceNotFoundException {
//		String emp_type = monthlyPaybillRequest.getEmp_type();
//		String emp_id = monthlyPaybillRequest.getEmp_id();
//		String fromMonth = monthlyPaybillRequest.getFromMonth();
//		String toMonth = monthlyPaybillRequest.getToMonth();
//		String fromYear = monthlyPaybillRequest.getFromYear();
//		String toYear = monthlyPaybillRequest.getToYear();
//	String payment_type=monthlyPaybillRequest.getPayment_type();
//		return payrollReports_Service.Emp_pay_values(emp_id, emp_type, fromMonth, toMonth, fromYear, toYear,payment_type);
//
//	}
	@PostMapping(value = "/getEmp_paypdf")
	public List<Map<String, String>> getEmp_paypdf(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest)
			throws IOException, ResourceNotFoundException {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String emp_id = monthlyPaybillRequest.getEmp_id();
		String fromMonth = monthlyPaybillRequest.getFromMonth();
		String toMonth = monthlyPaybillRequest.getToMonth();
		String fromYear = monthlyPaybillRequest.getFromYear();
		String toYear = monthlyPaybillRequest.getToYear();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		return payrollReports_Service.Emp_pay_values(emp_id, emp_type, fromMonth, toMonth, fromYear, toYear,
				payment_type);

	}

	@PostMapping("ViewDetailedPayParticular")
	public List<Map<String, Object>> ViewDetailedPayParticular(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();
		String emp_id = monthlyPaybillRequest.getEmp_id();
		String payment_type = monthlyPaybillRequest.getPayment_type();
		String fromMonth = monthlyPaybillRequest.getFromMonth();
		String toMonth = monthlyPaybillRequest.getToMonth();
		String fromYear = monthlyPaybillRequest.getFromYear();
		String toYear = monthlyPaybillRequest.getToYear();
		return payrollReports_Service.ViewDetailedPayParticular(emp_id, emp_type, payment_type, fromMonth, toMonth,
				fromYear, toYear);
	}

	// for the check all use these two apis
	// /paybillconfirm_UpdatList,/confirm_UpdatList in the 3rd payroll report

	// variation in calculation of paybill
	@PostMapping("VariationCalPaybill")
	public List<VariationInCal_PaybillResponse> VariationCalPaybill(
			@RequestBody PayrollReports_Request payrollReports_Request)

	{
		String confirm_month = payrollReports_Request.getConfirm_month();
		String year = payrollReports_Request.getYear();
		String category = payrollReports_Request.getCategory();
		String district = payrollReports_Request.getDistrict();
		String hra_percent = payrollReports_Request.getHra_percent();
		return payrollReports_Service.VariationCalPaybill(confirm_month, year, category, district, hra_percent);
	}

//DistrictwiseDesignationCount
	@PostMapping("DistrictwiseDesignationCount")
	public List<Map<String, String>> DistrictwiseDesignationCount(
			@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();

		return payrollReports_Service.DistrictwiseDesignationCount(emp_type);
	}

	// PAYBILL REPORT IN GMADM LOGIN
	@PostMapping("PaybillReport")
	public List<Map<String, String>> PaybillReport(@RequestBody MonthlyPaybillRequest monthlyPaybillRequest) {
		String emp_type = monthlyPaybillRequest.getEmp_type();

		return payrollReports_Service.PaybillReport(emp_type);
	}

	// EMPLOYEE DATA COUNT REPORT
	@GetMapping("DistrictsPayBill")
	public List<Map<String, Object>> DistrictsPayBill() {

		return payrollReports_Service.DistrictsPayBill();
	}
	@PostMapping("UpdateDistrictsPayBillCount")
	public Map<String, Object> UpdateDistrictsPayBillCount(
			@RequestBody List<PayrollReports_Request> payrollReports_Request) {
		return payrollReports_Service.UpdateDistrictsPayBillCount(payrollReports_Request);
	}
}
