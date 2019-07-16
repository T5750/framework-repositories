package t5750.springboot.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.springboot.model.Message;
import t5750.springboot.model.Order;
import t5750.springboot.model.Topic;
import t5750.springboot.service.JmsService;

/**
 * Spring Boot JMSTemplate with Embedded ActiveMQ
 */
@RestController
@RequestMapping("/jms")
public class JmsController {
	@Autowired
	private JmsService jmsService;

	@RequestMapping(value = "/sendMessage")
	public Message sendMessage() {
		Message message = new Message(1001L, "test body", new Date());
		jmsService.sendMessage(message);
		return message;
	}

	@RequestMapping(value = "/sendOrder")
	public List sendOrder() {
		final int listSize = 5;
		List<Order> orderList = new ArrayList<>(listSize);
		for (long i = 0; i < listSize; i++) {
			Order order = new Order(i, i
					+ " - Sending JMS Message using Embedded activeMQ",
					new Date());
			jmsService.sendOrder(order);
			orderList.add(order);
		}
		return orderList;
	}

	@RequestMapping(value = "/sendTopic")
	public List sendTopic() {
		final int listSize = 5;
		List<Topic> topicList = new ArrayList<>(listSize);
		for (int i = 0; i < listSize; i++) {
			Topic topic = new Topic("me", "you", new BigDecimal(i),
					LocalDateTime.now());
			jmsService.sendTopic(topic);
			topicList.add(topic);
		}
		return topicList;
	}
}