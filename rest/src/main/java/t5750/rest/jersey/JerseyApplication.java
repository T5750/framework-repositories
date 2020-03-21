package t5750.rest.jersey;

import java.util.Arrays;

import org.glassfish.jersey.server.ResourceConfig;

import io.swagger.jaxrs.config.SwaggerContextService;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.models.Info;
import io.swagger.models.SecurityRequirement;
import io.swagger.models.Swagger;
import io.swagger.models.auth.ApiKeyAuthDefinition;
import io.swagger.models.auth.In;
import t5750.rest.jersey.exception.MissingFileException;
import t5750.rest.jersey.provider.AuthenticationFilter;
import t5750.rest.jersey.provider.CustomLoggingFilter;
import t5750.rest.jersey.provider.GsonMessageBodyHandler;
import t5750.rest.jersey.util.Globals;

public class JerseyApplication extends ResourceConfig {
	private void updateSwagger() {
		Info info = new Info().title("Swagger Sample App").description(
				"This is a sample server, you can use the api key `special-key` to test the authorization filters.");
		Swagger swagger = new Swagger().info(info);
		swagger.securityDefinition(Globals.AUTHORIZATION,
				new ApiKeyAuthDefinition(Globals.AUTHORIZATION, In.HEADER));
		SecurityRequirement securityRequirement = new SecurityRequirement();
		securityRequirement.requirement(Globals.AUTHORIZATION);
		swagger.setSecurity(Arrays.asList(securityRequirement));
		new SwaggerContextService().updateSwagger(swagger);
	}

	public JerseyApplication() {
		packages("t5750.rest.jersey.resources", "t5750.rest.jersey.service");
		// register(LoggingFilter.class);
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
		register(CustomLoggingFilter.class);
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
		register(MissingFileException.class);
		updateSwagger();
	}
}