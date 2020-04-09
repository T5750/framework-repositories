package t5750.springcloud.dubboserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Dubbo Spring Cloud Server Bootstrap.
 */
@EnableDiscoveryClient
@EnableAutoConfiguration
public class CloudDubboServer {
	public static void main(String[] args) {
		SpringApplication.run(CloudDubboServer.class);
	}
}