package t5750.springbootnacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import t5750.springbootnacos.model.User;
import t5750.springbootnacos.service.UserService;

/**
 * for Nacos Config
 */
@RestController
@RequestMapping("/user")
public class UserController {
	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User get(@PathVariable int id) {
		return userService.findById(id);
	}
}