package in_Apcfss.Apofms.api.ofmsapi.servicesimpl;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.EntryForms_Repo;
import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.controllers.UserLoginController;
import in_Apcfss.Apofms.api.ofmsapi.request.BankDetails_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Dummy_journal_voucher_heads_Req;
import in_Apcfss.Apofms.api.ofmsapi.request.EmpdetailsNewDisp_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.EmpdetailsNewPromotions_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Empdetails_NewIncept_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.InterbankReq;
import in_Apcfss.Apofms.api.ofmsapi.request.List_PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.Payment_receipts_Req;
import in_Apcfss.Apofms.api.ofmsapi.request.PayrollReports_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.SubHeadMaster;
import in_Apcfss.Apofms.api.ofmsapi.request.UpdateDistrictTransferData;
import in_Apcfss.Apofms.api.ofmsapi.services.EntryForms_Service;

@Service
public class EntryForms_ServiceImpl implements EntryForms_Service {

	@Autowired
	EntryForms_Repo entryForms_Repo;

	@Autowired
	UsersSecurityRepo usersSecurityRepo;

	@Override
	public Map<String, Object> Bank_Details_Entry_Insertion(int accountid, String bankname, String branchname,
			String accountno, String operationaccount, String accounttype, String accountholder1, String accountholder2,
			double balance, String banknameaccountno, String accountholders, double remaining_balance,
			double available_balance, boolean is_approved_by_gm, String ifsc, String passbook_path, String ip_address) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		String msg = "";
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String dist_id = user_id.substring(user_id.length() - 2, user_id.length() - 0);
		String div_id = "";
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int DM_insertion = 0;
		int EE_insertion = 0;
		int Apshcl_insertion = 0;
		int appoveManager = 0;

		if (user_id.substring(0, 2).equals("DM") || user_id.substring(0, 2).equals("DH")) {
			accountholders = accountholder1 + "," + accountholder2;
			banknameaccountno = bankname + "-" + accountno;
			DM_insertion = entryForms_Repo.Post_DM_insertion(bankname, branchname, ifsc, accountno, operationaccount,
					accounttype, accountholder1, accountholder2, accountholders, balance, user_id, banknameaccountno,
					security_id, remaining_balance, passbook_path, dist_id, ip_address);
			System.out.println(DM_insertion + "DM_insertion......");

		}
		if (user_id.substring(0, 2).equals("EE")) {
			dist_id = user_id.substring(user_id.length() - 4, user_id.length() - 2);
			div_id = user_id.substring(user_id.length() - 2, user_id.length() - 0);
			accountholders = accountholder1 + "," + accountholder2;
			banknameaccountno = bankname + "-" + accountno;
			EE_insertion = entryForms_Repo.Post_EE_insertion(bankname, branchname, ifsc, accountno, operationaccount,
					accounttype, accountholder1, accountholder2, accountholders, balance, user_id, banknameaccountno,
					security_id, remaining_balance, div_id, passbook_path, dist_id);

			System.out.println(EE_insertion + "EE_insertion......");
		}

		if (user_id.equals("APSHCL")) {
			dist_id = "00";
			accountholders = accountholder1 + "," + accountholder2;
			banknameaccountno = bankname + "-" + accountno;
			Apshcl_insertion = entryForms_Repo.Post_Apshcl_insertion(bankname, branchname, ifsc, accountno,
					operationaccount, accounttype, accountholder1, accountholder2, accountholders, balance, user_id,
					banknameaccountno, security_id, remaining_balance, passbook_path, dist_id, ip_address);

			System.out.println(Apshcl_insertion + "Apshcl_insertion......");

		}
		if (security_id.startsWith("M1")) {
			accountholders = accountholder1 + "," + accountholder2;
			banknameaccountno = bankname + "-" + accountno;
			appoveManager = entryForms_Repo.updatemanager_status(accountid, bankname, branchname, ifsc, accountno,
					operationaccount, accounttype, accountholder1, accountholder2, accountholders, balance, user_id,
					banknameaccountno, remaining_balance, passbook_path, ip_address);
			System.out.println(appoveManager + "appoveManager_UPDATE......");
		}
		if (DM_insertion != 0 || EE_insertion != 0 || Apshcl_insertion != 0 || appoveManager != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Submitted Sucessfully");

		}

		return responseMap;
	}

	@Override
	public Map<String, Object> BankEntryMangerApproval(int accountid, String bankname, String branchname,
			String accountno, String operationaccount, String accounttype, String accountholder1, String accountholder2,
			double balance, String banknameaccountno, String accountholders, double remaining_balance, String ifsc,
			String passbook_path, String ip_address) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		String msg = "";
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String dist_id = user_id.substring(user_id.length() - 2, user_id.length() - 0);
		String div_id = "";
		int appoveManager = 0;
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		if (security_id.startsWith("M1")) {
			accountholders = accountholder1 + "," + accountholder2;
			banknameaccountno = bankname + "-" + accountno;
			appoveManager = entryForms_Repo.updatemanager_status(accountid, bankname, branchname, ifsc, accountno,
					operationaccount, accounttype, accountholder1, accountholder2, accountholders, balance, user_id,
					banknameaccountno, remaining_balance, passbook_path, ip_address);
			System.out.println(appoveManager + "appoveManager_UPDATE......");
		}
		if (appoveManager != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "EDit Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}

		return responseMap;
	}

	@Override
	public Map<String, Object> BankEntryMangerReject(String banknameaccountno) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int rejectManager = 0;
		if (security_id.startsWith("M1")) {

			rejectManager = entryForms_Repo.BankEntryMangerReject(banknameaccountno);
			System.out.println(rejectManager + "rejectManager......");
		}
		if (rejectManager != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "EDit Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}

		return responseMap;
	}

	@Override
	public List<Map<String, String>> GeneratePayment_Receipt_Id(String save_flag_Payment_Receipts_Journal) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		List<Map<String, String>> Payment_Receipt_Id = null;
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);

		if (save_flag_Payment_Receipts_Journal.equals("Receipt")) {
			System.out.println("save_flag_Payment_Receipts_Journal" + save_flag_Payment_Receipts_Journal);
			Payment_Receipt_Id = entryForms_Repo.getReceipt_Id(security_id);
		} else if (save_flag_Payment_Receipts_Journal.equals("Payment")) {
			System.out.println("save_flag_Payment_Receipts_Journal" + save_flag_Payment_Receipts_Journal);
			Payment_Receipt_Id = entryForms_Repo.getPayment_Id(security_id);

		} else if (save_flag_Payment_Receipts_Journal.equals("Journal")) {
			System.out.println("save_flag_Payment_Receipts_Journal" + save_flag_Payment_Receipts_Journal);
			Payment_Receipt_Id = entryForms_Repo.getJournal_Id(security_id);
		}

		System.out.println("Payment_Receipt_Id" + Payment_Receipt_Id);
		return Payment_Receipt_Id;
	}

	@Override
	public Map<String, Object> Generation_Receipt_Insertion(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double receiptamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("upload_copy" + upload_copy);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id:::::::" + security_id);

		System.out.println(security_id);
		int iterationNumber = 1; // Initialize the iteration number
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> responseMap = new LinkedHashMap<>();
		String ip_address = request.getRemoteAddr();
		int updatebankdetails = 0;
		// int deletedummy = 0;
		int dummy_head_entry = 0;
		int payments_receipts_subhead = 0;
//		int payments_receipts_incomplete = 0;

		int receipt_entry = 0;
		if (!security_id.startsWith("M")) {
			double accountbalance = balance_in_account;
			balance_in_account = remaining_balance + receiptamount;
			System.out.println("balance_in_account" + balance_in_account);
			System.out.println("remaining_balance" + remaining_balance);
			System.out.println("receiptamount" + receiptamount);
			System.out.println(" not called maanger approval");

			receipt_entry = entryForms_Repo.receipt_entry_insertion(payment_receipt_id, receiptno, date, cash_bank,
					banknameaccountno, receiptamount, balance_in_account, name, mode, cheque_dd_receipt_no,
					receipt_description, security_id, no_of_subheads, user_id, upload_copy, ip_address);
			System.out.println(receipt_entry + "---receipt_entry");

		}

		if (!security_id.startsWith("M")) {

			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {

				System.out.println("Iteration " + iterationNumber);
				dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_receipt(payment_receipt_id,
						headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), security_id, user_id,
						ip_address);

				System.out.println(dummy_head_entry + "------dummy_head_entry");

				payments_receipts_subhead = entryForms_Repo.receipts_subheads(payment_receipt_id, headDto.getHeadid(),
						headDto.getSubheadid(), headDto.getCredit(), security_id, user_id, ip_address);

				System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");

//				payments_receipts_incomplete = entryForms_Repo.payments_receipts_incomplete(payment_receipt_id,
//						headDto.getHeadid(), headDto.getSubheadid());
//
//				System.out.println(payments_receipts_incomplete + "------payments_receipts_incomplete");
				iterationNumber++;

			}

		}

		if (receipt_entry != 0) {

			System.out.println("balance_in_account" + balance_in_account);
			remaining_balance = balance_in_account;
			System.out.println("remaining_balance" + remaining_balance);

			if (security_id.equals("00") || security_id.equals("M1") || security_id.equals("M2")
					|| security_id.equals("M3") || security_id.equals("GM") || security_id.equals("SH")) {
				System.out.println("remaining_balance" + remaining_balance);
				updatebankdetails = entryForms_Repo.updatebankdetails(remaining_balance, banknameaccountno);

				System.out.println("updatebankdetails if::::" + updatebankdetails);
			} else {

				updatebankdetails = entryForms_Repo.updatebankdetailssec(remaining_balance, banknameaccountno,
						security_id);

				System.out.println("updatebankdetails else::::" + updatebankdetails);

			}
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Submitted Sucessfully");

		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}

		return responseMap;
	}

	public Map<String, Object> Generation_Receipt_Managerapproval(String payment_receipt_id, String receiptno,
			Date date, String cash_bank, double remaining_balance, String banknameaccountno, double receiptamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads, int olderreceiptamount,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("upload_copy" + upload_copy);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id:::::::" + security_id);

		System.out.println(security_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> responseMap = new LinkedHashMap<>();
		String ip_address = request.getRemoteAddr();
		int updatebankdetails = 0;

		int dummy_head_entry = 0;
		int payments_receipts_subhead = 0;

		int managerEdit = 0;

		if (security_id.startsWith("M")) {
			System.out.println("called maanger approval");
			System.out.println("balance_in_account before" + balance_in_account);
			balance_in_account = (balance_in_account - olderreceiptamount) + receiptamount;
			System.out.println("balance_in_account after" + balance_in_account);
			System.out.println("receiptamount" + receiptamount);
			System.out.println("olderreceiptamount" + olderreceiptamount);
			managerEdit = entryForms_Repo.receiptentryManagerEdit(payment_receipt_id, receiptno, date, cash_bank,
					banknameaccountno, receiptamount, balance_in_account, name, mode, cheque_dd_receipt_no,
					receipt_description, security_id, upload_copy, ip_address, user_id);
			System.out.println("managerEdit" + managerEdit);

		}
		if (security_id.startsWith("M")) {
			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {
				System.out.println("payment_receipt_id" + payment_receipt_id);
				System.out.println("headDto.getHeadid()" + headDto.getHeadid());
				System.out.println("headDto.getSubheadid()" + headDto.getSubheadid());
				System.out.println(" security_id" + security_id);
				int count = entryForms_Repo.getcountof_gr(payment_receipt_id, headDto.getJvslno());
				if (count == 0) {
					dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_receipt_Man_insert(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), security_id, user_id,
							ip_address);
					System.out.println(dummy_head_entry + "------dummy_head_entry managerEdit");

					payments_receipts_subhead = entryForms_Repo.receipts_subheads_man_insert(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), security_id, user_id,
							ip_address);
					System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");
				}
				if (count != 0) {

					dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_receipt_Man(payment_receipt_id,
							headDto.getJvslno(), headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(),
							security_id, user_id, ip_address);
					System.out.println(dummy_head_entry + "------dummy_head_entry managerEdit");

					payments_receipts_subhead = entryForms_Repo.receipts_subheads_man(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), security_id, user_id,
							ip_address);
					System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");

				}

			}
		}
		if (managerEdit != 0) {

//			balance_in_account=(balance_in_account-olderreceiptamount)+receiptamount;
			remaining_balance = (remaining_balance - olderreceiptamount) + receiptamount;
			if (security_id.equals("00") || security_id.equals("M1") || security_id.equals("M2")
					|| security_id.equals("M3") || security_id.equals("GM") || security_id.equals("SH")) {

				updatebankdetails = entryForms_Repo.updatebankdetails(remaining_balance, banknameaccountno);

				System.out.println("updatebankdetails if::::" + updatebankdetails);
			} else {

				updatebankdetails = entryForms_Repo.updatebankdetailssec(remaining_balance, banknameaccountno,
						security_id);

				System.out.println("updatebankdetails else::::" + updatebankdetails);

			}
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Submitted Sucessfully");

		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}

		return responseMap;
	}

	public Map<String, Object> Generation_Receipt_Managerreject(String payment_receipt_id) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int payment_receipts_reject = 0;
		int payment_receipts_subheads_reject = 0;
		int dummyvocuherheads_reject = 0;
		int bankDetailsUpdate = 0;
		BigDecimal paymentAmount = null;
		String banknameaccountno = null;
		BigDecimal receiptAmount = null;
		int UpdateRemainingBal = 0;
		List<Object[]> paymentInfo = entryForms_Repo.getReceiptAmountbyPayment_Receipt_Id(payment_receipt_id);
		for (Object[] row : paymentInfo) {
			paymentAmount = (BigDecimal) row[0];
			banknameaccountno = (String) row[1];
			receiptAmount = (BigDecimal) row[2];

		}
		System.out.println("banknameaccountno" + banknameaccountno);
		BigDecimal remaining_balance = entryForms_Repo.getremainingbalanceBAccount(banknameaccountno);
		System.out.println("remin" + remaining_balance);

		if (security_id.startsWith("M1") || security_id.startsWith("GM")) {

			payment_receipts_reject = entryForms_Repo.payment_receipts_reject(payment_receipt_id);

			System.out.println(payment_receipts_reject + "rejectManager......");
			payment_receipts_subheads_reject = entryForms_Repo.payment_receipts_subheads_reject(payment_receipt_id);

			System.out.println(payment_receipts_subheads_reject + "rejectManager......");
			dummyvocuherheads_reject = entryForms_Repo.dummyvocuherheads_reject(payment_receipt_id);

			System.out.println(dummyvocuherheads_reject + "rejectManager......");

			if (payment_receipt_id.startsWith("P")) {
				System.out.println("reamaining_balance after begfore" + remaining_balance);
				System.out.println("paymentAmount after update" + paymentAmount);
				BigDecimal remaining_balance_new = remaining_balance.add(paymentAmount);
				System.out.println("reamaining_balance after update" + remaining_balance);
				UpdateRemainingBal = entryForms_Repo.UpdateRemainingBal(banknameaccountno, remaining_balance,
						remaining_balance_new);

			} else if (payment_receipt_id.startsWith("R")) {
				System.out.println("reamaining_balance after begfore" + remaining_balance);
				System.out.println("paymentAmount after update" + paymentAmount);
				BigDecimal remaining_balance_new = remaining_balance.subtract(receiptAmount);
				System.out.println("reamaining_balance after update" + remaining_balance);
				UpdateRemainingBal = entryForms_Repo.UpdateRemainingBal(banknameaccountno, remaining_balance,
						remaining_balance_new);

			}
		}
		if (payment_receipts_reject != 0 && payment_receipts_subheads_reject != 0 && UpdateRemainingBal != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Deleted Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}

		return responseMap;
	}

	public Map<String, Object> Payment_Receipts_ManagerRemoveRow(String payment_receipt_id, String headid,
			String subheadid) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int payment_receipts_subheads_rowdelete = 0;
		int dummyvocuherheads_subheads_rowdelete = 0;
		if (security_id.startsWith("M1")) {

			payment_receipts_subheads_rowdelete = entryForms_Repo
					.payment_receipts_subheads_rowdelete(payment_receipt_id, headid, subheadid);

			System.out.println(payment_receipts_subheads_rowdelete + "rejectManager......");

			dummyvocuherheads_subheads_rowdelete = entryForms_Repo
					.dummyvocuherheads_subheads_rowdelete(payment_receipt_id, headid, subheadid);

			System.out.println(dummyvocuherheads_subheads_rowdelete + "rejectManager......");

		}
		if (payment_receipts_subheads_rowdelete != 0 && dummyvocuherheads_subheads_rowdelete != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Deleted Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}

		return responseMap;
	}

	public Map<String, Object> Generation_Payment_Insertion(String payment_receipt_id, String receiptno, Date date,
			String cash_bank, double remaining_balance, String banknameaccountno, double paymentamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("upload_copy" + upload_copy);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);

		Map<String, Object> responseMap = new LinkedHashMap<>();
		String ip_address = request.getRemoteAddr();
		int dummy_head_entry = 0;
		int payments_receipts_subhead = 0;
//		int payments_receipts_incomplete = 0;
		int updatebankdetails = 0;
//		int deletedummy = 0;
		balance_in_account = remaining_balance - paymentamount;
		System.out.println("balance_in_account" + balance_in_account);
		System.out.println("remaining_balance" + remaining_balance);
		System.out.println("paymentamount" + paymentamount);

		int payment_entry = 0;
		if (!security_id.startsWith("M")) {
			payment_entry = entryForms_Repo.payment_entry_insertion(payment_receipt_id, receiptno, date, cash_bank,
					banknameaccountno, paymentamount, balance_in_account, name, mode, cheque_dd_receipt_no,
					receipt_description, security_id, no_of_subheads, user_id, upload_copy, ip_address);
		}
		System.out.println(payment_entry + "---receipt_entry");
		if (!security_id.startsWith("M")) {
			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {

				dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_payment(payment_receipt_id,
						headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), security_id, user_id,
						ip_address);

				System.out.println(dummy_head_entry + "------dummy_head_entry");

				payments_receipts_subhead = entryForms_Repo.payments_subheads(payment_receipt_id, headDto.getHeadid(),
						headDto.getSubheadid(), headDto.getDebit(), security_id, user_id, ip_address);

				System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");

//				payments_receipts_incomplete = entryForms_Repo.payments_receipts_incomplete(payment_receipt_id,
//						headDto.getHeadid(), headDto.getSubheadid());
//
//				System.out.println(payments_receipts_incomplete + "------payments_receipts_incomplete");
			}
		}

		if (payment_entry != 0) {

			System.out.println("balance_in_account" + balance_in_account);
			System.out.println("paymentamount" + paymentamount);
			remaining_balance = balance_in_account;
			System.out.println("remaining_balance" + remaining_balance);
			if (security_id.equals("00") || security_id.equals("M1") || security_id.equals("M2")
					|| security_id.equals("M3") || security_id.equals("GM") || security_id.equals("SH")) {

				updatebankdetails = entryForms_Repo.updatebankdetails(remaining_balance, banknameaccountno);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Submitted Sucessfully");

				System.out.println("updatebankdetails if::::" + updatebankdetails);
			} else {

				updatebankdetails = entryForms_Repo.updatebankdetailssec(remaining_balance, banknameaccountno,
						security_id);

				System.out.println("updatebankdetails else::::" + updatebankdetails);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Submitted Sucessfully");
			}
		}

		return responseMap;
	}

	public Map<String, Object> Generation_Payment_Managerapproval(String payment_receipt_id, String receiptno,
			Date date, String cash_bank, double remaining_balance, String banknameaccountno, double paymentamount,
			double balance_in_account, String name, String mode, String cheque_dd_receipt_no,
			String receipt_description, String no_of_subheads, int oldpaymentamount,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, String upload_copy,
			HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		System.out.println("upload_copy" + upload_copy);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);

		Map<String, Object> responseMap = new LinkedHashMap<>();
		String ip_address = request.getRemoteAddr();
		int dummy_head_entry = 0;
		int payments_receipts_subhead = 0;
//		int payments_receipts_incomplete = 0;
		int updatebankdetails = 0;
//		int deletedummy = 0;

		int managerEdit = 0;

		if (security_id.startsWith("M")) {
			System.out.println("called payemnt maanger approval");
			System.out.println("balance_in_account before" + balance_in_account);
			balance_in_account = (balance_in_account + oldpaymentamount) - paymentamount;
			System.out.println("balance_in_account after" + balance_in_account);
			System.out.println("receiptamount" + paymentamount);
			System.out.println("olderoldpaymentamount" + oldpaymentamount);
			managerEdit = entryForms_Repo.paymententryManagerEdit(payment_receipt_id, receiptno, date, cash_bank,
					banknameaccountno, paymentamount, balance_in_account, name, mode, cheque_dd_receipt_no,
					receipt_description, security_id, upload_copy, ip_address);
			System.out.println("managerEdit" + managerEdit);

		}
		if (security_id.startsWith("M")) {
			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {
				int count = entryForms_Repo.getcountof_gp(payment_receipt_id, headDto.getJvslno());
				System.out.println("count" + count);
				if (count == 0) {
					dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_payment_Man_insert(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), security_id, user_id,
							ip_address);
					System.out.println(dummy_head_entry + "------dummy_head_entry managerEdit");

					payments_receipts_subhead = entryForms_Repo.payments_subheads_man_insert(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), security_id, user_id,
							ip_address);
					System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");

				} else {
					dummy_head_entry = entryForms_Repo.dummy_head_entry_insertion_payment_Man(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), security_id, user_id,
							ip_address, headDto.getJvslno());
					System.out.println(dummy_head_entry + "------dummy_head_entry managerEdit");

					payments_receipts_subhead = entryForms_Repo.payments_subheads_man(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), security_id, user_id,
							ip_address);
					System.out.println(payments_receipts_subhead + "------payments_receipts_subhead");
				}

			}

		}
		if (managerEdit != 0) {

			System.out.println("remaining_balance" + remaining_balance);
			System.out.println("paymentamount" + paymentamount);
			remaining_balance = (remaining_balance + oldpaymentamount) - paymentamount;
			System.out.println("remaining_balance" + remaining_balance);
			if (security_id.equals("00") || security_id.equals("M1") || security_id.equals("M2")
					|| security_id.equals("M3") || security_id.equals("GM") || security_id.equals("SH")) {

				updatebankdetails = entryForms_Repo.updatebankdetails(remaining_balance, banknameaccountno);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Submitted Sucessfully");

				System.out.println("updatebankdetails if::::" + updatebankdetails);
			} else {

				updatebankdetails = entryForms_Repo.updatebankdetailssec(remaining_balance, banknameaccountno,
						security_id);

				System.out.println("updatebankdetails else::::" + updatebankdetails);
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Data Submitted Sucessfully");
			}
		}

		return responseMap;
	}

	public Map<String, Object> Generation_Journal_Insertion(String payment_receipt_id, String voucher_id, Date date,
			String description, double credit, double debit, String upload_copy,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String ip_address = request.getRemoteAddr();
		System.out.println(security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();

		int journal_voucher_heads = 0;
//		int jv_incomplete = 0;
		int dummy_journal_voucher_heads = 0;
		int journal_entry = 0;
		System.out.println("upload_copy" + upload_copy);

		int countof_paymentreceipt_id = entryForms_Repo.getcountof_paymentreceipt_id(payment_receipt_id);
		System.out.println("countof_paymentreceipt_id" + countof_paymentreceipt_id);
		if (countof_paymentreceipt_id == 0) {
			System.out.println("called");
			journal_entry = entryForms_Repo.journal_entry_insertion(payment_receipt_id, date, voucher_id, description,
					credit, debit, upload_copy, security_id, ip_address, user_id);

			System.out.println(journal_entry + "---journal_entry");
			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {

				journal_voucher_heads = entryForms_Repo.journal_voucher_heads(payment_receipt_id, headDto.getHeadid(),
						headDto.getSubheadid(), headDto.getCredit(), headDto.getDebit(), security_id, user_id,
						ip_address);
				System.out.println(journal_voucher_heads + "------journal_voucher_heads");

			}

			if (journal_voucher_heads != 0 && journal_entry != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Sucessfully Saved");
			} else {
				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");
			}

		}

		if (security_id.equals("00") && countof_paymentreceipt_id != 0) {
			journal_entry = entryForms_Repo.journal_entry_update_apshcl(payment_receipt_id, date, voucher_id,
					description, credit, debit, upload_copy);

			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {
				int count = entryForms_Repo.getcountofjv(payment_receipt_id, headDto.getJvslno());
				System.out.println("count" + count);
				if (count == 0) {
					journal_voucher_heads = entryForms_Repo.journal_voucher_heads_insert_heads(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), headDto.getDebit(),
							security_id, ip_address, user_id);
					System.out.println(journal_voucher_heads + "------journal_voucher_heads");
				} else {
					journal_voucher_heads = entryForms_Repo.journal_voucher_heads_update_heads(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), headDto.getCredit(),
							security_id, headDto.getJvslno(), user_id, ip_address);
					System.out.println(journal_voucher_heads + "------journal_voucher_heads");
//					jv_incomplete = entryForms_Repo.jv_incomplete_manager(payment_receipt_id, headDto.getHeadid());
//					System.out.println(jv_incomplete + "------jv_incomplete");
//					dummy_journal_voucher_heads = entryForms_Repo.dummy_journal_voucher_heads_manager(payment_receipt_id,
//							headDto.getHeadid());
//					System.out.println(dummy_journal_voucher_heads + "------dummy_journal_voucher_heads");
				}

			}

			System.out.println(journal_entry + "---journal_entry");
			if (journal_entry != 0 || dummy_journal_voucher_heads != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Sucessfully Saved");

			} else {

				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");

			}

		}

		return responseMap;
	}

	public Map<String, Object> Generation_Journal_Managerapproval(String payment_receipt_id, String voucher_id,
			Date date, String description, double credit, double debit, String upload_copy,
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req, HttpServletRequest request) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String ip_address = request.getRemoteAddr();
		System.out.println(security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();

		int journal_voucher_heads = 0;

		int dummy_journal_voucher_heads = 0;
		int journal_entry = 0;
		System.out.println("upload_copy" + upload_copy);

		if (security_id.startsWith("M")) {
			journal_entry = entryForms_Repo.journal_entry_update_insert(payment_receipt_id, date, voucher_id,
					description, credit, debit, upload_copy);

			for (Dummy_journal_voucher_heads_Req headDto : dummy_journal_voucher_heads_Req) {
				int count = entryForms_Repo.getcountofjv(payment_receipt_id, headDto.getJvslno());
				System.out.println("count" + count);
				if (count == 0) {
					journal_voucher_heads = entryForms_Repo.journal_voucher_headsmanagetinsert(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getCredit(), headDto.getDebit(),
							security_id, user_id, ip_address);
					System.out.println(journal_voucher_heads + "------journal_voucher_heads");
				} else {
					journal_voucher_heads = entryForms_Repo.journal_voucher_heads_manager(payment_receipt_id,
							headDto.getHeadid(), headDto.getSubheadid(), headDto.getDebit(), headDto.getCredit(),
							security_id, headDto.getJvslno(), user_id, ip_address);
					System.out.println(journal_voucher_heads + "------journal_voucher_heads");

				}

			}

			System.out.println(journal_entry + "---journal_entry");
			if (journal_entry != 0 || dummy_journal_voucher_heads != 0) {
				responseMap.put("SCODE", "01");
				responseMap.put("SDESC", "Sucessfully Saved");

			} else {

				responseMap.put("SCODE", "02");
				responseMap.put("SDESC", "Having an Error");

			}

		}

		return responseMap;
	}

	public Map<String, Object> Generation_Journal_reject(String payment_receipt_id) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int journal_vocuher_reject = 0;
		int journal_voucher_heads_reject = 0;
//		int dummyvocuherheads_reject = 0;
		if (security_id.startsWith("M1") || security_id.startsWith("GM")) {

			journal_vocuher_reject = entryForms_Repo.journal_voucher_heads_reject(payment_receipt_id);

			System.out.println(journal_vocuher_reject + "rejectManager......");

			journal_vocuher_reject = entryForms_Repo.journal_vocuher_reject(payment_receipt_id);

			System.out.println(journal_vocuher_reject + "rejectManager......");

		}
		if (journal_vocuher_reject != 0 && journal_vocuher_reject != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Deleted Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}

		return responseMap;
	}

	public Map<String, Object> Generation_Journal_RowDelete(String payment_receipt_id, String headid, String subheadid,
			long credit, long debit) {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + user_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("credit" + credit + "debit" + debit + "payment_receipt_id" + payment_receipt_id);
		int journal_vocuherheadsRowDelete = entryForms_Repo.Generation_Journal_RowDelete(payment_receipt_id, headid,
				subheadid, security_id);
		int journal_vocuherupdateRowDelete = entryForms_Repo.journal_vocuherupdateRowDelete(credit, debit,
				payment_receipt_id, security_id);
		if (journal_vocuherheadsRowDelete != 0 && journal_vocuherupdateRowDelete != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Deleted Submitted Sucessfully");

		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an eror");

		}
		return responseMap;
	}

	// **************************Cadre Wise Employees*************************
	@Override
	public List<Map<String, String>> getCadreWiseData() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String dist_code = user_id.substring(2, 4);
		System.out.println("dist_code :::" + dist_code);
		return entryForms_Repo.getCadreWiseData(dist_code);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetailsByEmpID(String empid) {

		return entryForms_Repo.getCadreWiseDetailsByEmpID(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetailsByEmpID_SubsequentPromotions(String empid) {

		return entryForms_Repo.getCadreWiseDetailsByEmpID_SubsequentPromotions(empid);
	}

	@Override
	public List<Map<String, String>> getDisExp_PresentConditon(String empid) {

		return entryForms_Repo.getDisExp_PresentConditon(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetailsByEmpID_POWI(String empid) {

		return entryForms_Repo.getCadreWiseDetailsByEmpID_POWI(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID(String empid) {
		return entryForms_Repo.getCadreWiseDetails_Edit_ByEmpID(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(String empid) {
		return entryForms_Repo.getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_POWI(String empid) {
		return entryForms_Repo.getCadreWiseDetails_Edit_ByEmpID_POWI(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_Disp(String empid) {
		return entryForms_Repo.getCadreWiseDetails_Edit_ByEmpID_Disp(empid);
	}

	@Override
	public List<Map<String, String>> getCadreWiseCasteDropdown() {

		return entryForms_Repo.getCadreWiseCasteDropdown();
	}

	@Override
	public List<Map<String, String>> getCadreWiseDesignationDropdown() {

		return entryForms_Repo.getCadreWiseDesignationDropdown();
	}

	@Override
	public List<Map<String, String>> getCadreWiseDistDivManDropdowns(int dist_code, int div_code, int sdiv_code) {
		List<Map<String, String>> dropdowns = null;
		if (dist_code == 0) {
			dropdowns = entryForms_Repo.getCadreWiseDistDropdown();
		} else if (dist_code != 0 && div_code == 0) {
			dropdowns = entryForms_Repo.getCadreWiseDivDropdown(dist_code);
		} else if (dist_code != 0 && div_code != 0 && sdiv_code == 0) {
			dropdowns = entryForms_Repo.getCadreWiseSubDivDropdown(dist_code, div_code);
		} else if (dist_code != 0 && div_code != 0 && sdiv_code != 0) {
			dropdowns = entryForms_Repo.getCadreWiseMandalDropdown(dist_code, div_code, sdiv_code);
		}

		return dropdowns;
	}

	@Override
	public List<Map<String, String>> getCadreWiseWorkConditionDropdown() {

		return entryForms_Repo.getCadreWiseWorkConditionDropdown();
	}

	@Override
	public List<Map<String, String>> getCadreWiseQualificationDropdown() {

		return entryForms_Repo.getCadreWiseQualificationDropdown();
	}

	@Override
	public List<Map<String, String>> getCadreWiseAppointmentDropdown() {

		return entryForms_Repo.getCadreWiseAppointmentDropdown();
	}

	public List<Map<String, Object>> insertcadre(String empid, String empname, String empfather, int designation,
			int caste, String dob, String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal,
			String workcond, int natdist, int natdiv, int natsdiv, int natmandal, String otherwork, String othercase,
			String otherworkarea, String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint,
			String cadrerefno, String cadrerefdate, String empaadhar, String emppan, String emppfuno,
			String prdiv_other, String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications) {

		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		int update_empdetails_new = entryForms_Repo.update_empdetails_new(empid, empname, empfather, designation, caste,
				dob, doj, dor, prdist, prdiv, prsdiv, prmandal, workcond, natdist, natdiv, natsdiv, natmandal,
				otherwork, othercase, otherworkarea, dojpresent, eduappoint, eduafterappoint, cadreappoint, cadrerefno,
				cadrerefdate, empaadhar, emppan, emppfuno, prdiv_other, prsubdiv_other, prmandal_other, natdist_other,
				natdiv_other, natsdiv_other, natmandal_other, other_qualifications);

		if (update_empdetails_new > 0) {

			map.put("responseToken", 1);
			map.put("responseMsg", "Data Updated Successfully");
			map.put("respose", update_empdetails_new);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", "Data not Updated for the requested PRO number");

		}

		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> Edit_Subsequent_Promotion(
			List<EmpdetailsNewPromotions_Request> empdetails_new_promotions) {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		for (EmpdetailsNewPromotions_Request sp_list_update : empdetails_new_promotions) {

			String empid = sp_list_update.getEmpid();
			String empname = sp_list_update.getEmpname();
			String fromcadre = sp_list_update.getFromcadre();
			String fromdate = sp_list_update.getFromdate();
			String fromref = sp_list_update.getFromref();
			String tocadre = sp_list_update.getTocadre();
			String todate = sp_list_update.getTodate();
			String toref = sp_list_update.getToref();
			count = entryForms_Repo.Edit_Subsequent_Promotion(empid, empname, fromcadre, fromdate, fromref, tocadre,
					todate, toref);
			System.out.println("count" + count);
			count = empdetails_new_promotions.size();
			System.out.println("empdetails_new_promotions.size()" + empdetails_new_promotions.size());

		}
		if (count > 0) {

			map.put("responseToken", 1);
			map.put("responseMsg", count + " : Subsequent_Promotion Added");
			map.put("respose", count);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", count + "Subsequent_Promotion are not added");

		}

		respose.add(map);
		return respose;

	}

	@Override
	public List<Map<String, Object>> Edit_Placeofworkingfrominception_Add(
			List<Empdetails_NewIncept_Request> empdetails_new_incept) {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		for (Empdetails_NewIncept_Request pow_list_update : empdetails_new_incept) {

			String empid = pow_list_update.getEmpid();
			String empname = pow_list_update.getEmpname();
			String incdist = pow_list_update.getIncdist();
			String incmandal = pow_list_update.getIncmandal();
			String incdesign = pow_list_update.getIncdesign();
			String incfrom = pow_list_update.getIncfrom();
			String incto = pow_list_update.getIncto();
			String incemptype = pow_list_update.getIncemptype();
			String incdist_other = pow_list_update.getIncdist_other();
			String incmandal_other = pow_list_update.getIncmandal_other();
			String incdiv = pow_list_update.getIncdiv();
			String incsdiv = pow_list_update.getIncsdiv();

			count = entryForms_Repo.Edit_Placeofworkingfrominception_Add(empid, empname, incdist, incmandal, incdesign,
					incfrom, incto, incemptype, incdist_other, incmandal_other, incdiv, incsdiv);
			count = empdetails_new_incept.size();

			System.out.println("empdetails_new_incept.size()" + empdetails_new_incept.size());

		}
		if (count > 0) {

			map.put("responseToken", 1);
			map.put("responseMsg", count + " : Edit_Place of working from inception was  Added");
			map.put("respose", count);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", count + "Edit_Place of working from inception was not  Added");

		}

		respose.add(map);
		return respose;

	}

	@Override
	public List<Map<String, Object>> Edit_Other_Basic_Data_Add(List<EmpdetailsNewDisp_Request> empdetailsNewDisp) {

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int count = 0;
		for (EmpdetailsNewDisp_Request pow_list_update : empdetailsNewDisp) {

			String empid = pow_list_update.getEmpid();
			String empname = pow_list_update.getEmpname();
			String displanary = pow_list_update.getDisplanary();
			String discase = pow_list_update.getDiscase();

			count = entryForms_Repo.Edit_Other_Basic_Data_Add(empid, empname, displanary, discase);
			count = empdetailsNewDisp.size();
			System.out.println("empdetails_new_promotions.size()" + empdetailsNewDisp.size());
		}
		if (count > 0) {

			map.put("responseToken", 1);
			map.put("responseMsg", count + " :  Other Basic Data was  Added");
			map.put("respose", count);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", count + " Other Basic Data was not  Added");

		}

		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> deleteInceptionDetails(String empid, String ip_address, int sno) {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		int insert_empdetails_new_incept_deleted = entryForms_Repo.empdetails_new_incept_deleted(empid, ip_address,
				user_id, sno);
		int delete = entryForms_Repo.delete_empdetails_new_incept(sno, empid);
		if (insert_empdetails_new_incept_deleted > 0 && delete > 0 && insert_empdetails_new_incept_deleted == delete) {

			map.put("responseToken", 1);
			map.put("responseMsg", delete + " :  deleteInceptionDetails _deleted");
			map.put("respose", delete);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", delete + " deleteInceptionDetailsnot  deleted");

		}

		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> deletePromotionsDetails(String empid, String ip_address, int sno) {
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		int insert_empdetails_new_promotions_deleted = entryForms_Repo.insert_empdetails_new_promotions_deleted(empid,
				ip_address, user_id, sno);
		int delete = entryForms_Repo.empdetails_new_promotions(sno, empid);
		if (insert_empdetails_new_promotions_deleted > 0 && delete > 0
				&& insert_empdetails_new_promotions_deleted == delete) {

			map.put("responseToken", 1);
			map.put("responseMsg", delete + " :  deletePromotionsDetails _deleted");
			map.put("respose", delete);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", delete + " deletePromotionsDetails was not  delete");

		}

		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> deletedisciplinaryDetails(String empid, String ip_address, int sno) {

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		int insert_empdetails_new_disp_deleted = entryForms_Repo.insert_empdetails_new_disp_deleted(empid, ip_address,
				user_id, sno);
		int delete = entryForms_Repo.empdetails_new_disp(sno, empid);
		if (insert_empdetails_new_disp_deleted > 0 && delete > 0 && insert_empdetails_new_disp_deleted == delete) {

			map.put("responseToken", 1);
			map.put("responseMsg", delete + " :  deletedisciplinaryDetails _deleted");
			map.put("respose", delete);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", delete + " deletedisciplinaryDetails was not  delete");

		}

		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> Add_button_insert_cadreWise(String empname, String empfather, int designation,
			int caste, String dob, String doj, String dor, int prdist, int prdiv, int prsdiv, int prmandal,
			String workcond, int natdist, int natdiv, int natsdiv, int natmandal, String otherwork, String othercase,
			String otherworkarea, String dojpresent, String eduappoint, String eduafterappoint, String cadreappoint,
			String cadrerefno, String cadrerefdate, String empaadhar, String emppan, String emppfuno,
			String prdiv_other, String prsubdiv_other, String prmandal_other, String natdist_other, String natdiv_other,
			String natsdiv_other, String natmandal_other, String other_qualifications, String no_of_the_bank,
			String account_no, String ifsc_code) {

		final String userid = CommonServiceImpl.getLoggedInUserId();
		System.out.println("### userid: " + userid);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		String max_emp_id = entryForms_Repo.get_emp_id();
		System.out.println("new emp_id" + max_emp_id);
		int update_empdetails_new = entryForms_Repo.Add_button_insert_cadreWise(max_emp_id, empname, empfather,
				designation, caste, dob, doj, dor, prdist, prdiv, prsdiv, prmandal, workcond, natdist, natdiv, natsdiv,
				natmandal, otherwork, othercase, otherworkarea, dojpresent, eduappoint, eduafterappoint, cadreappoint,
				cadrerefno, cadrerefdate, empaadhar, emppan, emppfuno, prdiv_other, prsubdiv_other, prmandal_other,
				natdist_other, natdiv_other, natsdiv_other, natmandal_other, other_qualifications, no_of_the_bank,
				account_no, ifsc_code);

		if (update_empdetails_new > 0) {

			map.put("responseToken", 1);
			map.put("responseMsg", "Employee Details are Added Successfully ");
			map.put("respose", update_empdetails_new);

		} else {

			map.put("responseToken", 0);
			map.put("responseMsg", "Employee Details are  Not Added Successfully ");

		}

		respose.add(map);
		return respose;
	}

	public String AddNewEmpid() {
		return entryForms_Repo.AddNewEmpid();

	}
//	@Override
//	public Map<String, Object> InterBankTransferInsertion(String payment_id, String receipt_id, String receiptno,
//			Date date, String banknameaccountno, String banknameaccountno1, long balance_in_account,
//			long balance_in_account1, long paymentamount, long receiptamount, String mode, String cheque_dd_receipt_no,
//			String receipt_description) {
//		final String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("Logged User Id is :::" + user_id);
//
//		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//		System.out.println(security_id);
//		int receipt_entry = 0;
//		int payment_entry = 0;
//		int bank_entry = 0;
//		int insertrelation = 0;
//		Map<String, Object> responseMap = new LinkedHashMap<>();
//		System.out.println("payment_id" + payment_id);
//		if (!payment_id.equals("")) {
//			System.out.println("payment_id in if" + payment_id);
//
//			payment_entry = entryForms_Repo.InterBankTransferInsertion_Payment(payment_id, receiptno, date,
//					banknameaccountno, balance_in_account, paymentamount, mode, cheque_dd_receipt_no,
//					receipt_description, security_id, user_id);
//			double frombankbalancedouble = (balance_in_account) - (paymentamount);
//			System.out.println("frombankbalancedouble" + frombankbalancedouble);
//			bank_entry = entryForms_Repo.InterBankTransferInsertion_bank(frombankbalancedouble, banknameaccountno);
//			System.out.println(bank_entry + "------bank_entry");
//		}
//		if (!receipt_id.equals("")) {
//			System.out.println("receipt_id" + receipt_id);
//
//			receipt_entry = entryForms_Repo.InterBankTransferInsertion_Receipt(receipt_id, receiptno, date,
//					banknameaccountno1, balance_in_account1, receiptamount, mode, cheque_dd_receipt_no,
//					receipt_description, security_id, user_id);
//			double frombankbalancedouble = (balance_in_account1) + (receiptamount);
//			insertrelation = entryForms_Repo.insertrelation(payment_id, receipt_id);
//			System.out.println("frombankbalancedouble" + frombankbalancedouble);
//			bank_entry = entryForms_Repo.InterBankTransferInsertion_bank1(frombankbalancedouble, banknameaccountno1);
//			System.out.println(bank_entry + "------bank_entry");
//		}
//
//		if (payment_entry != 0 && receipt_entry != 0 && insertrelation != 0 && bank_entry != 0) {
//			responseMap.put("SCODE", "01");
//			responseMap.put("SDESC", "Data Submitted Sucessfully");
//		} else {
//
//			responseMap.put("SCODE", "02");
//			responseMap.put("SDESC", " Some Error occured while performing your request ");
//		}
//		return responseMap;
//	}

	@Override
	public Map<String, Object> InterBankTransferInsertion(String payment_id, String receiptno, Date date,
			String banknameaccountno, double balance_in_account, double paymentamount, String mode,
			String cheque_dd_receipt_no, String receipt_description, int no_of_banks, String upload_copy,
			List<InterbankReq> interbankreq) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		int receipt_entry = 0;
		int payment_entry = 0;
		int bank_entry = 0;
		int insertrelation = 0;
		int count = 0;
		Map<String, Object> responseMap = new LinkedHashMap<>();
		System.out.println("payment_id" + payment_id);
		if (!payment_id.equals("")) {
			System.out.println("payment_id in if" + payment_id);
			System.out.println("balance_in_account in payment" + balance_in_account);
			payment_entry = entryForms_Repo.InterBankTransferInsertion_Payment(payment_id, receiptno, date,
					banknameaccountno, balance_in_account, paymentamount, mode, cheque_dd_receipt_no,
					receipt_description, security_id, user_id, upload_copy);
			double frombankbalancedouble = (balance_in_account) - (paymentamount);
			System.out.println("frombankbalancedouble" + frombankbalancedouble);
			bank_entry = entryForms_Repo.InterBankTransferInsertion_bank(frombankbalancedouble, banknameaccountno);
			System.out.println(bank_entry + "------bank_entry");
		}
		System.out.println(no_of_banks + "------no_of_banks");

		for (InterbankReq list_bank : interbankreq) {

			String receipt_id = list_bank.getReceipt_id();
			String banknameaccountno1 = list_bank.getBanknameaccountno1();
			long balance_in_account1 = list_bank.getBalance_in_account1();
			long receiptamount = list_bank.getReceiptamount();
			System.out.println("receipt_id in receipt" + receipt_id);
			System.out.println("banknameaccountno1 in receipt" + banknameaccountno1);
			System.out.println("balance_in_account1 in receipt" + balance_in_account1);
			System.out.println("receiptamount in receipt" + receiptamount);
			receipt_entry = entryForms_Repo.InterBankTransferInsertion_Receipt(receipt_id, receiptno, date,
					banknameaccountno1, balance_in_account1, receiptamount, mode, cheque_dd_receipt_no,
					receipt_description, security_id, user_id);
			double tobankbalancedouble = (balance_in_account1) + (receiptamount);

			insertrelation = entryForms_Repo.insertrelation(payment_id, receipt_id);
			System.out.println("tobankbalancedouble" + tobankbalancedouble);
			bank_entry = entryForms_Repo.InterBankTransferInsertion_bank1(tobankbalancedouble, banknameaccountno1);
			System.out.println(bank_entry + "------bank_entry");
		}

		if (payment_entry != 0 && receipt_entry != 0 && insertrelation != 0 && bank_entry != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Submitted Sucessfully");
		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", " Some Error occured while performing your request ");
		}
		return responseMap;
	}

	// SUB-HEAD MASTER

	@Override
	public List<Map<String, String>> GetSubHeadsData(String headid) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		return entryForms_Repo.GetSubHeadsData(headid, security_id);
	}

	@Override
	public List<Map<String, Object>> AddSubheads(String headid, String subheadid, String subheadname) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		int insertSubHeads = 0;
		String subheadseqid = entryForms_Repo.getsubheadseqid();
		System.out.println("subheadseqid" + subheadseqid);
		insertSubHeads = entryForms_Repo.insertSubHeads(subheadseqid, subheadid, subheadname, headid, security_id);
		if (insertSubHeads != 0) {

			map.put("Status", 1);
			map.put("responseMsg", "Sucessfully Added Subhead");
			respose.add(map);
		} else {
			map.put("Status", 2);
			map.put("responseMsg", "Having some Error");
			respose.add(map);
		}
		return respose;
	}

	@Override
	public List<Map<String, Object>> EditSubheads(String subheadname, String subheadid, String subheadseqid) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);
		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		int updateSubheads = entryForms_Repo.updateSubheads(subheadname, subheadid, subheadseqid, security_id);
		System.out.println(updateSubheads + "updateSubheads");
		if (updateSubheads != 0) {

			map.put("Status", 1);
			map.put("responseMsg", "Sucessfully Edit Subhead");
			respose.add(map);
		} else {
			map.put("Status", 2);
			map.put("responseMsg", "Having some Error");
			respose.add(map);
		}
		return respose;
	}

	@Override
	public Map<String, Object> DeleteSubheads(List<SubHeadMaster> subHeadMaster) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println(security_id);

		Map<String, Object> responseMap = new LinkedHashMap<>();
		Map<String, Object> map = new HashMap<String, Object>();
		int DeleteSubheads = 0;
		for (SubHeadMaster requestData : subHeadMaster) {
			String headid = requestData.getHeadid();
			System.out.println("headid" + headid);
			String subheadseqid = requestData.getSubheadseqid();
			System.out.println("subheadseqid" + subheadseqid);
			DeleteSubheads = entryForms_Repo.DeleteSubheads(subheadseqid, headid, security_id);
			System.out.println(DeleteSubheads + "DeleteSubheads");
		}
		if (DeleteSubheads != 0)

		{
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Succesffuly deleted");

		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}

		return responseMap;
	}

//	@Override
//	public List<Map<String, Object>> EditSubheads(List<SubHeadMaster> subHeadMaster) {
//		final String user_id = CommonServiceImpl.getLoggedInUserId();
//		System.out.println("Logged User Id is :::" + user_id);
//		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
//		System.out.println(security_id);
//
//		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
//		Map<String, Object> map = new HashMap<String, Object>();
//		int updateSubheads = 0;
//		for (SubHeadMaster requestData : subHeadMaster) {
//			String headid = requestData.getHeadid();
//			System.out.println("headid" + headid);
//			String subheadname = requestData.getSubheadname();
//			System.out.println("subheadname" + subheadname);
//			String subheadseqid = requestData.getSubheadseqid();
//			System.out.println("subheadseqid" + subheadseqid);
//			updateSubheads = entryForms_Repo.updateSubheads(subheadname, subheadseqid, headid, security_id);
//			System.out.println(updateSubheads + "updateSubheads");
//		}
//		if (updateSubheads != 0)
//
//		{
//			map.put("Status", 1);
//			map.put("responseMsg", "Sucessfully updated Subheads");
//
//		} else {
//			map.put("Status", 2);
//			map.put("responseMsg", "Having some Error");
//		}
//
//		respose.add(map);
//		return respose;
//	}
	// Online Report Journal Vocher

	@Override
	public List<Map<String, String>> getVoucherListDropdown() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		return entryForms_Repo.getVoucherListDropdown();
	}

	@Override
	public List<Map<String, String>> getDescription_Heads(String voucher_id) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		return entryForms_Repo.getDescription_Heads(voucher_id);
	}

	// Salary Journal Voucher
	@Override
	public List<Map<String, String>> getSalaryVoucherListDropdown() {

		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		String flag = entryForms_Repo.getCase(security_id);
		List<Map<String, String>> data = null;
		if (flag.equals("0")) {

			data = entryForms_Repo.getSalaryVoucherListDropdown_0();
		}

		else

		{
			data = entryForms_Repo.getSalaryVoucherListDropdown_1();
		}
		return data;
	}

	// ********************Heads Master **************** in APSHCL LOGIN
	@Override
	public List<Map<String, String>> getHeadsDetails() {

		return entryForms_Repo.getHeadsDetails();
	}

	public List<Map<String, String>> classificationDropdown() {
		return entryForms_Repo.classificationDropdown();
	}

	@Override
	public Map<String, Object> HeadMasterActions(Payment_receipts_Req payment_receipts_Req) {
		String headid = payment_receipts_Req.getHeadid();
		String headname = payment_receipts_Req.getHeadname();
		Boolean receipts = payment_receipts_Req.getReceipts();
		Boolean payments = payment_receipts_Req.getPayments();
		Boolean flag = payment_receipts_Req.getFlag();
		String classification = payment_receipts_Req.getClassification();
		String action = payment_receipts_Req.getAction();
		int add = 0;
		int edit = 0;
		int delete = 0;
		Map<String, Object> responseMap = new LinkedHashMap<>();
		if (action.equals("add")) {
			add = entryForms_Repo.headsmasterAdd(headid, headname, receipts, payments, classification);
		} else if (action.equals("edit")) {
			edit = entryForms_Repo.headsmasterEdit(headid, headname, receipts, payments, classification);
		} else if (action.equals("delete")) {
			delete = entryForms_Repo.headsmasterDelete(headid);
		}
		if (add != 0 || edit != 0 || delete != 0) {
			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Action Sucessfully Completed");
		} else {
			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an error");
		}

		return responseMap;
	}

	@Override
	public List<Map<String, String>> GetHeadbyPayment_receipt_id(String payment_receipt_id) {

		return entryForms_Repo.GetHeadbyPayment_receipt_id(payment_receipt_id);
	}

	@Override
	public List<Map<String, String>> GetDataforBankEntnry_Manger() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return entryForms_Repo.GetDataforBankEntnry_Manger();
	}

	@Override
	public List<Map<String, String>> GetDataforGR_Manger() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return entryForms_Repo.GetDataforGR_Manger();
	}

	@Override
	public List<Map<String, String>> GetDataforGP_Manger() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return entryForms_Repo.GetDataforGP_Manger();
	}

	@Override
	public List<Map<String, Object>> GetSubHeadsDataforGR_Manger(String payment_receipt_id) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is ::: " + user_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("payment_receipt_id" + payment_receipt_id);
		List<Map<String, Object>> HeadsData = null;
		HeadsData = entryForms_Repo.GetSubHeadsDataforGR_Manger(payment_receipt_id);
		System.out.println("data.size :" + HeadsData.size());
		System.out.println("data :" + HeadsData);

		if (HeadsData.size() > 0) {
			map.put("responseToken", 01);
			map.put("responseMsg", "Data fetched Successfully");

			map.put("HeadsListData", HeadsData);

		} else {
			map.put("responseToken", 02);
			map.put("responseMsg", "Data not fetched");
		}
		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, Object>> GetSubHeadsDataforGJ_Manger(String payment_receipt_id) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is ::: " + user_id);

		List<Map<String, Object>> respose = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		System.out.println("payment_receipt_id" + payment_receipt_id);
		List<Map<String, Object>> HeadsData = null;
		HeadsData = entryForms_Repo.GetSubHeadsDataforGJ_Manger(payment_receipt_id);
		System.out.println("data.size :" + HeadsData.size());
		System.out.println("data :" + HeadsData);

		if (HeadsData.size() > 0) {
			map.put("responseToken", 01);
			map.put("responseMsg", "Data fetched Successfully");

			map.put("HeadsListData", HeadsData);

		} else {
			map.put("responseToken", 02);
			map.put("responseMsg", "Data not fetched");
		}
		respose.add(map);
		return respose;
	}

	@Override
	public List<Map<String, String>> GetDataforGJ_Manger() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return entryForms_Repo.GetDataforGJ_Manger();
	}

	@Override
	public List<Map<String, String>> GetDataforGJ_Edit() {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		return entryForms_Repo.GetDataforGJ_Edit(security_id);
	}

	@Override
	public List<Map<String, String>> GetJV_Edit_data(String fromDate, String toDate, String payment_receipt_id,
			String district) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		List<Map<String, String>> GetJV = null;

		if (district.equals(("state")) && !district.equals(("")) && !fromDate.equals("") && !toDate.equals("")
				&& payment_receipt_id.equals("")) {
			GetJV = entryForms_Repo.GetJV_Edit_dataByDates_state(fromDate, toDate);
		}
		if (!district.equals(("state")) && !district.equals(("")) && !fromDate.equals("") && !toDate.equals("")
				&& payment_receipt_id.equals("")) {
			GetJV = entryForms_Repo.GetJV_Edit_dataByDates(fromDate, toDate, security_id);
		}
		if (district.equals(("")) && !payment_receipt_id.equals("") && fromDate.equals("") && toDate.equals("")) {
			GetJV = entryForms_Repo.GetJV_EdidataById_state(payment_receipt_id);
		}

		return GetJV;
	}

	// GMADM (DISTRICT WISE TRANSFER)
	@Override
	public List<Map<String, String>> getDistrictwiseTransferData(String emp_type, String fromDistrict) {
		final String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("Logged User Id is :::" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		return entryForms_Repo.getDistrictwiseTransferData(emp_type, fromDistrict);
	}

	@Override
	public Map<String, Object> UpdateDistrictwiseTransferData(
			List<UpdateDistrictTransferData> updatedistricttranferdata) {
		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);
		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		System.out.println("security_id" + security_id);
		Map<String, Object> responseMap = new LinkedHashMap<>();
		int empdetailsupdate = 0;
		int earningdetailsupdate = 0;
		int deductiondetailsupdate = 0;
		int loandetailsupdate = 0;
		int inserttransfetdetails = 0;
		for (UpdateDistrictTransferData transferlist : updatedistricttranferdata) {
			String emp_id = transferlist.getEmp_id();
			String fromDistrict = transferlist.getFromDistrict();
			String toDistrict = transferlist.getToDistrict();
			String emp_type = transferlist.getEmp_type();
			String remarks = transferlist.getRemarks();
			String tranferDate = transferlist.getTranferDate();

			empdetailsupdate = entryForms_Repo.empdetailsupdate(toDistrict, emp_id, fromDistrict, emp_type);
			System.out.println("empdetailsupdate: " + empdetailsupdate);

			earningdetailsupdate = entryForms_Repo.earningdetailsupdate(toDistrict, emp_id, fromDistrict);
			System.out.println("earningdetailsupdate: " + earningdetailsupdate);

			deductiondetailsupdate = entryForms_Repo.deductiondetailsupdate(toDistrict, emp_id, fromDistrict);
			System.out.println("deductiondetailsupdate: " + deductiondetailsupdate);

			loandetailsupdate = entryForms_Repo.loandetailsupdate(toDistrict, emp_id, fromDistrict);
			System.out.println("loandetailsupdate: " + loandetailsupdate);

			inserttransfetdetails = entryForms_Repo.inserttransfetdetails(toDistrict, fromDistrict, emp_id, remarks,
					tranferDate);
			System.out.println("inserttransfetdetails: " + inserttransfetdetails);

		}
		if (empdetailsupdate != 0 && earningdetailsupdate != 0 && deductiondetailsupdate != 0 && loandetailsupdate != 0
				&& inserttransfetdetails != 0) {

			responseMap.put("SCODE", "01");
			responseMap.put("SDESC", "Data Updated Successfully");
		} else {

			responseMap.put("SCODE", "02");
			responseMap.put("SDESC", "Having an Error");
		}
		return responseMap;

	}

	// New Employee registration
	@Override
	public List<Map<String, Object>> NewEmpRegistration(PayrollReports_Request payrollReports_Request) {

		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		int emptype_id = payrollReports_Request.getEmptype_id();
		String emp_id = entryForms_Repo.empIdGeneation();
		System.out.println("emp_id" + emp_id);
		String prefix = payrollReports_Request.getPrefix();
		String firstname = payrollReports_Request.getFirstname();
		String lastname = payrollReports_Request.getLastname();
		String surname = payrollReports_Request.getSurname();
		String fathername = payrollReports_Request.getFathername();
		String sex = payrollReports_Request.getSex();
		int designation = payrollReports_Request.getDesignation();
		String account_no = payrollReports_Request.getAccount_no();
		String others = payrollReports_Request.getOthers();
		String bank_name = payrollReports_Request.getBank_name();
		String branch_code = payrollReports_Request.getBranch_code();
		String ifsc = payrollReports_Request.getIfsc();
		// Earning Details
		double basic_pay_earnings = payrollReports_Request.getBasic_pay_earnings();
		double per_pay_earnings = payrollReports_Request.getPer_pay_earnings();
		double spl_pay_earnings = payrollReports_Request.getSpl_pay_earnings();
		double da_earnings = payrollReports_Request.getDa_earnings();
		double hra_earnings = payrollReports_Request.getHra_earnings();
		double cca_earnings = payrollReports_Request.getCca_earnings();
		double gp_earnings = payrollReports_Request.getGp_earnings();
		double ir_earnings = payrollReports_Request.getIr_earnings();
		double medical_earnings = payrollReports_Request.getMedical_earnings();
		double ca_earnings = payrollReports_Request.getCa_earnings();
		double spl_all = payrollReports_Request.getSpl_all();
		double misc_h_c = payrollReports_Request.getMisc_h_c();
		double addl_hra = payrollReports_Request.getAddl_hra();
		double sca = payrollReports_Request.getSca();

		// Deduction Details
		double gpfs_deductions = payrollReports_Request.getGpfs_deductions();
		double gpfl_deductions = payrollReports_Request.getGpfl_deductions();
		double gpfsa_deductions = payrollReports_Request.getGpfsa_deductions();
		double house_rent_deductions = payrollReports_Request.getHouse_rent_deductions();
		double gis_deductions = payrollReports_Request.getGis_deductions();

//		int pt_deductions = payrollReports_Request.getPt_deductions();
		double pt_deductions = payrollReports_Request.getPt_deductions();
		double it_deductions = payrollReports_Request.getIt_deductions();
		double cca_deductions = payrollReports_Request.getCca_deductions();
		double license_deductions = payrollReports_Request.getLicense_deductions();
		double con_decd_deductions = payrollReports_Request.getCon_decd_deductions();
		double lic_deductions = payrollReports_Request.getLic_deductions();
		double rcs_cont_deductions = payrollReports_Request.getRcs_cont_deductions();
		double sal_rec_deductions = payrollReports_Request.getSal_rec_deductions();
		double cmrf_deductions = payrollReports_Request.getCmrf_deductions();
		double fcf_deductions = payrollReports_Request.getFcf_deductions();
		double epf_l_deductions = payrollReports_Request.getEpf_l_deductions();
		double vpf_deductions = payrollReports_Request.getVpf_deductions();
		double apglis_deductions = payrollReports_Request.getApglis_deductions();
		double apglil_deductions = payrollReports_Request.getApglil_deductions();
		double epf_deductions = payrollReports_Request.getEpf_deductions();

		double ppf_deductions = payrollReports_Request.getPpf_deductions();
		double other_deductions = payrollReports_Request.getOther_deductions();

		// Loan Details
		double car_i_loan = payrollReports_Request.getCar_i_loan();
		double car_a_loan = payrollReports_Request.getCar_a_loan();
		double car_i_loanpi = payrollReports_Request.getCar_i_loanpi();
		double car_a_loanpi = payrollReports_Request.getCar_a_loanpi();
		double car_i_loanti = payrollReports_Request.getCar_i_loanti();
		double car_a_loanti = payrollReports_Request.getCar_a_loanti();

		double cyc_i_loan = payrollReports_Request.getCyc_i_loan();
		double cyc_a_loan = payrollReports_Request.getCyc_a_loan();
		double cyc_i_loanpi = payrollReports_Request.getCyc_i_loanpi();
		double cyc_a_loanpi = payrollReports_Request.getCyc_a_loanpi();
		double cyc_i_loanti = payrollReports_Request.getCyc_i_loanti();
		double cyc_a_loanti = payrollReports_Request.getCyc_a_loanti();

		double mca_i_loan = payrollReports_Request.getMca_i_loan();
		double mca_a_loan = payrollReports_Request.getMca_a_loan();
		double mca_i_loanpi = payrollReports_Request.getMca_i_loanpi();
		double mca_a_loanpi = payrollReports_Request.getMca_a_loanpi();
		double mca_i_loanti = payrollReports_Request.getMca_i_loanti();
		double mca_a_loanti = payrollReports_Request.getMca_a_loanti();

		double mar_a_loan = payrollReports_Request.getMar_a_loan();
		double mar_a_loanpi = payrollReports_Request.getMar_a_loanpi();
		double mar_i_loan = payrollReports_Request.getMar_i_loan();
		double mar_a_loanti = payrollReports_Request.getMar_a_loanti();
		double mar_i_loanti = payrollReports_Request.getMar_a_loanti();
		double mar_i_loanpi = payrollReports_Request.getMar_i_loanpi();

		double med_a_loan = payrollReports_Request.getMed_a_loan();
		double med_a_i_loan = payrollReports_Request.getMed_a_i_loan();
		double med_a_loanpi = payrollReports_Request.getMed_a_loanpi();
		double med_a_i_loanpi = payrollReports_Request.getMed_a_i_loanpi();
		double med_a_loanti = payrollReports_Request.getMed_a_i_loanti();
		double med_a_i_loanti = payrollReports_Request.getMed_a_i_loanti();

		double hba_loan = payrollReports_Request.getHba_loan();
		double hba_loanpi = payrollReports_Request.getHba_loanpi();
		double hba_loanti = payrollReports_Request.getHba_loanti();
		double hba1_loanti = payrollReports_Request.getHba1_loanti();
		double hba1_loan = payrollReports_Request.getHba1_loan();
		double hba1_loanpi = payrollReports_Request.getHba1_loanpi();

		double add_hba_loanpi = payrollReports_Request.getAdd_hba_loanpi();
		double add_hba1_loanpi = payrollReports_Request.getAdd_hba1_loanpi();
		double add_hba_loan = payrollReports_Request.getAdd_hba1_loan();
		double add_hba1_loan = payrollReports_Request.getAdd_hba1_loan();
		double add_hba_loanti = payrollReports_Request.getAdd_hba_loanti();
		double add_hba1_loanti = payrollReports_Request.getAdd_hba1_loanti();

		double comp_loanpi = payrollReports_Request.getComp_loanpi();
		double comp_loanti = payrollReports_Request.getComp_loanti();
		double comp1_loan = payrollReports_Request.getComp1_loan();
		double comp_loan = payrollReports_Request.getComp_loan();
		double comp1_loanti = payrollReports_Request.getComp1_loanti();
		double comp1_loanpi = payrollReports_Request.getComp1_loanpi();

		double hcesa_loan = payrollReports_Request.getHcesa_i_loan();
		double hcesa_I_loan = payrollReports_Request.getHcesa_i_loan();
		double hcesa_loanpi = payrollReports_Request.getHcesa_loanpi();
		double hcesa_i_loanpi = payrollReports_Request.getHcesa_i_loanpi();
		double hcesa_loanti = payrollReports_Request.getHcesa_i_loanti();
		double hcesa_i_loanti = payrollReports_Request.getHcesa_i_loanti();

		double hbao_loanti = payrollReports_Request.getHbao_loanti();
		double hbao_loanpi = payrollReports_Request.getHbao_loanpi();
		double hbao_loan = payrollReports_Request.getHbao_loan();
		double vij_bank_loan = payrollReports_Request.getVij_bank_loan();
		double vij_bank_loanti = payrollReports_Request.getVij_bank_loanti();
		double vij_bank_loanpi = payrollReports_Request.getVij_bank_loanpi();

		double ea_loanti = payrollReports_Request.getEa_loanti();
		double ea_loan = payrollReports_Request.getEa_loan();
		double ea_loanpi = payrollReports_Request.getEa_loanpi();
		double fa_loan = payrollReports_Request.getFa_loan();
		double fa_loanpi = payrollReports_Request.getFa_loanpi();
		double fa_loanti = payrollReports_Request.getFa_loanti();

		double sfa_loan = payrollReports_Request.getSfa_loan();
		double sfa_loanti = payrollReports_Request.getSfa_loanti();
		double sfa_loanpi = payrollReports_Request.getSfa_loanpi();
		double sal_adv_loanpi = payrollReports_Request.getSal_adv_loanpi();
		double sal_adv_loan = payrollReports_Request.getSal_adv_loan();
		double sal_adv_loanti = payrollReports_Request.getSal_adv_loanti();

		double hr_arrear_loan = payrollReports_Request.getHr_arrear_loan();
		double hr_arrear_loanpi = payrollReports_Request.getHr_arrear_loanpi();
		double hr_arrear_loanti = payrollReports_Request.getHr_arrear_loanti();
		double cell_loan = payrollReports_Request.getCell_loan();
		double cell_loanpi = payrollReports_Request.getCell_loanpi();
		double cell_loanti = payrollReports_Request.getCell_loanti();

		double staff_pl_loan = payrollReports_Request.getStaff_pl_loan();
		double staff_pl_loanti = payrollReports_Request.getStaff_pl_loanti();
		double staff_pl_loanpi = payrollReports_Request.getStaff_pl_loanpi();
		double court_loan = payrollReports_Request.getCourt_loan();
		double court_loanti = payrollReports_Request.getCourt_loanti();
		double court_loanpi = payrollReports_Request.getCourt_loanpi();

		// Os Earn Ded Details
		double os_basic_pay_earnings = payrollReports_Request.getOs_basic_pay_earnings();
		double os_hra_earnings = payrollReports_Request.getOs_hra_earnings();
		double os_medical_earnings = payrollReports_Request.getOs_medical_earnings();
		double os_ca_earnings = payrollReports_Request.getOs_ca_earnings();
		double os_performance_earnings = payrollReports_Request.getOs_ec_epf();
		double os_ec_epf = payrollReports_Request.getOs_ec_epf();
		double os_ees_epf_deductions = payrollReports_Request.getOs_ees_epf_deductions();
		double os_ers_epf_deductions = payrollReports_Request.getOs_ers_epf_deductions();

		double os_prof_tax_deductions = payrollReports_Request.getOs_prof_tax_deductions();
		double os_other_deductions = payrollReports_Request.getOs_other_deductions();
		String os_work_place = payrollReports_Request.getOs_work_place();
		String os_location = payrollReports_Request.getOs_location();
		// Nmr Earn Ded Details
		double nmr_gross_earnings = payrollReports_Request.getNmr_gross_earnings();
		double nmr_postalrd_deductions = payrollReports_Request.getNmr_postalrd_deductions();
		double nmr_tds_deductions = payrollReports_Request.getNmr_tds_deductions();
		double nmr_fa_deductions = payrollReports_Request.getNmr_fa_deductions();
		double nmr_ea_deductions = payrollReports_Request.getNmr_ea_deductions();
		double nmr_ma_deductions = payrollReports_Request.getNmr_ma_deductions();
		double nmr_lic_deductions = payrollReports_Request.getNmr_lic_deductions();
		double nmr_otherliab_deductions = payrollReports_Request.getNmr_otherliab_deductions();

		String user_id = CommonServiceImpl.getLoggedInUserId();
		System.out.println("------Userid" + user_id);

		String security_id = usersSecurityRepo.GetSecurity_Id(user_id);
		int insert_empdetails = 0;
		int insert_earnings125 = 0;
		int insert_deductions125 = 0;
		int insert_loans125 = 0;
		int insert_earningsDeductions3 = 0;
		int insert_earningsDeductions4 = 0;

		insert_empdetails = entryForms_Repo.insert_into_EmployeeDetails(security_id, emp_id, prefix, firstname,
				lastname, surname, fathername, emptype_id, sex, designation, account_no, bank_name, branch_code, ifsc);
		if (emptype_id == 1 || emptype_id == 2 || emptype_id == 5) {
			System.out.println("called 125");
			insert_earnings125 = entryForms_Repo.insert_into_EmpEarnings125(security_id, emp_id, basic_pay_earnings,
					per_pay_earnings, spl_pay_earnings, da_earnings, hra_earnings, cca_earnings, gp_earnings,
					ir_earnings, medical_earnings, ca_earnings, spl_all, misc_h_c, addl_hra, sca);
			System.out.println("insert_earnings125" + insert_earnings125);
			insert_deductions125 = entryForms_Repo.insert_into_EmpDeductions125(security_id, emp_id, gpfs_deductions,
					gpfsa_deductions, gpfl_deductions, apglis_deductions, apglil_deductions, gis_deductions,
					lic_deductions, license_deductions, con_decd_deductions, epf_deductions, epf_l_deductions,
					vpf_deductions, ppf_deductions, rcs_cont_deductions, cmrf_deductions, fcf_deductions,
					house_rent_deductions, sal_rec_deductions, pt_deductions, it_deductions, other_deductions);
			System.out.println("insert_deductions125" + insert_deductions125);
			insert_loans125 = entryForms_Repo.insert_into_EmpLoans125(security_id, emp_id, car_i_loan, car_a_loan,
					cyc_i_loan, cyc_a_loan, mca_i_loan, mca_a_loan, mar_a_loan, med_a_loan, hba_loan, hba1_loan,
					comp_loan, fa_loan, ea_loan, cell_loan, add_hba_loan, add_hba1_loan, sal_adv_loan, sfa_loan,
					med_a_i_loan, hcesa_loan, hcesa_I_loan, staff_pl_loan, court_loan, vij_bank_loan, mar_i_loan,
					hr_arrear_loan, hbao_loan, comp1_loan, car_i_loanpi, car_a_loanpi, cyc_i_loanpi, cyc_a_loanpi,
					mca_i_loanpi, mca_a_loanpi, mar_a_loanpi, med_a_loanpi, hba_loanpi, hba1_loanpi, comp_loanpi,
					fa_loanpi, ea_loanpi, cell_loanpi, add_hba_loanpi, add_hba1_loanpi, sal_adv_loanpi, sfa_loanpi,
					med_a_i_loanpi, hcesa_loanpi, hcesa_i_loanpi, staff_pl_loanpi, hr_arrear_loanpi, hbao_loanpi,
					comp1_loanpi, car_i_loanti, car_a_loanti, cyc_i_loanti, cyc_a_loanti, mca_i_loanti, mca_a_loanti,
					mar_a_loanti, med_a_loanti, hba_loanti, hba1_loanti, comp_loanti, fa_loanti, ea_loanti, cell_loanti,
					add_hba_loanti, add_hba1_loanti, sal_adv_loanti, sfa_loanti, med_a_i_loanti, hcesa_loanti,
					hcesa_i_loanti, staff_pl_loanti, court_loanti, vij_bank_loanti, mar_i_loanti, hr_arrear_loanti,
					hbao_loanti, comp1_loanti, court_loanpi, vij_bank_loanpi, mar_i_loanpi);
			System.out.println("insert_loans125" + insert_loans125);
		}
		if (emptype_id == 3) {
			insert_earningsDeductions3 = entryForms_Repo.insert_into_earningsDeductions3(security_id, emp_id,
					os_basic_pay_earnings, os_hra_earnings, os_medical_earnings, os_ca_earnings,
					os_performance_earnings, os_ec_epf, os_ees_epf_deductions, os_ers_epf_deductions,
					os_prof_tax_deductions, os_other_deductions, os_work_place, os_location);
			System.out.println("insert_earningsDeductions3" + insert_earningsDeductions3);
		}
		if (emptype_id == 4) {
			insert_earningsDeductions4 = entryForms_Repo.insert_into_earningsDeductions4(security_id, emp_id,
					nmr_gross_earnings, os_ees_epf_deductions, nmr_postalrd_deductions, os_prof_tax_deductions,
					os_work_place, os_location, nmr_tds_deductions, nmr_fa_deductions, nmr_ea_deductions,
					nmr_ma_deductions, nmr_lic_deductions, nmr_otherliab_deductions);
			System.out.println("insert_earningsDeductions4" + insert_earningsDeductions4);
		}
		if (insert_empdetails > 0 && (insert_earnings125 > 0 && insert_deductions125 > 0 && insert_loans125 > 0)
				|| insert_earningsDeductions3 > 0 || insert_earningsDeductions4 > 0) {
			System.out.println("called 1");
			map.put("responseToken", 1);
			map.put("responseMsg", "Data uploaded Successfully");
		} else {
			map.put("responseToken", 0);
			map.put("responseMsg", "Data Not uploaded");
		}
		response.add(map);
		return response;

	}

}
