package t5750.springbootjms.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import t5750.springbootjms.model.Message;
import t5750.springbootjms.model.Order;
import t5750.springbootjms.model.Topic;
import t5750.springbootjms.service.JmsService;
import t5750.springbootjms.util.Globals;

@Service
public class JmsServiceImpl implements JmsService {
	private static Logger logger = LoggerFactory
			.getLogger(JmsServiceImpl.class);
	@Autowired
	private JmsTemplate jmsTemplate;

	@Override
	public void sendMessage(Message message) {
		// Send a message
		System.out.println("Sending a message.");
		jmsTemplate.convertAndSend(Globals.JMS_DESTINATION, message);
	}

	@Override
	public void sendOrder(Order order) {
		logger.info("sending with convertAndSend() to queue <" + order + ">");
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_QUEUE, order);
	}

	@Override
	public void sendTopic(Topic topic) {
		logger.info("sending with convertAndSend() to topic <" + topic + ">");
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_TOPIC, topic);
	}
}