package t5750.server;

import org.apache.zookeeper.server.LogFormatter;

/**
 *
 */
public class ZkLog {
	public static void main(String[] args) {
		args = new String[] { "D:\\zookeeper\\log.4b8f" };
		try {
			LogFormatter.main(args);
			// SnapshotFormatter.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}