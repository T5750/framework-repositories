package com.evangel.activemqprovider.selector;

import javax.jms.*;

import com.evangel.activemqprovider.selector.util.SelectorUtil;

/**
 *
 */
public class Producer {
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
	 * 4 生产者
	 */
	private MessageProducer messageProducer;

	public Producer() {
		try {
			this.connectionFactory = SelectorUtil.getConnectionFactory();
			this.connection = this.connectionFactory.createConnection();
			this.connection.start();
			this.session = this.connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			this.messageProducer = this.session.createProducer(null);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public Session getSession() {
		return this.session;
	}

	public void send(/* String queuename, Message message */) {
		try {
			Destination destination = this.session
					.createQueue(SelectorUtil.QUEUE_NAME);
			for (int i = 0; i < 100; i++) {
				MapMessage msg = this.session.createMapMessage();
				int id = i;
				msg.setInt("id", id);
				msg.setString("name", "name-" + i);
				msg.setString("age", "" + i);
				String receiver = i % 2 == 0 ? "A" : "B";
				// setStringProperty设置用于消息过滤器的条件
				msg.setStringProperty("receiver", receiver);
				this.messageProducer.send(destination, msg,
						DeliveryMode.NON_PERSISTENT, 2, 1000 * 60 * 10L);
				System.out.println("message send id : " + id);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Producer p = new Producer();
		p.send();
	}
}
