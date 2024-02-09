package in_Apcfss.Apofms.api.ofmsapi.request;

public class Dummy_journal_voucher_heads_Req {
	int jvslno;
	String payment_receipt_id;
	String headid;
	String subheadid;
	double debit;
	double credit;
	String security_id;
	
	
	public int getJvslno() {
		return jvslno;
	}
	public void setJvslno(int jvslno) {
		this.jvslno = jvslno;
	}
	public String getPayment_receipt_id() {
		return payment_receipt_id;
	}
	public void setPayment_receipt_id(String payment_receipt_id) {
		this.payment_receipt_id = payment_receipt_id;
	}
	public String getHeadid() {
		return headid;
	}
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	public String getSubheadid() {
		return subheadid;
	}
	public void setSubheadid(String subheadid) {
		this.subheadid = subheadid;
	}

	

	public double getDebit() {
		return debit;
	}
	public void setDebit(double debit) {
		this.debit = debit;
	}
	public double getCredit() {
		return credit;
	}
	public void setCredit(double credit) {
		this.credit = credit;
	}
	public String getSecurity_id() {
		return security_id;
	}
	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
	}
	
	

}
