package t5750.storm.redis;

import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import redis.clients.jedis.Jedis;
import t5750.storm.util.JedisUtil;

public class RedisOperations implements Serializable {
	private static final long serialVersionUID = 1L;
	Jedis jedis = null;

	public RedisOperations(String redisIP, int port) {
		// Connecting to Redis
		jedis = new Jedis(redisIP, port);
	}

	public RedisOperations() {
		// Connecting to Redis
		jedis = JedisUtil.getJedis();
	}

	/*
	 * This method takes the record and record id as input. We will first
	 * serialize the record into String using Jackson library and then store the
	 * whole record into Redis.User can use the record id to retrieve the record
	 * from Redis
	 */
	public void insert(Map<String, Object> record, String id) {
		try {
			// jedis.set(id, new ObjectMapper().writeValueAsString(record));
			jedis.setnx(id, new ObjectMapper().writeValueAsString(record));
			jedis.expire(id, 60);
		} catch (Exception e) {
			System.out.println("Record not persisted into datastore");
		}
	}
}