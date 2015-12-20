package eu.hcomb.authz.client;

import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

import eu.hcomb.authn.dto.TokenDTO;
import eu.hcomb.authz.dto.UserDTO;
import eu.hcomb.common.client.BaseClient;

@Singleton
public class UserCRUDClient extends BaseClient {

	@Inject
	@Named("authz.url")
	private String targetUrl;
	
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public UserDTO login(String username, String password) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/login");

        Invocation.Builder invocationBuilder = webResource.request();
        
        Form form = new Form(); 
        form.param("username", username); 
        form.param("password", password); 
        
        Response response = invocationBuilder.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));

        expect(response, new int[]{200, 204});

        if(response.getStatus() == 204)
        	return null;
        else     
        	return response.readEntity(UserDTO.class);
	}
	
	public int deleteUser(TokenDTO token, Long id) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/users/"+id);

        Invocation.Builder invocationBuilder = webResource.request();
        
        Response response = invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer "+token.getValue()).delete();
        
        expect(response, new int[]{204});

        return response.getStatus();
	}

	public UserDTO updateUser(TokenDTO token, Long id, UserDTO user) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/users/"+id);

        Invocation.Builder invocationBuilder = webResource.request();
        
        Response response = invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer "+token.getValue()).put(Entity.entity(user, MediaType.APPLICATION_JSON));

        expect(response, new int[]{200});

        return response.readEntity(UserDTO.class);
	}
	
	public UserDTO insertUser(TokenDTO token, UserDTO user) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/users");

        Invocation.Builder invocationBuilder = webResource.request();
        
        Response response = invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer "+token.getValue()).post(Entity.entity(user, MediaType.APPLICATION_JSON));

        expect(response, new int[]{200});

        return response.readEntity(UserDTO.class);
	}
	
	public UserDTO getUserById(TokenDTO token, Long id) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/users/"+id);

        Invocation.Builder invocationBuilder = webResource.request();
        
        Response response = invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer "+token.getValue()).get();

        expect(response, new int[]{200,204});

        if(response.getStatus() == 204)
        	return null;
        else
        	return response.readEntity(UserDTO.class);
	}
	
	public List<UserDTO> getAllUsers(TokenDTO token) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/users");

        Invocation.Builder invocationBuilder = webResource.request();
        
        Response response = invocationBuilder.header(HttpHeaders.AUTHORIZATION, "Bearer "+token.getValue()).get();

        expect(response, new int[]{200});

        return response.readEntity(new GenericType<List<UserDTO>>(){});
	}

}
