package t5750.storm.drpc.reach;

import org.apache.storm.Config;
import org.apache.storm.utils.DRPCClient;

import t5750.storm.util.StormUtil;

/**
 * http://storm.apache.org/releases/1.2.2/Distributed-RPC.html<br/>
 * storm drpc &<br/>
 * storm jar storm-1.0.jar t5750.storm.drpc.reach.ReachTopology reach
 */
public class DrpcReach {
	public static void main(String[] args) throws Exception {
		Config conf = new Config();
		conf.put("storm.thrift.transport",
				"org.apache.storm.security.auth.plain.PlainSaslTransportPlugin");
		conf.put(Config.STORM_NIMBUS_RETRY_TIMES, 3);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL, 10);
		conf.put(Config.STORM_NIMBUS_RETRY_INTERVAL_CEILING, 20);
		DRPCClient client = new DRPCClient(conf, StormUtil.INET_HOST, 3772);
		for (String url : new String[] { "foo.com/blog/1",
				"engineering.twitter.com/blog/5" }) {
			System.out.println(client.execute("reach", url));
		}
	}
}
