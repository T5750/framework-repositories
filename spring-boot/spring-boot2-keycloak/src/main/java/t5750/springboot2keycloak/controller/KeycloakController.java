package t5750.springboot2keycloak.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/keycloak")
public class KeycloakController {
	@RequestMapping(value = "/hello")
	public String hello(Principal principal) {
		return "Hello " + principal.getName();
	}
}