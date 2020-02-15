package t5750.network.http;

import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.junit.After;
import org.junit.Test;

import com.google.common.collect.Lists;

import t5750.network.http.util.HcUtil;

/**
 * Custom HTTP Header with the HttpClient
 */
public class HttpClientCustomHttpHeaderTest {
	private HttpClient client;
	private HttpUriRequest request;

	@After
	public void execute() throws Exception {
		HttpResponse response = client.execute(request);
		System.out.println(response.getStatusLine().getStatusCode());
	}

	/**
	 * 2. Set Header on Request – 4.3 and Above
	 */
	@Test
	public void setHeaderOnRequest() throws Exception {
		client = HttpClients.custom().build();
		request = RequestBuilder.get().setUri(HcUtil.HTTPCLIENT_URL)
				.setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
				.build();
	}

	/**
	 * 3. Set Header on Request – Before 4.3
	 */
	@Test
	public void setHeaderOnRequestOlder() throws Exception {
		client = new DefaultHttpClient();
		request = new HttpGet(HcUtil.HTTPCLIENT_URL);
		request.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
	}

	/**
	 * 4. Set Default Header on the Client
	 */
	@Test
	public void setDefaultHeader() throws Exception {
		Header header = new BasicHeader(HttpHeaders.CONTENT_TYPE,
				"application/json");
		List<Header> headers = Lists.newArrayList(header);
		client = HttpClients.custom().setDefaultHeaders(headers).build();
		request = RequestBuilder.get().setUri(HcUtil.HTTPCLIENT_URL).build();
	}
}