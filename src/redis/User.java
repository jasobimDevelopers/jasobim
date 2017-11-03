package redis;

import java.io.Serializable;  
public class User implements Serializable{  
	private static final long serialVersionUID = 2724888087391664167L;  
	private String id;  
	private String username;  
	private String password;  
	
	public User() {  
	}  
	public User(String id, String username, String password) {  
		this.id = id;  
		this.username = username;  
		this.password = password;  
	}  
	public String getId() {  
		return id;  
	}  
	public void setId(String id) {  
		this.id = id;  
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
	public static long getSerialversionuid() {  
	return serialVersionUID;  
	}  
	@Override  
	public String toString() {  
	return "User [id=" + id + ", username=" + username + ", password="  
			+ password + "]";  
	}  
}  
	