package t5750.storm.kafka.wordcount;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import t5750.storm.util.KafkaUtil;
import t5750.storm.util.ZkUtil;

public class WordsProducer {
	public static void main(String[] args) {
		// Build the configuration required for connecting to Kafka
		Properties props = new Properties();
		props.put("zookeeper.connect", ZkUtil.CONNECT_ADDR);
		// List of Kafka brokers. Complete list of brokers is not
		// required as the producer will auto discover the rest of
		// the brokers. Change this to suit your deployment.
		props.put("metadata.broker.list", KafkaUtil.CONNECT_ADDR);
		// Serializer used for sending data to kafka. Since we are sending
		// string, we are using StringEncoder.
		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// We want acks from Kafka that messages are properly received.
		props.put("request.required.acks", "1");
		// Create the producer instance
		ProducerConfig config = new ProducerConfig(props);
		Producer<String, String> producer = new Producer<String, String>(
				config);
		// Now we break each word from the paragraph
		for (String word : METAMORPHOSIS_OPENING_PARA.split("\\s")) {
			// Create message to be sent to "words_topic" topic with the word
			KeyedMessage<String, String> data = new KeyedMessage<String, String>(
					KafkaUtil.TOPIC_WORDS, word);
			// Send the message
			producer.send(data);
		}
		System.out.println("Produced data");
		// close the producer
		producer.close();
	}

	// First paragraph from Franz Kafka's Metamorphosis
	private static String METAMORPHOSIS_OPENING_PARA = "One morning, when Gregor Samsa woke from troubled dreams, "
			+ "he found himself transformed in his bed into a horrible "
			+ "vermin. He lay on his armour-like back, and if he lifted "
			+ "his head a little he could see his brown belly, slightly "
			+ "domed and divided by arches into stiff sections.";
}
