package t5750.springcloudalibaba.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;

import t5750.springcloudalibaba.consumer.service.RocketMqSink;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableBinding({ RocketMqSink.class })
public class CloudAlibabaConsumer {
	public static void main(String[] args) {
		SpringApplication.run(CloudAlibabaConsumer.class, args);
	}
}
