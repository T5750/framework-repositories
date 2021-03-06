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
### Swagger UI 2.x
1. Download https://github.com/swagger-api/swagger-ui/tree/2.x
2. `cp -avx swagger-ui-2.x/dist/ webapps/`
3. `vi index.html`:
	- `http://petstore.swagger.io/v2/swagger.json` -> `http://localhost:8080/rest/jersey/swagger.json`
	- `http://swagger.io` -> `/api`

### Swagger UI 3.x
https://github.com/swagger-api/swagger-ui

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

## Config
### Setting scan.all.resources
```xml
<init-param>
	<param-name>scan.all.resources</param-name>
	<param-value>true</param-value>
</init-param>
```

### CORS Support
- `ApiOriginFilter`

## Authorization
### Loading a Swagger spec (.json/.yaml) that is protected by Basic auth
`vi index.html`
```html
const url = "http://localhost:8080/rest/jersey/swagger.json";
const ui = SwaggerUIBundle({
  url: url,
  requestInterceptor: (req) => {
    if (req.url === url) {
      req.headers.Authorization = "Basic " + btoa("t5750" + ":" + "123");
    }
    return req;
  }
```

### Auto-add the Authorization header to all "try it out" requests
Swagger UI 2.x: `vi index.html`
```html
window.swaggerUi.load();
swaggerUi.api.clientAuthorizations.add("key", new SwaggerClient.ApiKeyAuthorization("Authorization", "Basic dDU3NTA6MTIz", "header"));
```
Swagger UI 3.x: `vi index.html`
```html
requestInterceptor: (req) => {
  if (!req.loadSpec) {
    req.headers.Authorization = 'Basic ' + btoa('t5750:123');
  }
  return req;
}
```
Or `vi index.html`
```html
onComplete: function() {
  // "basicAuth" is the key name of the security scheme in securityDefinitions
  // ui.preauthorizeBasic("basicAuth", "username", "password");
  ui.preauthorizeApiKey("Authorization", 'Basic ' + btoa('t5750:123'));
}
```
Or `JerseyApplication`
```
Swagger swagger = new Swagger().info(info);
swagger.securityDefinition(Globals.AUTHORIZATION,
		new ApiKeyAuthDefinition(Globals.AUTHORIZATION, In.HEADER));
SecurityRequirement securityRequirement = new SecurityRequirement();
securityRequirement.requirement(Globals.AUTHORIZATION);
swagger.setSecurity(Arrays.asList(securityRequirement));
new SwaggerContextService().updateSwagger(swagger);
```

## References
- [Swagger Core Jersey 2.X Project Setup 1.5](https://github.com/swagger-api/swagger-core/wiki/Swagger-Core-Jersey-2.X-Project-Setup-1.5)
- [Annotations 1.5.X](https://github.com/swagger-api/swagger-core/wiki/Annotations-1.5.X)
- [swagger-samples](https://github.com/swagger-api/swagger-samples/blob/master/java/java-jaxrs-no-annotations/src/main/webapp/WEB-INF/web.xml)
- [Adding Basic Authorization for Swagger-UI](https://stackoverflow.com/questions/31057343/adding-basic-authorization-for-swagger-ui)
- [Add possibility to loading a Swagger spec (.json/.yaml) that is protected by Basic auth. UI version 3.0](https://github.com/swagger-api/swagger-ui/issues/2793)
- [How Can I add basic auth in swagger 3.0 automatically with out the user to type in at authorize button?](https://stackoverflow.com/questions/49422437/how-can-i-add-basic-auth-in-swagger-3-0-automatically-with-out-the-user-to-type)
- [Add JWT authorization header in Swagger v3](https://github.com/swagger-api/swagger-ui/issues/2915)