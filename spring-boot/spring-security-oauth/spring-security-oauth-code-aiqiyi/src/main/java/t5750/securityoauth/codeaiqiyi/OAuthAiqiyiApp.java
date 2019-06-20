package t5750.securityoauth.codeaiqiyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class OAuthAiqiyiApp {
	public static void main(String[] args) {
		SpringApplication.run(OAuthAiqiyiApp.class, args);
	}

	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}
}