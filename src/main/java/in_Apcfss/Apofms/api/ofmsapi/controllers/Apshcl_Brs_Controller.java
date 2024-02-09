package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import in_Apcfss.Apofms.api.ofmsapi.request.Brs_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.Apshcl_Brs_Service;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class Apshcl_Brs_Controller {

	@Autowired
	Apshcl_Brs_Service apshcl_Brs_Service;

//1st
	@GetMapping("getBanks_Brs")
	public List<Map<String, String>> getBanks_Brs() {

		return apshcl_Brs_Service.getBanks_Brs();
	}

	@PostMapping("getBRS_BBU")
	public List<Map<String, String>> getBRS_BBU(@RequestBody Brs_Request brs_Request) {

		String year = brs_Request.getYear();
		String month = brs_Request.getMonth();
		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_BBU(year, month, banknameaccountno);
	}

	@PostMapping("CheckInsert_Brs")
	public Map<String, Object> CheckInsert_Brs(@RequestBody List<Brs_Request> brs_Request, HttpServletRequest request) {

		return apshcl_Brs_Service.CheckInsert_Brs(brs_Request, request);
	}

	// 2nd
	@PostMapping("getBRS_OutstandingPayments")
	public List<Map<String, String>> getBRS_OutstandingPayments(@RequestBody Brs_Request brs_Request) {

		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_OutstandingPayments(banknameaccountno);
	}

//	@PostMapping("getBRS_OutstandingPayments_CheckInsert")
//	public Map<String, Object> getBRS_OutstandingPayments_CheckInsert(@RequestBody List<Brs_Request> brs_Request,
//			HttpServletRequest request) {
//
//		return apshcl_Brs_Service.getBRS_OutstandingPayments_CheckInsert(brs_Request, request);
//	}
	//3rd
	@PostMapping("getBRS_OutstandingReceipts")
	public List<Map<String, String>> getBRS_OutstandingReceipts(@RequestBody Brs_Request brs_Request) {

		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_OutstandingReceipts(banknameaccountno);
	}
//4th
	@PostMapping("getBRS_Direct_CD")
	public List<Map<String, String>> getBRS_Direct_CD(@RequestBody Brs_Request brs_Request,HttpServletRequest request) {
		String banknameaccountno = brs_Request.getBanknameaccountno();
		String cheque_dd_receipt_no = brs_Request.getCheque_dd_receipt_no();
		Date bank_date = brs_Request.getBank_date();
		long amount = brs_Request.getAmount();
		String transaction_type = brs_Request.getTransaction_type();
		return apshcl_Brs_Service.getBRS_Direct_CD(cheque_dd_receipt_no,bank_date,amount,transaction_type,banknameaccountno,request);
	}
//	5th
	@PostMapping("BRS_BankAcc_AsPerEntries")
	public List<Map<String, String>> BRS_BankAcc_AsPerEntries(@RequestBody Brs_Request brs_Request,HttpServletRequest request) {
		String fromDate = brs_Request.getFromDate();
		String toDate = brs_Request.getToDate();
		String banknameaccountno = brs_Request.getBanknameaccountno();
		System.out.println("fromDate::::::" + fromDate);
		System.out.println("fromDate::::::" + toDate);
		return apshcl_Brs_Service.BRS_BankAcc_AsPerEntries(fromDate,toDate,banknameaccountno);
	}
	//6th
	@PostMapping("BankReconciliationStatement")
	public List<Map<String, String>> BankReconciliationStatement(@RequestBody Brs_Request brs_Request,HttpServletRequest request) {
		String year = brs_Request.getYear();
		String month = brs_Request.getMonth();
		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.BankReconciliationStatement(year,month,banknameaccountno);
	}

	///BRS OLD**********
	//1st
	@GetMapping("getBanks_Brs_Old")
	public List<Map<String, String>> getBanks_Brs_Old() {

		return apshcl_Brs_Service.getBanks_Brs_Old();
	}
	@PostMapping("getBRS_BBU_Old")
	public List<Map<String, String>> getBRS_BBU_Old(@RequestBody Brs_Request brs_Request) {

		String year = brs_Request.getYear();
		String month = brs_Request.getMonth();
		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_BBU_Old(year, month, banknameaccountno);
	}

	@PostMapping("CheckInsert_Brs_Old")
	public Map<String, Object> CheckInsert_Brs_Old(@RequestBody List<Brs_Request> brs_Request, HttpServletRequest request) {

		return apshcl_Brs_Service.CheckInsert_Brs_Old(brs_Request, request);
	}
	//old outstanding payments
	@PostMapping("getBRS_OutstandingPayments_Old")
	public List<Map<String, String>> getBRS_OutstandingPayments_Old(@RequestBody Brs_Request brs_Request) {

		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_OutstandingPayments_Old(banknameaccountno);
	}

	//old outstanding receipts
	@PostMapping("getBRS_OutstandingReceipts_Old")
	public List<Map<String, String>> getBRS_OutstandingReceipts_Old(@RequestBody Brs_Request brs_Request) {

		String banknameaccountno = brs_Request.getBanknameaccountno();
		return apshcl_Brs_Service.getBRS_OutstandingReceipts_Old(banknameaccountno);
	}
	//old brs passbook report
	@PostMapping("BRS_PassBookReport_Old")
	public List<Map<String, String>> BRS_PassBookReport_Old(@RequestBody Brs_Request brs_Request,HttpServletRequest request) {
		String fromDate = brs_Request.getFromDate();
		String toDate = brs_Request.getToDate();
		String banknameaccountno = brs_Request.getBanknameaccountno();
		System.out.println("fromDate::::::" + fromDate);
		System.out.println("fromDate::::::" + toDate);
		return apshcl_Brs_Service.BRS_PassBookReport_Old(fromDate,toDate,banknameaccountno);
	}
	@PostMapping("getBRS_Direct_CD_Old")
	public List<Map<String, String>> getBRS_Direct_CD_Old(@RequestBody Brs_Request brs_Request,HttpServletRequest request) {
		String banknameaccountno = brs_Request.getBanknameaccountno();
		String cheque_dd_receipt_no = brs_Request.getCheque_dd_receipt_no();
		Date bank_date = brs_Request.getBank_date();
		long amount = brs_Request.getAmount();
		String transaction_type = brs_Request.getTransaction_type();
		return apshcl_Brs_Service.getBRS_Direct_CD_Old(cheque_dd_receipt_no,bank_date,amount,transaction_type,banknameaccountno,request);
	}
}
