package eu.hcomb.authz.client;



import java.security.SecureRandom;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;

import eu.hcomb.authn.client.AuthenticationClient;
import eu.hcomb.authn.dto.TokenDTO;
import eu.hcomb.authz.dto.UserDTO;

public class TestCreateUser {

	public static void main(String[] args) throws Exception {
		Client jerseyClient = new JerseyClientBuilder().build();
		String targetUrlUsers = "http://localhost:8180/authz/api";
		String targetUrlAuthn = "http://localhost:8280/authn/api";
		
		AuthenticationClient authClient = new AuthenticationClient();
		authClient.setJerseyClient(jerseyClient);
		authClient.setTargetUrl(targetUrlAuthn);
		
		UserCRUDClient client = new UserCRUDClient();
		client.setJerseyClient(jerseyClient);
		client.setTargetUrl(targetUrlUsers);
		
		TokenDTO token = authClient.login("alex", "pippo");

		System.out.println(token.getValue());


		UserDTO newUser = new UserDTO();
		newUser.setUsername(""+new SecureRandom().nextDouble());
		newUser.setPassword("test345");
		UserDTO check = client.insertUser(token, newUser);
		System.out.println(check.getId() + " - " + check.getUsername());


	}
}
