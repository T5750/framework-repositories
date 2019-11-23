package t5750.springboot2oauth2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
public class Boot2Oauth2 extends WebSecurityConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(Boot2Oauth2.class, args);
	}
}