package eu.hcomb.authz.client;



import java.security.SecureRandom;
import java.util.List;

import javax.ws.rs.client.Client;

import org.glassfish.jersey.client.JerseyClientBuilder;

import eu.hcomb.authn.client.AuthenticationClient;
import eu.hcomb.authn.dto.TokenDTO;
import eu.hcomb.authz.dto.UserDTO;

public class TestUserCRUDClient {

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

		System.out.println("1 =====");

		for (int i = 0; i < 1000; i++) {
		
			Thread.sleep(1000);
			
			List<UserDTO> users = client.getAllUsers(token);
			System.out.println(users);
	
			System.out.println("3 =====");
			for (UserDTO dto : users) {
				UserDTO check = client.getUserById(token, dto.getId());
				if(check!=null)
					System.out.println(check.getUsername());
			}
			System.out.println("4 =====");
			UserDTO newUser = new UserDTO();
			newUser.setUsername(""+new SecureRandom().nextDouble());
			newUser.setPassword("test345");
			UserDTO check = client.insertUser(token, newUser);
			System.out.println(check.getId() + " - " + check.getUsername());
			
			System.out.println("5 =====");
			newUser.setUsername(""+new SecureRandom().nextDouble());
			newUser.setPassword("bbbbbbb");
			check = client.updateUser(token, check.getId(), newUser);
			System.out.println(check.getId() + " - " + check.getUsername());
			
			System.out.println("6 =====");
	
			int status = client.deleteUser(token, check.getId());
			System.out.println(status);

		}

	}
}
