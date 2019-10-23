package t5750.dubbox.service.impl.user.facade;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.UserService;
import t5750.dubbox.service.user.facade.RegistrationResult;
import t5750.dubbox.service.user.facade.UserRestService;

@Service
public class UserRestServiceImpl implements UserRestService {
	// private static final Logger logger =
	// LoggerFactory.getLogger(UserRestServiceImpl.class);
	@Resource
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser(Long id/* , @Context HttpServletRequest request */) {
		// test context injection
		// System.out.println("Client address from @Context injection: " +
		// (request != null ? request.getRemoteAddr() : ""));
		// System.out.println("Client address from RpcContext: " +
		// RpcContext.getContext().getRemoteAddressString());
		if (RpcContext.getContext()
				.getRequest(HttpServletRequest.class) != null) {
			System.out
					.println("Client IP address from RpcContext: " + RpcContext
							.getContext().getRequest(HttpServletRequest.class)
							.getRemoteAddr());
		}
		if (RpcContext.getContext()
				.getResponse(HttpServletResponse.class) != null) {
			System.out.println("Response object from RpcContext: " + RpcContext
					.getContext().getResponse(HttpServletResponse.class));
		}
		return userService.getUser(id);
	}

	public RegistrationResult registerUser(User user) {
		return new RegistrationResult(userService.registerUser(user));
	}
}