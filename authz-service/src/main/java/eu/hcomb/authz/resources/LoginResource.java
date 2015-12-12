package eu.hcomb.authz.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import eu.hcomb.authz.dto.UserDTO;
import eu.hcomb.authz.service.UserService;

@Api(tags="login")
@Path("/login")
@Produces(MediaType.APPLICATION_JSON)
public class LoginResource {

	@Inject
	protected UserService userService;
	
    @POST
    @Timed
    @ApiOperation(value="User by username and password.", notes = "Get an user with the given username and password")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public UserDTO login(
    		@ApiParam(required = true) @FormParam("username") String username, 
    		@ApiParam(required = true) @FormParam("password") String password
    	) {

    	UserDTO user = userService.login(username, password);

    	return user;
    }

}