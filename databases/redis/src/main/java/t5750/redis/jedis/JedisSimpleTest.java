package t5750.redis.jedis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * Jedis的基本使用方法
 */
public class JedisSimpleTest {
	private static Logger logger = Logger.getRootLogger();

	/**
	 * Jedis对于Redis五种数据结构的操作
	 */
	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = new Jedis("127.0.0.1", 6379);
			// 1.string
			// 输出结果：OK
			jedis.set("hello", "world");
			// 输出结果：world
			jedis.get("hello");
			// 输出结果：1
			jedis.incr("counter");
			// 2.hash
			jedis.hset("myhash", "f1", "v1");
			jedis.hset("myhash", "f2", "v2");
			// 输出结果：{f1=v1, f2=v2}
			jedis.hgetAll("myhash");
			// 3.list
			jedis.rpush("mylist", "1");
			jedis.rpush("mylist", "2");
			jedis.rpush("mylist", "3");
			// 输出结果：[1, 2, 3]
			jedis.lrange("mylist", 0, -1);
			// 4.set
			jedis.sadd("myset", "a");
			jedis.sadd("myset", "b");
			jedis.sadd("myset", "a");
			// 输出结果：[b, a]
			jedis.smembers("myset");
			// 5.zset
			jedis.zadd("myzset", 99, "tom");
			jedis.zadd("myzset", 66, "peter");
			jedis.zadd("myzset", 33, "james");
			// 输出结果：[[["james"],33.0], [["peter"],66.0], [["tom"],99.0]]
			jedis.zrangeWithScores("myzset", 0, -1);
			jedis.expire("hello", 60);
			jedis.expire("counter", 60);
			jedis.expire("myhash", 60);
			jedis.expire("mylist", 60);
			jedis.expire("myset", 60);
			jedis.expire("myzset", 60);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
