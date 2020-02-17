# HttpClient Basic Authentication

## Basic Authentication With the API
```
CredentialsProvider provider = new BasicCredentialsProvider();
UsernamePasswordCredentials credentials
 = new UsernamePasswordCredentials("user1", "user1Pass");
provider.setCredentials(AuthScope.ANY, credentials);
HttpClient client = HttpClientBuilder.create()
  .setDefaultCredentialsProvider(provider)
  .build();
HttpResponse response = client.execute(
  new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION));
int statusCode = response.getStatusLine()
  .getStatusCode();
assertThat(statusCode, equalTo(HttpStatus.SC_OK));
```
The entire **Client-Server communication is now clear**:
- the Client sends the HTTP Request with no credentials
- the Server sends back a challenge
- the Client negotiates and identifies the right authentication scheme
- the Client sends **a second Request**, this time with credentials

## Preemptive Basic Authentication
Out of the box, the HttpClient doesn't do preemptive authentication. Instead, this has to be an explicit decision made by the client.

First, **we need to create the HttpContext – pre-populating it with an authentication cache** with the right type of authentication scheme pre-selected. This will mean that the negotiation from the previous example is no longer necessary – **Basic Authentication is already chosen**:
```
HttpHost targetHost = new HttpHost("localhost", 8082, "http");
CredentialsProvider credsProvider = new BasicCredentialsProvider();
credsProvider.setCredentials(AuthScope.ANY, 
  new UsernamePasswordCredentials(DEFAULT_USER, DEFAULT_PASS));
AuthCache authCache = new BasicAuthCache();
authCache.put(targetHost, new BasicScheme());
// Add AuthCache to the execution context
HttpClientContext context = HttpClientContext.create();
context.setCredentialsProvider(credsProvider);
context.setAuthCache(authCache);
```
Now we can use the client with the new context and **send the pre-authentication request**:
```
HttpClient client = HttpClientBuilder.create().build();
response = client.execute(
  new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION), context);
int statusCode = response.getStatusLine().getStatusCode();
assertThat(statusCode, equalTo(HttpStatus.SC_OK));
```
Everything looks OK:
- the “Basic Authentication” scheme is pre-selected
- the Request is sent with the `Authorization` header
- the Server responds with a `200 OK`
- Authentication succeeds

## Basic Auth With Raw HTTP Headers
**we can take control of this header and construct it by hand**:
```
HttpGet request = new HttpGet(URL_SECURED_BY_BASIC_AUTHENTICATION);
String auth = DEFAULT_USER + ":" + DEFAULT_PASS;
byte[] encodedAuth = Base64.encodeBase64(
  auth.getBytes(StandardCharsets.ISO_8859_1));
String authHeader = "Basic " + new String(encodedAuth);
request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
HttpClient client = HttpClientBuilder.create().build();
HttpResponse response = client.execute(request);
int statusCode = response.getStatusLine().getStatusCode();
assertThat(statusCode, equalTo(HttpStatus.SC_OK));
```

## Results
- `HttpClientBasicAuthenticationTest`

## References
- [HttpClient Basic Authentication](https://www.baeldung.com/httpclient-4-basic-authentication)