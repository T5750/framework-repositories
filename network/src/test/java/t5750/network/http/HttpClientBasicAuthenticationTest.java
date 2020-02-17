package t5750.network.http;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Test;

import t5750.network.http.util.HcUtil;
import t5750.network.test.AbstractTest;

/**
 * HttpClient Basic Authentication
 */
public class HttpClientBasicAuthenticationTest extends AbstractTest {
	private static final String USERNAME = "user";
	private static final String PASSWORD = "pass";
	private CloseableHttpClient client;

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	/**
	 * 2. Basic Authentication With the API
	 */
	@Test
	public void basicAuthentication() throws Exception {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				USERNAME, PASSWORD);
		provider.setCredentials(AuthScope.ANY, credentials);
		client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		HttpResponse response = client
				.execute(new HttpGet(HcUtil.SECURITY_PRODUCTS_URL));
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 3. Preemptive Basic Authentication
	 */
	@Test
	public void preemptiveBasicAuthentication() throws Exception {
		HttpHost targetHost = new HttpHost(HcUtil.SECURITY_HOSTNAME,
				HcUtil.SECURITY_PORT, "http");
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(USERNAME, PASSWORD));
		AuthCache authCache = new BasicAuthCache();
		authCache.put(targetHost, new BasicScheme());
		// Add AuthCache to the execution context
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);
		context.setAuthCache(authCache);
		client = HttpClientBuilder.create().build();
		HttpResponse response = client
				.execute(new HttpGet(HcUtil.SECURITY_PRODUCTS_URL), context);
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}

	/**
	 * 4. Basic Auth With Raw HTTP Headers
	 */
	@Test
	public void basicAuthWithRawHttpHeaders() throws Exception {
		HttpGet request = new HttpGet(HcUtil.SECURITY_PRODUCTS_URL);
		String auth = USERNAME + ":" + PASSWORD;
		byte[] encodedAuth = Base64
				.encodeBase64(auth.getBytes(StandardCharsets.ISO_8859_1));
		String authHeader = "Basic " + new String(encodedAuth);
		request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
		client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(request);
		assertEquals(response.getStatusLine().getStatusCode(),
				HttpStatus.SC_OK);
	}
}