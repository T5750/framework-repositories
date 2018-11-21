package t5750.curator.barrier;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;

import t5750.curator.util.CuratorUtil;

public class CuratorBarrier2 {
	static DistributedBarrier barrier = null;

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						CuratorFramework cf = CuratorUtil.startCuratorFramework();
						barrier = new DistributedBarrier(cf, "/super");
						System.out.println(Thread.currentThread().getName()
								+ "设置barrier!");
						barrier.setBarrier(); // 设置
						barrier.waitOnBarrier(); // 等待
						System.out.println("---------开始执行程序----------");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "t" + i).start();
		}
		Thread.sleep(5000);
		barrier.removeBarrier(); // 释放
	}
}
