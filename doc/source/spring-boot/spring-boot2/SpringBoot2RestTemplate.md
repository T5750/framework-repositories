# Spring Boot 2 RestTemplate

## Step to do
- Create a Spring Boot project for Restful Web-Services.
- Create a Spring Boot project for Spring RestTemplate
- Create a simple Bussiness Model
- Implement RestController for Restful Web-Services
- Configure Restful-Web Services Security
- Config Spring RestTemplate Security
- Create a RestfulClient
- Make calls Restful Service by RestfulClient
- Build & Enjoy Results

### Config Spring RestTemplate Security
- For customize RestTemplate Client, we use constructure:
```
RestTemplate restTemplate = new RestTemplate(requestFactory);
public class RestTemplateBuilder {
	public static RestTemplate securityRestTemplateBuilder(String username, String password) {
		//...
	}
}
```
- Configure `HttpClient` for Username/Password credential
```
private static HttpClient createHttpClient(String username, String password) {
	CredentialsProvider provider = new BasicCredentialsProvider();
	UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
	provider.setCredentials(AuthScope.ANY, credentials);
	HttpClient client = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	return client;
}
```
- Config `ClientHttpRequestFactory` for Spring RestTemplate:
```
private static ClientHttpRequestFactory createRequestFactory(HttpClient httpClient) {
	HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	clientHttpRequestFactory.setHttpClient(httpClient);
	return clientHttpRequestFactory;
}
```
- Config Spring RestTemplate with `ClientHttpRequestFactory`:
```java
public class RestTemplateBuilder {
	public static RestTemplate securityRestTemplateBuilder(String username, String password) {
		HttpClient httpClient = createHttpClient(username, password);
		ClientHttpRequestFactory requestFactory = createRequestFactory(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}
}
```

## References
- [How to configure Spring RestTemplate Security](https://grokonez.com/java-integration/configure-spring-resttemplate-security)