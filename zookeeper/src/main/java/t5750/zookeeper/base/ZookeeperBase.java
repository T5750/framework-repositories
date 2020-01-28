package t5750.zookeeper.base;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.*;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.data.Stat;

import t5750.socket.util.ZkUtil;

/**
 * Zookeeper base学习笔记
 */
public class ZookeeperBase {
	/** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
	static final CountDownLatch connectedSemaphore = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper(ZkUtil.CONNECT_ADDR,
				ZkUtil.SESSION_TIMEOUT, new Watcher() {
					@Override
					public void process(WatchedEvent event) {
						// 获取事件的状态
						KeeperState keeperState = event.getState();
						EventType eventType = event.getType();
						// 如果是建立连接
						if (KeeperState.SyncConnected == keeperState) {
							if (EventType.None == eventType) {
								// 如果建立连接成功，则发送信号量，让后续阻塞程序向下执行
								connectedSemaphore.countDown();
								System.out.println("zk 建立连接");
							}
						}
					}
				});
		// 进行阻塞
		connectedSemaphore.await();
		System.out.println("主线程执行...");
		String pathTestRoot = "/testRoot";
		// "/testRoot/children"
		String pathTestRootChildren = pathTestRoot + "/children";
		Stat testRootStat = zk.exists(pathTestRoot, false);
		Stat testRootChildrenStat = zk.exists(pathTestRootChildren, false);
		if (null == testRootStat) {
			// 创建父节点
			zk.create(pathTestRoot, "testRoot".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		if (null == testRootChildrenStat) {
			// 创建子节点
			zk.create(pathTestRootChildren, "children data".getBytes(),
					ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}
		// 获取节点信息
		byte[] data = zk.getData(pathTestRoot, false, null);
		System.out.println(new String(data));
		System.out.println(zk.getChildren(pathTestRoot, false));
		// 修改节点的值
		zk.setData(pathTestRoot, "modify data root".getBytes(), -1);
		byte[] dataNeo = zk.getData(pathTestRoot, false, null);
		System.out.println(new String(dataNeo));
		// 判断节点是否存在
		System.out.println(zk.exists(pathTestRootChildren, false));
		// 删除节点
		zk.delete(pathTestRootChildren, -1);
		System.out.println(zk.exists(pathTestRootChildren, false));
		zk.close();
	}
}