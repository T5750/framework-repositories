package t5750.dubbox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.UserService;

//@Controller
public class UserController {
	// @Reference
	private UserService userService;

	@RequestMapping("/getUser")
	@ResponseBody
	public User getUser(Long id) {
		return userService.getUser(id);
	}

	@RequestMapping("/registerUser")
	@ResponseBody
	public Long registerUser(User user) {
		return userService.registerUser(user);
	}
}
