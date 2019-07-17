package t5750.springbootjms.listener;

import java.util.Map;

import javax.jms.Session;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.support.JmsHeaders;
import org.springframework.jms.support.JmsMessageHeaderAccessor;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import t5750.springbootjms.exception.OrderProcessingException;
import t5750.springbootjms.model.Message;
import t5750.springbootjms.model.Order;
import t5750.springbootjms.model.Topic;
import t5750.springbootjms.util.Globals;

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
	public void receiveOrder(@Payload @Valid Order order,
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

	@JmsListener(destination = Globals.JMS_ORDER_QUEUE_EXCEPTION, containerFactory = "queueListenerFactory")
	public void receiveException(@Payload Order order,
			@Headers MessageHeaders headers,
			org.springframework.messaging.Message message, Session session) {
		logger.info("received <" + order + ">");
		// processing or the order
		throw new OrderProcessingException(
				"problem occurred while processing the order.");
	}

	@JmsListener(destination = Globals.JMS_ORDER_QUEUE_HEADER, containerFactory = "queueListenerFactory")
	public void receiveHeader(
			@Payload Order order,
			@Header(JmsHeaders.CORRELATION_ID) String correlationId,
			@Header(name = "jms-header-not-exists", defaultValue = "default") String nonExistingHeader,
			@Headers Map<String, Object> headers,
			MessageHeaders messageHeaders,
			JmsMessageHeaderAccessor jmsMessageHeaderAccessor) {
		logger.info("received <" + order + ">");
		logger.info("\n# Spring JMS accessing single header property");
		logger.info("- jms_correlationId=" + correlationId);
		logger.info("- jms-header-not-exists=" + nonExistingHeader);
		logger.info("\n# Spring JMS retrieving all header properties using Map<String, Object>");
		logger.info("- jms-custom-header="
				+ String.valueOf(headers.get("jms-custom-property")));
		logger.info("\n# Spring JMS retrieving all header properties MessageHeaders");
		logger.info("- jms-custom-property-price="
				+ messageHeaders.get("jms-custom-property-price", Double.class));
		logger.info("\n# Spring JMS retrieving all header properties JmsMessageHeaderAccessor");
		logger.info("- jms_destination="
				+ jmsMessageHeaderAccessor.getDestination());
		logger.info("- jms_priority=" + jmsMessageHeaderAccessor.getPriority());
		logger.info("- jms_timestamp="
				+ jmsMessageHeaderAccessor.getTimestamp());
		logger.info("- jms_type=" + jmsMessageHeaderAccessor.getType());
		logger.info("- jms_redelivered="
				+ jmsMessageHeaderAccessor.getRedelivered());
		logger.info("- jms_replyTo=" + jmsMessageHeaderAccessor.getReplyTo());
		logger.info("- jms_correlationId="
				+ jmsMessageHeaderAccessor.getCorrelationId());
		logger.info("- jms_contentType="
				+ jmsMessageHeaderAccessor.getContentType());
		logger.info("- jms_expiration="
				+ jmsMessageHeaderAccessor.getExpiration());
		logger.info("- jms_messageId="
				+ jmsMessageHeaderAccessor.getMessageId());
		logger.info("- jms_deliveryMode="
				+ jmsMessageHeaderAccessor.getDeliveryMode() + "\n");
	}
}
