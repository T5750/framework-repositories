package t5750.dubbox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.facade.AnotherUserRestService;
import t5750.dubbox.service.user.facade.RegistrationResult;

@RestController
@RequestMapping("/u")
public class AnotherUserRestController {
	@Reference
	private AnotherUserRestService anotherUserRestService;

	@RequestMapping("/getUser")
	public User getUser(Long id) {
		RpcContext.getContext().setAttachment("clientName", "dubbox-consumer");
		RpcContext.getContext().setAttachment("clientImpl", "dubbox");
		User user = anotherUserRestService.getUser(id);
		System.out.println("SUCCESS: got user " + user);
		return user;
	}

	@RequestMapping("/registerUser")
	public RegistrationResult registerUser(User user) {
		RegistrationResult registrationResult = anotherUserRestService
				.registerUser(user);
		System.out.println("SUCCESS: registered user with id "
				+ registrationResult.getId());
		return registrationResult;
	}
}
