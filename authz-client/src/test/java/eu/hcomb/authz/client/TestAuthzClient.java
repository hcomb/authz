package eu.hcomb.authz.client;



import java.util.List;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;

public class TestAuthzClient {

	public static void main(String[] args) {
		
		Client jerseyClient = new JerseyClientBuilder().build();
		String targetUrl = "http://localhost:8180/authz/rest";
		
		AuthorizationClient client = new AuthorizationClient(jerseyClient, targetUrl);
		List<String> roles = client.getRolesByUser("alex");
		for (String role : roles) {
			System.out.println(" * "+role);
		}
		
		
	}
}
