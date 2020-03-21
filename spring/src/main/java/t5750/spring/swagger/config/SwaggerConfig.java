package t5750.spring.swagger.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import io.swagger.models.auth.In;

@EnableSwagger2
// @Profile("dev")
public class SwaggerConfig {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Bean
	public Docket userApi() {
		boolean enable = "dev".equals(activeProfile) ? true : false;
		return new Docket(DocumentationType.SWAGGER_2).enable(enable)
				.apiInfo(apiInfo()).select().build()
				// .globalOperationParameters(globalParameters())
				.securitySchemes(securitySchemes())
				.securityContexts(securityContexts());
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Swagger API").version("1.0")
				.build();
	}

	/**
	 * Allows globally configuration of default path-/request-/headerparameters
	 * which are common for every rest operation of the api
	 */
	private List<Parameter> globalParameters() {
		ParameterBuilder parameterBuilder = new ParameterBuilder();
		List<Parameter> parameters = new ArrayList<>();
		parameterBuilder.name(HttpHeaders.AUTHORIZATION)
				.defaultValue(HttpHeaders.AUTHORIZATION)
				.modelRef(new ModelRef("string"))
				.parameterType(In.HEADER.name()).required(false).build();
		parameters.add(parameterBuilder.build());
		return parameters;
	}

	private List<ApiKey> securitySchemes() {
		return Arrays.asList(new ApiKey(HttpHeaders.AUTHORIZATION,
				HttpHeaders.AUTHORIZATION, In.HEADER.name()));
	}

	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("/auth.*")).build());
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope(
				"global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference(HttpHeaders.AUTHORIZATION,
				authorizationScopes));
	}

	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder
				.builder()
				.deepLinking(true)
				.displayOperationId(false)
				.defaultModelsExpandDepth(1)
				.defaultModelExpandDepth(1)
				.defaultModelRendering(ModelRendering.EXAMPLE)
				.displayRequestDuration(false)
				.docExpansion(DocExpansion.LIST)
				.filter(false)
				.maxDisplayedTags(null)
				.operationsSorter(OperationsSorter.ALPHA)
				.showExtensions(false)
				.tagsSorter(TagsSorter.ALPHA)
				.supportedSubmitMethods(
						UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
				.validatorUrl(null).build();
	}
}