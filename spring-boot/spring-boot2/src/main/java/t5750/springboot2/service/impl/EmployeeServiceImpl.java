package t5750.springboot2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import t5750.springboot2.exception.RecordNotFoundException;
import t5750.springboot2.model.EmployeeEntity;
import t5750.springboot2.repository.EmployeeRepository;
import t5750.springboot2.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository repository;

	public List<EmployeeEntity> getAllEmployees() {
		List<EmployeeEntity> employeeList = repository.findAll();
		if (employeeList.size() > 0) {
			return employeeList;
		} else {
			return new ArrayList<EmployeeEntity>();
		}
	}

	public EmployeeEntity getEmployeeById(Long id)
			throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);
		if (employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException(
					"No employee record exist for given id");
		}
	}

	public EmployeeEntity createOrUpdateEmployee(EmployeeEntity entity)
			throws RecordNotFoundException {
		if (StringUtils.isEmpty(entity.getId())) {
			EmployeeEntity employeeEntity = new EmployeeEntity();
			employeeEntity.setFirstName(entity.getFirstName());
			employeeEntity.setLastName(entity.getLastName());
			employeeEntity.setEmail(entity.getEmail());
			employeeEntity = repository.save(employeeEntity);
			return employeeEntity;
		}
		Optional<EmployeeEntity> employee = repository.findById(entity.getId());
		if (employee.isPresent()) {
			EmployeeEntity newEntity = employee.get();
			newEntity.setEmail(entity.getEmail());
			newEntity.setFirstName(entity.getFirstName());
			newEntity.setLastName(entity.getLastName());
			newEntity = repository.save(newEntity);
			return newEntity;
		} else {
			entity = repository.save(entity);
			return entity;
		}
	}

	public void deleteEmployeeById(Long id) throws RecordNotFoundException {
		Optional<EmployeeEntity> employee = repository.findById(id);
		if (employee.isPresent()) {
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException(
					"No employee record exist for given id");
		}
	}
}