package t5750.dubbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import t5750.dubbox.domain.User;
import t5750.dubbox.service.UserService;

@Controller
public class UserController {
	@Reference
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
