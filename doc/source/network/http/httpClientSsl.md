# HttpClient with SSL

## Overview
This article will show how to **configure the Apache HttpClient 4 with “Accept All” SSL support**. The goal is simple – consume HTTPS URLs which do not have valid certificates.

## The SSLPeerUnverifiedException
```
@Test(expected = SSLPeerUnverifiedException.class)
public void whenHttpsUrlIsConsumed_thenException() 
  throws ClientProtocolException, IOException {
    CloseableHttpClient httpClient = HttpClients.createDefault();
    String urlOverHttps = "https://localhost:8082/httpclient-simple";
    HttpGet getMethod = new HttpGet(urlOverHttps);
    HttpResponse response = httpClient.execute(getMethod);
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
}
```
The `javax.net.ssl.SSLPeerUnverifiedException` exception occurs whenever a valid chain of trust couldn't be established for the URL.

## Configure SSL – Accept All (HttpClient < 4.3)
```
@Test
public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk() 
  throws GeneralSecurityException {
    HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    CloseableHttpClient httpClient = (CloseableHttpClient) requestFactory.getHttpClient();
    TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
    SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy, ALLOW_ALL_HOSTNAME_VERIFIER);
    httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", 8443, sf));
    ResponseEntity<String> response = new RestTemplate(requestFactory).
      exchange(urlOverHttps, HttpMethod.GET, null, String.class);
    assertThat(response.getStatusCode().value(), equalTo(200));
}
```
With the new `TrustStrategy` now **overriding the standard certificate verification process** (which should consult a configured trust manager)

## Configure SSL – Accept All (HttpClient 4.4 and Above)
```
@Test
public final void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenOk()
  throws GeneralSecurityException {
    TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
    SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
    SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, 
      NoopHostnameVerifier.INSTANCE);
    Registry<ConnectionSocketFactory> socketFactoryRegistry = 
      RegistryBuilder.<ConnectionSocketFactory> create()
      .register("https", sslsf)
      .register("http", new PlainConnectionSocketFactory())
      .build();
    BasicHttpClientConnectionManager connectionManager = 
      new BasicHttpClientConnectionManager(socketFactoryRegistry);
    CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf)
      .setConnectionManager(connectionManager).build();
    HttpComponentsClientHttpRequestFactory requestFactory = 
      new HttpComponentsClientHttpRequestFactory(httpClient);
    ResponseEntity<String> response = new RestTemplate(requestFactory)
      .exchange(urlOverHttps, HttpMethod.GET, null, String.class);
    assertThat(response.getStatusCode().value(), equalTo(200));
}
```

## The Spring RestTemplate with SSL (HttpClient < 4.3)
Now that we have seen how to configure a raw HttpClient with SSL support, let's take a look at a higher level client – the Spring `RestTemplate`.

With no SSL configured, the following test fails as expected:
```
@Test(expected = ResourceAccessException.class)
public void whenHttpsUrlIsConsumed_thenException() {
    String urlOverHttps = "https://localhost:8443/httpclient-simple/api/bars/1";
    ResponseEntity<String> response 
      = new RestTemplate().exchange(urlOverHttps, HttpMethod.GET, null, String.class);
    assertThat(response.getStatusCode().value(), equalTo(200));
}
```
So let's configure SSL:
```
@Test
public void givenAcceptingAllCertificates_whenHttpsUrlIsConsumed_thenException() 
  throws GeneralSecurityException {
    HttpComponentsClientHttpRequestFactory requestFactory 
      = new HttpComponentsClientHttpRequestFactory();
    DefaultHttpClient httpClient
      = (DefaultHttpClient) requestFactory.getHttpClient();
    TrustStrategy acceptingTrustStrategy = (cert, authType) -> true
    SSLSocketFactory sf = new SSLSocketFactory(
      acceptingTrustStrategy, ALLOW_ALL_HOSTNAME_VERIFIER);
    httpClient.getConnectionManager().getSchemeRegistry()
      .register(new Scheme("https", 8443, sf));
    String urlOverHttps = "https://localhost:8443/httpclient-simple/api/bars/1";
    ResponseEntity<String> response = new RestTemplate(requestFactory).
      exchange(urlOverHttps, HttpMethod.GET, null, String.class);
    assertThat(response.getStatusCode().value(), equalTo(200));
}
```

## The Spring RestTemplate with SSL (HttpClient 4.4)
```
@Test
public void givenAcceptingAllCertificatesUsing4_4_whenUsingRestTemplate_thenCorrect() 
throws ClientProtocolException, IOException {
    CloseableHttpClient httpClient
      = HttpClients.custom()
        .setSSLHostnameVerifier(new NoopHostnameVerifier())
        .build();
    HttpComponentsClientHttpRequestFactory requestFactory 
      = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setHttpClient(httpClient);
    ResponseEntity<String> response 
      = new RestTemplate(requestFactory).exchange(
      urlOverHttps, HttpMethod.GET, null, String.class);
    assertThat(response.getStatusCode().value(), equalTo(200));
}
```

## Results
- `HttpClientSslTest`

## References
- [HttpClient with SSL](https://www.baeldung.com/httpclient-ssl)