package in_Apcfss.Apofms.api.ofmsapi.request;

import java.util.Date;

public class Brs_Request {
	
	String month;
	String year;
	String banknameaccountno;
	String payment_receipt_id;
	String cheque_dd_receipt_no;
	Date bank_date;
	long amount;
	String transaction_type;

	String fromDate;
	String toDate;
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getBanknameaccountno() {
		return banknameaccountno;
	}
	public void setBanknameaccountno(String banknameaccountno) {
		this.banknameaccountno = banknameaccountno;
	}
	public String getPayment_receipt_id() {
		return payment_receipt_id;
	}
	public void setPayment_receipt_id(String payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
	}
	public String getCheque_dd_receipt_no() {
		return cheque_dd_receipt_no;
	}
	public void setCheque_dd_receipt_no(String cheque_dd_receipt_no) {
		this.cheque_dd_receipt_no = cheque_dd_receipt_no;
	}
	public Date getBank_date() {
		return bank_date;
	}
	public void setBank_date(Date bank_date) {
		this.bank_date = bank_date;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public String getTransaction_type() {
		return transaction_type;
	}
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
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
	
	

}
