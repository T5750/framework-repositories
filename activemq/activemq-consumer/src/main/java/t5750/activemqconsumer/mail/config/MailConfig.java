package t5750.activemqconsumer.mail.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件 MailConfig
 */
@Configuration
@EnableAutoConfiguration
public class MailConfig {
	@Value("${queueNameMail}")
	private String queueNameMail;
	@Value("${spring.mail.username}")
	private String mailUsername;

	@Bean
	public Queue queueMail() {
		return new ActiveMQQueue(queueNameMail);
	}

	@Bean
	public String mailUsername() {
		return mailUsername;
	}
}
