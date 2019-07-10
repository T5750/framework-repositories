package t5750.storm.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Jedis连接池
 */
public class JedisUtil {
	/**
	 * src/redis-server redis.conf &
	 */
	public static final String REDIS_HOST = "127.0.0.1";
	public static final int REDIS_PORT = 6379;
	public static final String REDIS_PASSWORD = "123456";

	public static GenericObjectPoolConfig setupPoolConfig() {
		GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		// 设置最大连接数为默认值的5倍
		poolConfig.setMaxTotal(GenericObjectPoolConfig.DEFAULT_MAX_TOTAL * 5);
		// 设置最大空闲连接数为默认值的3倍
		poolConfig.setMaxIdle(GenericObjectPoolConfig.DEFAULT_MAX_IDLE * 3);
		// 设置最小空闲连接数为默认值的2倍
		poolConfig.setMinIdle(GenericObjectPoolConfig.DEFAULT_MIN_IDLE * 2);
		// 设置开启jmx功能
		poolConfig.setJmxEnabled(true);
		// 设置连接池没有连接后客户端的最大等待时间(单位为毫秒)
		poolConfig.setMaxWaitMillis(3000);
		return poolConfig;
	}

	public static Jedis getJedis() {
		// common-pool连接池配置，这里使用默认配置，后面小节会介绍具体配置说明
		// GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
		GenericObjectPoolConfig poolConfig = setupPoolConfig();
		// 初始化Jedis连接池
		JedisPool jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);
		Jedis jedis = null;
		jedis = jedisPool.getResource();
		jedis.auth(REDIS_PASSWORD);
		return jedis;
	}
}
