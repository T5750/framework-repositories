package t5750.tcctransaction.repository.helper;

import redis.clients.jedis.Jedis;

/**
 */
public interface JedisCallback<T> {
	public T doInJedis(Jedis jedis);
}