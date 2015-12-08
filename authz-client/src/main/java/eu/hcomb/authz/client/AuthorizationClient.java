package eu.hcomb.authz.client;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class AuthorizationClient {

	@Inject
	private Client jerseyClient;
	
	@Inject
	@Named("authz.url")
	private String targetUrl;
	
    public void setJerseyClient(Client jerseyClient) {
		this.jerseyClient = jerseyClient;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public List<String> getRolesByUser(String username) {
        WebTarget webResource = jerseyClient.target(targetUrl).path("/roles/"+username);

        Invocation.Builder invocationBuilder = webResource.request(MediaType.APPLICATION_JSON_TYPE);
        Response response = invocationBuilder.get();

        return response.readEntity(new GenericType<List<String>>(){});
    }
}
