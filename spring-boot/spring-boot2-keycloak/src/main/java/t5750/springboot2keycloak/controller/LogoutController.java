package t5750.springboot2keycloak.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LogoutController {
	@PostMapping(path = "/logout")
	public String logout(HttpServletRequest request) throws ServletException {
		request.logout();
		return "redirect:/";
	}
}