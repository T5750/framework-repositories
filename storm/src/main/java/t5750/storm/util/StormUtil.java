package t5750.storm.util;

import java.io.File;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class StormUtil {
	public static final String WINDOWS_FILE_DIR = "D:\\code\\";
	public static final String INET_HOST = "192.168.100.163";

	public static void hasDirectory() {
		File file = new File(WINDOWS_FILE_DIR);
		if (!file.exists() && !file.isDirectory()) {
			file.mkdir();
		} else {
		}
	}

	public static void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public static void waitForMillis(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// e.printStackTrace();
		}
	}

	public static void submitTopology(String[] args, TopologyBuilder builder,
			String topologyName) throws Exception {
		Config config = new Config();
		if (args == null || args.length == 0) {
			// 1 本地模式
			config.setDebug(false);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyName, config,
					builder.createTopology());
			StormUtil.waitForSeconds(10);
			cluster.killTopology(topologyName);
			cluster.shutdown();
		} else {
			// 2 集群模式
			config.setNumWorkers(2);
			StormSubmitter.submitTopology(topologyName, config,
					builder.createTopology());
		}
	}

	/**
	 * for Trident
	 */
	public static void submitTopology(String[] args, StormTopology topology,
			String topologyName) throws Exception {
		Config conf = new Config();
		// 设置batch最大处理
		conf.setNumWorkers(2);
		conf.setMaxSpoutPending(20);
		if (args.length == 0) {
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology(topologyName, conf, topology);
			StormUtil.waitForSeconds(10);
			cluster.shutdown();
		} else {
			StormSubmitter.submitTopology(args[0], conf, topology);
		}
	}

	public static FixedBatchSpout getSpout() {
		// 设置数据源
		FixedBatchSpout spout = new FixedBatchSpout(
		// 声明输入的域字段为"a", "b", "c", "d"
				new Fields("a", "b", "c", "d"),
				// 设置批处理大小
				4,
				// 设置数据源内容，测试数据
				new Values(1, 4, 7, 10), new Values(1, 1, 3, 11), new Values(2,
						2, 7, 1), new Values(2, 5, 7, 2));
		// 指定是否循环
		spout.setCycle(false);
		return spout;
	}
}