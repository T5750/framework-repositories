package t5750.springboot2.controller;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import t5750.springboot2.util.Globals;

@RestController
@RequestMapping(path = "/keycloak")
public class KeycloakController {
	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/employeeCrud")
	public String getProductList(HttpServletRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate
				.exchange(Globals.getBasePath(request) + "employeeCrud",
						HttpMethod.GET, entity, String.class)
				.getBody();
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "Logout Keycloak";
	}
}