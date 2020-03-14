package t5750.rest.jersey.client;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import t5750.rest.jersey.model.Employee;
import t5750.rest.jersey.model.Employees;
import t5750.rest.jersey.provider.GsonMessageBodyHandler;
import t5750.rest.jersey.util.Globals;

public class JerseyClientApiTest {
	private static final String REST_URL = "http://localhost:8080/rest/jersey";
	private Client client;

	@Before
	public void setup() {
		// HttpAuthenticationFeature feature = HttpAuthenticationFeature
		// .basicBuilder().nonPreemptive()
		// .credentials(Globals.T5750, Globals.PASSWORD).build();
		HttpAuthenticationFeature feature = HttpAuthenticationFeature
				.basic(Globals.T5750, Globals.PASSWORD);
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(feature);
		clientConfig.register(GsonMessageBodyHandler.class);
		client = ClientBuilder.newClient(clientConfig);
	}

	@After
	public void destroy() throws Exception {
		if (null != client) {
			client.close();
		}
	}

	@Test
	public void httpGETCollection() {
		WebTarget webTarget = client.target(REST_URL).path("employees");
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		System.out.println(response.getStatus());
		System.out.println(response.getStatusInfo());
		if (response.getStatus() == 200) {
			Employees employees = response.readEntity(Employees.class);
			List<Employee> listOfEmployees = employees.getEmployeeList();
			System.out.println(Arrays.toString(listOfEmployees
					.toArray(new Employee[listOfEmployees.size()])));
		}
	}

	@Test
	public void httpGETEntity() {
		WebTarget webTarget = client.target(REST_URL).path("employees")
				.path("1");
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();
		Employee employee = response.readEntity(Employee.class);
		System.out.println(response.getStatus());
		System.out.println(employee);
	}

	@Test
	public void httpPOSTMethod() {
		WebTarget webTarget = client.target(REST_URL).path("employees");
		Employee emp = new Employee();
		emp.setId(4);
		emp.setName(Globals.T5750.toUpperCase());
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder
				.post(Entity.entity(emp, MediaType.APPLICATION_JSON));
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}

	@Test
	public void httpPUTMethod() {
		WebTarget webTarget = client.target(REST_URL).path("employees")
				.path("1");
		Employee emp = new Employee();
		emp.setId(1);
		emp.setName(Globals.T5750.toUpperCase());
		Invocation.Builder invocationBuilder = webTarget
				.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder
				.put(Entity.entity(emp, MediaType.APPLICATION_JSON));
		Employee employee = response.readEntity(Employee.class);
		System.out.println(response.getStatus());
		System.out.println(employee);
	}

	@Test
	public void httpDELETEMethod() {
		WebTarget webTarget = client.target(REST_URL).path("employees")
				.path("1");
		Invocation.Builder invocationBuilder = webTarget.request();
		Response response = invocationBuilder.delete();
		System.out.println(response.getStatus());
		System.out.println(response.readEntity(String.class));
	}
}