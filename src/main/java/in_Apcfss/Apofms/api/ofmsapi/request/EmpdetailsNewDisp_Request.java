package in_Apcfss.Apofms.api.ofmsapi.request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class EmpdetailsNewDisp_Request {
	
	private int sno;
	private String empname;
	private String empid;
	private String displanary;
	private String discase;
	public int getSno() {
		return sno;
	}
	public void setSno(int sno) {
		this.sno = sno;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public String getEmpid() {
		return empid;
	}
	public void setEmpid(String empid) {
		this.empid = empid;
	}
	public String getDisplanary() {
		return displanary;
	}
	public void setDisplanary(String displanary) {
		this.displanary = displanary;
	}
	public String getDiscase() {
		return discase;
	}
	public void setDiscase(String discase) {
		this.discase = discase;
	}
	
	

}
