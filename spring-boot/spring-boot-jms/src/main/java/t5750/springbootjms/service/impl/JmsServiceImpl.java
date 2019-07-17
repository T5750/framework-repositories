package t5750.springbootjms.service.impl;

import java.util.UUID;

import javax.jms.DeliveryMode;

import org.apache.activemq.command.ActiveMQQueue;
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
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.convertAndSend(Globals.JMS_DESTINATION, message);
	}

	@Override
	public void sendOrder(Order order) {
		logger.info("sending with convertAndSend() to queue <" + order + ">");
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_QUEUE, order);
	}

	@Override
	public void sendTopic(Topic topic) {
		logger.info("sending with convertAndSend() to topic <" + topic + ">");
		jmsTemplate.setPubSubDomain(true);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_TOPIC, topic);
	}

	@Override
	public void sendInvaildOrder(Order order) {
		logger.info("sending invaild order to " + Globals.JMS_ORDER_QUEUE
				+ " <" + order + ">");
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_QUEUE, order);
	}

	@Override
	public void sendException(Order order) {
		logger.info("sending an exception to "
				+ Globals.JMS_ORDER_QUEUE_EXCEPTION + " <" + order + ">");
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_QUEUE_EXCEPTION, order);
	}

	@Override
	public void sendHeader(Order order) {
		logger.info("sending a header to " + Globals.JMS_ORDER_QUEUE_HEADER
				+ " <" + order + ">");
		jmsTemplate.setPubSubDomain(false);
		jmsTemplate.convertAndSend(Globals.JMS_ORDER_QUEUE_HEADER, order,
				m -> {
					logger.info("setting standard JMS headers before sending");
					m.setJMSCorrelationID(UUID.randomUUID().toString());
					m.setJMSExpiration(1000);
					m.setJMSMessageID("message-id");
					m.setJMSDestination(new ActiveMQQueue(
							Globals.JMS_ORDER_QUEUE_HEADER));
					m.setJMSReplyTo(new ActiveMQQueue(
							Globals.JMS_ORDER_QUEUE_HEADER));
					m.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
					m.setJMSPriority(javax.jms.Message.DEFAULT_PRIORITY);
					m.setJMSTimestamp(System.nanoTime());
					m.setJMSType("type");
					logger.info("setting custom JMS headers before sending");
					m.setStringProperty("jms-custom-header",
							"this is a custom jms property");
					m.setBooleanProperty("jms-custom-property", true);
					m.setDoubleProperty("jms-custom-property-price", 0.0);
					return m;
				});
	}
}