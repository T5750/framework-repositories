package t5750.spring.swagger.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/swagger")
public class SwaggerController {
	@GetMapping("/login")
	@ApiOperation(value = "Logs user into the system", response = String.class)
	@ApiResponses({ @io.swagger.annotations.ApiResponse(code = 400, message = "Invalid username/password supplied") })
	public ResponseEntity login(
			@ApiParam(value = "The user name for login", required = true) @RequestParam("username") String username,
			@ApiParam(value = "The password for login in clear text", required = true) @RequestParam("password") String password) {
		Map<String, Object> map = new HashMap<>(2);
		map.put("errcode", 0);
		map.put("errmsg",
				"logged in user session: " + System.currentTimeMillis());
		return ResponseEntity.ok(map);
	}

	@RequestMapping(value = { "/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	@ApiOperation("Logs out current logged in user session")
	public ResponseEntity logout() {
		Map<String, Object> map = new HashMap<>(2);
		map.put("errcode", 0);
		map.put("errmsg", HttpStatus.OK.getReasonPhrase());
		return ResponseEntity.ok(map);
	}
}