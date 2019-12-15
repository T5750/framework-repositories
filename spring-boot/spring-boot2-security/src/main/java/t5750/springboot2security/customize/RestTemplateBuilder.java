package t5750.springboot2security.customize;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class RestTemplateBuilder {
	public static RestTemplate securityRestTemplateBuilder(String username,
			String password) {
		HttpClient httpClient = createHttpClient(username, password);
		ClientHttpRequestFactory requestFactory = createRequestFactory(
				httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		return restTemplate;
	}

	private static ClientHttpRequestFactory createRequestFactory(
			HttpClient httpClient) {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClient);
		return clientHttpRequestFactory;
	}

	private static HttpClient createHttpClient(String username,
			String password) {
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
				username, password);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpClient client = HttpClientBuilder.create()
				.setDefaultCredentialsProvider(provider).build();
		return client;
	}
}
