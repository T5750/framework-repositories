package t5750.springboot2.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2.service.AsyncService;
import t5750.springboot2api.model.EmployeeAddresses;
import t5750.springboot2api.model.EmployeeNames;
import t5750.springboot2api.model.EmployeePhone;

@RestController
public class AsyncController {
	private static Logger logger = LoggerFactory
			.getLogger(AsyncController.class);
	@Autowired
	private AsyncService service;

	@RequestMapping(value = "/testAsynch", method = RequestMethod.GET)
	public ResponseEntity<String> testAsynch()
			throws InterruptedException, ExecutionException {
		logger.info("testAsynch Start");
		CompletableFuture<EmployeeAddresses> employeeAddress = service
				.getEmployeeAddress();
		CompletableFuture<EmployeeNames> employeeName = service
				.getEmployeeName();
		CompletableFuture<EmployeePhone> employeePhone = service
				.getEmployeePhone();
		// Wait until they are all done
		CompletableFuture.allOf(employeeAddress, employeeName, employeePhone)
				.join();
		logger.info("EmployeeAddress--> " + employeeAddress.get());
		logger.info("EmployeeName--> " + employeeName.get());
		logger.info("EmployeePhone--> " + employeePhone.get());
		String result = "Observe the output in console.";
		return new ResponseEntity<String>(result, HttpStatus.OK);
	}
}
