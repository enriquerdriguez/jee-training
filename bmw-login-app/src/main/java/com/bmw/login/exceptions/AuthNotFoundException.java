package com.bmw.login.exceptions;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AuthNotFoundException  extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthNotFoundException(String message) {
		super(message);
	}
	
}
