package t5750.mysql;

import java.util.concurrent.atomic.AtomicInteger;

public class LocksTest {
	// unsafe：线程不安全
	private static int unsafe = 0;
	// optimistic：使用乐观锁
	private static AtomicInteger optimistic = new AtomicInteger(0);
	// pessimistic：使用悲观锁
	private static int pessimistic = 0;

	private static synchronized void increaseValue3() {
		pessimistic++;
	}

	public static void main(String[] args) throws Exception {
		// 开启1000个线程，并执行自增操作
		for (int i = 0; i < 1000; ++i) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					unsafe++;
					optimistic.getAndIncrement();
					increaseValue3();
				}
			}).start();
		}
		// 打印结果
		Thread.sleep(1000);
		System.out.println("线程不安全：" + unsafe);
		System.out.println("乐观锁(AtomicInteger)：" + optimistic);
		System.out.println("悲观锁(synchronized)：" + pessimistic);
	}
}