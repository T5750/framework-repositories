package t5750.tcctransaction.repository.helper;

import java.util.*;

import javax.transaction.xa.Xid;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import t5750.tcctransaction.utils.ByteUtils;

/**
 */
public class RedisHelper {
	public static byte[] getRedisKey(String keyPrefix, Xid xid) {
		byte[] prefix = keyPrefix.getBytes();
		byte[] globalTransactionId = xid.getGlobalTransactionId();
		byte[] branchQualifier = xid.getBranchQualifier();
		byte[] key = new byte[prefix.length + globalTransactionId.length
				+ branchQualifier.length];
		System.arraycopy(prefix, 0, key, 0, prefix.length);
		System.arraycopy(globalTransactionId, 0, key, prefix.length,
				globalTransactionId.length);
		System.arraycopy(branchQualifier, 0, key, prefix.length
				+ globalTransactionId.length, branchQualifier.length);
		return key;
	}

	public static byte[] getKeyValue(JedisPool jedisPool, final byte[] key) {
		return execute(jedisPool, new JedisCallback<byte[]>() {
			@Override
			public byte[] doInJedis(Jedis jedis) {
				Map<byte[], byte[]> fieldValueMap = jedis.hgetAll(key);
				List<Map.Entry<byte[], byte[]>> entries = new ArrayList<Map.Entry<byte[], byte[]>>(
						fieldValueMap.entrySet());
				Collections.sort(entries,
						new Comparator<Map.Entry<byte[], byte[]>>() {
							@Override
							public int compare(
									Map.Entry<byte[], byte[]> entry1,
									Map.Entry<byte[], byte[]> entry2) {
								return (int) (ByteUtils.bytesToLong(entry1
										.getKey()) - ByteUtils
										.bytesToLong(entry2.getKey()));
							}
						});
				byte[] content = entries.get(entries.size() - 1).getValue();
				return content;
			}
		});
	}

	public static byte[] getKeyValue(Jedis jedis, final byte[] key) {
		Map<byte[], byte[]> fieldValueMap = jedis.hgetAll(key);
		List<Map.Entry<byte[], byte[]>> entries = new ArrayList<Map.Entry<byte[], byte[]>>(
				fieldValueMap.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<byte[], byte[]>>() {
			@Override
			public int compare(Map.Entry<byte[], byte[]> entry1,
					Map.Entry<byte[], byte[]> entry2) {
				return (int) (ByteUtils.bytesToLong(entry1.getKey()) - ByteUtils
						.bytesToLong(entry2.getKey()));
			}
		});
		byte[] content = entries.get(entries.size() - 1).getValue();
		return content;
	}

	public static <T> T execute(JedisPool jedisPool, JedisCallback<T> callback) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return callback.doInJedis(jedis);
		} finally {
			if (jedis != null) {
				jedis.close();
			}
		}
	}
}
/*
 * ==================================================================== 龙果学院：
 * www.roncoo.com （微信公众号：RonCoo_com） 超级教程系列：《微服务架构的分布式事务解决方案》视频教程
 * 讲师：吴水成（水到渠成），840765167@qq.com
 * 课程地址：http://www.roncoo.com/course/view/7ae3d7eddc4742f78b0548aa8bd9ccdb
 * ====================================================================
 */