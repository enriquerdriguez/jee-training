package com.bmw.login.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider 
public class UserNotFoundExceptionMapper implements ExceptionMapper <UserNotFoundException>{

	@Override
	public Response toResponse(UserNotFoundException ex) {
		
		ErrorMessage error = new ErrorMessage(ex.getMessage(), 404, "");
		return Response.status(Status.NOT_FOUND).entity(error).build();
		
	}
}
