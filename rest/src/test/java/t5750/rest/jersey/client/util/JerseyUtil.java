package t5750.rest.jersey.client.util;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import t5750.rest.jersey.provider.GsonMessageBodyHandler;
import t5750.rest.jersey.util.Globals;

public class JerseyUtil {
	public static final String REST_URL = "http://localhost:8080/rest/jersey";

	public static final Client newClient() {
		// HttpAuthenticationFeature feature = HttpAuthenticationFeature
		// .basicBuilder().nonPreemptive()
		// .credentials(Globals.T5750, Globals.PASSWORD).build();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature
				.basic(Globals.T5750, Globals.PASSWORD);
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);
		clientConfig.register(GsonMessageBodyHandler.class);
		return ClientBuilder.newClient(clientConfig);
	}
}