package com.bmw.login.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class UserNotFoundException extends RuntimeException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1114024284904025718L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
