package t5750.springboot2.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import t5750.springboot2.converter.EmployeeToEmployeeVO;
import t5750.springboot2.converter.EmployeeVOToEmployee;
import t5750.springboot2.dao.EmployeeDAO;
import t5750.springboot2.exception.RecordNotFoundException;
import t5750.springboot2.model.Employee;
import t5750.springboot2.model.Employees;
import t5750.springboot2.vo.EmployeeVO;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {
	@Autowired
	private EmployeeDAO employeeDao;
	@Autowired
	private EmployeeToEmployeeVO employeeToEmployeeVO;
	@Autowired
	private EmployeeVOToEmployee employeeVOToEmployee;

	@GetMapping(path = "/", produces = "application/json")
	public Employees getEmployees() {
		return employeeDao.getAllEmployees();
	}

	@PostMapping(path = "/", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Object> addEmployee(
			@RequestHeader(name = "X-COM-PERSIST", required = true) String headerPersist,
			@RequestHeader(name = "X-COM-LOCATION", required = false, defaultValue = "ASIA") String headerLocation,
			@RequestBody Employee employee) throws Exception {
		// Generate resource id
		Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
		employee.setId(id);
		// add resource
		employeeDao.addEmployee(employee);
		// Create resource location
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(employee.getId()).toUri();
		// Send location in response
		return ResponseEntity.created(location).build();
	}

	@PostMapping(value = "/vo")
	public ResponseEntity<EmployeeVO> addEmployeeVO(
			@Valid @RequestBody EmployeeVO employeeVO) {
		Employee employee = employeeVOToEmployee.convert(employeeVO);
		Integer id = employeeDao.getAllEmployees().getEmployeeList().size() + 1;
		employee.setId(id);
		employeeVO.setEmployeeId(id);
		employeeDao.addEmployee(employee);
		return new ResponseEntity<EmployeeVO>(employeeVO, HttpStatus.OK);
	}

	@GetMapping(value = "/vo/{id}")
	public ResponseEntity<EmployeeVO> getEmployeeById(
			@PathVariable("id") int id) {
		EmployeeVO employeeVO;
		Employee employee = employeeDao.getEmployeeById(id);
		if (employee == null) {
			throw new RecordNotFoundException("Invalid employee id : " + id);
		} else {
			employeeVO = employeeToEmployeeVO.convert(employee);
		}
		return new ResponseEntity<EmployeeVO>(employeeVO, HttpStatus.OK);
	}
}
