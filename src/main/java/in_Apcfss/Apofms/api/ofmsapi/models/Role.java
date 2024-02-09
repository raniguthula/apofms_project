package in_Apcfss.Apofms.api.ofmsapi.models;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer role_id;
//private String role_name;
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	
	public Integer getRole_id() {
		return role_id;
	}
	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
//	public String getRole_name() {
//		return role_name;
//	}
//	public void setRole_name(String role_name) {
//		this.role_name = role_name;
//	}
//	public String getRole_name() {
//		return role_name;
//	}
//	public void setRole_name(String role_name) {
//		this.role_name = role_name;
//	}
	
	


}