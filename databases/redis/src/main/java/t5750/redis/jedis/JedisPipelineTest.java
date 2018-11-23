package t5750.redis.jedis;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

/**
 * Redis中Pipeline的使用方法
 */
public class JedisPipelineTest {
	private static Logger logger = Logger.getRootLogger();

	/**
	 * mdel删除的实现过程
	 */
	public static void mdel(List<String> keys, Jedis jedis) {
		// 1)生成pipeline对象
		Pipeline pipeline = jedis.pipelined();
		// 2)pipeline执行命令，注意此时命令并未真正执行
		for (String key : keys) {
			pipeline.del(key);
		}
		// 3)执行命令
		pipeline.sync();
	}

	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			Pipeline pipeline = jedis.pipelined();
			String keyHello = "hello";
			String keyCounter = "counter";
			pipeline.set(keyHello, "world");
			pipeline.incr(keyCounter);
			List<Object> resultList = pipeline.syncAndReturnAll();
			for (Object object : resultList) {
				logger.info(object);
			}
			List<String> keys = new ArrayList<>(2);
			keys.add(keyHello);
			keys.add(keyCounter);
			mdel(keys, jedis);
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
