package t5750.springboot2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import t5750.springboot2.model.EmployeeEntity;

@Repository
public interface EmployeeRepository
		extends JpaRepository<EmployeeEntity, Long> {
}
