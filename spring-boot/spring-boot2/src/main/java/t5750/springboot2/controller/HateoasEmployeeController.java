package t5750.springboot2.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2.dao.EmployeeVODB;
import t5750.springboot2.vo.EmployeeListVO;
import t5750.springboot2.vo.EmployeeReport;
import t5750.springboot2.vo.EmployeeVO;

@RestController
@RequestMapping(path = "/hateoas/employees")
public class HateoasEmployeeController {
	@RequestMapping(value = "")
	public EmployeeListVO getAllEmployees() {
		EmployeeListVO employeesList = new EmployeeListVO();
		for (EmployeeVO employee : EmployeeVODB.getEmployeeList()) {
			// Adding self link employee 'singular' resource
			Link link = ControllerLinkBuilder
					.linkTo(HateoasEmployeeController.class)
					.slash(employee.getEmployeeId()).withSelfRel();
			// Add link to singular resource
			employee.add(link);
			// Adding method link employee 'singular' resource
			ResponseEntity<EmployeeReport> methodLinkBuilder = ControllerLinkBuilder
					.methodOn(HateoasEmployeeController.class)
					.getReportByEmployeeById(employee.getEmployeeId());
			Link reportLink = ControllerLinkBuilder.linkTo(methodLinkBuilder)
					.withRel("employee-report");
			// Add link to singular resource
			employee.add(reportLink);
			employeesList.getEmployees().add(employee);
		}
		// Adding self link employee collection resource
		Link selfLink = ControllerLinkBuilder.linkTo(ControllerLinkBuilder
				.methodOn(HateoasEmployeeController.class).getAllEmployees())
				.withSelfRel();
		// Add link to collection resource
		employeesList.add(selfLink);
		return employeesList;
	}

	@RequestMapping(value = "/{id}")
	public ResponseEntity<EmployeeVO> getEmployeeById(
			@PathVariable("id") int id) {
		if (id <= 3) {
			EmployeeVO employee = EmployeeVODB.getEmployeeList().get(id - 1);
			// Self link
			Link selfLink = ControllerLinkBuilder
					.linkTo(HateoasEmployeeController.class)
					.slash(employee.getEmployeeId()).withSelfRel();
			// Method link
			Link reportLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder
							.methodOn(HateoasEmployeeController.class)
							.getReportByEmployeeById(employee.getEmployeeId()))
					.withRel("report");
			employee.add(selfLink);
			employee.add(reportLink);
			return new ResponseEntity<EmployeeVO>(employee, HttpStatus.OK);
		}
		return new ResponseEntity<EmployeeVO>(HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/{id}/report")
	public ResponseEntity<EmployeeReport> getReportByEmployeeById(
			@PathVariable("id") int id) {
		// Do some operation and return report
		if (id <= 3) {
			EmployeeVO employee = EmployeeVODB.getEmployeeList().get(id - 1);
			EmployeeReport employeeReport = new EmployeeReport();
			employeeReport.setEmployee(employee);
			Link selfLink = ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder
							.methodOn(HateoasEmployeeController.class)
							.getReportByEmployeeById(employee.getEmployeeId()))
					.withRel("report");
			employeeReport.add(selfLink);
			return new ResponseEntity<EmployeeReport>(employeeReport,
					HttpStatus.OK);
		}
		return new ResponseEntity<EmployeeReport>(HttpStatus.NOT_FOUND);
	}
}
