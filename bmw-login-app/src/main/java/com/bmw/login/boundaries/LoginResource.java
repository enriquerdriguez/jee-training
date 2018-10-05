package com.bmw.login.boundaries;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bmw.logger.Logged;
import com.bmw.login.security.BasicAuthentication;


@Path("login")
@BasicAuthentication
@Logged
public class LoginResource {

	@Inject
	private LoginService loginService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response login() {
		return Response.status(200).build();
	}
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout() {
		return Response.status(200).build();
	}
	
	
	
	
}
