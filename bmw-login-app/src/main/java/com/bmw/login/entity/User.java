package com.bmw.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "USERS")
@NamedQueries({
	@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.byEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
})
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Version
	private long version;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	 
	/**
	 * @param id
	 * @param version
	 * @param email
	 * @param password
	 */
	public User(long id, long version, String email, String password) {
		super();
		this.id = id;
		this.version = version;
		this.email = email;
		this.password = password;
	}
	
	/**
	 * Empty constructor
	 */
	public User() {
		super();
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the version
	 */
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void updateUser(User user) {
		this.email = user.getEmail();
		this.password = user.getPassword();
	}
	
	
	

}
