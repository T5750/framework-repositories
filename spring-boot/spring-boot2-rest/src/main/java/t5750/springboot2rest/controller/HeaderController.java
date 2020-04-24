package t5750.springboot2rest.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/header")
public class HeaderController {
	@Autowired
	private FindByIndexNameSessionRepository<? extends Session> sessionRepository;

	@RequestMapping("/findByUsername")
	public Map findByUsername(@RequestParam String username) {
		Map<String, ? extends Session> usersSessions = sessionRepository
				.findByIndexNameAndIndexValue(
						FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME,
						username);
		return usersSessions;
	}

	@RequestMapping(value = "/hello", produces = "application/json")
	public Map<String, String> hello(Principal principal) {
		HashMap<String, String> result = new HashMap<>(2);
		result.put("username", principal.getName());
		return result;
	}

	@RequestMapping("/logout")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void logout(HttpSession session) {
		session.invalidate();
	}
}