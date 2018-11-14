package com.evangel.activemqprovider.selector;

import javax.jms.*;

import com.evangel.activemqprovider.selector.util.SelectorUtil;

/**
 *
 */
public class ConsumerA {
	public final String SELECTOR = "receiver = 'A'";
	/**
	 * 1 连接工厂
	 */
	private ConnectionFactory connectionFactory;
	/**
	 * 2 连接对象
	 */
	private Connection connection;
	/**
	 * 3 Session对象
	 */
	private Session session;
	/**
	 * 4 消费者
	 */
	private MessageConsumer messageConsumer;
	/**
	 * 5 目标地址
	 */
	private Destination destination;

	public ConsumerA() {
		try {
			this.connectionFactory = SelectorUtil.getConnectionFactory();
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			this.destination = this.session
					.createQueue(SelectorUtil.QUEUE_NAME);
			this.messageConsumer = this.session.createConsumer(
					this.destination, SELECTOR);
			System.out.println("Consumer A start...");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public void receiver() {
		try {
			this.messageConsumer.setMessageListener(new Listener());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		ConsumerA c = new ConsumerA();
		c.receiver();
	}
}
