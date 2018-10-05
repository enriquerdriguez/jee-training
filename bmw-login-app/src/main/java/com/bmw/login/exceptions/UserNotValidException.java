package com.bmw.login.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class UserNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotValidException(String message) {
		super(message);
	}

}
