package t5750.springbootjersey.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		// register(UserResource.class);
		packages("t5750.springbootjersey.service");
	}
}
