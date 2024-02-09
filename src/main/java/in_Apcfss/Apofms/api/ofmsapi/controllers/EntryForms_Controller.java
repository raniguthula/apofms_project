
package in_Apcfss.Apofms.api.ofmsapi.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in_Apcfss.Apofms.api.ofmsapi.Repositories.UsersSecurityRepo;
import in_Apcfss.Apofms.api.ofmsapi.request.BankDetails_Request;
import in_Apcfss.Apofms.api.ofmsapi.request.CadreWise_Request;
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
import in_Apcfss.Apofms.api.ofmsapi.services.UploadService;
import in_Apcfss.Apofms.api.ofmsapi.servicesimpl.CommonServiceImpl;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class EntryForms_Controller {
	@Autowired
	EntryForms_Service entryForms_Service;

	@Value("${file.upload-dir}")
	private String documentsurl;

	@Autowired
	UploadService uploadService;

//**************************Bank Details Entry*************************

//----------http://172.17.205.53:9082/ofmsapi/Bank_Details_Entry_Insertion
//	bankname:Andra Bank, branchname:gollala mamidada, ifsc:andra1010, accountno:960053569171, operationaccount:joint,accounttype:sb,accountholder1:rani
//	accountholder2:veni,balance:10000, userid: by dynamic, banknameaccountno:andhara-103010,security_id: by dynamic, remaining_balance:10
//	 is_approved_by_gm:false,ee_entered_date:now(), mandal_account: true, distid:
	@RequestMapping(value = "/Bank_Details_Entry_Insertion", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public Map<String, Object> Bank_Details_Entry_Insertion(@ModelAttribute BankDetails_Request bankDetails_Request,
			HttpServletRequest request) {
		String ip_address = request.getRemoteAddr();
		int accountid = bankDetails_Request.getAccountid();
		String bankname = bankDetails_Request.getBankname();
		String branchname = bankDetails_Request.getBranchname();
		String accountno = bankDetails_Request.getAccountno();
		String operationaccount = bankDetails_Request.getOperationaccount();
		String accounttype = bankDetails_Request.getAccounttype();
		String accountholder1 = bankDetails_Request.getAccountholder1();
		String accountholder2 = bankDetails_Request.getAccountholder2();
		double balance = bankDetails_Request.getBalance();
//		String distid = bankDetails_Request.getDistid();
//		String userid = bankDetails_Request.getUserid();
		String banknameaccountno = bankDetails_Request.getBanknameaccountno();
		String accountholders = bankDetails_Request.getAccountholders();
//		String security_id = bankDetails_Request.getSecurity_id();
		double remaining_balance = bankDetails_Request.getRemaining_balance();
//		boolean flag = bankDetails_Request.isFlag();
//		Timestamp timestamp = bankDetails_Request.getTimestamp();
		double available_balance = bankDetails_Request.getAvailable_balance();
//		boolean mandal_account = bankDetails_Request.isMandal_account();
		double balance_allotted = bankDetails_Request.getBalance_allotted();
//		boolean balance_allotted_by_cgm = bankDetails_Request.isBalance_allotted_by_cgm();
//		Timestamp allotted_date = bankDetails_Request.getAllotted_date();
//		double opening_balance_as_on_date = bankDetails_Request.getOpening_balance_as_on_date();
//		Timestamp ee_entered_date = bankDetails_Request.getEe_entered_date();
//		Timestamp gmfinance_aproved_date = bankDetails_Request.getGmfinance_aproved_date();
//		String ee_id = bankDetails_Request.getEe_id();
//		String gm_id = bankDetails_Request.getGm_id();
		boolean is_approved_by_gm = bankDetails_Request.isIs_approved_by_gm();
//		int div_id = bankDetails_Request.getDiv_id();
		String ifsc = bankDetails_Request.getIfsc();

//		long ms = System.currentTimeMillis();

//		String filePath = "Bank_photo/";
//		MultipartFile passbook_path = bankDetails_Request.getPassbook_path();
//		String extension = StringUtils.getFilenameExtension(passbook_path.getOriginalFilename());
//		String complete_path = "bank_doc_" + ms + "." + extension;
//		String fileName = filePath + complete_path;
//		boolean isUplaod = uploadService.uploadDocument(passbook_path, filePath, complete_path);
//		System.out.println("file uploaded or not" + isUplaod);

		String passbook_path = bankDetails_Request.getPassbook_path();
		return entryForms_Service.Bank_Details_Entry_Insertion(accountid, bankname, branchname, accountno,
				operationaccount, accounttype, accountholder1, accountholder2, balance, banknameaccountno,
				accountholders, remaining_balance, available_balance, is_approved_by_gm, ifsc, passbook_path,
				ip_address);

	}

	@PostMapping("BankEntryMangerApproval")
	public Map<String, Object> BankEntryMangerApproval(@RequestBody BankDetails_Request bankDetails_Request,
			HttpServletRequest request) {
		String ip_address = request.getRemoteAddr();
		int accountid = bankDetails_Request.getAccountid();
		String bankname = bankDetails_Request.getBankname();
		String branchname = bankDetails_Request.getBranchname();
		String accountno = bankDetails_Request.getAccountno();
		String operationaccount = bankDetails_Request.getOperationaccount();
		String accounttype = bankDetails_Request.getAccounttype();
		String accountholder1 = bankDetails_Request.getAccountholder1();
		String accountholder2 = bankDetails_Request.getAccountholder2();
		double balance = bankDetails_Request.getBalance();

		String banknameaccountno = bankDetails_Request.getBanknameaccountno();
		String accountholders = bankDetails_Request.getAccountholders();

		double remaining_balance = bankDetails_Request.getRemaining_balance();

		String ifsc = bankDetails_Request.getIfsc();

		String passbook_path = bankDetails_Request.getPassbook_path();
		return entryForms_Service.BankEntryMangerApproval(accountid, bankname, branchname, accountno, operationaccount,
				accounttype, accountholder1, accountholder2, balance, banknameaccountno, accountholders,
				remaining_balance, ifsc, passbook_path, ip_address);

	}

	@PostMapping("BankEntryMangerReject")
	public Map<String, Object> BankEntryMangerReject(@RequestBody BankDetails_Request bankDetails_Request,
			HttpServletRequest request) {

		String banknameaccountno = bankDetails_Request.getBanknameaccountno();
		System.out.println("banknameaccountno" + banknameaccountno);
		return entryForms_Service.BankEntryMangerReject(banknameaccountno);

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/downloadPassbookCopy")
	public ResponseEntity downloadPassbookCopyFromLocal(@RequestBody Payment_receipts_Req payment_receipts_Req)
			throws IOException {

		String fileName = payment_receipts_Req.getFileName();

		File file = new File(documentsurl + fileName);
		System.out.println("File::::" + file);
		HttpHeaders header = new HttpHeaders();
		System.out.println(header);
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());

		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}

	@PostMapping("GeneratePayment_Receipt_Id")
	public List<Map<String, String>> GeneratePayment_Receipt_Id(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String save_flag_Payment_Receipts_Journal = payment_receipts_Req.getSave_flag_Payment_Receipts_Journal();
		System.out.println("save_flag_Payment_Receipts_Journal" + save_flag_Payment_Receipts_Journal);

		return entryForms_Service.GeneratePayment_Receipt_Id(save_flag_Payment_Receipts_Journal);
	}

	@PostMapping("Generation_Receipt_Insertion")
	public Map<String, Object> Generation_Receipt_Insertion(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String receiptno = payment_receipts_Req.getReceiptno();
		Date date = payment_receipts_Req.getDate();
		String cash_bank = payment_receipts_Req.getCash_bank();
		double remaining_balance = payment_receipts_Req.getRemaining_balance();
		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
		// long paymentamount = payment_receipts_Req.getPaymentamount();
//		long receiptamount = payment_receipts_Req.getReceiptamount();
		double receiptamount = payment_receipts_Req.getReceiptamount();
		double balance_in_account = payment_receipts_Req.getBalance_in_account();
		String name = payment_receipts_Req.getName();
		String mode = payment_receipts_Req.getMode();
		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
		String receipt_description = payment_receipts_Req.getReceipt_description();
		// String transaction_type = payment_receipts_Req.getTransaction_type();

//		int olderreceiptamount=payment_receipts_Req.getOlderreceiptamount();
		String upload_copy = payment_receipts_Req.getUpload_copy();

		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		System.out.println("headDto" + dummy_journal_voucher_heads_Req.size());

		String no_of_subheads = payment_receipts_Req.getNo_of_subheads();
		return entryForms_Service.Generation_Receipt_Insertion(payment_receipt_id, receiptno, date, cash_bank,
				remaining_balance, banknameaccountno, receiptamount, balance_in_account, name, mode,
				cheque_dd_receipt_no, receipt_description, no_of_subheads, dummy_journal_voucher_heads_Req, upload_copy,
				request);

	}

	@PostMapping("Generation_Receipt_Managerapproval")
	public Map<String, Object> Generation_Receipt_Managerapproval(
			@RequestBody Payment_receipts_Req payment_receipts_Req, HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String receiptno = payment_receipts_Req.getReceiptno();
		Date date = payment_receipts_Req.getDate();
		String cash_bank = payment_receipts_Req.getCash_bank();
		double remaining_balance = payment_receipts_Req.getRemaining_balance();
		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
		// long paymentamount = payment_receipts_Req.getPaymentamount();
		double receiptamount = payment_receipts_Req.getReceiptamount();
		double balance_in_account = payment_receipts_Req.getBalance_in_account();
		String name = payment_receipts_Req.getName();
		String mode = payment_receipts_Req.getMode();
		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
		String receipt_description = payment_receipts_Req.getReceipt_description();
		// String transaction_type = payment_receipts_Req.getTransaction_type();
		String no_of_subheads = payment_receipts_Req.getNo_of_subheads();
		int olderreceiptamount = payment_receipts_Req.getOlderreceiptamount();
		String upload_copy = payment_receipts_Req.getUpload_copy();

		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		return entryForms_Service.Generation_Receipt_Managerapproval(payment_receipt_id, receiptno, date, cash_bank,
				remaining_balance, banknameaccountno, receiptamount, balance_in_account, name, mode,
				cheque_dd_receipt_no, receipt_description, no_of_subheads, olderreceiptamount,
				dummy_journal_voucher_heads_Req, upload_copy, request);

	}

	@PostMapping("Payment_Receipts_Managerreject")
	public Map<String, Object> Generation_Receipt_Managerreject(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();

		return entryForms_Service.Generation_Receipt_Managerreject(payment_receipt_id);

	}

	@PostMapping("Payment_Receipts_ManagerRemoveRow")
	public Map<String, Object> Payment_Receipts_ManagerRemoveRow(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String headid = payment_receipts_Req.getHeadid();
		String subheadid = payment_receipts_Req.getSubheadid();
		return entryForms_Service.Payment_Receipts_ManagerRemoveRow(payment_receipt_id, headid, subheadid);

	}

	@PostMapping("Generation_Payment_Insertion")
	public Map<String, Object> Generation_Payment_Insertion(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String receiptno = payment_receipts_Req.getReceiptno();
		Date date = payment_receipts_Req.getDate();
		String cash_bank = payment_receipts_Req.getCash_bank();
		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
		double paymentamount = payment_receipts_Req.getPaymentamount();
		// long receiptamount = payment_receipts_Req.getReceiptamount();
		double remaining_balance = payment_receipts_Req.getRemaining_balance();
		double balance_in_account = payment_receipts_Req.getBalance_in_account();
		String name = payment_receipts_Req.getName();
		String mode = payment_receipts_Req.getMode();
		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
		String receipt_description = payment_receipts_Req.getReceipt_description();
		// String transaction_type = payment_receipts_Req.getTransaction_type();
		String no_of_subheads = payment_receipts_Req.getNo_of_subheads();
		String upload_copy = payment_receipts_Req.getUpload_copy();
		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		return entryForms_Service.Generation_Payment_Insertion(payment_receipt_id, receiptno, date, cash_bank,
				remaining_balance, banknameaccountno, paymentamount, balance_in_account, name, mode,
				cheque_dd_receipt_no, receipt_description, no_of_subheads, dummy_journal_voucher_heads_Req, upload_copy,
				request);

	}

	@PostMapping("Generation_Payment_Managerapproval")
	public Map<String, Object> Generation_Payment_Managerapproval(
			@RequestBody Payment_receipts_Req payment_receipts_Req, HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String receiptno = payment_receipts_Req.getReceiptno();
		Date date = payment_receipts_Req.getDate();
		String cash_bank = payment_receipts_Req.getCash_bank();
		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
		double paymentamount = payment_receipts_Req.getPaymentamount();
		// long receiptamount = payment_receipts_Req.getReceiptamount();
		double remaining_balance = payment_receipts_Req.getRemaining_balance();
		double balance_in_account = payment_receipts_Req.getBalance_in_account();
		String name = payment_receipts_Req.getName();
		String mode = payment_receipts_Req.getMode();
		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
		String receipt_description = payment_receipts_Req.getReceipt_description();
		// String transaction_type = payment_receipts_Req.getTransaction_type();
		String no_of_subheads = payment_receipts_Req.getNo_of_subheads();
		String upload_copy = payment_receipts_Req.getUpload_copy();
		int oldpaymentamount = payment_receipts_Req.getOldpaymentamount();
		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		return entryForms_Service.Generation_Payment_Managerapproval(payment_receipt_id, receiptno, date, cash_bank,
				remaining_balance, banknameaccountno, paymentamount, balance_in_account, name, mode,
				cheque_dd_receipt_no, receipt_description, no_of_subheads, oldpaymentamount,
				dummy_journal_voucher_heads_Req, upload_copy, request);

	}

	@PostMapping("Generation_Journal_Insertion")
	public Map<String, Object> Generation_Journal_Insertion(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String voucher_id = payment_receipts_Req.getVoucher_id();
		Date date = payment_receipts_Req.getDate();
		String description = payment_receipts_Req.getDescription();
		double credit = payment_receipts_Req.getCredit();
		double debit = payment_receipts_Req.getDebit();
		String upload_copy = payment_receipts_Req.getUpload_copy();
		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		return entryForms_Service.Generation_Journal_Insertion(payment_receipt_id, voucher_id, date, description,
				credit, debit, upload_copy, dummy_journal_voucher_heads_Req, request);

	}

	@PostMapping("Generation_Journal_Managerapproval")
	public Map<String, Object> Generation_Journal_Managerapproval(
			@RequestBody Payment_receipts_Req payment_receipts_Req, HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String voucher_id = payment_receipts_Req.getVoucher_id();
		Date date = payment_receipts_Req.getDate();
		String description = payment_receipts_Req.getDescription();
		double credit = payment_receipts_Req.getCredit();
		double debit = payment_receipts_Req.getDebit();
		String upload_copy = payment_receipts_Req.getUpload_copy();
		List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req = payment_receipts_Req
				.getDummy_journal_voucher_heads_Req();

		return entryForms_Service.Generation_Journal_Managerapproval(payment_receipt_id, voucher_id, date, description,
				credit, debit, upload_copy, dummy_journal_voucher_heads_Req, request);

	}

	@PostMapping("Generation_Journal_reject")
	public Map<String, Object> Generation_Journal_reject(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();

		return entryForms_Service.Generation_Journal_reject(payment_receipt_id);

	}

	@PostMapping("Generation_Journal_RowDelete")
	public Map<String, Object> Generation_Journal_RowDelete(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		String headid = payment_receipts_Req.getHeadid();
		String subheadid = payment_receipts_Req.getSubheadid();
		long credit = payment_receipts_Req.getCredit();
		long debit = payment_receipts_Req.getDebit();

		return entryForms_Service.Generation_Journal_RowDelete(payment_receipt_id, headid, subheadid, credit, debit);

	}

//	// **************************Cadre Wise Employees*************************
//	http://172.17.205.53:9082/ofmsapi/getCadreWiseData
//	{  "dist_code": 1}
	@GetMapping("getCadreWiseData")
	public List<Map<String, String>> getCadreWiseData() {

		return entryForms_Service.getCadreWiseData();
	}

	// =====================================VIEW===============================================
	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetailsByEmpID
	// {"empid": "1001"}
	@PostMapping("getCadreWiseDetailsByEmpID")
	public List<Map<String, String>> getCadreWiseDetailsByEmpID(@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetailsByEmpID(empid);
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetailsByEmpID_SubsequentPromotions
	// {"empid": "1001"}
	@PostMapping("getCadreWiseDetailsByEmpID_SubsequentPromotions")
	public List<Map<String, String>> getCadreWiseDetailsByEmpID_SubsequentPromotions(
			@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetailsByEmpID_SubsequentPromotions(empid);
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetailsByEmpID_POWI
	// {"empid": "1001"}
	@PostMapping("getDisExp_PresentConditon")
	public List<Map<String, String>> getDisExp_PresentConditon(@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getDisExp_PresentConditon(empid);
	}

	@PostMapping("getCadreWiseDetailsByEmpID_POWI")
	public List<Map<String, String>> getCadreWiseDetailsByEmpID_POWI(@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetailsByEmpID_POWI(empid);
	}

	// ====================================EDIT======================================
	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetails_Edit_ByEmpID
	// {"empid": "1001"}

	@PostMapping("getCadreWiseDetails_Edit_ByEmpID")
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID(
			@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetails_Edit_ByEmpID(empid);
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions
	// {"empid": "1001"}
	@PostMapping("getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions")
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(
			@RequestBody CadreWise_Request cadreWise_Request) {

		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetails_Edit_ByEmpID_SubsequentPromotions(empid);
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetails_Edit_ByEmpID_POWI
	// {"empid": "1001"}
	@PostMapping("getCadreWiseDetails_Edit_ByEmpID_POWI") // ---- Place of working for Inception
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_POWI(
			@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetails_Edit_ByEmpID_POWI(empid);
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDetails_Edit_ByEmpID_Disp
	// {"empid": "1001"}
	@PostMapping("getCadreWiseDetails_Edit_ByEmpID_Disp") // ---- Place of working for Inception
	public List<Map<String, String>> getCadreWiseDetails_Edit_ByEmpID_Disp(
			@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		return entryForms_Service.getCadreWiseDetails_Edit_ByEmpID_Disp(empid);
	}

//Drop Downs
	// http://172.17.205.53:9082/ofmsapi/getCadreWiseCasteDropdown
	@GetMapping("getCadreWiseCasteDropdown")
	public List<Map<String, String>> getCadreWiseCasteDropdown() {
		return entryForms_Service.getCadreWiseCasteDropdown();
	}

//http://172.17.205.53:9082/ofmsapi/getCadreWiseDesignationDropdown
	@GetMapping("getCadreWiseDesignationDropdown")
	public List<Map<String, String>> getCadreWiseDesignationDropdown() {
		return entryForms_Service.getCadreWiseDesignationDropdown();
	}

	// http://172.17.205.53:9082/ofmsapi/getCadreWiseDistDivManDropdowns
	// { "dist_code": "1","div_code":"1", "sdiv_code":"1"}
	@PostMapping("getCadreWiseDistDivManDropdowns")
	public List<Map<String, String>> getCadreWiseDistDivManDropdowns(@RequestBody CadreWise_Request cadreWise_Request) {
		int dist_code = cadreWise_Request.getDist_code();
		int div_code = cadreWise_Request.getDiv_code();
		int sdiv_code = cadreWise_Request.getSdiv_code();
		return entryForms_Service.getCadreWiseDistDivManDropdowns(dist_code, div_code, sdiv_code);
	}

//http://172.17.205.53:9082/ofmsapi/getCadreWiseRegular_deputationDropdown
	@GetMapping("getCadreWiseRegular_deputationDropdown")
	public List<Map<String, String>> getCadreWiseWorkConditionDropdown() {
		return entryForms_Service.getCadreWiseWorkConditionDropdown();
	}

//http://172.17.205.53:9082/ofmsapi/getCadreWiseQualificationDropdown
	@GetMapping("getCadreWiseQualificationDropdown")
	public List<Map<String, String>> getCadreWiseQualificationDropdown() {
		return entryForms_Service.getCadreWiseQualificationDropdown();
	}

//http://172.17.205.53:9082/ofmsapi/getCadreWiseAppointmentDropdown
	@GetMapping("getCadreWiseAppointmentDropdown")
	public List<Map<String, String>> getCadreWiseAppointmentDropdown() {
		return entryForms_Service.getCadreWiseAppointmentDropdown();
	}

	@PutMapping("Edit_insertcadreWise")
	public List<Map<String, Object>> insertcadre(@RequestBody CadreWise_Request cadreWise_Request) {
		String empid = cadreWise_Request.getEmpid();
		String empname = cadreWise_Request.getEmpname();
		String empfather = cadreWise_Request.getEmpfather();
		int designation = cadreWise_Request.getDesignation();
		int caste = cadreWise_Request.getCaste();
		String dob = cadreWise_Request.getDob();
		String doj = cadreWise_Request.getDoj();
		String dor = cadreWise_Request.getDor();
		int prdist = cadreWise_Request.getPrdist();
		int prdiv = cadreWise_Request.getPrdiv();
		int prsdiv = cadreWise_Request.getPrsubdiv();
		int prmandal = cadreWise_Request.getPrsubmandal();
		String workcond = cadreWise_Request.getPrwork();
		int natdist = cadreWise_Request.getNatdist();
		int natdiv = cadreWise_Request.getNatmandal();
		int natsdiv = cadreWise_Request.getNatdiv();
		int natmandal = cadreWise_Request.getNatmandal();
		String otherwork = cadreWise_Request.getOtherwork();
		String othercase = cadreWise_Request.getOthercase();
		String otherworkarea = cadreWise_Request.getOtherworkarea();
		String dojpresent = cadreWise_Request.getDowpc();
		String eduappoint = cadreWise_Request.getEduappoint();
		String eduafterappoint = cadreWise_Request.getEduafterappoint();
		String cadreappoint = cadreWise_Request.getCadreappoint();
		String cadrerefno = cadreWise_Request.getCadrerefno();
		String cadrerefdate = cadreWise_Request.getCadrerefdate();
		String empaadhar = cadreWise_Request.getEmpaadhar();
		String emppan = cadreWise_Request.getEmppan();
		String emppfuno = cadreWise_Request.getEmppfuno();
		String prdiv_other = cadreWise_Request.getPrdiv_other();

		// otherdivision
		if (prdiv_other != null && (prdiv_other == "null" || prdiv_other.equals("null")))
			prdiv_other = "0";
		// other_sub_division
		String prsubdiv_other = cadreWise_Request.getPrsubdiv_other();
		if (prsubdiv_other != null && (prsubdiv_other == "null" || prsubdiv_other.equals("null")))
			prsubdiv_other = "0";
		// other_mandal
		String prmandal_other = cadreWise_Request.getPrmandal_other();
		if (prmandal_other != null && (prmandal_other == "null" || prmandal_other.equals("null")))
			prmandal_other = "0";
		// other_district2
		String natdist_other = cadreWise_Request.getNatdist_other();
		if (natdist_other != null && (natdist_other == "null" || natdist_other.equals("null")))
			natdist_other = "0";
		// other_division2
		String natdiv_other = cadreWise_Request.getNatdiv_other();
		if (natdiv_other != null && (natdiv_other == "null" || natdiv_other.equals("null")))
			natdiv_other = "0";
		// other_sub_division2
		String natsdiv_other = cadreWise_Request.getNatsdiv_other();
		if (natsdiv_other != null && (natsdiv_other == "null" || natsdiv_other.equals("null")))
			natsdiv_other = "0";
		// other_mandal2
		String natmandal_other = cadreWise_Request.getNatmandal_other();
		if (natmandal_other != null && (natmandal_other == "null" || natmandal_other.equals("null")))
			natmandal_other = "0";
		// otherqualifications
		String other_qualifications = cadreWise_Request.getOther_qualifications();
		if (other_qualifications != null && (other_qualifications == "null" || other_qualifications.equals("null")))
			prdiv_other = "";
		else {
			// otherqualifications
			other_qualifications = cadreWise_Request.getOther_qualifications();

		}

		return entryForms_Service.insertcadre(empid, empname, empfather, designation, caste, dob, doj, dor, prdist,
				prdiv, prsdiv, prmandal, workcond, natdist, natdiv, natsdiv, natmandal, otherwork, othercase,
				otherworkarea, dojpresent, eduappoint, eduafterappoint, cadreappoint, cadrerefno, cadrerefdate,
				empaadhar, emppan, emppfuno, prdiv_other, prsubdiv_other, prmandal_other, natdist_other, natdiv_other,
				natsdiv_other, natmandal_other, other_qualifications);

	}

	// http://172.17.205.53:8082/ofmsapi/Edit_Subsequent_Promotion
	// SAME API FOR EDIT AND ADD BUTTONS
	@PostMapping("Edit_Subsequent_Promotion_Add")
	public List<Map<String, Object>> Edit_Subsequent_Promotion(
			@RequestBody List<EmpdetailsNewPromotions_Request> empdetails_new_promotions) {
		return entryForms_Service.Edit_Subsequent_Promotion(empdetails_new_promotions);
	}

//http://172.17.205.53:8082/ofmsapi/Edit_Placeofworkingfrominception_Add
	// SAME API FOR EDIT AND ADD BUTTONS
	@PostMapping("Edit_Placeofworkingfrominception_Add")
	public List<Map<String, Object>> Edit_Placeofworkingfrominception_Add(
			@RequestBody List<Empdetails_NewIncept_Request> empdetails_new_incept) {
		return entryForms_Service.Edit_Placeofworkingfrominception_Add(empdetails_new_incept);
	}

	// http://172.17.205.53:8082/ofmsapi/Edit_Other_Basic_Data_Add
	// SAME API FOR EDIT AND ADD BUTTONS
	@PostMapping("Edit_Other_Basic_Data_Add")
	public List<Map<String, Object>> Edit_Other_Basic_Data_Add(
			@RequestBody List<EmpdetailsNewDisp_Request> empdetailsNewDisp) {
		return entryForms_Service.Edit_Other_Basic_Data_Add(empdetailsNewDisp);
	}
	// http://172.17.205.53:8082/ofmsapi/deleteInceptionDetails

	@DeleteMapping("deleteInceptionDetails")
	public List<Map<String, Object>> deleteInceptionDetails(@RequestBody CadreWise_Request cadreWise_Request,
			HttpServletRequest request) {
		String ip_address = request.getRemoteAddr();
		String empid = cadreWise_Request.getEmpid();
		int sno = cadreWise_Request.getSno();
		return entryForms_Service.deleteInceptionDetails(empid, ip_address, sno);

	}

	// http://172.17.205.53:8082/ofmsapi/deletePromotionsDetails
	@DeleteMapping("deletePromotionsDetails")
	public List<Map<String, Object>> deletePromotionsDetails(@RequestBody CadreWise_Request cadreWise_Request,
			HttpServletRequest request) {
		String ip_address = request.getRemoteAddr();
		String empid = cadreWise_Request.getEmpid();
		int sno = cadreWise_Request.getSno();
		return entryForms_Service.deletePromotionsDetails(empid, ip_address, sno);

	}

	// http://172.17.205.53:8082/ofmsapi/deletedisciplinaryDetails
	@DeleteMapping("deletedisciplinaryDetails")
	public List<Map<String, Object>> deletedisciplinaryDetails(@RequestBody CadreWise_Request cadreWise_Request,
			HttpServletRequest request) {
		String ip_address = request.getRemoteAddr();
		String empid = cadreWise_Request.getEmpid();
		int sno = cadreWise_Request.getSno();
		return entryForms_Service.deletedisciplinaryDetails(empid, ip_address, sno);

	}

	// http://172.17.205.53:8082/ofmsapi/Add_button_insert_cadreWise
	@PostMapping("Add_button_insert_cadreWise")
	public List<Map<String, Object>> Add_button_insert_cadreWise(@RequestBody CadreWise_Request cadreWise_Request) {
		// String empid = cadreWise_Request.getEmpid();
		String empname = cadreWise_Request.getEmpname();
		String empfather = cadreWise_Request.getEmpfather();
		int designation = cadreWise_Request.getDesignation();
		int caste = cadreWise_Request.getCaste();
		String dob = cadreWise_Request.getDob();
		String doj = cadreWise_Request.getDoj();
		String dor = cadreWise_Request.getDor();
		int prdist = cadreWise_Request.getPrdist();
		int prdiv = cadreWise_Request.getPrdiv();
		int prsdiv = cadreWise_Request.getPrsubdiv();
		int prmandal = cadreWise_Request.getPrsubmandal();
		String workcond = cadreWise_Request.getPrwork();
		int natdist = cadreWise_Request.getNatdist();
		int natdiv = cadreWise_Request.getNatmandal();
		int natsdiv = cadreWise_Request.getNatdiv();
		int natmandal = cadreWise_Request.getNatmandal();
		String otherwork = cadreWise_Request.getOtherwork();
		String othercase = cadreWise_Request.getOthercase();
		String otherworkarea = cadreWise_Request.getOtherworkarea();
		String dojpresent = cadreWise_Request.getDowpc();
		String eduappoint = cadreWise_Request.getEduappoint();
		String eduafterappoint = cadreWise_Request.getEduafterappoint();
		String cadreappoint = cadreWise_Request.getCadreappoint();
		String cadrerefno = cadreWise_Request.getCadrerefno();
		String cadrerefdate = cadreWise_Request.getCadrerefdate();
		String empaadhar = cadreWise_Request.getEmpaadhar();
		String emppan = cadreWise_Request.getEmppan();
		String emppfuno = cadreWise_Request.getEmppfuno();
		String prdiv_other = cadreWise_Request.getPrdiv_other();
		// otherdivision
		if (prdiv_other != null && (prdiv_other == "null" || prdiv_other.equals("null")))
			prdiv_other = "0";
		// other_sub_division
		String prsubdiv_other = cadreWise_Request.getPrsubdiv_other();
		if (prsubdiv_other != null && (prsubdiv_other == "null" || prsubdiv_other.equals("null")))
			prsubdiv_other = "0";
		// other_mandal
		String prmandal_other = cadreWise_Request.getPrmandal_other();
		if (prmandal_other != null && (prmandal_other == "null" || prmandal_other.equals("null")))
			prmandal_other = "0";
		// other_district2
		String natdist_other = cadreWise_Request.getNatdist_other();
		if (natdist_other != null && (natdist_other == "null" || natdist_other.equals("null")))
			natdist_other = "0";
		// other_division2
		String natdiv_other = cadreWise_Request.getNatdiv_other();
		if (natdiv_other != null && (natdiv_other == "null" || natdiv_other.equals("null")))
			natdiv_other = "0";
		// other_sub_division2
		String natsdiv_other = cadreWise_Request.getNatsdiv_other();
		if (natsdiv_other != null && (natsdiv_other == "null" || natsdiv_other.equals("null")))
			natsdiv_other = "0";
		// other_mandal2
		String natmandal_other = cadreWise_Request.getNatmandal_other();
		if (natmandal_other != null && (natmandal_other == "null" || natmandal_other.equals("null")))
			natmandal_other = "0";
		// otherqualifications
		String other_qualifications = cadreWise_Request.getOther_qualifications();
		if (other_qualifications != null && (other_qualifications == "null" || other_qualifications.equals("null")))
			prdiv_other = "";
		else {
			// otherqualifications
			other_qualifications = cadreWise_Request.getOther_qualifications();

		}
		String no_of_the_bank = cadreWise_Request.getNo_of_the_bank();
		String account_no = cadreWise_Request.getAccount_no();
		String ifsc_code = cadreWise_Request.getIfsc_code();
		return entryForms_Service.Add_button_insert_cadreWise(empname, empfather, designation, caste, dob, doj, dor,
				prdist, prdiv, prsdiv, prmandal, workcond, natdist, natdiv, natsdiv, natmandal, otherwork, othercase,
				otherworkarea, dojpresent, eduappoint, eduafterappoint, cadreappoint, cadrerefno, cadrerefdate,
				empaadhar, emppan, emppfuno, prdiv_other, prsubdiv_other, prmandal_other, natdist_other, natdiv_other,
				natsdiv_other, natmandal_other, other_qualifications, no_of_the_bank, account_no, ifsc_code);

	}

	@GetMapping("AddNewEmpid")
	public String AddNewEmpid() {
		return entryForms_Service.AddNewEmpid();

	}
	// use above add queries in edit for new _add also

//	@PostMapping("Placeofworkingfrominception_Add_new")
//	public List<Map<String, Object>> Placeofworkingfrominception_Add_new(
//			@RequestBody List<Empdetails_NewIncept_Request> empdetails_new_incept) {
//		return entryForms_Service.Placeofworkingfrominception_Add_new(empdetails_new_incept);
//	}
//
//	@PostMapping("Disp_Add_new")
//	public List<Map<String, Object>> Disp_Add_new(@RequestBody List<EmpdetailsNewDisp_Request> empdetailsNewDisp) {
//		return entryForms_Service.Disp_Add_new(empdetailsNewDisp);
//	}
//
//	@PostMapping("Subsequent_Promotion_Add_new")
//	public List<Map<String, Object>> Subsequent_Promotion_Add_new(
//			@RequestBody List<EmpdetailsNewPromotions_Request> empdetails_new_promotions) {
//		return entryForms_Service.Subsequent_Promotion_Add_new(empdetails_new_promotions);
//	}
//
	// ********************Inter Bank Transfer*******************
//	{ "payment_id":"P010706235391", "receipt_id":"R010706235391", "receiptno": "100","date": "2022-05-24", "banknameaccountno": "AB-142710100034244","paymentamount": 1000,
//	    "receiptamount":10,"balance_in_account": 300, "mode": "mode","cheque_dd_receipt_no": "100","receipt_description": "100"}
//	@PostMapping("InterBankTransferInsertion")
//	public Map<String, Object> InterBankTransferInsertion(@RequestBody Payment_receipts_Req payment_receipts_Req,
//			HttpServletRequest request) {
//
//		String payment_id = payment_receipts_Req.getPayment_id();
//		String receipt_id = payment_receipts_Req.getReceipt_id();
//		Date date = payment_receipts_Req.getDate();
//		String receiptno = payment_receipts_Req.getReceiptno();
//		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
//		String banknameaccountno1 = payment_receipts_Req.getBanknameaccountno1();
//		long paymentamount = payment_receipts_Req.getPaymentamount();
//		long receiptamount = payment_receipts_Req.getReceiptamount();
//		long balance_in_account = payment_receipts_Req.getBalance_in_account();
//		long balance_in_account1 = payment_receipts_Req.getBalance_in_account1();
//		String mode = payment_receipts_Req.getMode();
//		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
//		String receipt_description = payment_receipts_Req.getReceipt_description();
//
//		return entryForms_Service.InterBankTransferInsertion(payment_id, receipt_id, receiptno, date, banknameaccountno,banknameaccountno1,
//				balance_in_account,balance_in_account1, paymentamount, receiptamount, mode, cheque_dd_receipt_no, receipt_description);
//	}

	// ********************Inter Bank Transfer*******************
//  { "payment_id":"P010706235391", "receipt_id":"R010706235391", "receiptno": "100","date": "2022-05-24", "banknameaccountno": "AB-142710100034244","paymentamount": 1000,
//      "receiptamount":10,"balance_in_account": 300, "mode": "mode","cheque_dd_receipt_no": "100","receipt_description": "100"}
	@PostMapping("InterBankTransferInsertion")
	public Map<String, Object> InterBankTransferInsertion(@RequestBody Payment_receipts_Req payment_receipts_Req,
			HttpServletRequest request) {

		String payment_id = payment_receipts_Req.getPayment_id();
//      String receipt_id = payment_receipts_Req.getReceipt_id();
		Date date = payment_receipts_Req.getDate();
		String receiptno = payment_receipts_Req.getReceiptno();
		String banknameaccountno = payment_receipts_Req.getBanknameaccountno();
//      String banknameaccountno1 = payment_receipts_Req.getBanknameaccountno1();
		double paymentamount = payment_receipts_Req.getPaymentamount();
//     long receiptamount = payment_receipts_Req.getReceiptamount();
		double balance_in_account = payment_receipts_Req.getBalance_in_account();
//      long balance_in_account1 = payment_receipts_Req.getBalance_in_account1();
		String mode = payment_receipts_Req.getMode();
		String cheque_dd_receipt_no = payment_receipts_Req.getCheque_dd_receipt_no();
		String receipt_description = payment_receipts_Req.getReceipt_description();
		int no_of_banks = payment_receipts_Req.getNo_of_banks();
		String upload_copy = payment_receipts_Req.getUpload_copy();
		List<InterbankReq> interbankreq = payment_receipts_Req.getInterbankreq();

		return entryForms_Service.InterBankTransferInsertion(payment_id, receiptno, date, banknameaccountno,
				balance_in_account, paymentamount, mode, cheque_dd_receipt_no, receipt_description, no_of_banks,
				upload_copy, interbankreq);
	}

//SUB-HEAD MASTER

	@PostMapping("GetSubHeadsData")
	public List<Map<String, String>> GetSubHeadsData(@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String headid = payment_receipts_Req.getHeadid();
		return entryForms_Service.GetSubHeadsData(headid);
	}

	@PostMapping("AddSubheads")
	public List<Map<String, Object>> AddSubheads(@RequestBody Payment_receipts_Req payment_receipts_Req) {

		String subheadname = payment_receipts_Req.getSubheadname();
		System.out.println("subheadname" + subheadname);
		String subheadid = payment_receipts_Req.getSubheadid();
		System.out.println("subheadid" + subheadid);
		String headid = payment_receipts_Req.getHeadid();
		System.out.println("headid" + headid);

		return entryForms_Service.AddSubheads(headid, subheadid, subheadname);
	}

	@PutMapping("DeleteSubheads")
	public Map<String, Object> DeleteSubheads(@RequestBody List<SubHeadMaster> SubHeadMaster) {

		return entryForms_Service.DeleteSubheads(SubHeadMaster);
	}

//	@PutMapping("EditSubheads")
//	public List<Map<String, Object>> EditSubheads(@RequestBody List<SubHeadMaster> SubHeadMaster) {
//
//		return entryForms_Service.EditSubheads(SubHeadMaster);
//	}
	@PutMapping("EditSubheads")
	public List<Map<String, Object>> EditSubheads(@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String subheadname = payment_receipts_Req.getSubheadname();
		System.out.println("subheadname" + subheadname);

		String subheadid = payment_receipts_Req.getSubheadid();
		System.out.println("headid" + subheadid);
		String subheadseqid = payment_receipts_Req.getSubheadseqid();
		return entryForms_Service.EditSubheads(subheadname, subheadid, subheadseqid);
	}

////************************ONLINE REPORT JOURNAL VOUCHER********************
	@GetMapping("getVoucherListDropdown")
	public List<Map<String, String>> getVoucherListDropdown() {
		return entryForms_Service.getVoucherListDropdown();
	}

	@GetMapping("getDescription_Heads/{voucher_id}")
	public List<Map<String, String>> getDescription_Heads(@PathVariable String voucher_id) {
//		String voucher_id = payment_receipts_Req.getVoucher_id();
		return entryForms_Service.getDescription_Heads(voucher_id);
	}

//	//Api For complete save http://172.17.205.53:8082/ofmsapi/Generation_Journal_Insertion

	// *************Salary Journal Voucher ****************
	// http://172.17.205.53:9082/ofmsapi/getSalaryVoucherListDropdown
	@GetMapping("getSalaryVoucherListDropdown")
	public List<Map<String, String>> getSalaryVoucherListDropdown() {
		return entryForms_Service.getSalaryVoucherListDropdown();
	}
//	//Api For complete save http://172.17.205.53:8082/ofmsapi/Generation_Journal_Insertion

	// ********************Heads Master **************** in APSHCL LOGIN
	@GetMapping("HeadsDetails")
	public List<Map<String, String>> getHeadsDetails() {
		return entryForms_Service.getHeadsDetails();

	}

	@GetMapping("classificationDropdown")
	public List<Map<String, String>> classificationDropdown() {
		return entryForms_Service.classificationDropdown();

	}

	@PostMapping("HeadMasterActions")
	public Map<String, Object> HeadMasterActions(@RequestBody Payment_receipts_Req payment_receipts_Req) {

		return entryForms_Service.HeadMasterActions(payment_receipts_Req);

	}
	// *****************************Manager APPROVALS******************************

	@GetMapping("GetDataforBankEntnry_Manger")
	public List<Map<String, String>> GetDataforBankEntnry_Manger() {

		return entryForms_Service.GetDataforBankEntnry_Manger();

	}

	@GetMapping("GetDataforGR_Manger")
	public List<Map<String, String>> GetDataforGR_Manger() {

		return entryForms_Service.GetDataforGR_Manger();

	}

	@GetMapping("GetDataforGP_Manger")
	public List<Map<String, String>> GetDataforGP_Manger() {

		return entryForms_Service.GetDataforGP_Manger();

	}

	@PostMapping("GetSubHeadsDataforGR_Manger")
	public List<Map<String, Object>> GetSubHeadsDataforGR_Manger(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		return entryForms_Service.GetSubHeadsDataforGR_Manger(payment_receipt_id);

	}

	@PostMapping("GetSubHeadsDataforGJ_Manger")
	public List<Map<String, Object>> GetSubHeadsDataforGJ_Manger(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		return entryForms_Service.GetSubHeadsDataforGJ_Manger(payment_receipt_id);

	}

	@PostMapping("GetHeadbyPayment_receipt_id")
	public List<Map<String, String>> GetHeadbyPayment_receipt_id(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();
		return entryForms_Service.GetHeadbyPayment_receipt_id(payment_receipt_id);

	}

	@GetMapping("GetDataforGJ_Manger")
	public List<Map<String, String>> GetDataforGJ_Manger() {

		return entryForms_Service.GetDataforGJ_Manger();

	}

	@GetMapping("GetDataforGJ_Edit")
	public List<Map<String, String>> GetDataforGJ_Edit() {

		return entryForms_Service.GetDataforGJ_Edit();

	}

	@PostMapping("GetJV_Edit_data")
	public List<Map<String, String>> GetJV_Edit_data(@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String fromDate = payment_receipts_Req.getFromDate();
		String toDate = payment_receipts_Req.getToDate();
		String district = payment_receipts_Req.getDistrict();
		String payment_receipt_id = payment_receipts_Req.getPayment_receipt_id();

		return entryForms_Service.GetJV_Edit_data(fromDate, toDate, payment_receipt_id, district);

	}
	// GMADM (DISTRICT WISE TRANSFER)

//	http://localhost:8082/ofmsapi/GetDistrictwiseTransferData(post)
	@PostMapping("GetDistrictwiseTransferData")
	public List<Map<String, String>> getDistrictwiseTransferData(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		String emp_type = payment_receipts_Req.getEmp_type();
		String fromDistrict = payment_receipts_Req.getFromDistrict();

		return entryForms_Service.getDistrictwiseTransferData(emp_type, fromDistrict);

	}
//	{
//	    "updatedistricttranferdata": [
//	        {
//	            "emp_id": "062204091",
//	            "fromDistrict": "00",
//	            "toDistrict": "519",
//	            "emp_type": "1",
//	            "remarks": "sampletest",
//	            "tranferDate": "14/12/2023"
//	        },
//	        {
//	            "emp_id": "062010095",
//	            "fromDistrict": "00",
//	            "toDistrict": "519",
//	            "emp_type": "1",
//	            "remarks": "sampletest",
//	            "tranferDate": "14/12/2023"
//	        }
//	       
//	    ]
//	}
	@PostMapping("UpdateDistrictwiseTransferData")
	public Map<String, Object>  UpdateDistrictwiseTransferData(
			@RequestBody Payment_receipts_Req payment_receipts_Req) {
		List<UpdateDistrictTransferData> updatedistricttranferdata = payment_receipts_Req.getUpdatedistricttranferdata();

		return entryForms_Service.UpdateDistrictwiseTransferData(updatedistricttranferdata);

	}
	//New Employee registration
	@PostMapping("NewEmpRegistration")
	public  List<Map<String, Object>> NewEmpRegistration(@RequestBody PayrollReports_Request payrollReports_Request){
	
		
		return entryForms_Service.NewEmpRegistration(payrollReports_Request);
	}

}
