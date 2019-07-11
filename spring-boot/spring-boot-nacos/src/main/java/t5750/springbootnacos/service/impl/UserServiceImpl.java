package t5750.springbootnacos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.springbootnacos.dao.UserRepository;
import t5750.springbootnacos.model.User;
import t5750.springbootnacos.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public User findById(Integer id) {
		return userRepository.findOne(id);
	}
}