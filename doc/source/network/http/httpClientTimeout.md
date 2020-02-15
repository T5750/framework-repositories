# HttpClient Timeout

## Configure Timeouts via Raw String Parameters
```
DefaultHttpClient httpClient = new DefaultHttpClient();
int timeout = 5; // seconds
HttpParams httpParams = httpClient.getParams();
httpParams.setParameter(
  CoreConnectionPNames.CONNECTION_TIMEOUT, timeout * 1000);
httpParams.setParameter(
  CoreConnectionPNames.SO_TIMEOUT, timeout * 1000);
httpParams.setParameter(
  ClientPNames.CONN_MANAGER_TIMEOUT, new Long(timeout * 1000));
```

## Configure Timeouts via the API
```
DefaultHttpClient httpClient = new DefaultHttpClient();
int timeout = 5; // seconds
HttpParams httpParams = httpClient.getParams();
HttpConnectionParams.setConnectionTimeout(
  httpParams, timeout * 1000); // http.connection.timeout
HttpConnectionParams.setSoTimeout(
  httpParams, timeout * 1000); // http.socket.timeout
```

## Configure Timeouts Using the New 4.3. Builder
```
int timeout = 5;
RequestConfig config = RequestConfig.custom()
  .setConnectTimeout(timeout * 1000)
  .setConnectionRequestTimeout(timeout * 1000)
  .setSocketTimeout(timeout * 1000).build();
CloseableHttpClient client = 
  HttpClientBuilder.create().setDefaultRequestConfig(config).build();
```
That is the recommended way of configuring all three timeouts in a type-safe and readable manner.

## Timeout Properties Explained
Now, let's explain what these various types of timeouts mean:
- the **Connection Timeout** (`http.connection.timeout`) – the time to establish the connection with the remote host
- the **Socket Timeout** (`http.socket.timeout`) – the time waiting for data – after establishing the connection; maximum time of inactivity between two data packets
- the **Connection Manager Timeout** (`http.connection-manager.timeout`) – the time to wait for a connection from the connection manager/pool

## Hard Timeout
While setting timeouts on establishing the HTTP connection and not receiving data is very useful, sometimes we need to set a **hard timeout for the entire request**.
```
HttpGet getMethod = new HttpGet(
  "http://localhost:8080/httpclient-simple/api/bars/1");
int hardTimeout = 5; // seconds
TimerTask task = new TimerTask() {
    @Override
    public void run() {
        if (getMethod != null) {
            getMethod.abort();
        }
    }
};
new Timer(true).schedule(task, hardTimeout * 1000);
HttpResponse response = httpClient.execute(getMethod);
System.out.println(
  "HTTP Status of response: " + response.getStatusLine().getStatusCode());
```

## Timeout and DNS Round Robin
It's quite common that some larger domains will be using a DNS round robin configuration – essentially having **the same domain mapped to multiple IP addresses**.
```
int timeout = 3;
RequestConfig config = RequestConfig.custom().
  setConnectTimeout(timeout * 1000).
  setConnectionRequestTimeout(timeout * 1000).
  setSocketTimeout(timeout * 1000).build();
CloseableHttpClient client = HttpClientBuilder.create()
  .setDefaultRequestConfig(config).build();
HttpGet request = new HttpGet("http://www.google.com:81");
response = client.execute(request);
```

## Results
- `HttpClientTimeoutTest`

## References
- [HttpClient Timeout](https://www.baeldung.com/httpclient-timeout)