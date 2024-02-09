package in_Apcfss.Apofms.api.ofmsapi.request;

public class BioOtpAuthenticatorRequest {

	String uid;
	String auth_uid;
	String txnOtpresp;
	String otp_ent;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getAuth_uid() {
		return auth_uid;
	}
	public void setAuth_uid(String auth_uid) {
		this.auth_uid = auth_uid;
	}
	public String getTxnOtpresp() {
		return txnOtpresp;
	}
	public void setTxnOtpresp(String txnOtpresp) {
		this.txnOtpresp = txnOtpresp;
	}
	public String getOtp_ent() {
		return otp_ent;
	}
	public void setOtp_ent(String otp_ent) {
		this.otp_ent = otp_ent;
	}
	
}
