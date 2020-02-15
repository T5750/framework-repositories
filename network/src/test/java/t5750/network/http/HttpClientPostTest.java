package t5750.network.http;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import t5750.network.http.util.ResponseUtil;
import t5750.network.test.AbstractTest;

/**
 * Posting with HttpClient
 */
public class HttpClientPostTest extends AbstractTest {
	private static final String HTTPCLIENT_URL = "http://www.example.com";
	private static final String FILE_PATHNAME = "build.gradle";
	private static final String FILENAME = "file.ext";
	private CloseableHttpClient client;
	private HttpPost httpPost;
	private int statusCode;

	@Before
	public void setup() {
		client = HttpClients.createDefault();
		httpPost = new HttpPost(HTTPCLIENT_URL);
	}

	@After
	public void destroy() throws Exception {
		if (statusCode == 0) {
			CloseableHttpResponse response = client.execute(httpPost);
			assertEquals(response.getStatusLine().getStatusCode(), 200);
			ResponseUtil.closeResponse(response);
		} else {
			// Fluent API
			assertEquals(statusCode, 200);
		}
	}

	/**
	 * 2. Basic POST
	 */
	@Test
	public void sendPostRequest() throws Exception {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", "John"));
		params.add(new BasicNameValuePair("password", "pass"));
		httpPost.setEntity(new UrlEncodedFormEntity(params));
	}

	/**
	 * 3. POST With Authorization
	 */
	@Test
	public void sendPostRequestWithAuthorization() throws Exception {
		httpPost.setEntity(new StringEntity("test post"));
		UsernamePasswordCredentials creds = new UsernamePasswordCredentials(
				"John", "pass");
		httpPost.addHeader(
				new BasicScheme().authenticate(creds, httpPost, null));
	}

	/**
	 * 4. POST With JSON
	 */
	@Test
	public void postJson() throws Exception {
		String json = "{\"id\":1,\"name\":\"John\"}";
		StringEntity entity = new StringEntity(json);
		httpPost.setEntity(entity);
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
	}

	/**
	 * 5. POST With the HttpClient Fluent API
	 */
	@Test
	public void postFormFluentAPI() throws Exception {
		HttpResponse response = Request.Post(HTTPCLIENT_URL)
				.bodyForm(Form.form().add("username", "John")
						.add("password", "pass").build())
				.execute().returnResponse();
		statusCode = response.getStatusLine().getStatusCode();
	}

	/**
	 * 6. POST Multipart Request
	 */
	@Test
	public void sendMultipartRequest() throws Exception {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addTextBody("username", "John");
		builder.addTextBody("password", "pass");
		builder.addBinaryBody("file", new File(FILE_PATHNAME),
				ContentType.APPLICATION_OCTET_STREAM, FILENAME);
		HttpEntity multipart = builder.build();
		httpPost.setEntity(multipart);
	}

	/**
	 * 7. Upload a File Using HttpClient
	 */
	@Test
	public void uploadFile() throws Exception {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody("file", new File(FILE_PATHNAME),
				ContentType.APPLICATION_OCTET_STREAM, FILENAME);
		HttpEntity multipart = builder.build();
		httpPost.setEntity(multipart);
	}

	/**
	 * 8. Get File Upload Progress
	 */
	@Test
	public void getUploadFileProgress() throws Exception {
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		builder.addBinaryBody("file", new File(FILE_PATHNAME),
				ContentType.APPLICATION_OCTET_STREAM, FILENAME);
		HttpEntity multipart = builder.build();
		ProgressEntityWrapper.ProgressListener pListener = percentage -> assertFalse(
				Float.compare(percentage, 100) > 0);
		httpPost.setEntity(new ProgressEntityWrapper(multipart, pListener));
	}
}