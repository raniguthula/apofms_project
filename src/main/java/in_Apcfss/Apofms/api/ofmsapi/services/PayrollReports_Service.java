package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Response.VariationInCal_PaybillResponse;
import in_Apcfss.Apofms.api.ofmsapi.request.Generate_paybill_Updatelist;
import in_Apcfss.Apofms.api.ofmsapi.request.List_PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.MonthlyPaybillRequest;
import in_Apcfss.Apofms.api.ofmsapi.request.PayrollReports_Request;

@Service
public interface PayrollReports_Service {
//1st Payroll Report
	List<Map<String, String>> EmployeeTypeDropDown();

	List<Map<String, String>> ConfirmEmp_OnRolls_Report(int emptype_id, String district);

	Map<String, Object> ConfirmEmp_WorkStatus_Report(List<List_PayrollReports_Request> employeeStatusList);

	List<Map<String, String>> ConfirmEmp_EmpName_Report(int emptype_id, String emp_id,String year,String confirm_month,String payment_type);

//2nd Payroll Report
	List<Map<String, String>> DesignationDropDown();

	List<Map<String, String>> MonthsDropDown();

	List<Map<String, String>> YearsDropDown();

	List<Map<String, String>> GetMonthlyPaybillConfirmationReport(String emp_type, String payment_type,
			String confirm_month, String year);

	List<Map<String, String>> GetMonthlyPaybillConfirmationReport_AfterConfirm(String emp_type, String payment_type,
			String confirm_month, String year);

	List<Map<String, String>> GetMonthlyPaybillConfirmationReport_beforeConfirm(String emp_type, String payment_type,
			String confirm_month, String year);

	Map<String, Object> paybillconfirm_UpdatList(List<Generate_paybill_Updatelist> genertedPaybillList);

	Map<String, Object> confirm_UpdatList(MonthlyPaybillRequest monthlyPaybillRequest);

//3rd Reports
	List<Map<String, String>> GenertedMonthlyPaybill_Report(String emp_type, String payment_type, String confirm_month,
			String year);

	// 4th payroll report
	List<Map<String, String>> getEmpNamesbyType(String emp_type);
	List<Map<String, String>> getEmpNamesbyTypeandDistrict(String emp_type,String district);
	List<Map<String, String>> empPayParticluarsReport(String emp_id, String emp_type, String payment_type,
			String fromMonth, String toMonth, String fromYear, String toYear);
	List<Map<String, String>> Emp_pay_values(String emp_id, String emp_type, String fromMonth, String toMonth,
			String fromYear, String toYear, String payment_type);

	List<Map<String, Object>> ViewDetailedPayParticular(String emp_id, String emp_type, String payment_type,
			String fromMonth, String toMonth, String fromYear, String toYear);
	List<VariationInCal_PaybillResponse> VariationCalPaybill(String confirm_month, String year, String category,
			String district, String hra_percent);

	Map<String, Object> ConfirmEmp_Update_Report(PayrollReports_Request payrollReports_Request);
	//DistrictwiseDesignationCount
	List<Map<String, String>> DistrictwiseDesignationCount(String emp_type);
	//PAYBILL REPORT IN GMADM LOGIN
	List<Map<String, String>> PaybillReport(String emp_type);
	//EMPLOYEE DATA COUNT REPORT
	 List<Map<String, Object>>  DistrictsPayBill();

	Map<String, Object> UpdateDistrictsPayBillCount(List<PayrollReports_Request> payrollReports_Request);

	
	

}
