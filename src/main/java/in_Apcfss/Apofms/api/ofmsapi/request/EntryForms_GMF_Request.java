package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Timestamp;

public class EntryForms_GMF_Request {
	private String code;
	private float regular;
	private float out_sourcing;
	private float conveyance;
	private float administative;
	private float others;
	private Timestamp timestamp;
	private float capital_exp;
	private float adv_staff;
	private float liabilities;
	private String month;
	private int year;

//	/*------- District Wise Date Limits --------*/

	private String dl_sw;
	private String dl_conveyance;

	private String dl_reclib;
	private String dl_jvs;
///*------- Bank Detail Confirmation --------*/
private String status;
private String banknameaccountno;

//Employee Details Confrimation
private String emp_id;
private String designation_code;
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	public float getRegular() {
		return regular;
	}

	public float getOut_sourcing() {
		return out_sourcing;
	}

	public void setOut_sourcing(float out_sourcing) {
		this.out_sourcing = out_sourcing;
	}

	public float getConveyance() {
		return conveyance;
	}

	public void setConveyance(float conveyance) {
		this.conveyance = conveyance;
	}

	public float getAdministative() {
		return administative;
	}

	public void setAdministative(float administative) {
		this.administative = administative;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public float getCapital_exp() {
		return capital_exp;
	}

	public void setCapital_exp(float capital_exp) {
		this.capital_exp = capital_exp;
	}

	public float getAdv_staff() {
		return adv_staff;
	}

	public void setAdv_staff(float adv_staff) {
		this.adv_staff = adv_staff;
	}

	public float getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(float liabilities) {
		this.liabilities = liabilities;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setRegular(float regular) {
		this.regular = regular;
	}

	public void setOthers(float others) {
		this.others = others;
	}

	public float getOthers() {
		return others;
	}

	public String getDl_sw() {
		return dl_sw;
	}

	public void setDl_sw(String dl_sw) {
		this.dl_sw = dl_sw;
	}

	public String getDl_conveyance() {
		return dl_conveyance;
	}

	public void setDl_conveyance(String dl_conveyance) {
		this.dl_conveyance = dl_conveyance;
	}

	public String getDl_reclib() {
		return dl_reclib;
	}

	public void setDl_reclib(String dl_reclib) {
		this.dl_reclib = dl_reclib;
	}

	public String getDl_jvs() {
		return dl_jvs;
	}

	public void setDl_jvs(String dl_jvs) {
		this.dl_jvs = dl_jvs;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBanknameaccountno() {
		return banknameaccountno;
	}

	public void setBanknameaccountno(String banknameaccountno) {
		this.banknameaccountno = banknameaccountno;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}

	public String getDesignation_code() {
		return designation_code;
	}

	public void setDesignation_code(String designation_code) {
		this.designation_code = designation_code;
	}
	

	

}
