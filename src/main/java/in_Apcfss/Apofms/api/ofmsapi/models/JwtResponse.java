package in_Apcfss.Apofms.api.ofmsapi.models;

import java.util.List;
import java.util.Map;

public class JwtResponse {

	private String status;
	private String msg;
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	/* private String email; */
	private List<String> roles;

	private String flag_password_change;

	public JwtResponse() {
    }

	public JwtResponse(String status, String accessToken, String username,String flag_password_change, List<String> roles) {
		this.status = status;
		this.token = accessToken;
		this.username = username;
		/* this.email = email; */
		this.flag_password_change=flag_password_change;
		this.roles = roles;

	}
	
	

	public String getFlag_password_change() {
		return flag_password_change;
	}

	public void setFlag_password_change(String flag_password_change) {
		this.flag_password_change = flag_password_change;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getRoles() {
		return roles;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	/*
	 * private String token; private String type = "Bearer"; private Long id;
	 * private String username; private String email; private List<String> roles;
	 * 
	 * public JwtResponse(String accessToken, Long id, String username, List<String>
	 * roles) { this.token = accessToken; this.id = id; this.username = username;
	 * this.email = email; this.roles = roles; }
	 * 
	 * public String getAccessToken() { return token; }
	 * 
	 * public void setAccessToken(String accessToken) { this.token = accessToken; }
	 * 
	 * public String getTokenType() { return type; }
	 * 
	 * public void setTokenType(String tokenType) { this.type = tokenType; }
	 * 
	 * public Long getId() { return id; }
	 * 
	 * public void setId(Long id) { this.id = id; }
	 * 
	 * 
	 * public String getEmail() { return email; }
	 * 
	 * public void setEmail(String email) { this.email = email; }
	 * 
	 * public String getUsername() { return username; }
	 * 
	 * public void setUsername(String username) { this.username = username; }
	 * 
	 * public List<String> getRoles() { return roles; }
	 */
}