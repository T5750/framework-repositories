package t5750.storm.util;

import java.io.File;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;

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
}