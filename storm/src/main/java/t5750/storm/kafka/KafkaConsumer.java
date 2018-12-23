package t5750.storm.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.serializer.StringDecoder;
import kafka.utils.VerifiableProperties;
import t5750.storm.util.KafkaUtil;
import t5750.storm.util.ZkUtil;

public class KafkaConsumer {
	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("zookeeper.connect", ZkUtil.CONNECT_ADDR);
		props.put("group.id", "group1");
		props.put("zookeeper.session.timeout.ms", ZkUtil.SESSION_TIMEOUT);
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");
		props.put("auto.offset.reset", "smallest");
		// 序列化类
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		ConsumerConfig config = new ConsumerConfig(props);
		ConsumerConnector consumer = Consumer
				.createJavaConsumerConnector(config);
		Map<String, Integer> topicCountMap = new HashMap<>();
		topicCountMap.put(KafkaUtil.TOPIC, new Integer(1));
		StringDecoder keyDecoder = new StringDecoder(
				new VerifiableProperties());
		StringDecoder valueDecoder = new StringDecoder(
				new VerifiableProperties());
		Map<String, List<KafkaStream<String, String>>> consumerMap = consumer
				.createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
		KafkaStream<String, String> stream = consumerMap.get(KafkaUtil.TOPIC)
				.get(0);
		ConsumerIterator<String, String> it = stream.iterator();
		while (it.hasNext()) {
			System.out.println(it.next().message());
		}
	}
}
