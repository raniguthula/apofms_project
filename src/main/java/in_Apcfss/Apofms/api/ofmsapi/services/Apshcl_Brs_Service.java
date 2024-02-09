package in_Apcfss.Apofms.api.ofmsapi.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.request.Brs_Request;

@Service
public interface Apshcl_Brs_Service {

	List<Map<String, String>> getBanks_Brs();

	List<Map<String, String>> getBRS_BBU(String year, String month, String banknameaccountno);

	Map<String, Object> CheckInsert_Brs(List<Brs_Request> brs_Request, HttpServletRequest request);
//2nd
	List<Map<String, String>> getBRS_OutstandingPayments(String banknameaccountno);

//	Map<String, Object> getBRS_OutstandingPayments_CheckInsert(List<Brs_Request> brs_Request,
//			HttpServletRequest request);
	//3rd
	List<Map<String, String>> getBRS_OutstandingReceipts(String banknameaccountno);
//4th
	List<Map<String, String>> getBRS_Direct_CD(String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String banknameaccountno,HttpServletRequest request);
//5th
	List<Map<String, String>> BRS_BankAcc_AsPerEntries(String fromDate, String toDate,String banknameaccountno);
//6th
	List<Map<String, String>> BankReconciliationStatement(String year, String month, String banknameaccountno);
	///BRS OLD**********
	List<Map<String, String>> getBanks_Brs_Old();

	List<Map<String, String>> getBRS_BBU_Old(String year, String month, String banknameaccountno);

	Map<String, Object> CheckInsert_Brs_Old(List<Brs_Request> brs_Request, HttpServletRequest request);
	//old outstanding payments
	List<Map<String, String>> getBRS_OutstandingPayments_Old(String banknameaccountno);
//	old outstanding receipts
	List<Map<String, String>> getBRS_OutstandingReceipts_Old(String banknameaccountno);
	//old brs passbook report
	List<Map<String, String>> BRS_PassBookReport_Old(String fromDate, String toDate, String banknameaccountno);

	List<Map<String, String>> getBRS_Direct_CD_Old(String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String banknameaccountno, HttpServletRequest request);

}
