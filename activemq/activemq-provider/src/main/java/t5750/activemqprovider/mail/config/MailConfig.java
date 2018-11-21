package t5750.activemqprovider.mail.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置文件 MailConfig
 */
@Configuration
public class MailConfig {
	@Value("${queueNameMail}")
	private String queueNameMail;

	@Bean
	public Queue queueMail() {
		return new ActiveMQQueue(queueNameMail);
	}
}