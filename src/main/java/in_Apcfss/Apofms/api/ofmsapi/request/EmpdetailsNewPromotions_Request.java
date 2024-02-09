package in_Apcfss.Apofms.api.ofmsapi.request;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class EmpdetailsNewPromotions_Request{

	private int sno;
	private String empname;
	private String empid;
	private String fromcadre;
	private String fromdate;
	private String fromref;
	private String tocadre;
	private String todate;
	private String toref;

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

	public String getFromcadre() {
		return fromcadre;
	}

	public void setFromcadre(String fromcadre) {
		this.fromcadre = fromcadre;
	}

	public String getFromref() {
		return fromref;
	}

	public void setFromref(String fromref) {
		this.fromref = fromref;
	}

	public String getTocadre() {
		return tocadre;
	}

	public void setTocadre(String tocadre) {
		this.tocadre = tocadre;
	}


	
	public String getFromdate() {
		return fromdate;
	}

	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}

	public String getTodate() {
		return todate;
	}

	public void setTodate(String todate) {
		this.todate = todate;
	}

	public String getToref() {
		return toref;
	}

	public void setToref(String toref) {
		this.toref = toref;
	}

}
