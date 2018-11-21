package t5750.curator.framework;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;
import org.apache.curator.utils.CloseableUtils;

import t5750.curator.util.CuratorTestUtil;

public class CreateClientExample {
	private static final String PATH = "/example/basic";

	public static void main(String[] args) throws Exception {
		TestingServer server = new TestingServer();
		CuratorFramework client = null;
		try {
			client = CuratorTestUtil.startClient(server);
			client.create().creatingParentsIfNeeded()
					.forPath(PATH, "test".getBytes());
			CloseableUtils.closeQuietly(client);
			client = createWithOptions(server.getConnectString(),
					new ExponentialBackoffRetry(1000, 3), 1000, 1000);
			client.start();
			System.out.println(new String(client.getData().forPath(PATH)));
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			CloseableUtils.closeQuietly(client);
			CloseableUtils.closeQuietly(server);
		}
	}

	public static CuratorFramework createWithOptions(String connectionString,
			RetryPolicy retryPolicy, int connectionTimeoutMs,
			int sessionTimeoutMs) {
		// using the CuratorFrameworkFactory.builder() gives fine grained
		// control
		// over creation options. See the CuratorFrameworkFactory.Builder
		// javadoc details
		return CuratorFrameworkFactory.builder()
				.connectString(connectionString).retryPolicy(retryPolicy)
				.connectionTimeoutMs(connectionTimeoutMs)
				.sessionTimeoutMs(sessionTimeoutMs)
				// etc. etc.
				.build();
	}
}