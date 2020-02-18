package t5750.network.http;

import java.security.cert.X509Certificate;
import java.util.concurrent.Future;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.junit.After;
import org.junit.Test;

import t5750.network.http.util.HcUtil;
import t5750.network.test.AbstractTest;

/**
 * HttpAsyncClient Tutorial
 */
public class HttpAsyncClientTest extends AbstractTest {
	private CloseableHttpAsyncClient client;

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	/**
	 * 2. Simple Example
	 */
	@Test
	public void useHttpAsyncClient() throws Exception {
		client = HttpAsyncClients.createDefault();
		client.start();
		HttpGet request = new HttpGet(HcUtil.HTTPCLIENT_URL);
		Future<HttpResponse> future = client.execute(request, null);
		HttpResponse response = future.get();
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 3. Multi-Threading With HttpAsyncClient
	 */
	@Test
	public void useMultipleHttpAsyncClient() throws Exception {
		ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor();
		PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(
				ioReactor);
		client = HttpAsyncClients.custom().setConnectionManager(cm).build();
		client.start();
		String[] toGet = { HcUtil.HTTPCLIENT_URL, "http://www.apache.org/",
				"http://www.bing.com/" };
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

	static class GetThread extends Thread {
		private CloseableHttpAsyncClient client;
		private HttpContext context;
		private HttpGet request;

		public GetThread(CloseableHttpAsyncClient client, HttpGet req) {
			this.client = client;
			context = HttpClientContext.create();
			this.request = req;
		}

		@Override
		public void run() {
			try {
				Future<HttpResponse> future = client.execute(request, context,
						null);
				HttpResponse response = future.get();
				System.out.println(response.getStatusLine().getStatusCode());
			} catch (Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
	}

	/**
	 * 4. Proxy With HttpAsyncClient
	 */
	@Test
	public void useProxyWithHttpClient() throws Exception {
		client = HttpAsyncClients.createDefault();
		client.start();
		HttpHost proxy = new HttpHost(HcUtil.SECURITY_HOSTNAME,
				HcUtil.SECURITY_PORT);
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
		HttpGet request = new HttpGet("http://www.google.com");
		request.setConfig(config);
		Future<HttpResponse> future = client.execute(request, null);
		HttpResponse response = future.get();
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 5. SSL Certificate With HttpAsyncClient
	 */
	@Test
	public void useSSLWithHttpAsyncClient() throws Exception {
		TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
			public boolean isTrusted(X509Certificate[] certificate,
					String authType) {
				return true;
			}
		};
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy).build();
		client = HttpAsyncClients.custom()
				.setSSLHostnameVerifier(
						SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
				.setSSLContext(sslContext).build();
		client.start();
		HttpGet request = new HttpGet("https://mms.nw.ru/");
		Future<HttpResponse> future = client.execute(request, null);
		HttpResponse response = future.get();
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 6. Cookies With HttpAsyncClient
	 */
	@Test
	public void useCookiesWithHttpAsyncClient() throws Exception {
		BasicCookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("JSESSIONID", "1234");
		cookie.setDomain(".github.com");
		cookie.setPath("/");
		cookieStore.addCookie(cookie);
		client = HttpAsyncClients.custom().build();
		client.start();
		HttpGet request = new HttpGet("http://www.github.com");
		HttpContext localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
		Future<HttpResponse> future = client.execute(request, localContext,
				null);
		HttpResponse response = future.get();
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 7. Authentication With HttpAsyncClient
	 */
	@Test
	public void useAuthenticationWithHttpAsyncClient() throws Exception {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
				HcUtil.SECURITY_USERNAME, HcUtil.SECURITY_PASSWORD);
		provider.setCredentials(AuthScope.ANY, creds);
		client = HttpAsyncClients.custom()
				.setDefaultCredentialsProvider(provider).build();
		client.start();
		HttpGet request = new HttpGet(HcUtil.SECURITY_PRODUCTS_URL);
		Future<HttpResponse> future = client.execute(request, null);
		HttpResponse response = future.get();
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}
}