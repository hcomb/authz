package eu.hcomb.authz.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import eu.hcomb.authz.UserEvents;
import eu.hcomb.authz.dto.UserDTO;
import eu.hcomb.authz.service.UserService;
import eu.hcomb.common.service.EventEmitter;

@Path("/users")
@Api(tags="user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	protected UserService userService;
	
    @Inject 
    protected EventEmitter eventEmitter;

    @GET
    @Timed
    @ApiOperation(value="User list.", notes = "Get the users list.")
    @RolesAllowed("ADMIN")
    public List<UserDTO> list() {
    	
    	eventEmitter.emit(UserEvents.LIST, "");
    	
    	return userService.getAllUsers();
    }

    @GET
    @Timed
    @Path("/{id}")
    @ApiOperation(value="User by id.", notes = "Get a single user by id.")
    @RolesAllowed("ADMIN")
    public UserDTO get(@PathParam("id") Long id) {

    	eventEmitter.emit(UserEvents.READ, id);

    	return userService.getUserById(id);
    }

    @POST
    @Timed
    @ApiOperation(value="Add User.", notes = "Add a new user.")
    @RolesAllowed("ADMIN")
    public UserDTO add(UserDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {
    	UserDTO ret = userService.insertUser(user);
    	
    	eventEmitter.emit(UserEvents.CREATE, ret);
    	
    	return ret;

    }
    
    @PUT
    @Timed
    @Path("/{id}")
    @ApiOperation(value="Update User", notes = "Update an user by id.")
    @RolesAllowed("ADMIN")
    public UserDTO update(@PathParam("id") Long id, UserDTO user) throws NoSuchAlgorithmException, InvalidKeySpecException {

    	user.setId(id);

    	eventEmitter.emit(UserEvents.UPDATE, user);

    	return userService.updateUser(user);
    }
    
    @DELETE
    @Timed
    @Path("/{id}")
    @ApiOperation(value="Delete User", notes = "Delete an user by id.")
    @RolesAllowed("ADMIN")
    public void delete(@PathParam("id") Long id) throws NoSuchAlgorithmException, InvalidKeySpecException {

    	eventEmitter.emit(UserEvents.DELETE, id);

    	userService.deleteUser(id);
    }

}
