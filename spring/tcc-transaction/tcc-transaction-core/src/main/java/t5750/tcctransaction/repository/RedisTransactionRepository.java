package t5750.tcctransaction.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.xa.Xid;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.common.TransactionType;
import t5750.tcctransaction.repository.helper.JedisCallback;
import t5750.tcctransaction.repository.helper.RedisHelper;
import t5750.tcctransaction.repository.helper.TransactionSerializer;
import t5750.tcctransaction.serializer.JdkSerializationSerializer;
import t5750.tcctransaction.serializer.ObjectSerializer;
import t5750.tcctransaction.utils.ByteUtils;

/**
 * Redis缓存事务库.
 * <p/>
 * As the storage of transaction need safely durable,make sure the redis server
 * is set as AOF mode and always fsync. set below directives in your redis.conf
 * appendonly yes appendfsync always
 */
public class RedisTransactionRepository extends CachableTransactionRepository {
	private JedisPool jedisPool;
	private String keyPrefix = "TCC:";

	public void setKeyPrefix(String keyPrefix) {
		this.keyPrefix = keyPrefix;
	}

	private ObjectSerializer serializer = new JdkSerializationSerializer();

	public void setSerializer(ObjectSerializer serializer) {
		this.serializer = serializer;
	}

	public JedisPool getJedisPool() {
		return jedisPool;
	}

	public void setJedisPool(JedisPool jedisPool) {
		this.jedisPool = jedisPool;
	}

	@Override
	protected int doCreate(final Transaction transaction) {
		try {
			final byte[] key = RedisHelper.getRedisKey(keyPrefix,
					transaction.getXid());
			Long statusCode = RedisHelper.execute(jedisPool,
					new JedisCallback<Long>() {
						@Override
						public Long doInJedis(Jedis jedis) {
							return jedis.hsetnx(key, ByteUtils
									.longToBytes(transaction.getVersion()),
									TransactionSerializer.serialize(serializer,
											transaction));
						}
					});
			return statusCode.intValue();
		} catch (Exception e) {
			throw new TransactionIOException(e);
		}
	}

	@Override
	protected int doUpdate(final Transaction transaction) {
		try {
			final byte[] key = RedisHelper.getRedisKey(keyPrefix,
					transaction.getXid());
			Long statusCode = RedisHelper.execute(jedisPool,
					new JedisCallback<Long>() {
						@Override
						public Long doInJedis(Jedis jedis) {
							transaction.updateTime();
							transaction.updateVersion();
							return jedis.hsetnx(key, ByteUtils
									.longToBytes(transaction.getVersion()),
									TransactionSerializer.serialize(serializer,
											transaction));
						}
					});
			return statusCode.intValue();
		} catch (Exception e) {
			throw new TransactionIOException(e);
		}
	}

	@Override
	protected int doDelete(Transaction transaction) {
		try {
			final byte[] key = RedisHelper.getRedisKey(keyPrefix,
					transaction.getXid());
			Long result = RedisHelper.execute(jedisPool,
					new JedisCallback<Long>() {
						@Override
						public Long doInJedis(Jedis jedis) {
							return jedis.del(key);
						}
					});
			return result.intValue();
		} catch (Exception e) {
			throw new TransactionIOException(e);
		}
	}

	@Override
	protected Transaction doFindOne(Xid xid) {
		try {
			final byte[] key = RedisHelper.getRedisKey(keyPrefix, xid);
			byte[] content = RedisHelper.getKeyValue(jedisPool, key);
			if (content != null) {
				return TransactionSerializer.deserialize(serializer, content);
			}
			return null;
		} catch (Exception e) {
			throw new TransactionIOException(e);
		}
	}

	@Override
	protected List<Transaction> doFindAllUnmodifiedSince(Date date) {
		List<Transaction> allTransactions = doFindAll();
		List<Transaction> allUnmodifiedSince = new ArrayList<Transaction>();
		for (Transaction transaction : allTransactions) {
			if (transaction.getTransactionType().equals(TransactionType.ROOT)
					&& transaction.getLastUpdateTime().compareTo(date) < 0) {
				allUnmodifiedSince.add(transaction);
			}
		}
		return allUnmodifiedSince;
	}

	// @Override
	protected List<Transaction> doFindAll() {
		try {
			List<Transaction> transactions = new ArrayList<Transaction>();
			Set<byte[]> keys = RedisHelper.execute(jedisPool,
					new JedisCallback<Set<byte[]>>() {
						@Override
						public Set<byte[]> doInJedis(Jedis jedis) {
							return jedis.keys((keyPrefix + "*").getBytes());
						}
					});
			for (final byte[] key : keys) {
				byte[] content = RedisHelper.getKeyValue(jedisPool, key);
				if (content != null) {
					transactions.add(TransactionSerializer.deserialize(
							serializer, content));
				}
			}
			return transactions;
		} catch (Exception e) {
			throw new TransactionIOException(e);
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