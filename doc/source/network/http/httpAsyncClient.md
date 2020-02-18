# HttpAsyncClient Tutorial

## Simple Example
```
@Test
public void whenUseHttpAsyncClient_thenCorrect() throws Exception {
    CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
    client.start();
    HttpGet request = new HttpGet("http://www.google.com");
    Future<HttpResponse> future = client.execute(request, null);
    HttpResponse response = future.get();
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    client.close();
}
```

## Multi-Threading With HttpAsyncClient
```
@Test
public void whenUseMultipleHttpAsyncClient_thenCorrect() throws Exception {
    ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
    PoolingNHttpClientConnectionManager cm = 
      new PoolingNHttpClientConnectionManager(ioReactor);
    CloseableHttpAsyncClient client = 
      HttpAsyncClients.custom().setConnectionManager(cm).build();
    client.start();
    String[] toGet = { 
        "http://www.google.com/", 
        "http://www.apache.org/", 
        "http://www.bing.com/"
    };
    GetThread[] threads = new GetThread[toGet.length];
    for (int i = 0; i < threads.length; i++) {
        HttpGet request = new HttpGet(toGet[i]);
        threads[i] = new GetThread(client, request);
    }
    for (GetThread thread : threads) {
        thread.start();
    }
    for (GetThread thread : threads) {
        thread.join();
    }
}
```

## Proxy With HttpAsyncClient
```
@Test
public void whenUseProxyWithHttpClient_thenCorrect() throws Exception {
    CloseableHttpAsyncClient client = HttpAsyncClients.createDefault();
    client.start();
    HttpHost proxy = new HttpHost("74.50.126.248", 3127);
    RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
    HttpGet request = new HttpGet("https://issues.apache.org/");
    request.setConfig(config);
    Future<HttpResponse> future = client.execute(request, null);
    HttpResponse response = future.get();
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    client.close();
}
```

## SSL Certificate With HttpAsyncClient
we configure HttpAsyncClient to **accept all certificates**:
```
@Test
public void whenUseSSLWithHttpAsyncClient_thenCorrect() throws Exception {
    TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
        public boolean isTrusted(X509Certificate[] certificate,  String authType) {
            return true;
        }
    };
    SSLContext sslContext = SSLContexts.custom()
      .loadTrustMaterial(null, acceptingTrustStrategy).build();
    CloseableHttpAsyncClient client = HttpAsyncClients.custom()
      .setSSLHostnameVerifier(SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
      .setSSLContext(sslContext).build();
    client.start();
    HttpGet request = new HttpGet("https://mms.nw.ru/");
    Future<HttpResponse> future = client.execute(request, null);
    HttpResponse response = future.get();
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    client.close();
}
```

## Cookies With HttpAsyncClient
```
@Test
public void whenUseCookiesWithHttpAsyncClient_thenCorrect() throws Exception {
    BasicCookieStore cookieStore = new BasicCookieStore();
    BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
    cookie.setDomain(".github.com");
    cookie.setPath("/");
    cookieStore.addCookie(cookie);
    CloseableHttpAsyncClient client = HttpAsyncClients.custom().build();
    client.start();
    HttpGet request = new HttpGet("http://www.github.com");
    HttpContext localContext = new BasicHttpContext();
    localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
    Future<HttpResponse> future = client.execute(request, localContext, null);
    HttpResponse response = future.get();
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    client.close();
}
```

## Authentication With HttpAsyncClient
```
@Test
public void whenUseAuthenticationWithHttpAsyncClient_thenCorrect() throws Exception {
    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials creds = new UsernamePasswordCredentials("user", "pass");
    provider.setCredentials(AuthScope.ANY, creds);
    CloseableHttpAsyncClient client = 
      HttpAsyncClients.custom().setDefaultCredentialsProvider(provider).build();
    client.start();
    HttpGet request = new HttpGet("http://localhost:8080");
    Future<HttpResponse> future = client.execute(request, null);
    HttpResponse response = future.get();
    assertThat(response.getStatusLine().getStatusCode(), equalTo(200));
    client.close();
}
```

## Results
- `HttpAsyncClientTest`

## References
- [HttpAsyncClient Tutorial](https://www.baeldung.com/httpasyncclient-tutorial)