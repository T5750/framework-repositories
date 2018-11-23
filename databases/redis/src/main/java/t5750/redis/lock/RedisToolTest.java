package t5750.redis.lock;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * Java 使用 Redis<br/>
 * https://www.w3cschool.cn/redis/redis-java.html
 */
public class RedisToolTest {
	public static final String PONG = "PONG";

	public static void main(String[] args) {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		String ping = jedis.ping();
		System.out.println("服务器正在运行: " + ping);
		if (PONG.equals(ping)) {
			String lockKey = "lockKeyTest";
			String requestId = "requestId001";
			int expireTime = 1000;
			boolean tryGet = RedisTool.tryGetDistributedLock(jedis, lockKey,
					requestId, expireTime);
			System.out.println("tryGet: " + tryGet);
			if (tryGet) {
				testString(jedis);
				testList(jedis);
				testKeys(jedis);
				boolean result = RedisTool.releaseDistributedLock(jedis,
						lockKey, requestId);
				System.out.println("release: " + result);
			}
		}
	}

	public static void testString(Jedis jedis) {
		// 设置 redis 字符串数据
		jedis.setnx("w3ckey", "www.w3cschool.cn");
		// 获取存储的数据并输出
		System.out.println("redis 存储的字符串为: " + jedis.get("w3ckey"));
	}

	public static void testList(Jedis jedis) {
		boolean isExists = jedis.exists("tutorial-list");
		if (!isExists) {
			// 存储数据到列表中
			jedis.lpush("tutorial-list", "Redis");
			jedis.lpush("tutorial-list", "Mongodb");
			jedis.lpush("tutorial-list", "Mysql");
		}
		// 获取存储的数据并输出
		List<String> list = jedis.lrange("tutorial-list", 0, 2);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("列表项为: " + list.get(i));
		}
	}

	public static void testKeys(Jedis jedis) {
		// 获取数据并输出
		Set<String> keys = jedis.keys("*");
		Iterator<String> it = keys.iterator();
		while (it.hasNext()) {
			String key = it.next();
			System.out.println(key);
		}
	}
}
