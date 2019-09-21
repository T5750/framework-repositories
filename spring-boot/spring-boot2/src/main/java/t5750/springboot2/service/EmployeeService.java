package t5750.springboot2.service;

import java.util.List;

import t5750.springboot2.exception.RecordNotFoundException;
import t5750.springboot2.model.EmployeeEntity;

public interface EmployeeService {
	List<EmployeeEntity> getAllEmployees();

	EmployeeEntity getEmployeeById(Long id) throws RecordNotFoundException;

	EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity)
			throws RecordNotFoundException;

	void deleteEmployeeById(Long id) throws RecordNotFoundException;
}