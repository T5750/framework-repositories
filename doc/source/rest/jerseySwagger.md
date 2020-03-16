# Jersey Swagger

## Adding the dependencies to your application
```xml
<dependency>
  <groupId>io.swagger</groupId>
  <artifactId>swagger-jersey2-jaxrs</artifactId>
  <version>1.5.24</version>
</dependency>
```

## Hooking up Swagger-Core in your Application
### Using a custom Application subclass
```java
public class JerseyApplication extends ResourceConfig {
	public JerseyApplication() {
		packages("t5750.rest.jersey.resources", "t5750.rest.jersey.service");
		register(GsonMessageBodyHandler.class);
		register(AuthenticationFilter.class);
		register(CustomLoggingFilter.class);
		register(ApiListingResource.class);
		register(SwaggerSerializers.class);
	}
}
```

## Configure and Initialize Swagger
### Using Swagger's Servlet in the web.xml
```xml
<servlet>
	<servlet-name>Jersey2Config</servlet-name>
	<servlet-class>io.swagger.jersey.config.JerseyJaxrsConfig</servlet-class>
	<init-param>
		<param-name>api.version</param-name>
		<param-value>1.0.0</param-value>
	</init-param>
	<init-param>
		<param-name>swagger.api.basepath</param-name>
		<param-value>http://localhost:8080/api</param-value>
	</init-param>
	<load-on-startup>2</load-on-startup>
</servlet>
```
A few things to note:
1. The `api.version` should reflect the version of your own API.
2. `swagger.api.basepath` should point to the context root of your API. This defers from server to server and how you configured your JAX-RS application.
3. There's no `<servlet-mapping>` for this servlet as it is only used for initialization and doesn't actually expose any interface.

## Swagger UI
1. Download https://github.com/swagger-api/swagger-ui/tree/2.x
2. `cp -avx swagger-ui-2.x/dist/ webapps/`
3. `vi index.html`:
	- `http://petstore.swagger.io/v2/swagger.json` -> `http://localhost:8080/rest/jersey/swagger.json`
	- `http://swagger.io` -> `http://localhost:8080/api`

## Quick Annotation Overview

Name | Description
---|-----
@Api | Marks a class as a Swagger resource.
@ApiImplicitParam | Represents a single parameter in an API Operation.
@ApiImplicitParams | A wrapper to allow a list of multiple ApiImplicitParam objects.
@ApiModel | Provides additional information about Swagger models.
@ApiModelProperty | Adds and manipulates data of a model property.
@ApiOperation | Describes an operation or typically a HTTP method against a specific path.
@ApiParam | Adds additional meta-data for operation parameters.
@ApiResponse | Describes a possible response of an operation.
@ApiResponses | A wrapper to allow a list of multiple ApiResponse objects.
@Authorization | Declares an authorization scheme to be used on a resource or an operation.
@AuthorizationScope | Describes an OAuth2 authorization scope.
@ResponseHeader | Represents a header that can be provided as part of the response.

The latest release also adds a number of annotations for adding extensions and metadata at the Swagger Definition level:

Name | Description
---|-----
@SwaggerDefinition | Definition-level properties to be added to the generated Swagger definition
@Info | General metadata for a Swagger definition
@Contact | Properties to describe the contact person for a Swagger definition
@License | Properties to describe the license for a Swagger definition
@Extension | Adds an extension with contained properties
@ExtensionProperty | Adds custom properties to an extension

## References
- [Swagger Core Jersey 2.X Project Setup 1.5](https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5)
- [Annotations 1.5.X](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)