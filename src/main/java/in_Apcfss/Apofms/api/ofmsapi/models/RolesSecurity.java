package in_Apcfss.Apofms.api.ofmsapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="roles")
public class RolesSecurity {
	
	@Id
	@Column(name="role_id")
	
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int role_id;
	@Column(name="role_name")
	private String role_name;
	public RolesSecurity(int role_id, String role_name) {
		super();
		this.role_id = role_id;
		this.role_name = role_name;
	}
	public RolesSecurity() {
		super();
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	@Override
	public String toString() {
		return "RolesSecurity [role_id=" + role_id + ", role_name=" + role_name + "]";
	}
	
	

}
