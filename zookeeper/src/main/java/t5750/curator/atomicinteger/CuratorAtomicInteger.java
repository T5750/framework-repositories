package t5750.curator.atomicinteger;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.RetryNTimes;

import t5750.curator.util.CuratorUtil;

public class CuratorAtomicInteger {
	public static void main(String[] args) throws Exception {
		CuratorFramework cf = CuratorUtil.startCuratorFramework();
		// 4 使用DistributedAtomicInteger
		DistributedAtomicInteger atomicIntger = new DistributedAtomicInteger(
				cf, "/super", new RetryNTimes(3, 1000));
		AtomicValue<Integer> value = atomicIntger.add(1);
		System.out.println(value.succeeded());
		System.out.println(value.postValue()); // 最新值
		System.out.println(value.preValue()); // 原始值
		Thread.sleep(1000);
		cf.delete().forPath("/super");
	}
}
