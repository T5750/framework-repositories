package t5750.network.http;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import t5750.network.http.util.HcUtil;
import t5750.network.http.util.ResponseUtil;

/**
 * HttpClient Timeout
 */
public class HttpClientTimeoutTest {
	private HttpGet httpGet;

	@Before
	public void setup() {
		httpGet = new HttpGet(HcUtil.HTTPCLIENT_URL);
	}

	private void executeDefaultHttpClient(DefaultHttpClient httpClient)
			throws Exception {
		HttpResponse response = httpClient.execute(httpGet);
		System.out.println("HTTP Status of response: "
				+ response.getStatusLine().getStatusCode());
	}

	/**
	 * 2. Configure Timeouts via Raw String Parameters
	 */
	@Test
	public void configTimeoutsViaParameters() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				HcUtil.TIMEOUT);
		httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT,
				HcUtil.TIMEOUT);
		httpParams.setParameter(ClientPNames.CONN_MANAGER_TIMEOUT,
				new Long(HcUtil.TIMEOUT));
		executeDefaultHttpClient(httpClient);
	}

	/**
	 * 3. Configure Timeouts via the API
	 */
	@Test
	public void configTimeoutsViaApi() throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, HcUtil.TIMEOUT); // http.connection.timeout
		HttpConnectionParams.setSoTimeout(httpParams, HcUtil.TIMEOUT); // http.socket.timeout
		executeDefaultHttpClient(httpClient);
	}

	private void executeCloseableHttpClient(CloseableHttpClient client)
			throws Exception {
		CloseableHttpResponse response = client.execute(httpGet);
		System.out.println("HTTP Status of response: "
				+ response.getStatusLine().getStatusCode());
		ResponseUtil.closeResponse(response);
		client.close();
	}

	/**
	 * 4. Configure Timeouts Using the New 4.3. Builder
	 */
	@Test
	public void configTimeoutsUsingBuilder() throws Exception {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(HcUtil.TIMEOUT)
				.setConnectionRequestTimeout(HcUtil.TIMEOUT)
				.setSocketTimeout(HcUtil.TIMEOUT).build();
		CloseableHttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(config).build();
		executeCloseableHttpClient(client);
	}

	/**
	 * 7. Hard Timeout
	 */
	@Test
	public void hardTimeout() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				if (httpGet != null) {
					httpGet.abort();
				}
			}
		};
		new Timer(true).schedule(task, HcUtil.TIMEOUT);
		executeCloseableHttpClient(client);
	}

	/**
	 * 8. Timeout and DNS Round Robin – Something to Be Aware Of
	 */
	@Test(expected = ConnectTimeoutException.class)
	@Ignore
	public void timeoutDNSRoundRobin() throws Exception {
		RequestConfig config = RequestConfig.custom()
				.setConnectTimeout(HcUtil.TIMEOUT)
				.setConnectionRequestTimeout(HcUtil.TIMEOUT)
				.setSocketTimeout(HcUtil.TIMEOUT).build();
		CloseableHttpClient client = HttpClientBuilder.create()
				.setDefaultRequestConfig(config).build();
		httpGet = new HttpGet("http://www.google.com:81");
		executeCloseableHttpClient(client);
	}
}