package t5750.springbootnacos.service;

import t5750.springbootnacos.model.User;

public interface UserService {
	User findById(Integer id);
}