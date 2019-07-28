package org.safebusiness;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * User that will exist in the application context
 * @author Kinene
 */
@Entity
public class User extends Person {
	@Column
	private String username;
	@Column
	private String password;
	
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
	
}
