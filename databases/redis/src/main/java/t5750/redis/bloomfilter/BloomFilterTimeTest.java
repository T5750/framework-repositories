package t5750.redis.bloomfilter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 测试一个元素是否属于一个百万元素集合所需耗时<br/>
 * https://www.cnblogs.com/rjzheng/p/8908073.html
 */
public class BloomFilterTimeTest {
	private static int size = 1000000;
	private static BloomFilter<Integer> bloomFilter = BloomFilter.create(
			Funnels.integerFunnel(), size);

	public static void main(String[] args) {
		for (int i = 0; i < size; i++) {
			bloomFilter.put(i);
		}
		long startTime = System.nanoTime(); // 获取开始时间
		// 判断这一百万个数中是否包含29999这个数
		if (bloomFilter.mightContain(29999)) {
			System.out.println("命中了");
		}
		long endTime = System.nanoTime(); // 获取结束时间
		System.out.println("程序运行时间： " + (endTime - startTime) + "纳秒");
	}
}
