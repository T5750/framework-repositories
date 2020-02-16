package t5750.network.http;

import java.util.concurrent.TimeUnit;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ConnectionRequest;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Test;

import t5750.network.http.util.HcUtil;
import t5750.network.test.AbstractTest;

/**
 * HttpClient Connection Management
 */
public class HttpClientConnectionManagementTest extends AbstractTest {
	private BasicHttpClientConnectionManager basicConnManager;
	private PoolingHttpClientConnectionManager connManager;
	private CloseableHttpClient client;

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
		if (null != basicConnManager) {
			basicConnManager.close();
		}
		if (null != connManager) {
			connManager.close();
		}
	}

	/**
	 * 2. The BasicHttpClientConnectionManager for a Low Level, Single Threaded
	 * Connection
	 */
	@Test
	public void basicHttpClientConnectionManager() throws Exception {
		basicConnManager = new BasicHttpClientConnectionManager();
		HttpRoute route = new HttpRoute(
				new HttpHost(HcUtil.HOSTNAME, HcUtil.PORT));
		ConnectionRequest connRequest = basicConnManager
				.requestConnection(route, null);
	}

	/**
	 * 3. Using the PoolingHttpClientConnectionManager to Get and Manage a Pool
	 * of Multithreaded Connections
	 */
	@Test
	public void poolingHttpClientConnectionManager() throws Exception {
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setConnectionManager(connManager).build();
		client.execute(new HttpGet(HcUtil.HTTPCLIENT_URL));
		assertTrue(connManager.getTotalStats().getLeased() == 1);
	}

	/**
	 * Using Two HttpClients to Connect to One Target Host Each
	 */
	@Test
	public void useTwoHttpClients() throws Exception {
		HttpGet get1 = new HttpGet(HcUtil.HTTPCLIENT_URL);
		HttpGet get2 = new HttpGet(HcUtil.BAELDUNG_URL);
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setConnectionManager(connManager).build();
		CloseableHttpClient client2 = HttpClients.custom()
				.setConnectionManager(connManager).build();
		MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(
				client, get1);
		MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(
				client2, get2);
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();
		client2.close();
	}

	/**
	 * 4. Configure the Connection Manager
	 */
	@Test
	public void configConnectionManager() throws Exception {
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setMaxTotal(5);
		connManager.setDefaultMaxPerRoute(4);
		HttpHost host = new HttpHost(HcUtil.HOSTNAME, HcUtil.PORT);
		connManager.setMaxPerRoute(new HttpRoute(host), 5);
	}

	/**
	 * Using Threads to Execute Connections
	 */
	@Test
	public void executeConnections() throws Exception {
		HttpGet get = new HttpGet(HcUtil.HTTPCLIENT_URL);
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setConnectionManager(connManager).build();
		MultiHttpClientConnThread thread1 = new MultiHttpClientConnThread(
				client, get, connManager);
		MultiHttpClientConnThread thread2 = new MultiHttpClientConnThread(
				client, get, connManager);
		MultiHttpClientConnThread thread3 = new MultiHttpClientConnThread(
				client, get, connManager);
		thread1.start();
		thread2.start();
		thread3.start();
		thread1.join();
		thread2.join();
		thread3.join();
	}

	/**
	 * 5. Connection Keep-Alive Strategy
	 */
	@Test
	public void connectionKeepAliveStrategy() throws Exception {
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response,
					HttpContext context) {
				HeaderElementIterator it = new BasicHeaderElementIterator(
						response.headerIterator(HTTP.CONN_KEEP_ALIVE));
				while (it.hasNext()) {
					HeaderElement he = it.nextElement();
					String param = he.getName();
					String value = he.getValue();
					if (value != null && param.equalsIgnoreCase("timeout")) {
						return Long.parseLong(value) * 1000;
					}
				}
				return HcUtil.TIMEOUT;
			}
		};
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setKeepAliveStrategy(myStrategy)
				.setConnectionManager(connManager).build();
	}

	/**
	 * 6. Connection Persistence / Re-Use
	 */
	@Test
	public void connectionPersistenceReUse() throws Exception {
		basicConnManager = new BasicHttpClientConnectionManager();
		HttpClientContext context = HttpClientContext.create();
		// low level
		HttpRoute route = new HttpRoute(
				new HttpHost(HcUtil.HOSTNAME, HcUtil.PORT));
		ConnectionRequest connRequest = basicConnManager
				.requestConnection(route, null);
		HttpClientConnection conn = connRequest.get(10, TimeUnit.SECONDS);
		basicConnManager.connect(conn, route, 1000, context);
		basicConnManager.routeComplete(conn, route, context);
		HttpRequestExecutor exeRequest = new HttpRequestExecutor();
		context.setTargetHost((new HttpHost(HcUtil.HOSTNAME, HcUtil.PORT)));
		HttpGet get = new HttpGet(HcUtil.BAELDUNG_URL);
		exeRequest.execute(get, conn, context);
		basicConnManager.releaseConnection(conn, null, 1, TimeUnit.SECONDS);
		// high level
		client = HttpClients.custom().setConnectionManager(basicConnManager)
				.build();
		client.execute(get);
		conn.close();
	}

	/**
	 * PoolingHttpClientConnectionManager: Re-Using Connections with Threads
	 */
	@Test
	public void reUseConnectionsWithThreads() throws Exception {
		HttpGet get = new HttpGet(HcUtil.HTTPCLIENT_URL);
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setDefaultMaxPerRoute(5);
		connManager.setMaxTotal(5);
		client = HttpClients.custom().setConnectionManager(connManager).build();
		MultiHttpClientConnThread[] threads = new MultiHttpClientConnThread[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new MultiHttpClientConnThread(client, get,
					connManager);
		}
		for (MultiHttpClientConnThread thread : threads) {
			thread.start();
		}
		for (MultiHttpClientConnThread thread : threads) {
			thread.join(1000);
		}
	}

	/**
	 * 7. Configuring Timeouts – Socket Timeout Using The Connection Manager
	 */
	@Test
	public void configTimeouts() throws Exception {
		HttpRoute route = new HttpRoute(
				new HttpHost(HcUtil.HOSTNAME, HcUtil.PORT));
		connManager = new PoolingHttpClientConnectionManager();
		connManager.setSocketConfig(route.getTargetHost(),
				SocketConfig.custom().setSoTimeout(HcUtil.TIMEOUT).build());
	}

	/**
	 * 8. Connection Eviction<br/>
	 * Setting the HttpClient to Check for Stale Connections
	 */
	@Test
	public void connectionEviction() throws Exception {
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom()
						.setStaleConnectionCheckEnabled(true).build())
				.setConnectionManager(connManager).build();
	}

	/**
	 * Using a Stale Connection Monitor Thread
	 */
	@Test
	public void useMonitorThread() throws Exception {
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setConnectionManager(connManager).build();
		IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(
				connManager);
		staleMonitor.start();
		staleMonitor.join(1000);
	}

	/**
	 * 9. Connection Closing
	 */
	@Test
	public void connectionClosing() throws Exception {
		connManager = new PoolingHttpClientConnectionManager();
		client = HttpClients.custom().setConnectionManager(connManager).build();
		HttpGet get = new HttpGet(HcUtil.HTTPCLIENT_URL);
		CloseableHttpResponse response = client.execute(get);
		EntityUtils.consume(response.getEntity());
		response.close();
		client.close();
		connManager.close();
	}
}