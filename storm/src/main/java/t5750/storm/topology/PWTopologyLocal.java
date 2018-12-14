package t5750.storm.topology;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

import t5750.storm.bolt.PrintBolt;
import t5750.storm.bolt.WriteBolt;
import t5750.storm.spout.PWSpout;
import t5750.storm.util.StormUtil;

public class PWTopologyLocal {
	public static void main(String[] args) throws Exception {
		StormUtil.hasDirectory();
		Config cfg = new Config();
		cfg.setNumWorkers(2);
		cfg.setDebug(true);
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("spout", new PWSpout());
		builder.setBolt("print-bolt", new PrintBolt()).shuffleGrouping("spout");
		builder.setBolt("write-bolt", new WriteBolt()).shuffleGrouping(
				"print-bolt");
		// 1 本地模式
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("top1", cfg, builder.createTopology());
		Thread.sleep(10000);
		cluster.killTopology("top1");
		cluster.shutdown();
		// 2 集群模式
		// StormSubmitter.submitTopology("top1", cfg, builder.createTopology());
	}
}
