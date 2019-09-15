package t5750.springboot2;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import t5750.springboot2.model.Employee;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class Boot2ApplicationTests {
	@LocalServerPort
	int randomServerPort;

	@Test
	public void testGetEmployeeListSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort
				+ "/employees/";
		URI uri = new URI(baseUrl);
		ResponseEntity<String> result = restTemplate.getForEntity(uri,
				String.class);
		// Verify request succeed
		Assert.assertEquals(200, result.getStatusCodeValue());
		Assert.assertEquals(true, result.getBody().contains("employeeList"));
	}

	@Test
	public void testGetEmployeeListSuccessWithHeaders()
			throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort
				+ "/employees/";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-LOCATION", "USA");
		HttpEntity<Employee> requestEntity = new HttpEntity<>(null, headers);
		try {
			restTemplate.exchange(uri, HttpMethod.GET, requestEntity,
					String.class);
			// Assert.fail();
		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString()
					.contains("Missing request header"));
		}
	}

	@Test
	public void testAddEmployeeSuccess() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort
				+ "/employees/";
		URI uri = new URI(baseUrl);
		Employee employee = new Employee(null, "Adam", "Gilly",
				"test@email.com");
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-COM-PERSIST", "true");
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		ResponseEntity<String> result = restTemplate.postForEntity(uri, request,
				String.class);
		// Verify request succeed
		Assert.assertEquals(201, result.getStatusCodeValue());
	}

	@Test
	public void testAddEmployeeMissingHeader() throws URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		final String baseUrl = "http://localhost:" + randomServerPort
				+ "/employees/";
		URI uri = new URI(baseUrl);
		Employee employee = new Employee(null, "Adam", "Gilly",
				"test@email.com");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<Employee> request = new HttpEntity<>(employee, headers);
		try {
			restTemplate.postForEntity(uri, request, String.class);
			Assert.fail();
		} catch (HttpClientErrorException ex) {
			// Verify bad request and missing header
			Assert.assertEquals(400, ex.getRawStatusCode());
			Assert.assertEquals(true, ex.getResponseBodyAsString()
					.contains("Missing request header"));
		}
	}
}
