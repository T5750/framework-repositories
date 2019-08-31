package t5750.springboot2.service;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import t5750.springboot2.util.Globals;
import t5750.springboot2api.model.EmployeeAddresses;
import t5750.springboot2api.model.EmployeeNames;
import t5750.springboot2api.model.EmployeePhone;

@Service
public class AsyncService {
	private static Logger logger = LoggerFactory.getLogger(AsyncService.class);
	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Async("asyncExecutor")
	public CompletableFuture<EmployeeNames> getEmployeeName()
			throws InterruptedException {
		logger.info("getEmployeeName Starts");
		EmployeeNames employeeNameData = restTemplate.getForObject(
				Globals.BOOT2PROVIDER_PATH + "names",
				EmployeeNames.class);
		logger.info("employeeNameData, {}", employeeNameData);
		Thread.sleep(1000L); // Intentional delay
		logger.info("employeeNameData completed");
		return CompletableFuture.completedFuture(employeeNameData);
	}

	@Async("asyncExecutor")
	public CompletableFuture<EmployeeAddresses> getEmployeeAddress()
			throws InterruptedException {
		logger.info("getEmployeeAddress Starts");
		EmployeeAddresses employeeAddressData = restTemplate.getForObject(
				Globals.BOOT2PROVIDER_PATH + "addresses",
				EmployeeAddresses.class);
		logger.info("employeeAddressData, {}", employeeAddressData);
		Thread.sleep(1000L); // Intentional delay
		logger.info("employeeAddressData completed");
		return CompletableFuture.completedFuture(employeeAddressData);
	}

	@Async("asyncExecutor")
	public CompletableFuture<EmployeePhone> getEmployeePhone()
			throws InterruptedException {
		logger.info("getEmployeePhone Starts");
		EmployeePhone employeePhoneData = restTemplate.getForObject(
				Globals.BOOT2PROVIDER_PATH + "phones",
				EmployeePhone.class);
		logger.info("employeePhoneData, {}", employeePhoneData);
		Thread.sleep(1000L); // Intentional delay
		logger.info("employeePhoneData completed");
		return CompletableFuture.completedFuture(employeePhoneData);
	}
}
