package t5750.springboot2.service;

import java.util.HashMap;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import t5750.springboot2.model.Employee;

@Service
public class EmployeeManager {
	static HashMap<Integer, Employee> db = new HashMap<>();
	static {
		db.put(1, new Employee(1, "Alex", "Gussin"));
		db.put(2, new Employee(2, "Brian", "Schultz"));
	}

	@Cacheable(cacheNames = "employeeCache", key = "#id")
	public Employee getEmployeeById(Integer id) {
		System.out.println("Getting employee from DB");
		return db.get(id);
	}
}