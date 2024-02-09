package in_Apcfss.Apofms.api.ofmsapi.models;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class UsersSecurity {
	
	
	                         

	@Id
	@Column(name="user_id")
	private String userid;
	@Column(name="user_name")
	private String user_name;
	@Column(name="password")
	private String password;
	@Column(name="is_active")
	private boolean is_active;
	
	@Column(name="role_id")
	private Integer role_id;
	
	private Timestamp pwd_change_date;
	//private String pwd_change_ip;
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="users_roles",joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name="role_id"))
	private Set<RolesSecurity> roles=new HashSet<>();

	public UsersSecurity(String userid, String user_name, String password, boolean is_active, Set<RolesSecurity> roles, Integer role_id) {
		super();
		this.userid = userid;
		this.user_name = user_name;
		this.password = password;
		this.is_active = is_active;
		this.roles = roles;
		this.role_id = role_id;
	}

	public Integer getRoleId() {
		return role_id;
	}

	public void setRoleId(Integer role_id) {
		this.role_id = role_id;
	}

	public UsersSecurity() {
		super();
	}

	public String getUser_id() {
		return userid;
	}


	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}
//
//	public String getPwd_change_ip() {
//		return pwd_change_ip;
//	}
//
//	public void setPwd_change_ip(String pwd_change_ip) {
//		this.pwd_change_ip = pwd_change_ip;
//	}

	public void setUser_id(String user_id) {
		this.userid = user_id;
	}

	public Timestamp getPwd_change_date() {
		return pwd_change_date;
	}

	public void setPwd_change_date(Timestamp pwd_change_date) {
		this.pwd_change_date = pwd_change_date;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public Set<RolesSecurity> getRoles() {
		return roles;
	}

	public void setRoles(Set<RolesSecurity> roles) {
		this.roles = roles;
	}

	
	@Override
    public String toString() {
        return "UsersSecurity [userid=" + userid + ", user_name=" + user_name + ", password=" + password + ", role_id="
                + role_id + ", is_active=" + is_active + ", roles=" + roles + "]";
    }
    
	
	
}
