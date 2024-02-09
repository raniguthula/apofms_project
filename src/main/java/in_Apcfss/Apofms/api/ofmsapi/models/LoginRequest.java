package in_Apcfss.Apofms.api.ofmsapi.models;

import java.util.List;

import javax.validation.constraints.NotBlank;

import in_Apcfss.Apofms.api.ofmsapi.request.ServiceIdsListReq;


public class LoginRequest {
    @NotBlank
    private String username;
    private String prevUserId;
    @NotBlank
    private String password;
    int parent_id;
	int service_id;
	int display_id;
	String target;
	String service_name;
	
	int role_id;
	
	List<ServiceIdsListReq> service_ids;
	
	public int getParent_id() {
		return parent_id;
	}
	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public int getDisplay_id() {
		return display_id;
	}
	public void setDisplay_id(int display_id) {
		this.display_id = display_id;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public List<ServiceIdsListReq> getService_ids() {
		return service_ids;
	}
	public void setService_ids(List<ServiceIdsListReq> service_ids) {
		this.service_ids = service_ids;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
	public String getPrevUserId() {
		return prevUserId;
	}
	public void setPrevUserId(String prevUserId) {
		this.prevUserId = prevUserId;
	}
	
}
