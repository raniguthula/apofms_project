package in_Apcfss.Apofms.api.ofmsapi.request;

import java.util.List;

public class MonthlyPaybillRequest {

	String emp_type;
	String payment_type;
	String confirm_month;
	String year;
	List<Generate_paybill_Updatelist> genertedPaybillList;
	List<Generate_Confirm_Updatelist> GenerateConfirmlist;

	String fromYear;
	String toYear;
	String fromMonth;
	String toMonth;
	String emp_id;
String district;

	public String getDistrict() {
	return district;
}

public void setDistrict(String district) {
	this.district = district;
}

	public String getEmp_type() {
		return emp_type;
	}

	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public String getConfirm_month() {
		return confirm_month;
	}

	public void setConfirm_month(String confirm_month) {
		this.confirm_month = confirm_month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}



	public List<Generate_paybill_Updatelist> getGenertedPaybillList() {
		return genertedPaybillList;
	}

	public void setGenertedPaybillList(List<Generate_paybill_Updatelist> genertedPaybillList) {
		this.genertedPaybillList = genertedPaybillList;
	}

	public List<Generate_Confirm_Updatelist> getGenerateConfirmlist() {
		return GenerateConfirmlist;
	}

	public void setGenerateConfirmlist(List<Generate_Confirm_Updatelist> generateConfirmlist) {
		GenerateConfirmlist = generateConfirmlist;
	}

	public String getFromYear() {
		return fromYear;
	}

	public void setFromYear(String fromYear) {
		this.fromYear = fromYear;
	}

	public String getToYear() {
		return toYear;
	}

	public void setToYear(String toYear) {
		this.toYear = toYear;
	}

	public String getFromMonth() {
		return fromMonth;
	}

	public void setFromMonth(String fromMonth) {
		this.fromMonth = fromMonth;
	}

	public String getToMonth() {
		return toMonth;
	}

	public void setToMonth(String toMonth) {
		this.toMonth = toMonth;
	}

	public String getEmp_id() {
		return emp_id;
	}

	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	

}
