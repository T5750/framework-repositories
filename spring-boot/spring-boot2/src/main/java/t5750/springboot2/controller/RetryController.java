package t5750.springboot2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot2.service.BackendAdapter;

@RestController
public class RetryController {
	@Autowired
	private BackendAdapter backendAdapter;

	@GetMapping("/retry")
	@ExceptionHandler({ Exception.class })
	public String validateSPringRetryCapability(
			@RequestParam(required = false) boolean simulateretry,
			@RequestParam(required = false) boolean simulateretryfallback) {
		System.out.println("===============================");
		System.out.println("Inside RestController mathod..");
		return backendAdapter.getBackendResponse(simulateretry,
				simulateretryfallback);
	}
}
