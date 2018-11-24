package t5750.dubbox.service.impl.hello;

import java.util.concurrent.atomic.AtomicLong;

import com.alibaba.dubbo.config.annotation.Service;

import t5750.dubbox.service.hello.HelloService;
import t5750.dubbox.service.user.User;

@Service
public class HelloServiceImpl implements HelloService {
	private final AtomicLong idGen = new AtomicLong();

	public User hello(String name) {
		User user = new User();
		user.setId(idGen.incrementAndGet());
		user.setName(name);
		return user;
	}
}
