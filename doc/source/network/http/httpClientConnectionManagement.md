# HttpClient Connection Management

## The BasicHttpClientConnectionManager for a Low Level, Single Threaded Connection
The `BasicHttpClientConnectionManager` is available since HttpClient 4.3.3 as the simplest implementation of an HTTP connection manager. It is used to create and manage a single connection that can only be used by one thread at a time.
```
BasicHttpClientConnectionManager connManager
 = new BasicHttpClientConnectionManager();
HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
ConnectionRequest connRequest = connManager.requestConnection(route, null);
```
The `requestConnection` method gets from the manager a pool of connections for a specific `route` to connect to. The `route` parameter specifies a route of “proxy hops” to the target host, or the target host itself.

## Using the PoolingHttpClientConnectionManager to Get and Manage a Pool of Multithreaded Connections
The `PoolingHttpClientConnectionManager` will create and manage a pool of connections for each route or target host we use. The default size of the pool of concurrent **connections** that can be open by the manager is **2 for each route or target host**, and **20 for total** open connections.
```
HttpClientConnectionManager poolingConnManager
  = new PoolingHttpClientConnectionManager();
CloseableHttpClient client
 = HttpClients.custom().setConnectionManager(poolingConnManager)
 .build();
client.execute(new HttpGet("/"));
assertTrue(poolingConnManager.getTotalStats().getLeased() == 1);
```

Notice the `EntityUtils.consume(response.getEntity)` call – necessary to consume the entire content of the response (entity) so that the manager can **release the connection back to the pool**.

## Configure the Connection Manager
```
PoolingHttpClientConnectionManager connManager 
  = new PoolingHttpClientConnectionManager();
connManager.setMaxTotal(5);
connManager.setDefaultMaxPerRoute(4);
HttpHost host = new HttpHost("www.baeldung.com", 80);
connManager.setMaxPerRoute(new HttpRoute(host), 5);
```

- `setMaxTotal(int max)`: Set the maximum number of total open connections.
- `setDefaultMaxPerRoute(int max)`: Set the maximum number of concurrent connections per route, which is 2 by default.
- `setMaxPerRoute(int max)`: Set the total number of concurrent connections to a specific route, which is 2 by default.

## Connection Keep-Alive Strategy
>If the `Keep-Alive` header is not present in the response, HttpClient assumes the connection can be kept alive indefinitely.

```
ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
    @Override
    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
        HeaderElementIterator it = new BasicHeaderElementIterator
            (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
        while (it.hasNext()) {
            HeaderElement he = it.nextElement();
            String param = he.getName();
            String value = he.getValue();
            if (value != null && param.equalsIgnoreCase
               ("timeout")) {
                return Long.parseLong(value) * 1000;
            }
        }
        return 5 * 1000;
    }
};
```
This strategy will first try to apply the host's `Keep-Alive` policy stated in the header. If that information is not present in the response header it will keep alive connections for 5 seconds.

## Connection Persistence / Re-Use
The HTTP/1.1 Spec states that connections can be re-used if they have not been closed – this is known as connection persistence.

Once a connection is released by the manager it stays open for re-use. When using a `BasicHttpClientConnectionManager`, which can only mange a single connection, the connection must be released before it is leased back again:
```
BasicHttpClientConnectionManager basicConnManager = 
    new BasicHttpClientConnectionManager();
HttpClientContext context = HttpClientContext.create();
// low level
HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
ConnectionRequest connRequest = basicConnManager.requestConnection(route, null);
HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
basicConnManager.connect(conn, route, 1000, context);
basicConnManager.routeComplete(conn, route, context);
HttpRequestExecutor exeRequest = new HttpRequestExecutor();
context.setTargetHost((new HttpHost("www.baeldung.com", 80)));
HttpGet get = new HttpGet("http://www.baeldung.com");
exeRequest.execute(get, conn, context);
basicConnManager.releaseConnection(conn, null, 1, TimeUnit.SECONDS);
// high level
CloseableHttpClient client = HttpClients.custom()
  .setConnectionManager(basicConnManager)
  .build();
client.execute(get);
```
First – notice that we're using a low-level connection first, just so that we have full control over when the connection gets released, then a normal higher level connection with a HttpClient. The complex low-level logic is not very relevant here – the only thing we care about is the `releaseConnection` call. That releases the only available connection and allows it to be reused.

Then, the client executes the GET request again with success. If we skip releasing the connection, we will get an `IllegalStateException` from the HttpClient

## Configuring Timeouts – Socket Timeout Using The Connection Manager
```
HttpRoute route = new HttpRoute(new HttpHost("www.baeldung.com", 80));
PoolingHttpClientConnectionManager connManager 
  = new PoolingHttpClientConnectionManager();
connManager.setSocketConfig(route.getTargetHost(),SocketConfig.custom().
    setSoTimeout(5000).build());
```

## Connection Eviction
Connection eviction is used to **detect idle and expired connections and close them**; there are two options to do this.
1. Relying on the HttpClient to check if the connection is stale before executing a request. This is an expensive option that is not always reliable.
2. Create a monitor thread to close idle and/or closed connections.

```
PoolingHttpClientConnectionManager connManager 
  = new PoolingHttpClientConnectionManager();
CloseableHttpClient client = HttpClients.custom()
  .setConnectionManager(connManager).build();
IdleConnectionMonitorThread staleMonitor
 = new IdleConnectionMonitorThread(connManager);
staleMonitor.start();
staleMonitor.join(1000);
```

## Connection Closing
A connection can be closed gracefully (an attempt to flush the output buffer prior to closing is made), or forcefully, by calling the `shutdown` method (the output buffer is not flushed).

To properly close connections we need to do all of the following:
- consume and close the response (if closeable)
- close the client
- close and shut down the connection manager

```
connManager = new PoolingHttpClientConnectionManager();
CloseableHttpClient client = HttpClients.custom()
  .setConnectionManager(connManager).build();
HttpGet get = new HttpGet("http://google.com");
CloseableHttpResponse response = client.execute(get);
EntityUtils.consume(response.getEntity());
response.close();
client.close();
connManager.close();
```
If the manager is shut down without connections being closed already – all connections will be closed and all resources released.

It's important to keep in mind that this will not flush any data that may have been ongoing for the existing connections.

## Results
- `HttpClientConnectionManagementTest`

## References
- [HttpClient Connection Management](https://www.baeldung.com/httpclient-connection-management)