package t5750.springbootnacos.dao;

import org.springframework.data.repository.CrudRepository;

import t5750.springbootnacos.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {
}
