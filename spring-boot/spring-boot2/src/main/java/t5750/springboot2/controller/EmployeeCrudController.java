package t5750.springboot2.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import t5750.springboot2.exception.RecordNotFoundException;
import t5750.springboot2.model.EmployeeEntity;
import t5750.springboot2.service.EmployeeService;

@RestController
@RequestMapping("/employeeCrud")
public class EmployeeCrudController {
	@Autowired
	private EmployeeService service;

	@GetMapping
	public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
		List<EmployeeEntity> list = service.getAllEmployees();
		return new ResponseEntity<List<EmployeeEntity>>(list, new HttpHeaders(),
				HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EmployeeEntity> getEmployeeById(
			@PathVariable("id") Long id) throws RecordNotFoundException {
		EmployeeEntity entity = service.getEmployeeById(id);
		return new ResponseEntity<EmployeeEntity>(entity, new HttpHeaders(),
				HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeEntity> createOrUpdateEmployee(
			EmployeeEntity employee) throws RecordNotFoundException {
		EmployeeEntity updated = service.createOrUpdateEmployee(employee);
		return new ResponseEntity<EmployeeEntity>(updated, new HttpHeaders(),
				HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public HttpStatus deleteEmployeeById(@PathVariable("id") Long id)
			throws RecordNotFoundException {
		service.deleteEmployeeById(id);
		return HttpStatus.FORBIDDEN;
	}
}