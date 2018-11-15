package com.evangel.curator.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import com.evangel.curator.util.CuratorUtil;

public class ClusterTest {
	public static void main(String[] args) throws Exception {
		CuratorFramework cf = CuratorUtil.startCuratorFramework();
		// Thread.sleep(3000);
		// System.out.println(cf.getChildren().forPath("/super").get(0));
		// 4 创建节点
		Thread.sleep(1000);
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/super/c1", "c1内容".getBytes());
		Thread.sleep(1000);
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/super/c2", "c2内容".getBytes());
		Thread.sleep(1000);
		// 5 读取节点
		Thread.sleep(1000);
		String ret1 = new String(cf.getData().forPath("/super/c1"));
		System.out.println(ret1);
		// 6 修改节点
		Thread.sleep(1000);
		cf.setData().forPath("/super/c2", "修改的新c2内容".getBytes());
		String ret2 = new String(cf.getData().forPath("/super/c2"));
		System.out.println(ret2);
		// 7 删除节点
		Thread.sleep(1000);
		cf.delete().forPath("/super/c1");
		cf.delete().forPath("/super/c2");
	}
}
