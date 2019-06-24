package t5750.springbootsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class BootSecurityApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootSecurityApplication.class, args);
	}
}
