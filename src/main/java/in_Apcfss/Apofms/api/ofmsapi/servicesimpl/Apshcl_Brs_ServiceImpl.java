package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.Apshcl_Brs_ServiceRepo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.Brs_Request;
import in_Apcfss.Apofms.api.ofmsapi.services.Apshcl_Brs_Service;

@Service
public class Apshcl_Brs_ServiceImpl implements Apshcl_Brs_Service {

	@Autowired
	Apshcl_Brs_ServiceRepo Apshcl_Brs_ServiceRepo;
	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Override
	public List<Map<String, String>> getBanks_Brs() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return Apshcl_Brs_ServiceRepo.getBanks_Brs(user_id);
	}

	@Override
	public List<Map<String, String>> getBRS_BBU(String year, String month, String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return Apshcl_Brs_ServiceRepo.getBRS_BBU(year, month, banknameaccountno);
	}
//	bank book update, outstanding payments,  outstanding receipts insertions are same api
	@Override
	public Map<String, Object> CheckInsert_Brs(@RequestBody List<Brs_Request> brs_Request, HttpServletRequest request) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String ip_address = request.getRemoteAddr();
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int submit = 0;
		for (Brs_Request requestData : brs_Request) {

			String banknameaccountno = requestData.getBanknameaccountno();
			String payment_receipt_id = requestData.getPayment_receipt_id();
			String cheque_dd_receipt_no = requestData.getCheque_dd_receipt_no();
			Date bank_date = requestData.getBank_date();
			long amount = requestData.getAmount();
			String transaction_type = requestData.getTransaction_type();
			submit = Apshcl_Brs_ServiceRepo.CheckInsert_Brs(payment_receipt_id, cheque_dd_receipt_no, bank_date, amount,
					transaction_type,user_id,ip_address, banknameaccountno,  security_id);
		}

		if (submit != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Submitted Sucessfully");
		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an error");
		}

		return responseMap;

	}

//2nd
	@Override
	public List<Map<String, String>> getBRS_OutstandingPayments(String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		 Date myDate = new Date();
         SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM");
         String dmy = dmyFormat.format(myDate);
         String monthfirst = String.valueOf(dmy) + "-01";
         System.out.println("monthfirst"+monthfirst);
		return Apshcl_Brs_ServiceRepo.getBRS_OutstandingPayments(monthfirst,banknameaccountno);
	}

//	@Override
//	public Map<String, Object> getBRS_OutstandingPayments_CheckInsert(List<Brs_Request> brs_Request,
//			HttpServletRequest request) {
//		String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("------Userid" + user_id);
//		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//		String ip_address = request.getRemoteAddr();
//		Map<String, Object> responseMap = new LinkedHashMap<>();
//		int submit = 0;
//		for (Brs_Request requestData : brs_Request) {
//
//			String banknameaccountno = requestData.getBanknameaccountno();
//			String payment_receipt_id = requestData.getPayment_receipt_id();
//			String cheque_dd_receipt_no = requestData.getCheque_dd_receipt_no();
//			Date bank_date = requestData.getBank_date();
//			Integer amount = requestData.getAmount();
//			String transaction_type = requestData.getTransaction_type();
//			submit = Apshcl_Brs_ServiceRepo.getBRS_OutstandingPayments_CheckInsert(payment_receipt_id, cheque_dd_receipt_no, bank_date, amount,
//					transaction_type, banknameaccountno, user_id, security_id, ip_address);
//		}
//
//		if (submit != 0) {
//			responseMap.put("SCODE", "01");
//			responseMap.put("SDESC", "Submitted Sucessfully");
//		} else {
//			responseMap.put("SCODE", "02");
//			responseMap.put("SDESC", "Having an error");
//		}
//
//		return responseMap;
//	}
	@Override
	public List<Map<String, String>> getBRS_OutstandingReceipts(String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		 Date myDate = new Date();
         SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM");
         String dmy = dmyFormat.format(myDate);
         String monthfirst = String.valueOf(dmy) + "-01";
         System.out.println("monthfirst"+monthfirst);
		return Apshcl_Brs_ServiceRepo.getBRS_OutstandingPayments(monthfirst,banknameaccountno);
	}

//4th direct credit/debit
	@Override
	public List<Map<String, String>> getBRS_Direct_CD(String cheque_dd_receipt_no, Date bank_date, long amount,
			String transaction_type, String banknameaccountno, HttpServletRequest request) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String ip_address = request.getRemoteAddr();
		return Apshcl_Brs_ServiceRepo.getBRS_Direct_CD(cheque_dd_receipt_no, bank_date, amount, transaction_type,
				user_id, banknameaccountno, ip_address, security_id);
	}
//5th
	@Override
	public List<Map<String, String>> BRS_BankAcc_AsPerEntries(String fromDate, String toDate,
			String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		List<Map<String, String>> data = null;
		if (banknameaccountno.equals("0")) {
			data = Apshcl_Brs_ServiceRepo.BRS_BankAcc_AsPerEntries(fromDate, toDate);
		} else {
			data = Apshcl_Brs_ServiceRepo.BRS_BankAcc_AsPerEntries_bank(fromDate, toDate, banknameaccountno);
		}
		return data;
	}

//6th
	@Override
	public List<Map<String, String>> BankReconciliationStatement(String year, String month, String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String maxdate = "";
		String mindate = "1/" + month + "/" + year;
		if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08")
				|| month.equals("10") || month.equals("12")) {
			maxdate = "31/" + month + "/" + year;
		} else if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
			maxdate = "30/" + month + "/" + year;
		} else if (month.equals("02")) {
			maxdate = "28/" + month + "/" + year;
		}
//		int balance = Apshcl_Brs_ServiceRepo.getbalance(maxdate, security_id, banknameaccountno);
//		
//	    System.out.println("balance" + balance);
		int receiptamount = Apshcl_Brs_ServiceRepo.getreceiptamount(mindate, maxdate, security_id, banknameaccountno);
		int paymentamount = Apshcl_Brs_ServiceRepo.getpaymentamount(mindate, maxdate, security_id, banknameaccountno);
		int directdebit = Apshcl_Brs_ServiceRepo.getdirectdebit(mindate, maxdate, security_id, banknameaccountno);
		int directcredit = Apshcl_Brs_ServiceRepo.getdirectcredit(mindate, maxdate, security_id, banknameaccountno);
		int outreceiptamount = Apshcl_Brs_ServiceRepo.getoutreceiptamount(maxdate, security_id, banknameaccountno);
		int outpaymentamount = Apshcl_Brs_ServiceRepo.getoutpaymentamount(maxdate, security_id, banknameaccountno);
		List<Map<String, String>> result = new ArrayList<>();

		Map<String, String> dataMap = new HashMap<>();
//	    dataMap.put("balance", String.valueOf(balance));
		dataMap.put("receiptamount", String.valueOf(receiptamount));
		dataMap.put("paymentamount", String.valueOf(paymentamount));
		dataMap.put("directdebit", String.valueOf(directdebit));
		dataMap.put("directcredit", String.valueOf(directcredit));
		dataMap.put("outreceiptamount", String.valueOf(outreceiptamount));
		dataMap.put("outpaymentamount", String.valueOf(outpaymentamount));

		result.add(dataMap);

		return result;
	}
	///BRS OLD**********
	@Override
	public List<Map<String, String>> getBanks_Brs_Old() {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return Apshcl_Brs_ServiceRepo.getBanks_Brs_Old(user_id);
	}
	@Override
	public List<Map<String, String>> getBRS_BBU_Old(String year, String month, String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		return Apshcl_Brs_ServiceRepo.getBRS_BBU_Old(year, month, banknameaccountno);
	}
	// old bank book update,old outstanding payments, old outstanding receipts insertions are same api
	@Override
	public Map<String, Object> CheckInsert_Brs_Old(@RequestBody List<Brs_Request> brs_Request, HttpServletRequest request) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String ip_address = request.getRemoteAddr();
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int submit = 0;
		for (Brs_Request requestData : brs_Request) {

			String banknameaccountno = requestData.getBanknameaccountno();
			String payment_receipt_id = requestData.getPayment_receipt_id();
			String cheque_dd_receipt_no = requestData.getCheque_dd_receipt_no();
			Date bank_date = requestData.getBank_date();
			long amount = requestData.getAmount();
			String transaction_type = requestData.getTransaction_type();
			submit = Apshcl_Brs_ServiceRepo.CheckInsert_Brs_Old(payment_receipt_id, cheque_dd_receipt_no, bank_date, amount,
					transaction_type, banknameaccountno, user_id, security_id, ip_address);
		}

		if (submit != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Submitted Sucessfully");
		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an error");
		}

		return responseMap;

	}
	
	//old outstanding payments
	@Override
	public List<Map<String, String>> getBRS_OutstandingPayments_Old(String banknameaccountno) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		   Date myDate = new Date();
            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM");
            String dmy = dmyFormat.format(myDate);
            String monthfirst = String.valueOf(dmy) + "-01";
            System.out.println("monthfirst"+monthfirst);
            
		return Apshcl_Brs_ServiceRepo.getBRS_OutstandingPayments_Old(monthfirst,banknameaccountno);
	}
	//old outstanding receipts
		@Override
		public List<Map<String, String>> getBRS_OutstandingReceipts_Old(String banknameaccountno) {
			String user_id = CommonServiceImpl.getLoggedInUserId();
			System.out.println("------Userid" + user_id);
			   Date myDate = new Date();
	            SimpleDateFormat dmyFormat = new SimpleDateFormat("yyyy-MM");
	            String dmy = dmyFormat.format(myDate);
	            String monthfirst = String.valueOf(dmy) + "-01";
	            System.out.println("monthfirst"+monthfirst);
	            
			return Apshcl_Brs_ServiceRepo.getBRS_OutstandingReceipts_Old(monthfirst,banknameaccountno);
		}
		//old brs passbook report
		@Override
		public List<Map<String, String>> BRS_PassBookReport_Old(String fromDate, String toDate,
				String banknameaccountno) {
			String user_id = CommonServiceImpl.getLoggedInUserId();
			System.out.println("------Userid" + user_id);
			List<Map<String, String>> data = null;
			if (banknameaccountno.equals("0")) {
				data = Apshcl_Brs_ServiceRepo.BRS_PassBookReport_Old(fromDate, toDate);
			} else {
				data = Apshcl_Brs_ServiceRepo.BRS_PassBookReport_Old_bank(fromDate, toDate, banknameaccountno);
			}
			return data;
		}
		//old direct cd
		@Override
		public List<Map<String, String>> getBRS_Direct_CD_Old(String cheque_dd_receipt_no, Date bank_date, long amount,
				String transaction_type, String banknameaccountno, HttpServletRequest request) {
			String user_id = CommonServiceImpl.getLoggedInUserId();
			System.out.println("------Userid" + user_id);
			String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
			String ip_address = request.getRemoteAddr();
			return Apshcl_Brs_ServiceRepo.getBRS_Direct_CD_Old(cheque_dd_receipt_no, bank_date, amount, transaction_type,
					user_id, banknameaccountno, ip_address, security_id);
		}
}
