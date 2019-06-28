package t5750.springboot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot.model.Message;
import t5750.springboot.util.Globals;

/**
 * Spring Boot JMSTemplate with Embedded ActiveMQ
 */
@RestController
public class MessageController {
	@Autowired
	private JmsTemplate jmsTemplate;

	@RequestMapping(value = "/sendMessage")
	public Message sendMessage() {
		Message message = new Message(1001L, "test body", new Date());
		// Send a message
		System.out.println("Sending a message.");
		jmsTemplate.convertAndSend(Globals.JMS_DESTINATION, message);
		return message;
	}
}