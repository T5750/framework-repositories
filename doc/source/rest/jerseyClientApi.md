# Jersey Client API

## Jersey ClientBuilder
**JAX-RS Client API** is a designed to allow fluent programming model. To create jersey client follow these steps:
1. Use `ClientBuilder.newClient()` static method.
2. Use `client.target()` method on above obtained client instance.
3. Get `Invocation.Builder` using `webTarget.request()` method on `WebTarget` instance obtained in second step.
4. Execute `invocationBuilder.get()`, `put()`, `post()` or `delete()` methods to invoke corresponding REST APIs.

```
Client client = ClientBuilder.newClient( new ClientConfig().register( LoggingFilter.class ) );
WebTarget webTarget = client.target("http://localhost:8080/rest/jersey").path("employees");
Invocation.Builder invocationBuilder =  webTarget.request(MediaType.APPLICATION_XML);
Response response = invocationBuilder.post(Entity.entity(emp, MediaType.APPLICATION_XML));
```

## Results
- `JerseyClientApiTest`

## References
- [Jersey Client Example – Jersey 2 Client API](https://howtodoinjava.com/jersey/jersey-restful-client-examples/)
- [Jersey 2.30.1 User Guide](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/user-guide.html)