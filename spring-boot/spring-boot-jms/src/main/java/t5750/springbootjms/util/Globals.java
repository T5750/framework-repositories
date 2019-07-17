package t5750.springbootjms.util;

public class Globals {
	public static final String JMS_DESTINATION = "jms.message.endpoint";
	public static final String JMS_ORDER_QUEUE = "order-queue";
	public static final String JMS_ORDER_TOPIC = "order-topic";
	public static final String JMS_ORDER_QUEUE_EXCEPTION = JMS_ORDER_QUEUE
			+ "-exception";
	public static final String JMS_ORDER_QUEUE_HEADER = JMS_ORDER_QUEUE
			+ "-header";
}