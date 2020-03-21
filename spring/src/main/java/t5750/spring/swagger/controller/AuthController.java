package t5750.spring.swagger.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
	@GetMapping("/default")
	public ResponseEntity auth(HttpServletRequest request) {
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		Map<String, Object> map = new HashMap<>(2);
		map.put("errcode", 0);
		map.put("errmsg", HttpHeaders.AUTHORIZATION + ": " + auth);
		return ResponseEntity.ok(map);
	}
}