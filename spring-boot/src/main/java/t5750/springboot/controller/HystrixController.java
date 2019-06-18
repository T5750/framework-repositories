package t5750.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class HystrixController {
	@RequestMapping(value = "/hystrix")
	@HystrixCommand(fallbackMethod = "fallbackHello", commandProperties = { @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public String hello() throws InterruptedException {
		Thread.sleep(3000);
		return "Welcome Hystrix";
	}

	private String fallbackHello() {
		return "Request fails. It takes long time to response";
	}
}