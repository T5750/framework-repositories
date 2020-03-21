package t5750.rest.jersey.client;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import t5750.rest.jersey.client.util.JerseyUtil;
import t5750.rest.jersey.model.Employee;
import t5750.rest.jersey.model.Employees;

public class JerseyCookieTest {
	private Client client;

	@Before
	public void setup() {
		client = JerseyUtil.newClient();
	}

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	@Test
	public void cookie() throws IOException {
		WebTarget webTarget = client.target(JerseyUtil.REST_URL)
				.path("employees/cookie");
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder
				.cookie("cookieFoo", "cookieFooValue")
				.cookie(new Cookie("cookieBar", "cookieBarValue")).get();
		Employees employees = response.readEntity(Employees.class);
		List<Employee> listOfEmployees = employees.getEmployeeList();
		System.out.println(response.getCookies());
		System.out.println(response.getStatus());
		System.out.println(Arrays.toString(
				listOfEmployees.toArray(new Employee[listOfEmployees.size()])));
	}
}