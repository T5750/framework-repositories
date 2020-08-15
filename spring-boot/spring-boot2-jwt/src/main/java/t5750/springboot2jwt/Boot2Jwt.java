package t5750.springboot2jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class Boot2Jwt {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Boot2Jwt.class, args);
	}
}
