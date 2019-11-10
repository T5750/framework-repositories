package t5750.springboot2security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import t5750.springboot2security.dao.EmployeeDAO;
import t5750.springboot2security.model.Employee;
import t5750.springboot2security.model.Employees;

import java.net.URI;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
	@Autowired
	private EmployeeDAO employeeDao;

	@GetMapping(path = "/", produces = "application/json")
	public Employees getEmployees() {
		return employeeDao.getAllEmployees();
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addEmployee(
			@RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
			@RequestHeader(name = "X-COM-LOCATION", defaultValue = "ASIA") String headerLocation,
			@RequestBody Employee employee)
			throws Exception {
		//Generate resource id
		Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
		employee.setId(id);
		//add resource
		employeeDao.addEmployee(employee);
		//Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(employee.getId())
				.toUri();
		//Send location in response
		return ResponseEntity.created(location).build();
	}
}
