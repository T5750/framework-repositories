package t5750.zkclient.watcher;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;

import t5750.util.ZkUtil;

public class ZkClientWatcher {
	public static void main(String[] args) throws Exception {
		ZkClient zkc = new ZkClient(new ZkConnection(ZkUtil.CONNECT_ADDR),
				ZkUtil.SESSION_TIMEOUT);
		// 对父节点添加监听子节点变化。
		zkc.subscribeChildChanges("/super", new IZkChildListener() {
			@Override
			public void handleChildChange(String parentPath,
					List<String> currentChilds) throws Exception {
				System.out.println("parentPath: " + parentPath);
				System.out.println("currentChilds: " + currentChilds);
			}
		});
		Thread.sleep(3000);
		zkc.createPersistent("/super");
		Thread.sleep(1000);
		zkc.createPersistent("/super" + "/" + "c1", "c1内容");
		Thread.sleep(1000);
		zkc.createPersistent("/super" + "/" + "c2", "c2内容");
		Thread.sleep(1000);
		zkc.delete("/super/c2");
		Thread.sleep(1000);
		zkc.deleteRecursive("/super");
		Thread.sleep(Integer.MAX_VALUE);
	}
}
