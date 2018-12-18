package t5750.storm.message.topology;

import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.message.bolt.SpliterBolt;
import t5750.storm.message.bolt.WriterBolt;
import t5750.storm.message.spout.MessageSpout;
import t5750.storm.util.StormUtil;

public class MessageTopology {
	private static final String TOPOLOGY_NAME = "message";

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new MessageSpout());
		builder.setBolt("split-bolt", new SpliterBolt())
				.shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriterBolt())
				.shuffleGrouping("split-bolt");
		StormUtil.submitTopology(args, builder, TOPOLOGY_NAME);
	}
}
