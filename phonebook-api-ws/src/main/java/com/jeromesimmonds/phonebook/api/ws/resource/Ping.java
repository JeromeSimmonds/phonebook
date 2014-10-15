package com.jeromesimmonds.phonebook.api.ws.resource;

import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/ping")
public class Ping {
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response ping() {
		return Response.status(Response.Status.OK).entity((new Date()).toString()).build();
	}
}