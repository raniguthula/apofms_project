package in_Apcfss.Apofms.api.ofmsapi.request;

import java.sql.Timestamp;
import java.util.List;


public class Mstheads_Req {
	List<MstSubheads_Req> MstSubheads_Req;
	private String headid;
	private String headname;
	private boolean receipts;
	private boolean payments;
	private boolean flag;
	private Timestamp timestamp;
	private String classification;
	private boolean head_office;
	private boolean jv;
	private String dlgroupid;
	private String schedule;
	private boolean ee_head;
	public List<MstSubheads_Req> getMstSubheads_Req() {
		return MstSubheads_Req;
	}
	public void setMstSubheads_Req(List<MstSubheads_Req> mstSubheads_Req) {
		MstSubheads_Req = mstSubheads_Req;
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
	public boolean isReceipts() {
		return receipts;
	}
	public void setReceipts(boolean receipts) {
		this.receipts = receipts;
	}
	public boolean isPayments() {
		return payments;
	}
	public void setPayments(boolean payments) {
		this.payments = payments;
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
	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}
	public boolean isHead_office() {
		return head_office;
	}
	public void setHead_office(boolean head_office) {
		this.head_office = head_office;
	}
	public boolean isJv() {
		return jv;
	}
	public void setJv(boolean jv) {
		this.jv = jv;
	}
	public String getDlgroupid() {
		return dlgroupid;
	}
	public void setDlgroupid(String dlgroupid) {
		this.dlgroupid = dlgroupid;
	}
	public String getSchedule() {
		return schedule;
	}
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	public boolean isEe_head() {
		return ee_head;
	}
	public void setEe_head(boolean ee_head) {
		this.ee_head = ee_head;
	}
	
	
	

}
