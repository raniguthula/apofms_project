package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.request.ReportsRequest;

public interface Reports_Service {

	List<Map<String, String>> GetBankBook_Report(String fromDate, String toDate);

	List<Map<String, String>> DesignationDrop_Down();

	List<Map<String, String>> GetSearchEmployee_Report(int dist_code, int designation_id, String empname,String empid);

	List<Map<String, String>> GetSubsidiary_Report(String fromDate, String toDate, String banknameaccountno);

	// Cash Bank Receipts Report
	List<Map<String, String>> GetCashBank_Receipts_Report(String fromDate, String toDate, String cash_bank,
			String transaction_type);
	Map<String, Object> Get_ByPaymentReceiptId(String payment_receipt_id);
	List<Map<String, Object>> Get_PaymentReceiptId_Heads(String payment_receipt_id);
	List<Map<String, String>> GetJournalVochersReport(String fromDate, String toDate);
	List<Map<String, String>> GetJournalVochersReport_DataById(String payment_receipt_id);
	List<Map<String, String>> GetJournalVochersReport_HeadsById(String payment_receipt_id);
	List<Map<String, String>> GetHeadWise_Details_Report(String fromDate, String toDate, String headid);

	List<Map<String, String>> GetSubLeadger_Report(String fromDate, String toDate, String headid, String subheadseqid,String district);

	List<Map<String, String>> GetJournalRegister_Report(String fromDate, String toDate,String district);
	List<Map<String, String>> getPaymentDataForReceiptHeads(String payment_receipt_id);

	List<Map<String, String>> GetGeneralLeadger_Report(String fromDate, String toDate,String district, String headid);

	List<Map<String, String>> GetExpenditureAnalaysis_Report(String fromDate, String toDate);

	List<Map<String, String>> GetReviewReport(String fromDate, String toDate, String review_flag,String district);
	
	List<Map<String, String>> GetReviewReport_OnClick(String fromDate, String toDate,String review_flag,String district,String headid);

	List<Map<String, String>> GetReviewReport_In_Exp(String fromDate, String toDate, String review_flag,
			String district,String in_exp);
//	List<Map<String, String>> GetReviewReport_In_Exp_State(String fromDate, String toDate, String review_flag,
//			String district);

	List<Map<String, String>> GetPayments_Receips_CashBank_Report(String fromDate, String toDate, String cash_bank,
			String transaction_type,String district);


	List<Map<String, String>> Months_Drop_Down();
	List<Map<String, String>> years_Drop_Down();

	List<Map<String, String>> GetSalary_OnlineJV_Report_Columns(String year, String month, String jv_type);

	List<Map<String, String>> GetSalary_OnlineJV_Report_Values(String year, String month, String jv_type);
	
	//Bank Balances Report************8APSHCL LOGIN**************
	List<Map<String, String>> BankBalancesReport();

	Map<String, Object> ClickingOnYes( ReportsRequest reportsRequest);

	//Bank Book Report ****Apshcl Login ******************
	List<Map<String, String>> BankBookReport_Apshcl(ReportsRequest reportsRequest);

	List<Map<String, String>> AccountNoDropDown(String bankshortname);

	List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Exp(String fromDate, String toDate, String classification,String code);

	List<Map<String, String>> GetExpenditureAnalaysis_OnClick_Headid(String fromDate, String toDate,
			String classification,String headid);

	

//	List<Map<String, String>> getOpeningBal(String fromDate, String banknameaccountno);
	//DeletePRJ report from GMF1 USER
	List<Map<String, String>> GetDeletePRJ(String fromDate, String toDate, String category,String district);






}
