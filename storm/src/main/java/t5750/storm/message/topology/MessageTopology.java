package t5750.storm.message.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.message.bolt.SpliterBolt;
import t5750.storm.message.bolt.WriterBolt;
import t5750.storm.message.spout.MessageSpout;
import t5750.storm.util.StormUtil;

public class MessageTopology {
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new MessageSpout());
		builder.setBolt("split-bolt", new SpliterBolt())
				.shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriterBolt())
				.shuffleGrouping("split-bolt");
		// 本地模式
		Config config = new Config();
		config.setDebug(false);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("message", config, builder.createTopology());
		StormUtil.waitForSeconds(10);
		cluster.killTopology("message");
		cluster.shutdown();
	}
}
