package t5750.springcloudalibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;

import t5750.springcloudalibaba.service.RocketMqSource;

@SpringBootApplication
@EnableDiscoveryClient
@EnableBinding({ RocketMqSource.class })
public class CloudAlibabaApplication {
	public static void main(String[] args) {
		SpringApplication.run(CloudAlibabaApplication.class, args);
	}
}