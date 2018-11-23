package t5750.redis.jedis;

import org.apache.log4j.Logger;

import redis.clients.jedis.Jedis;

/**
 * Jedis的Lua脚本
 */
public class JedisLuaTest {
	private static Logger logger = Logger.getRootLogger();

	public static void main(String[] args) {
		Jedis jedis = null;
		try {
			jedis = JedisUtil.getJedis();
			String key = "hello";
			String script = "return redis.call('get',KEYS[1])";
			Object result = jedis.eval(script, 1, key);
			// 打印结果为world
			logger.info(key + "=" + result + " by eval");
			String scriptSha = jedis.scriptLoad(script);
			Object resultSha = jedis.evalsha(scriptSha, 1, key);
			logger.info(key + "=" + resultSha + " by evalsha");
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
