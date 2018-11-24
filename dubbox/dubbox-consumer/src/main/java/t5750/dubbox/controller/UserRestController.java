package t5750.dubbox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.facade.RegistrationResult;
import t5750.dubbox.service.user.facade.UserRestService;

@RestController
@RequestMapping("/users")
public class UserRestController {
	@Reference
	private UserRestService userRestService;

	@RequestMapping("/getUser")
	public User getUser(Long id) {
		return userRestService.getUser(id);
	}

	@RequestMapping("/registerUser")
	public RegistrationResult registerUser(User user) {
		return userRestService.registerUser(user);
	}
}
