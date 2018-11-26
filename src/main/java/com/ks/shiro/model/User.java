package com.ks.shiro.model;

import java.io.Serializable;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;  
	private String password;  
	private Role role;  
	  
	public Role getRole() {  
	    return role;  
	}  
	  
	public void setRole(Role role) {  
	    this.role = role;  
	}  
	  
	public String getName() {  
	    return name;  
	}  
	  
	public void setName(String name) {  
	    this.name = name;  
	}  
	  
	public String getPassword() {  
	    return password;  
	}  
	  
	public void setPassword(String password) {  
	    this.password = password;  
	}  
	  
	public User() {  
	    // TODO Auto-generated constructor stub  
	}  
	  
	public User(String name, String password) {  
	    this.name = name;  
	    this.password = password;  
	}
}
