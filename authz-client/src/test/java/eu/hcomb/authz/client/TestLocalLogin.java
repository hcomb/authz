package eu.hcomb.authz.client;



import javax.ws.rs.client.Client;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.glassfish.jersey.client.JerseyClientBuilder;

import eu.hcomb.authz.dto.UserDTO;

public class TestLocalLogin {

	public static void main(String[] args) {
		Client jerseyClient = new JerseyClientBuilder().build();
		String targetUrl = "http://localhost:8180/authz/api";
		
		UserCRUDClient client = new UserCRUDClient();
		client.setJerseyClient(jerseyClient);
		client.setTargetUrl(targetUrl);
		
		UserDTO user = client.login("alex", "pippo");
		System.out.println(ToStringBuilder.reflectionToString(user, ToStringStyle.MULTI_LINE_STYLE));


	}
}
