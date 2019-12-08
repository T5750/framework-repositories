package t5750.springboot2.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;

@Configuration
public class EmailConfig {
	@Value("${spring.mail.username}")
	private String from;

	@Bean
	public SimpleMailMessage emailTemplate() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(from);
		message.setFrom(from);
		message.setSubject("Important email");
		message.setText("FATAL - Application crash. Save your job !!");
		return message;
	}
}