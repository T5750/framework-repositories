# Jersey Client Authentication

## HttpAuthenticationFeature
`HttpAuthenticationFeature` class provides HttpBasic and Digest client authentication capabilities. The feature work in one of 4 modes:
1. **BASIC** – It’s preemptive authentication way i.e. information is send always with each HTTP request. This mode must be combined with usage of SSL/TLS as the password is send only BASE64 encoded.
2. **BASIC NON-PREEMPTIVE** – It’s non-preemptive authentication way i.e. auth information is added only when server refuses the request with 401 status code and then the request is repeated with authentication information.
3. **DIGEST** – Http digest authentication. Does not require usage of SSL/TLS.
4. **UNIVERSAL** – Combination of basic and digest authentication in non-preemptive mode i.e. in case of 401 response, an appropriate authentication is used based on the authentication requested as defined in WWW-Authenticate HTTP header.

### 1. Basic authentication mode
```
HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("username", "password");
final Client client = ClientBuilder.newClient();
client.register(feature);
```

### 2. Basic authentication – non-prempitive mode
```
HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                                    .nonPreemptive()
                                    .credentials("username", "password")
                                    .build();
final Client client = ClientBuilder.newClient();
client.register(feature);
```

### 3. Universal mode
```
//HttpAuthenticationFeature feature = HttpAuthenticationFeature.universal("username", "password");
//Universal builder having different credentials for different schemes
HttpAuthenticationFeature feature = HttpAuthenticationFeature.universalBuilder()
                .credentialsForBasic("username1", "password1")
                .credentials("username2", "password2").build();
final Client client = ClientBuilder.newClient();
client.register(feature);
```

## Client Code
```java
public class JerseyClientApiTest {
	@Before
	public void setup() {
		HttpAuthenticationFeature feature = HttpAuthenticationFeature
				.basic(Globals.T5750, Globals.PASSWORD);
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);
		clientConfig.register(GsonMessageBodyHandler.class);
		client = ClientBuilder.newClient(clientConfig);
	}

	@Test
	public void httpGETCollection() {
		WebTarget webTarget = client.target(REST_URL).path("employees");
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.getStatusInfo());
		if (response.getStatus() == 200) {
			Employees employees = response.readEntity(Employees.class);
			List<Employee> listOfEmployees = employees.getEmployeeList();
			System.out.println(Arrays.toString(listOfEmployees
					.toArray(new Employee[listOfEmployees.size()])));
		}
	}
}
```

## References
- [Jersey REST Client Authentication](https://howtodoinjava.com/jersey/jersey-rest-client-authentication/)