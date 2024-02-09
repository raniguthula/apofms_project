package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.request.ReportsRequest;
import in_Apcfss.Apofms.api.ofmsapi.services.Reports_Service;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class Reports_Controller {

	@Autowired
	Reports_Service reports_Service;

	// Bank Book
	@PostMapping("GetBankBook_Report")
	public List<Map<String, String>> GetBankBook_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		System.out.println("fromDate::::::" + fromDate);
		System.out.println("fromDate::::::" + toDate);
		return reports_Service.GetBankBook_Report(fromDate, toDate);

	}

	// Search Employee Report
	@GetMapping("DesignationDrop_Down")
	public List<Map<String, String>> DesignationDrop_Down() {
		return reports_Service.DesignationDrop_Down();
	}

	@PostMapping("GetSearchEmployee_Report")
	public List<Map<String, String>> GetSearchEmployee_Report(@RequestBody ReportsRequest reportsRequest) {
		int dist_code = reportsRequest.getDist_code();
		System.out.println("dist_code::::" + dist_code);
		int designation_id = reportsRequest.getDesignation_id();
		String empname = reportsRequest.getEmpname();
		String empid = reportsRequest.getEmpid();

		return reports_Service.GetSearchEmployee_Report(dist_code, designation_id, empname, empid);
	}

//	@PostMapping("getOpeningBal")
//	public  List<Map<String, String>> getOpeningBal(@RequestBody ReportsRequest reportsRequest){
//		String fromDate = reportsRequest.getFromDate();
//		
//		String banknameaccountno = reportsRequest.getBanknameaccountno();
//		return reports_Service.getOpeningBal(fromDate,banknameaccountno);
//		
//	}
	// Bank Subsidiary Report
	@PostMapping("GetSubsidiary_Report")
	public List<Map<String, String>> GetSubsidiary_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String banknameaccountno = reportsRequest.getBanknameaccountno();
		return reports_Service.GetSubsidiary_Report(fromDate, toDate, banknameaccountno);
	}

	// Cash Bank Receipts Report And Cash Bank Payments Report are in single api
	@PostMapping("GetCashBank_Receipts_PaymentsReport")
	public List<Map<String, String>> GetCashBank_Receipts_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String cash_bank = reportsRequest.getCash_bank();
		String transaction_type = reportsRequest.getTransaction_type();
		return reports_Service.GetCashBank_Receipts_Report(fromDate, toDate, cash_bank, transaction_type);
	}

	@GetMapping("Get_PaymentReceiptId")
	public Map<String, Object> Get_ByPaymentReceiptId(@RequestParam String payment_receipt_id) {

		return reports_Service.Get_ByPaymentReceiptId(payment_receipt_id);
	}

	@GetMapping("Get_PaymentReceiptId_Heads")
	public List<Map<String, Object>> Get_PaymentReceiptId_Heads(@RequestParam String payment_receipt_id) {

		return reports_Service.Get_PaymentReceiptId_Heads(payment_receipt_id);
	}

	// Journal Vochers Report

	@PostMapping("GetJournalVochersReport")
	public List<Map<String, String>> GetJournalVochersReport(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();

		return reports_Service.GetJournalVochersReport(fromDate, toDate);
	}

	@PostMapping("GetJournalVochersReport_DataById")
	public List<Map<String, String>> GetJournalVochersReport_DataById(@RequestBody ReportsRequest reportsRequest) {
		String payment_receipt_id = reportsRequest.getPayment_receipt_id();

		return reports_Service.GetJournalVochersReport_DataById(payment_receipt_id);
	}

	@PostMapping("GetJournalVochersReport_HeadsById")
	public List<Map<String, String>> GetJournalVochersReport_HeadsById(@RequestBody ReportsRequest reportsRequest) {
		String payment_receipt_id = reportsRequest.getPayment_receipt_id();

		return reports_Service.GetJournalVochersReport_HeadsById(payment_receipt_id);
	}

	// Head Wise Details
	// Query Written by write by own
	@PostMapping("GetHeadWise_Details_Report")
	public List<Map<String, String>> GetHeadWise_Details_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String headid = reportsRequest.getHeadid();
		return reports_Service.GetHeadWise_Details_Report(fromDate, toDate, headid);
	}

	// Sub Leadger Report
	@PostMapping("GetSubLeadger_Report")
	public List<Map<String, String>> GetSubLeadger_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String headid = reportsRequest.getHeadid();
		String subheadseqid = reportsRequest.getSubheadseqid();
		String district = reportsRequest.getDistrict();
		return reports_Service.GetSubLeadger_Report(fromDate, toDate, headid, subheadseqid, district);
	}

	// Journal Register Report
	@PostMapping("GetJournalRegister_Report")
	public List<Map<String, String>> GetJournalRegister_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
String district=reportsRequest.getDistrict();
		return reports_Service.GetJournalRegister_Report(fromDate, toDate,district);
	}

	@PostMapping("getPaymentDataForReceiptHeads")
	public List<Map<String, String>> getPaymentDataForReceiptHeads(@RequestBody ReportsRequest reportsRequest) {
		String payment_receipt_id = reportsRequest.getPayment_receipt_id();

		return reports_Service.getPaymentDataForReceiptHeads(payment_receipt_id);
	}


	// General Leadger Report
	@PostMapping("GetGeneralLeadger_Report")
	public List<Map<String, String>> GetGeneralLeadger_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String headid = reportsRequest.getHeadid();
		String district=reportsRequest.getDistrict();
		return reports_Service.GetGeneralLeadger_Report(fromDate, toDate, district,headid);
	}

	@PostMapping("GetExpenditureAnalaysis_Report")
	public List<Map<String, String>> GetExpenditureAnalaysis_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		return reports_Service.GetExpenditureAnalaysis_Report(fromDate, toDate);
	}

	@PostMapping("GetExpenditureAnalaysis_OnClick_Exp")
	public List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Exp(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String classification = reportsRequest.getClassification();
		String code = reportsRequest.getCode();
		return reports_Service.GetExpenditureAnalaysis_OnClick_Exp(fromDate, toDate, classification, code);
	}

	@PostMapping("GetExpenditureAnalaysis_OnClick_Headid")
	public List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Headid(
			@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String classification = reportsRequest.getClassification();
		String headid = reportsRequest.getHeadid();
		return reports_Service.GetExpenditureAnalaysis_OnClick_Headid(fromDate, toDate, classification, headid);
	}

	@PostMapping("GetReviewReport")
	public List<Map<String, String>> GetReviewReport(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String review_flag = reportsRequest.getReview_flag();
		String district = reportsRequest.getDistrict();
		return reports_Service.GetReviewReport(fromDate, toDate, review_flag, district);
	}

	@PostMapping("GetReviewReport_OnClick")
	public List<Map<String, String>> GetReviewReport_OnClick(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String review_flag = reportsRequest.getReview_flag();
		String district = reportsRequest.getDistrict();
		String headid = reportsRequest.getHeadid();
		return reports_Service.GetReviewReport_OnClick(fromDate, toDate, review_flag, district, headid);
	}

	@PostMapping("GetReviewReport_In_Exp")
	public List<Map<String, String>> GetReviewReport_In_Exp(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String review_flag = reportsRequest.getReview_flag();
		String district = reportsRequest.getDistrict();
		String in_exp = reportsRequest.getIn_exp();
		return reports_Service.GetReviewReport_In_Exp(fromDate, toDate, review_flag, district, in_exp);
	}
//	@PostMapping("GetReviewReport_In_Exp_State")
//	public List<Map<String, String>> GetReviewReport_In_Exp_State(@RequestBody ReportsRequest reportsRequest) {
//		String fromDate = reportsRequest.getFromDate();
//		String toDate = reportsRequest.getToDate();
//		String review_flag = reportsRequest.getReview_flag();
//		String district=reportsRequest.getDistrict();
//		
//		return reports_Service.GetReviewReport_In_Exp_State(fromDate, toDate, review_flag,district);
//	}
	// Payments & Receipts (Cash &Bank)

	@PostMapping("GetPayments_Receips_CashBank_Report")
	public List<Map<String, String>> GetPayments_Receips_CashBank_Report(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String cash_bank = reportsRequest.getCash_bank();
		String transaction_type = reportsRequest.getTransaction_type();
		String district =reportsRequest.getDistrict();
		return reports_Service.GetPayments_Receips_CashBank_Report(fromDate, toDate, cash_bank, transaction_type,district);
	}

	// Salary and Online JV Report

	@GetMapping("Months_Drop_Down")
	public List<Map<String, String>> Months_Drop_Down() {
		return reports_Service.Months_Drop_Down();

	}

	@GetMapping("years_Drop_Down")
	public List<Map<String, String>> years_Drop_Down() {
		return reports_Service.years_Drop_Down();

	}

	@PostMapping("GetSalary_OnlineJV_Report_Coulmns")
	public List<Map<String, String>> GetSalary_OnlineJV_Report_Columns(@RequestBody ReportsRequest reportsRequest) {
		String year = reportsRequest.getYear();
		String month = reportsRequest.getMonth();
		String jv_type = reportsRequest.getJv_type();
		return reports_Service.GetSalary_OnlineJV_Report_Columns(year, month, jv_type);
	}

	@PostMapping("GetSalary_OnlineJV_Report_Values")
	public List<Map<String, String>> GetSalary_OnlineJV_Report_Values(@RequestBody ReportsRequest reportsRequest) {
		String year = reportsRequest.getYear();
		String month = reportsRequest.getMonth();
		String jv_type = reportsRequest.getJv_type();
		return reports_Service.GetSalary_OnlineJV_Report_Values(year, month, jv_type);
	}

	// Bank Balances Report************APSHCL LOGIN**************
	@GetMapping("BankBalancesReport")
	public List<Map<String, String>> BankBalancesReport() {

		return reports_Service.BankBalancesReport();
	}

	@PutMapping("ClickingOnYes")
	public Map<String, Object> ClickingOnYes(@RequestBody ReportsRequest reportsRequest) {

		return reports_Service.ClickingOnYes(reportsRequest);
	}

	// Bank Book Report ****Apshcl Login ******************
	@PostMapping("BankBookReport_Apshcl")
	public List<Map<String, String>> BankBookReport_Apshcl(@RequestBody ReportsRequest reportsRequest) {

		return reports_Service.BankBookReport_Apshcl(reportsRequest);
	}

	@PostMapping("AccountNoDropDown")
	public List<Map<String, String>> AccountNoDropDownt(@RequestBody ReportsRequest reportsRequest) {
		String bankshortname = reportsRequest.getBankshortname();
		return reports_Service.AccountNoDropDown(bankshortname);
	}
	
	//DeletePRJ report from GMF1 USER
	@PostMapping("GetDeletePRJ")
	public List<Map<String, String>> GetDeletePRJ(@RequestBody ReportsRequest reportsRequest) {
		String fromDate = reportsRequest.getFromDate();
		String toDate = reportsRequest.getToDate();
		String category=reportsRequest.getCategory();
		String district=reportsRequest.getDistrict();
		return reports_Service.GetDeletePRJ(fromDate,toDate,category,district);
	}
	

}
