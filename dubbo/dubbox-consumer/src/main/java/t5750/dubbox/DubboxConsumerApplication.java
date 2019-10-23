package t5750.dubbox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DubboxConsumerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication
				.run(DubboxConsumerApplication.class, args);
	}
}
// http://localhost:8080/dubbox-consumer/hello?name=world