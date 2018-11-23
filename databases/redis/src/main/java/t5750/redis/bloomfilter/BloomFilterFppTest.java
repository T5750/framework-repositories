package t5750.redis.bloomfilter;

import java.util.ArrayList;
import java.util.List;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 我们故意取10000个不在过滤器里的值，却还有330个被认为在过滤器里，这说明了误判率为0.03.即，在不做任何设置的情况下，默认的误判率为0.03<br/>
 * https://www.cnblogs.com/rjzheng/p/8908073.html
 */
public class BloomFilterFppTest {
	private static int size = 1000000;
	private static BloomFilter<Integer> bloomFilter = BloomFilter.create(
			Funnels.integerFunnel(), size);

	public static void main(String[] args) {
		for (int i = 0; i < size; i++) {
			bloomFilter.put(i);
		}
		List<Integer> list = new ArrayList<Integer>(1000);
		// 故意取10000个不在过滤器里的值，看看有多少个会被认为在过滤器里
		for (int i = size + 10000; i < size + 20000; i++) {
			if (bloomFilter.mightContain(i)) {
				list.add(i);
			}
		}
		System.out.println("误判的数量：" + list.size());
	}
}
