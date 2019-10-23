package t5750.dubbox.service.impl.user.facade;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.UserService;
import t5750.dubbox.service.user.facade.AnotherUserRestService;
import t5750.dubbox.service.user.facade.RegistrationResult;

@Service
public class AnotherUserRestServiceImpl implements AnotherUserRestService {
	@Resource
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser(Long id) {
		System.out.println("Client name is "
				+ RpcContext.getContext().getAttachment("clientName"));
		System.out.println("Client impl is "
				+ RpcContext.getContext().getAttachment("clientImpl"));
		return userService.getUser(id);
	}

	public RegistrationResult registerUser(User user) {
		return new RegistrationResult(userService.registerUser(user));
	}
}