package t5750.springbootsecurity.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins ="http://localhost:4200", maxAge = 3600)
public class UserCorsController {
	@GetMapping("/user-cors")
	public String userCors(Principal principal) {
		return principal.getName();
	}
}