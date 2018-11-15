package com.evangel.curator.base;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;

import com.evangel.curator.util.CuratorUtil;

public class CuratorBase {
	public static void main(String[] args) throws Exception {
		CuratorFramework cf = CuratorUtil.startCuratorFramework();
		System.out.println(States.CONNECTED);
		System.out.println(cf.getState());
		// 4 建立节点 指定节点类型（不加withMode默认为持久类型节点）、路径、数据内容
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/super/c1", "c1内容".getBytes());
		// 5 删除节点
		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
		// 读取、修改
		// 创建节点
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/super/c1", "c1内容".getBytes());
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.forPath("/super/c2", "c2内容".getBytes());
		// 读取节点
		String ret1 = new String(cf.getData().forPath("/super/c2"));
		System.out.println(ret1);
		// 修改节点
		cf.setData().forPath("/super/c2", "修改c2内容".getBytes());
		String ret2 = new String(cf.getData().forPath("/super/c2"));
		System.out.println(ret2);
		// 绑定回调函数
		ExecutorService pool = Executors.newCachedThreadPool();
		cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
				.inBackground(new BackgroundCallback() {
					@Override
					public void processResult(CuratorFramework cf,
							CuratorEvent ce) throws Exception {
						System.out.println("code:" + ce.getResultCode());
						System.out.println("type:" + ce.getType());
						System.out.println("线程为:"
								+ Thread.currentThread().getName());
					}
				}, pool).forPath("/super/c3", "c3内容".getBytes());
		// Thread.sleep(Integer.MAX_VALUE);
		Thread.sleep(2000);
		// 读取子节点getChildren方法 和 判断节点是否存在checkExists方法
		List<String> list = cf.getChildren().forPath("/super");
		for (String p : list) {
			System.out.println(p);
		}
		Stat stat = cf.checkExists().forPath("/super/c3");
		System.out.println(stat);
		Thread.sleep(2000);
		cf.delete().guaranteed().deletingChildrenIfNeeded().forPath("/super");
	}
}
