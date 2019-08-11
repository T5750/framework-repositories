package t5750.springboot2.controller;

import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CallableController {
	@GetMapping(value = "/testCallable")
	public Callable<String> testCallable() {
		return () -> {
			Thread.sleep(ThreadLocalRandom.current().nextInt(5000));
			return "Test Callable !!";
		};
	}
}
