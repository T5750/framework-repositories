package t5750.curator.barrier;

import java.util.Random;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;

import t5750.curator.util.CuratorUtil;

public class CuratorBarrier {
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 5; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						CuratorFramework cf = CuratorUtil.startCuratorFramework();
						DistributedDoubleBarrier barrier = new DistributedDoubleBarrier(
								cf, "/super", 5);
						Thread.sleep(1000 * (new Random()).nextInt(3));
						System.out.println(Thread.currentThread().getName()
								+ "已经准备");
						barrier.enter();
						System.out.println("同时开始运行...");
						Thread.sleep(1000 * (new Random()).nextInt(3));
						System.out.println(Thread.currentThread().getName()
								+ "运行完毕");
						barrier.leave();
						System.out.println("同时退出运行...");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "t" + i).start();
		}
	}
}
