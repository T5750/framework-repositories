package t5750.dubbox.service.impl.user;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import t5750.dubbox.service.user.User;
import t5750.dubbox.service.user.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final AtomicLong idGen = new AtomicLong();

	public User getUser(Long id) {
		return new User(id, "username" + id);
	}

	public Long registerUser(User user) {
		// System.out.println("Username is " + user.getName());
		return idGen.incrementAndGet();
	}
}
