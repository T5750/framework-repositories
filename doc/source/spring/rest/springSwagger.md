# Spring Swagger

## SwaggerConfig
```java
@EnableSwagger2
// @Profile("dev")
public class SwaggerConfig {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Bean
	public Docket userApi() {
		boolean enable = "dev".equals(activeProfile) ? true : false;
		return new Docket(DocumentationType.SWAGGER_2).enable(enable)
				.apiInfo(apiInfo()).select().build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Spring Swagger API").version("1.0")
				.build();
	}
}
```

## spring-context.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	   xmlns:context="http://www.springframework.org/schema/context"
	   xmlns:mvc="http://www.springframework.org/schema/mvc"
	   xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">
	<!-- Standard xml based mvc config-->
	<mvc:annotation-driven enable-matrix-variables="true"/>
	<context:component-scan base-package="t5750.spring.swagger.controller"/>
	<!-- Enables swgger ui-->
	<mvc:resources mapping="swagger-ui.html" location="classpath:/META-INF/resources/"/>
	<mvc:resources mapping="/webjars/**" location="classpath:/META-INF/resources/webjars/"/>
	<mvc:resources mapping="/static/**" location="/static/"/>
	<!-- Include a swagger configuration-->
	<bean name="/applicationSwaggerConfig" class="t5750.spring.swagger.config.SwaggerConfig"/>
</beans>
```

## Quick start guides
```java
@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackageClasses = {PetController.class})
public class Swagger2SpringBoot {
	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Swagger2SpringBoot.class, args);
	}

	@Bean
	public Docket petApi() {
		return new Docket(DocumentationType.SWAGGER_2)
		.select()
		.apis(RequestHandlerSelectors.any())
		.paths(PathSelectors.any())
		.build()
		.pathMapping("/")
		.directModelSubstitute(LocalDate.class, String.class)
		.genericModelSubstitutes(ResponseEntity.class)
		.alternateTypeRules(
			newRule(typeResolver.resolve(DeferredResult.class,
			typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
			typeResolver.resolve(WildcardType.class)))
		.useDefaultResponseMessages(false)
		.globalResponseMessage(RequestMethod.GET,
			newArrayList(new ResponseMessageBuilder()
				.code(500)
				.message("500 message")
				.responseModel(new ModelRef("Error"))
				.build()))
		.securitySchemes(newArrayList(apiKey()))
		.securityContexts(newArrayList(securityContext()))
		.enableUrlTemplating(true)
		.globalOperationParameters(
			newArrayList(new ParameterBuilder()
				.name("someGlobalParameter")
				.description("Description of someGlobalParameter")
				.modelRef(new ModelRef("string"))
				.parameterType("query")
				.required(true)
				.build()))
		.tags(new Tag("Pet Service", "All apis relating to pets")) 
		.additionalModels(typeResolver.resolve(AdditionalModel.class));
	}
	
	@Autowired
	private TypeResolver typeResolver;
	
	private ApiKey apiKey() {
		return new ApiKey("mykey", "api_key", "header");
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.regex("/anyPath.*"))
			.build();
	}
	
	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope
			= new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return newArrayList(new SecurityReference("mykey", authorizationScopes));
	}
	
	@Bean
	SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder()
			.clientId("test-app-client-id")
			.clientSecret("test-app-client-secret")
			.realm("test-app-realm")
			.appName("test-app")
			.scopeSeparator(",")
			.additionalQueryStringParams(null)
			.useBasicAuthenticationWithAccessCodeGrant(false)
			.build();
	}
	
	@Bean
	UiConfiguration uiConfig() {
		return UiConfigurationBuilder.builder()
			.deepLinking(true)
			.displayOperationId(false)
			.defaultModelsExpandDepth(1)
			.defaultModelExpandDepth(1)
			.defaultModelRendering(ModelRendering.EXAMPLE)
			.displayRequestDuration(false)
			.docExpansion(DocExpansion.NONE)
			.filter(false)
			.maxDisplayedTags(null)
			.operationsSorter(OperationsSorter.ALPHA)
			.showExtensions(false)
			.tagsSorter(TagsSorter.ALPHA)
			.supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
			.validatorUrl(null)
			.build();
	}
}
```

## References
- [springfox-demos GitHub](https://github.com/springfox/springfox-demos)
- [Springfox Reference Documentation](https://springfox.github.io/springfox/docs/current/)
- [SpringBoot2 配置swagger2并统一加入认证参数](https://www.jianshu.com/p/7a24d202b395)