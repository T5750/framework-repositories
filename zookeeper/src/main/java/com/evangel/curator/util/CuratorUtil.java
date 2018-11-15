package com.evangel.curator.util;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

import com.evangel.util.ZkUtil;

/**
 *
 */
public class CuratorUtil {
	public static CuratorFramework startCuratorFramework() {
		// 1 重试策略：初试时间为1s 重试10次
		RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
		// 2 通过工厂创建连接
		CuratorFramework cf = CuratorFrameworkFactory.builder()
				.connectString(ZkUtil.CONNECT_ADDR)
				.sessionTimeoutMs(ZkUtil.SESSION_TIMEOUT)
				.retryPolicy(retryPolicy).build();
		// 3 建立连接
		cf.start();
		return cf;
	}
}
