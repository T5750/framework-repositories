package t5750.storm.kafka.wordcount;

import java.util.UUID;

import org.apache.storm.kafka.*;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.util.KafkaUtil;
import t5750.storm.util.StormUtil;
import t5750.storm.util.ZkUtil;

/**
 * Storm Kafka Integration (0.8.x)<br/>
 * http://storm.apache.org/releases/1.2.2/storm-kafka.html
 */
public class KafkaTopology {
	private static final String TOPOLOGY_NAME = "KafkaToplogy";

	public static void main(String[] args) throws Exception {
		// zookeeper hosts for the Kafka clustere
		// ZkHosts zkHosts = new ZkHosts("192.168.100.142:2181");
		// 注意这里的Spout的来源是kafka(kafka数据流入storm)
		// SpoutConfig kafkaConfig = new SpoutConfig(zkHosts, "words_topic",
		// "","id7");
		// Specify that the kafka messages are String
		// kafkaConfig.scheme = new SchemeAsMultiScheme((Scheme)new
		// StringScheme());
		// We want to consume all the first messages in the topic everytime
		// we run the topology to help in debugging. In production, this
		// property should be false
		// kafkaConfig.forceFromStart = true;
		BrokerHosts hosts = new ZkHosts(ZkUtil.CONNECT_ADDR);
		SpoutConfig spoutConfig = new SpoutConfig(hosts, KafkaUtil.TOPIC_WORDS,
				"/" + KafkaUtil.TOPIC_WORDS, UUID.randomUUID().toString());
		spoutConfig.scheme = new SchemeAsMultiScheme(new StringScheme());
		// Now we create the topology
		TopologyBuilder builder = new TopologyBuilder();
		// set the kafka spout class
		builder.setSpout("KafkaSpout", new KafkaSpout(spoutConfig), 1);
		// configure the bolts
		builder.setBolt("SentenceBolt", new SentenceBolt(), 1)
				.globalGrouping("KafkaSpout");
		builder.setBolt("PrinterBolt", new PrinterBolt(), 1)
				.globalGrouping("SentenceBolt");
		StormUtil.submitTopology(args, builder, TOPOLOGY_NAME);
	}
}
