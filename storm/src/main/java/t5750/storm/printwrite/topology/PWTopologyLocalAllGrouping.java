package t5750.storm.printwrite.topology;

import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.printwrite.bolt.PrintBolt;
import t5750.storm.printwrite.bolt.WriteBolt;
import t5750.storm.printwrite.spout.PWSpout;
import t5750.storm.util.StormUtil;

public class PWTopologyLocalAllGrouping {
	private static final String TOPOLOGY_NAME = "top3";

	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new PWSpout(), 4);
		builder.setBolt("print-bolt", new PrintBolt(), 4)
				.shuffleGrouping("spout");
		// 设置字段分组
		// builder.setBolt("write-bolt", new WriteBolt(), 8)
		// .fieldsGrouping("print-bolt", new Fields("write"));
		// 设置广播分组
		builder.setBolt("write-bolt", new WriteBolt(), 4)
				.allGrouping("print-bolt");
		// 设置全局分组
		// builder.setBolt("write-bolt", new WriteBolt(),
		// 4).globalGrouping("print-bolt");
		StormUtil.submitTopology(args, builder, TOPOLOGY_NAME);
	}
}
