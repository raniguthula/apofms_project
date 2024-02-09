package in_Apcfss.Apofms.api.ofmsapi.request;

public class UserRequest {
	
	String oldpassword ;
	String newpassword ;
	String confirm_newpassword;
	public String getOldpassword() {
		return oldpassword;
	}
	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}
	public String getNewpassword() {
		return newpassword;
	}
	public void setNewpassword(String newpassword) {
		this.newpassword = newpassword;
	}
	public String getConfirm_newpassword() {
		return confirm_newpassword;
	}
	public void setConfirm_newpassword(String confirm_newpassword) {
		this.confirm_newpassword = confirm_newpassword;
	}
	
	

}
