package t5750.storm.printwrite.topology;

import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.printwrite.bolt.PrintBolt;
import t5750.storm.printwrite.bolt.WriteBolt;
import t5750.storm.printwrite.spout.PWSpout;
import t5750.storm.util.StormUtil;

public class PWTopologyLocal {
	private static final String TOPOLOGY_NAME = "top1";

	public static void main(String[] args) throws Exception {
		StormUtil.hasDirectory();
		// Config cfg = new Config();
		// cfg.setNumWorkers(1);
		// cfg.setDebug(true);
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new PWSpout());
		builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriteBolt())
				.shuffleGrouping("print-bolt");
		StormUtil.submitTopology(args, builder, TOPOLOGY_NAME);
	}
}
