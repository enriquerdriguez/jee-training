package com.bmw.login.boundaries;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.bmw.login.entity.User;
import com.bmw.login.exceptions.UserNotFoundException;


@Path("users")
public class LoginResource {

	@Inject
	private LoginService loginService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUsers() {
		List<User> users = this.loginService.getAll();
		return Response.status(200).entity(users).build();
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUser(User user) {
		User created = this.loginService.createUser(user);
		return Response.status(200).entity(user).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{email}")
	public Response getUserByEmail(@PathParam("email") String email){
		User user = this.loginService.getByEmail(email);
		return Response.status(200).entity(user).build();
	}
	
	
	
}
