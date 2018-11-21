package t5750.curator.util;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

/**
 *
 */
public class CuratorTestUtil {
	public static CuratorFramework startClient(TestingServer server) {
		CuratorFramework client = null;
		try {
			client = createSimple(server.getConnectString());
			client.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return client;
	}

	public static CuratorFramework createSimple(String connectionString) {
		// these are reasonable arguments for the ExponentialBackoffRetry.
		// The first retry will wait 1 second - the second will wait up to 2
		// seconds - the
		// third will wait up to 4 seconds.
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000,
				3);
		// The simplest way to get a CuratorFramework instance. This will use
		// default values.
		// The only required arguments are the connection string and the retry
		// policy
		return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
	}
}
