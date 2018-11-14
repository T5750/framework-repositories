package com.evangel.activemqprovider.selector.util;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 *
 */
public class SelectorUtil {
	public static final String QUEUE_NAME = "first";
	public static final String BROKER_URL = "failover:(tcp://192.168.100.163:51511,tcp://192.168.100.164:51512,tcp://192.168.100.165:51513)";

	public static ConnectionFactory getConnectionFactory() {
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				ActiveMQConnectionFactory.DEFAULT_USER,
				ActiveMQConnectionFactory.DEFAULT_PASSWORD, BROKER_URL);
		return connectionFactory;
	}
}
