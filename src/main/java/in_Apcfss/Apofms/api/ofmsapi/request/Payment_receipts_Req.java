package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class Payment_receipts_Req {

	List<Mstheads_Req> Mstheads_Req;
	List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req;
List<UpdateDistrictTransferData> updatedistricttranferdata;
	List<BankDetails_Request> bankDetails_Request;
	private long remaining_balance;
	private String payment_receipt_id;
	private String payment_id;
	private String receipt_id;
	private String receiptno;
	private Date date;
	private String cash_bank;
	private String banknameaccountno;
	private String banknameaccountno1;
	private double paymentamount;
	private double receiptamount;
	private long balance_in_account;
	private int balance_in_account1;
	private String name;
	private String mode;
	private String cheque_dd_receipt_no;
	private String receipt_description;
	private String security_id;
	private String transaction_type;
	private String no_of_subheads;
	private boolean isdelete;
	private Timestamp timestamp;
	private String user_id;
	private String upload_copy;
	private String fileName;
	// taken
	private String save_flag_Payment_Receipts_Journal;

	// Journal vocher

	private long debit;
	private int no_of_banks;
	private long credit;

	private String description;

	private String headid;
	private String headname;

	private String Subhead_Save_Flag;
	List<SubHeadMaster> SubHeadMaster;
	List<InterbankReq> interbankreq;
	private String subheadname;
	private String subheadid;
	private String subheadseqid;
private String fromDistrict;
	// Online Journal Vocher
	private String voucher_id;

	int dist_code;
	int designation_id;
	String empname;

	// ********************Heads Master **************** in APSHCL LOGIN
	private String action;
	private Boolean payments;
	private Boolean receipts;
	private Boolean flag;
	private String classification;

	//approvals
	private int olderreceiptamount;
	private int oldpaymentamount;
	private String fromDate;
	private String toDate;
	private String District;
	
	private String emp_type;
	
	
	
	public String getFromDistrict() {
		return fromDistrict;
	}

	public void setFromDistrict(String fromDistrict) {
		this.fromDistrict = fromDistrict;
	}

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

	public String getDistrict() {
		return District;
	}

	public void setDistrict(String district) {
		District = district;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public int getOlderreceiptamount() {
		return olderreceiptamount;
	}

	public void setOlderreceiptamount(int olderreceiptamount) {
		this.olderreceiptamount = olderreceiptamount;
	}
	
	

	
	public double getPaymentamount() {
		return paymentamount;
	}

	public void setPaymentamount(double paymentamount) {
		this.paymentamount = paymentamount;
	}

	public long getBalance_in_account() {
		return balance_in_account;
	}

	public void setBalance_in_account(long balance_in_account) {
		this.balance_in_account = balance_in_account;
	}

	public int getOldpaymentamount() {
		return oldpaymentamount;
	}

	public void setOldpaymentamount(int oldpaymentamount) {
		this.oldpaymentamount = oldpaymentamount;
	}

	public int getDist_code() {
		return dist_code;
	}

	public String getBanknameaccountno1() {
		return banknameaccountno1;
	}

	public int getBalance_in_account1() {
		return balance_in_account1;
	}

	public int getNo_of_banks() {
		return no_of_banks;
	}

	public void setNo_of_banks(int no_of_banks) {
		this.no_of_banks = no_of_banks;
	}

	public List<InterbankReq> getInterbankreq() {
		return interbankreq;
	}

	public void setInterbankreq(List<InterbankReq> interbankreq) {
		this.interbankreq = interbankreq;
	}

	public void setBalance_in_account1(int balance_in_account1) {
		this.balance_in_account1 = balance_in_account1;
	}

	public void setBanknameaccountno1(String banknameaccountno1) {
		this.banknameaccountno1 = banknameaccountno1;
	}

	public void setDist_code(int dist_code) {
		this.dist_code = dist_code;
	}

	public int getDesignation_id() {
		return designation_id;
	}

	public void setDesignation_id(int designation_id) {
		this.designation_id = designation_id;
	}

	public String getEmpname() {
		return empname;
	}

	public void setEmpname(String empname) {
		this.empname = empname;
	}

	public List<Mstheads_Req> getMstheads_Req() {
		return Mstheads_Req;
	}

	public void setMstheads_Req(List<Mstheads_Req> mstheads_Req) {
		Mstheads_Req = mstheads_Req;
	}

	public String getPayment_receipt_id() {
		return payment_receipt_id;
	}

	public void setPayment_receipt_id(String payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
	}

	public String getPayment_id() {
		return payment_id;
	}

	public void setPayment_id(String payment_id) {
		this.payment_id = payment_id;
	}

	public String getReceipt_id() {
		return receipt_id;
	}

	public void setReceipt_id(String receipt_id) {
		this.receipt_id = receipt_id;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getCash_bank() {
		return cash_bank;
	}

	public void setCash_bank(String cash_bank) {
		this.cash_bank = cash_bank;
	}

	public String getBanknameaccountno() {
		return banknameaccountno;
	}

	public void setBanknameaccountno(String banknameaccountno) {
		this.banknameaccountno = banknameaccountno;
	}

	
	

	
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getCheque_dd_receipt_no() {
		return cheque_dd_receipt_no;
	}

	public void setCheque_dd_receipt_no(String cheque_dd_receipt_no) {
		this.cheque_dd_receipt_no = cheque_dd_receipt_no;
	}

	public String getReceipt_description() {
		return receipt_description;
	}

	public void setReceipt_description(String receipt_description) {
		this.receipt_description = receipt_description;
	}

	public String getSecurity_id() {
		return security_id;
	}

	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
	}

	public String getTransaction_type() {
		return transaction_type;
	}

	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}

	public String getNo_of_subheads() {
		return no_of_subheads;
	}

	public void setNo_of_subheads(String no_of_subheads) {
		this.no_of_subheads = no_of_subheads;
	}

	public boolean isIsdelete() {
		return isdelete;
	}

	public void setIsdelete(boolean isdelete) {
		this.isdelete = isdelete;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getHeadid() {
		return headid;
	}

	public void setHeadid(String headid) {
		this.headid = headid;
	}

	public String getHeadname() {
		return headname;
	}

	public void setHeadname(String headname) {
		this.headname = headname;
	}

	public String getSave_flag_Payment_Receipts_Journal() {
		return save_flag_Payment_Receipts_Journal;
	}

	public void setSave_flag_Payment_Receipts_Journal(String save_flag_Payment_Receipts_Journal) {
		this.save_flag_Payment_Receipts_Journal = save_flag_Payment_Receipts_Journal;
	}

	public List<Dummy_journal_voucher_heads_Req> getDummy_journal_voucher_heads_Req() {
		return dummy_journal_voucher_heads_Req;
	}

	public void setDummy_journal_voucher_heads_Req(
			List<Dummy_journal_voucher_heads_Req> dummy_journal_voucher_heads_Req) {
		this.dummy_journal_voucher_heads_Req = dummy_journal_voucher_heads_Req;
	}

	public String getVoucher_id() {
		return voucher_id;
	}

	public void setVoucher_id(String voucher_id) {
		this.voucher_id = voucher_id;
	}



	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BankDetails_Request> getBankDetails_Request() {
		return bankDetails_Request;
	}

	public void setBankDetails_Request(List<BankDetails_Request> bankDetails_Request) {
		this.bankDetails_Request = bankDetails_Request;
	}

	public String getSubhead_Save_Flag() {
		return Subhead_Save_Flag;
	}

	public void setSubhead_Save_Flag(String subhead_Save_Flag) {
		Subhead_Save_Flag = subhead_Save_Flag;
	}

	public List<SubHeadMaster> getSubHeadMaster() {
		return SubHeadMaster;
	}

	public void setSubHeadMaster(List<SubHeadMaster> subHeadMaster) {
		SubHeadMaster = subHeadMaster;
	}

	public String getSubheadname() {
		return subheadname;
	}

	public void setSubheadname(String subheadname) {
		this.subheadname = subheadname;
	}

	public String getSubheadid() {
		return subheadid;
	}

	public void setSubheadid(String subheadid) {
		this.subheadid = subheadid;
	}

	public String getSubheadseqid() {
		return subheadseqid;
	}

	public void setSubheadseqid(String subheadseqid) {
		this.subheadseqid = subheadseqid;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Boolean getPayments() {
		return payments;
	}

	public void setPayments(Boolean payments) {
		this.payments = payments;
	}

	public Boolean getReceipts() {
		return receipts;
	}

	public void setReceipts(Boolean receipts) {
		this.receipts = receipts;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getUpload_copy() {
		return upload_copy;
	}

	public void setUpload_copy(String upload_copy) {
		this.upload_copy = upload_copy;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getRemaining_balance() {
		return remaining_balance;
	}

	public void setRemaining_balance(long remaining_balance) {
		this.remaining_balance = remaining_balance;
	}

	public long getDebit() {
		return debit;
	}

	public void setDebit(long debit) {
		this.debit = debit;
	}

	public double getReceiptamount() {
		return receiptamount;
	}

	public void setReceiptamount(double receiptamount) {
		this.receiptamount = receiptamount;
	}

	public long getCredit() {
		return credit;
	}

	public void setCredit(long credit) {
		this.credit = credit;
	}

	public List<UpdateDistrictTransferData> getUpdatedistricttranferdata() {
		return updatedistricttranferdata;
	}

	public void setUpdatedistricttranferdata(List<UpdateDistrictTransferData> updatedistricttranferdata) {
		this.updatedistricttranferdata = updatedistricttranferdata;
	}



}
