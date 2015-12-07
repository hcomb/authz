package eu.hcomb.authz.resources;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

import eu.hcomb.authz.service.ProfileService;

@Path("/roles")
@Produces(MediaType.APPLICATION_JSON)
public class RolesResource {
	
	@Inject
	protected ProfileService profileService;
	
	@Path("{username}")
    @GET
    @Timed
    public List<String> getRoles(@PathParam("username") String username) {

		return profileService.getRolesByUser(username);
    }

}
