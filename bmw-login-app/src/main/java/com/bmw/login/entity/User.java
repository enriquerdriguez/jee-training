package com.bmw.login.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "USERS",
		indexes = {
				@Index(name = "index_users_email", columnList = "EMAIL", unique = true)
		})
@NamedQueries({
	@NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "User.byEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.SaltbyEmail", query = "SELECT u.salt FROM User u WHERE u.email = :email"),
	@NamedQuery(name = "User.validation", query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password")
})


public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@Version
	private long version;
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	//@Column(name = "SALT", nullable = true)
	@JsonProperty(access = Access.WRITE_ONLY)
	private byte[] salt;
	
	 

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
//	@JsonIgnore
	public long getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
//	@JsonProperty
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

	/**
	 * @return the salt
	 */
	public byte[] getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt;
	}
	
	
	
	

}
