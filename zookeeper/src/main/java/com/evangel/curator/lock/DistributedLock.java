package com.evangel.curator.lock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import com.evangel.curator.util.CuratorUtil;

public class DistributedLock {
	static int count = 10;

	public static void genarNo() {
		try {
			count--;
			System.out.println(count);
		} finally {
		}
	}

	public static void main(String[] args) throws Exception {
		CuratorFramework cf = CuratorUtil.startCuratorFramework();
		// 4 分布式锁
		final InterProcessMutex lock = new InterProcessMutex(cf, "/super");
		// final ReentrantLock reentrantLock = new ReentrantLock();
		final CountDownLatch countdown = new CountDownLatch(1);
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						countdown.await();
						// 加锁
						lock.acquire();
						// reentrantLock.lock();
						// -------------业务处理开始
						// genarNo();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"HH:mm:ss|SSS");
						System.out.println(sdf.format(new Date()));
						// System.out.println(System.currentTimeMillis());
						// -------------业务处理结束
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						try {
							// 释放
							lock.release();
							// reentrantLock.unlock();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}, "t" + i).start();
		}
		Thread.sleep(100);
		countdown.countDown();
	}
}
