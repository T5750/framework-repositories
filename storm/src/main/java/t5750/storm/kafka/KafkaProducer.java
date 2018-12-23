package t5750.storm.kafka;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import t5750.storm.util.KafkaUtil;
import t5750.storm.util.ZkUtil;

public class KafkaProducer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("zookeeper.connect", ZkUtil.CONNECT_ADDR);
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		props.put("metadata.broker.list", KafkaUtil.CONNECT_ADDR);
		props.put("request.required.acks", "-1");
		Producer producer = new Producer(new ProducerConfig(props));
		String data = "";
		for (int i = 0; i < 10; i++) {
			data = "Hello Kafka " + i;
			producer.send(
					new KeyedMessage<Integer, String>(KafkaUtil.TOPIC, data));
			System.out.println("send message: " + data);
		}
		producer.close();
	}
}
