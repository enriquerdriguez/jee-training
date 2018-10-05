package com.bmw.login.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper <Throwable>{
	
	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage error = new ErrorMessage(ex.getMessage() , 500, "Generic Error");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(error).build();
	}
}
