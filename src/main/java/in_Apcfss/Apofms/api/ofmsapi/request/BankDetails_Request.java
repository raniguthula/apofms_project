package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class BankDetails_Request {
	
	private int accountid;
	private String bankname;
	private String branchname;
	private String accountno;
	private String operationaccount;
	private String accounttype;
	private String accountholder1;
	private String accountholder2;
	private double balance;
	private String distid;
	private String userid;
	private String banknameaccountno;
	private String accountholders;
	private String security_id;
	private double remaining_balance;
	private boolean flag;
	private Timestamp timestamp;
	private double available_balance;
	private boolean mandal_account;
	private double balance_allotted;
	private boolean balance_allotted_by_cgm;
	private Timestamp allotted_date;
	private double opening_balance_as_on_date;
	private Timestamp ee_entered_date;
	private Timestamp gmfinance_aproved_date;
	private String ee_id;
	private String gm_id;
	private boolean is_approved_by_gm;
	private int div_id;
	private String ifsc;
	private String passbook_path;
	public int getAccountid() {
		return accountid;
	}
	public void setAccountid(int accountid) {
		this.accountid = accountid;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public String getBranchname() {
		return branchname;
	}
	public void setBranchname(String branchname) {
		this.branchname = branchname;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getOperationaccount() {
		return operationaccount;
	}
	public void setOperationaccount(String operationaccount) {
		this.operationaccount = operationaccount;
	}
	public String getAccounttype() {
		return accounttype;
	}
	public void setAccounttype(String accounttype) {
		this.accounttype = accounttype;
	}
	public String getAccountholder1() {
		return accountholder1;
	}
	public void setAccountholder1(String accountholder1) {
		this.accountholder1 = accountholder1;
	}
	public String getAccountholder2() {
		return accountholder2;
	}
	public void setAccountholder2(String accountholder2) {
		this.accountholder2 = accountholder2;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getDistid() {
		return distid;
	}
	public void setDistid(String distid) {
		this.distid = distid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getBanknameaccountno() {
		return banknameaccountno;
	}
	public void setBanknameaccountno(String banknameaccountno) {
		this.banknameaccountno = banknameaccountno;
	}
	public String getAccountholders() {
		return accountholders;
	}
	public void setAccountholders(String accountholders) {
		this.accountholders = accountholders;
	}
	public String getSecurity_id() {
		return security_id;
	}
	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
	}
	public double getRemaining_balance() {
		return remaining_balance;
	}
	public void setRemaining_balance(double remaining_balance) {
		this.remaining_balance = remaining_balance;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	public double getAvailable_balance() {
		return available_balance;
	}
	public void setAvailable_balance(double available_balance) {
		this.available_balance = available_balance;
	}
	public boolean isMandal_account() {
		return mandal_account;
	}
	public void setMandal_account(boolean mandal_account) {
		this.mandal_account = mandal_account;
	}
	public double getBalance_allotted() {
		return balance_allotted;
	}
	public void setBalance_allotted(double balance_allotted) {
		this.balance_allotted = balance_allotted;
	}
	public boolean isBalance_allotted_by_cgm() {
		return balance_allotted_by_cgm;
	}
	public void setBalance_allotted_by_cgm(boolean balance_allotted_by_cgm) {
		this.balance_allotted_by_cgm = balance_allotted_by_cgm;
	}
	public Timestamp getAllotted_date() {
		return allotted_date;
	}
	public void setAllotted_date(Timestamp allotted_date) {
		this.allotted_date = allotted_date;
	}
	public double getOpening_balance_as_on_date() {
		return opening_balance_as_on_date;
	}
	public void setOpening_balance_as_on_date(double opening_balance_as_on_date) {
		this.opening_balance_as_on_date = opening_balance_as_on_date;
	}
	public Timestamp getEe_entered_date() {
		return ee_entered_date;
	}
	public void setEe_entered_date(Timestamp ee_entered_date) {
		this.ee_entered_date = ee_entered_date;
	}
	public Timestamp getGmfinance_aproved_date() {
		return gmfinance_aproved_date;
	}
	public void setGmfinance_aproved_date(Timestamp gmfinance_aproved_date) {
		this.gmfinance_aproved_date = gmfinance_aproved_date;
	}
	public String getEe_id() {
		return ee_id;
	}
	public void setEe_id(String ee_id) {
		this.ee_id = ee_id;
	}
	public String getGm_id() {
		return gm_id;
	}
	public void setGm_id(String gm_id) {
		this.gm_id = gm_id;
	}
	public boolean isIs_approved_by_gm() {
		return is_approved_by_gm;
	}
	public void setIs_approved_by_gm(boolean is_approved_by_gm) {
		this.is_approved_by_gm = is_approved_by_gm;
	}
	public int getDiv_id() {
		return div_id;
	}
	public void setDiv_id(int div_id) {
		this.div_id = div_id;
	}
	public String getIfsc() {
		return ifsc;
	}
	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}
	public String getPassbook_path() {
		return passbook_path;
	}
	public void setPassbook_path(String passbook_path) {
		this.passbook_path = passbook_path;
	}

	
	

}
