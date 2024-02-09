package in_Apcfss.Apofms.api.ofmsapi.request;

public class UpdateDistrictTransferData {
	
	private String fromDistrict;
	private String ToDistrict;
	private String emp_id;
	private String emp_type;
	private String remarks;
	private String tranferDate;
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getTranferDate() {
		return tranferDate;
	}
	public void setTranferDate(String tranferDate) {
		this.tranferDate = tranferDate;
	}
	public String getFromDistrict() {
		return fromDistrict;
	}
	public void setFromDistrict(String fromDistrict) {
		this.fromDistrict = fromDistrict;
	}
	public String getToDistrict() {
		return ToDistrict;
	}
	public void setToDistrict(String toDistrict) {
		ToDistrict = toDistrict;
	}
	public String getEmp_id() {
		return emp_id;
	}
	public void setEmp_id(String emp_id) {
		this.emp_id = emp_id;
	}
	public String getEmp_type() {
		return emp_type;
	}
	public void setEmp_type(String emp_type) {
		this.emp_type = emp_type;
	}
	
	

}
