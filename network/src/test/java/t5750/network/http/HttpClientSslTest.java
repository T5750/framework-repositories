package t5750.network.http;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import t5750.network.http.util.HcUtil;
import t5750.network.test.AbstractTest;

/**
 * HttpClient with SSL
 */
public class HttpClientSslTest extends AbstractTest {
	private BasicHttpClientConnectionManager basicConnManager;
	private CloseableHttpClient client;

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
		if (null != basicConnManager) {
			basicConnManager.close();
		}
	}

	/**
	 * 2. The SSLPeerUnverifiedException
	 */
	// @Test(expected = SSLPeerUnverifiedException.class)
	@Test
	public void whenHttpsUrlIsConsumed() throws Exception {
		client = HttpClients.createDefault();
		HttpGet getMethod = new HttpGet(HcUtil.BAELDUNG_SSL_URL);
		HttpResponse response = client.execute(getMethod);
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 3. Configure SSL – Accept All (HttpClient < 4.3)
	 */
	/**
	 * 4. Configure SSL – Accept All (HttpClient 4.4 and Above)
	 */
	@Test
	public final void givenAcceptingAllCertificates() throws Exception {
		TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
		SSLContext sslContext = SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				sslContext, NoopHostnameVerifier.INSTANCE);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory()).build();
		basicConnManager = new BasicHttpClientConnectionManager(
				socketFactoryRegistry);
		client = HttpClients.custom().setSSLSocketFactory(sslsf)
				.setConnectionManager(basicConnManager).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				client);
		ResponseEntity<String> response = new RestTemplate(requestFactory)
				.exchange(HcUtil.BAELDUNG_SSL_URL, HttpMethod.GET, null,
						String.class);
		assertEquals(response.getStatusCode().value(), HttpStatus.SC_OK);
	}

	/**
	 * 5. The Spring RestTemplate with SSL (HttpClient < 4.3)
	 */
	// @Test(expected = ResourceAccessException.class)
	@Test(expected = HttpClientErrorException.class)
	@Ignore
	public void whenHttpsUrlIsConsumedRestTemplate() {
		ResponseEntity<String> response = new RestTemplate().exchange(
				HcUtil.BAELDUNG_SSL_URL, HttpMethod.GET, null, String.class);
		assertEquals(response.getStatusCode().value(), HttpStatus.SC_OK);
	}

	/**
	 * 6. The Spring RestTemplate with SSL (HttpClient 4.4)
	 */
	@Test
	public void givenAcceptingAllCertificatesUsing4_4() throws Exception {
		client = HttpClients.custom()
				.setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(client);
		ResponseEntity<String> response = new RestTemplate(requestFactory)
				.exchange(HcUtil.BAELDUNG_SSL_URL, HttpMethod.GET, null,
						String.class);
		assertEquals(response.getStatusCode().value(), HttpStatus.SC_OK);
	}
}