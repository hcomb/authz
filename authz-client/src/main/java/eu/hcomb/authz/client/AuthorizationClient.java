package eu.hcomb.authz.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AuthorizationClient {

	private Client jerseyClient;
	private String targetUrl;

    public AuthorizationClient(Client jerseyClient, String targetUrl) {
        this.jerseyClient = jerseyClient;
        this.targetUrl = targetUrl;
    }
    
    public List<String> getRolesByUser(String username) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/roles/"+username);

        Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();

        return response.readEntity(new GenericType<List<String>>(){});
    }
}
