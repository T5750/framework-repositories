package t5750.springboot2provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;

@SpringBootApplication(exclude = {JacksonAutoConfiguration.class})
public class Boot2Provider {
	public static void main(String[] args) {
		SpringApplication.run(Boot2Provider.class, args);
	}
}
