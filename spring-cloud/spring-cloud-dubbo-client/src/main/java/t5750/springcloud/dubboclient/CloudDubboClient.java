package t5750.springcloud.dubboclient;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.dubbo.api.DemoService;

/**
 * Dubbo Spring Cloud Client Bootstrap.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@RestController
public class CloudDubboClient {
	@Reference
	private DemoService demoService;

	@GetMapping("/echo")
	public String echo(String message) {
		return demoService.sayHello(message);
	}

	public static void main(String[] args) {
		SpringApplication.run(CloudDubboClient.class);
	}
}
// http://localhost:18085/echo?message=T5750