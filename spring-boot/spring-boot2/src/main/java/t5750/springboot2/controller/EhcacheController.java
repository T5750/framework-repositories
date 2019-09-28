package t5750.springboot2.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2.model.Employee;
import t5750.springboot2.service.EmployeeManager;

@RestController
@RequestMapping("/ehcache")
public class EhcacheController {
	private static final Logger LOGGER = LogManager
			.getLogger(EhcacheController.class);
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private EmployeeManager employeeManager;

	@GetMapping(value = "/employee")
	public ResponseEntity<Object> employee() {
		// This will hit the database
		LOGGER.info(employeeManager.getEmployeeById(1));
		// This will hit the cache - verify the message in console output
		LOGGER.info(employeeManager.getEmployeeById(1));
		// Access cache instance by name
		Cache cache = cacheManager.getCache("employeeCache");
		// Add entry to cache
		cache.put(3, new Employee(3, "Charles", "Dave"));
		// Get entry from cache
		LOGGER.info(cache.get(3).get());
		return new ResponseEntity<Object>(cache.get(3).get(), new HttpHeaders(),
				HttpStatus.OK);
	}
}