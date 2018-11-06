package com.evangel.activemqprovider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 */
@SpringBootApplication
public class ActiveMQProviderApplication {
	public static void main(String[] args) {
		SpringApplication.run(ActiveMQProviderApplication.class, args);
	}
}
// http://localhost:8080/activemq-provider/publish/queue
// http://localhost:8080/activemq-provider/publish/topic