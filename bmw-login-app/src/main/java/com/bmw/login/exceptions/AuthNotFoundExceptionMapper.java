package com.bmw.login.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthNotFoundExceptionMapper implements ExceptionMapper <AuthNotFoundException> {

	@Override
	public Response toResponse(AuthNotFoundException ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage() , 500, "Generic Error");
		return Response.status(403).entity(error).build();
	}
}
