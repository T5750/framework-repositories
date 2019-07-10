package t5750.storm.starter.util;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.generated.StormTopology;

public final class StormRunner {
	private static final int MILLIS_IN_SEC = 1000;

	private StormRunner() {
	}

	public static void runTopologyLocally(StormTopology topology,
			String topologyName, Config conf, int runtimeInSeconds)
			throws InterruptedException {
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology(topologyName, conf, topology);
		Thread.sleep((long) runtimeInSeconds * MILLIS_IN_SEC);
		cluster.killTopology(topologyName);
		cluster.shutdown();
	}

	public static void runTopologyRemotely(StormTopology topology,
			String topologyName, Config conf) throws AlreadyAliveException,
			InvalidTopologyException, AuthorizationException {
		StormSubmitter.submitTopology(topologyName, conf, topology);
	}
}