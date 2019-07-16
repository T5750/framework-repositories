package t5750.springboot.listener;

import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import t5750.springboot.model.Message;
import t5750.springboot.model.Order;
import t5750.springboot.model.Topic;
import t5750.springboot.util.Globals;

@Component
public class JmsReceiver {
	private static Logger logger = LoggerFactory.getLogger(JmsReceiver.class);

	/**
	 * for the message
	 */
	@JmsListener(destination = Globals.JMS_DESTINATION, containerFactory = "queueListenerFactory")
	public void receiveMessage(Message msg) {
		System.out.println("Received " + msg);
	}

	/**
	 * for the order
	 */
	@JmsListener(destination = Globals.JMS_ORDER_QUEUE, containerFactory = "queueListenerFactory")
	public void receiveMessage(@Payload Order order,
			@Headers MessageHeaders headers,
			org.springframework.messaging.Message message, Session session) {
		logger.info("received <" + order + ">");
		String logPrefix = "order " + order.getId();
		logger.info(logPrefix + " headers: " + headers);
		logger.info(logPrefix + " message: " + message);
		logger.info(logPrefix + " session: " + session);
	}

	/**
	 * for the topic
	 */
	@JmsListener(destination = Globals.JMS_ORDER_TOPIC, containerFactory = "topicListenerFactory")
	public void receiveTopicMessage(@Payload Topic topic,
			@Headers MessageHeaders headers,
			org.springframework.messaging.Message message, Session session) {
		logger.info("received <" + topic + ">");
	}
}
