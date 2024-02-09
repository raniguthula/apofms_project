package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Timestamp;

public class MstSubheads_Req {
	private String subheadseqid;
	private String subheadid;
	private String subheadname;
	private String headid;
	private String security_id;
	private boolean isdelete;
	private Timestamp timestamp;
	public String getSubheadseqid() {
		return subheadseqid;
	}
	public void setSubheadseqid(String subheadseqid) {
		this.subheadseqid = subheadseqid;
	}
	public String getSubheadid() {
		return subheadid;
	}
	public void setSubheadid(String subheadid) {
		this.subheadid = subheadid;
	}
	public String getSubheadname() {
		return subheadname;
	}
	public void setSubheadname(String subheadname) {
		this.subheadname = subheadname;
	}
	public String getHeadid() {
		return headid;
	}
	public void setHeadid(String headid) {
		this.headid = headid;
	}
	public String getSecurity_id() {
		return security_id;
	}
	public void setSecurity_id(String security_id) {
		this.security_id = security_id;
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
	
	
}
