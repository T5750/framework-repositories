package t5750.redis.jedis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import t5750.redis.util.JedisUtil;

/**
 * Jedis连接池
 */
public class JedisPoolTest {
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		// 1. 从连接池获取jedis对象
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			// 2. 执行操作
			String key = "hello";
			Long setnx = jedis.setnx(key, "world");
			logger.info("setnx=" + setnx);
			jedis.expire(key, 60);
			String result = jedis.get(key);
			logger.info(key + "=" + result);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (jedis != null) {
				// 如果使用JedisPool，close操作不是关闭连接，代表归还连接池
				jedis.close();
			}
		}
	}
}
