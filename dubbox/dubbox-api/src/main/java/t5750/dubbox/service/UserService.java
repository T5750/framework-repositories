package t5750.dubbox.service;

import t5750.dubbox.domain.User;

public interface UserService {
	User getUser(Long id);

	Long registerUser(User user);
}
