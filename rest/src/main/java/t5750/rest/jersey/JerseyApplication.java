package t5750.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import t5750.rest.jersey.provider.AuthenticationFilter;
import t5750.rest.jersey.provider.CustomLoggingFilter;
import t5750.rest.jersey.provider.GsonMessageBodyHandler;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;

public class JerseyApplication extends ResourceConfig {
	public JerseyApplication() {
		packages("t5750.rest.jersey.resources", "t5750.rest.jersey.service");
		// register(LoggingFilter.class);
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
		register(CustomLoggingFilter.class);
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
	}
}