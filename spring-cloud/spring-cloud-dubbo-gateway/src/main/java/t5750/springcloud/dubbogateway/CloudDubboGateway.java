package t5750.springcloud.dubbogateway;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Dubbo Spring Cloud Servlet Gateway Bootstrap.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
@EnableFeignClients
@ServletComponentScan(basePackages = "t5750.springcloud.dubbogateway.gateway")
public class CloudDubboGateway {
	public static void main(String[] args) {
		new SpringApplicationBuilder(CloudDubboGateway.class)
				.properties("spring.profiles.active=nacos").run(args);
	}
}