package t5750.tcctransaction.repository;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.transaction.xa.Xid;

import org.apache.log4j.Logger;

import t5750.tcctransaction.OptimisticLockException;
import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.TransactionRepository;
import t5750.tcctransaction.api.TransactionXid;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 缓存事务库.
 */
public abstract class CachableTransactionRepository implements
		TransactionRepository {
	static final Logger LOG = Logger
			.getLogger(CachableTransactionRepository.class.getSimpleName());
	/**
	 * 到期时间(以秒为单位)
	 */
	private int expireDuration = 300;
	/**
	 * 事务日志记录缓存<Xid, Transaction>
	 */
	private Cache<Xid, Transaction> transactionXidCompensableTransactionCache;

	/**
	 * 创建事务日志记录
	 */
	@Override
	public int create(Transaction transaction) {
		LOG.debug("==>create(Transaction transaction)");
		int result = doCreate(transaction);
		if (result > 0) {
			putToCache(transaction);
		}
		return result;
	}

	@Override
	public int update(Transaction transaction) {
		LOG.debug("==>update(Transaction transaction)");
		int result = doUpdate(transaction);
		if (result > 0) {
			putToCache(transaction);
		} else {
			throw new OptimisticLockException();
		}
		return result;
	}

	@Override
	public int delete(Transaction transaction) {
		LOG.debug("==>delete(Transaction transaction)");
		int result = doDelete(transaction);
		if (result > 0) {
			removeFromCache(transaction);
		}
		return result;
	}

	/**
	 * 根据xid查找事务日志记录.
	 * 
	 * @param xid
	 * @return
	 */
	@Override
	public Transaction findByXid(TransactionXid transactionXid) {
		Transaction transaction = findFromCache(transactionXid);
		if (transaction == null) {
			transaction = doFindOne(transactionXid);
			if (transaction != null) {
				putToCache(transaction);
			}
		}
		return transaction;
	}

	/**
	 * 找出所有未处理事务日志（从某一时间点开始）.
	 * 
	 * @return
	 */
	@Override
	public List<Transaction> findAllUnmodifiedSince(Date date) {
		List<Transaction> transactions = doFindAllUnmodifiedSince(date);
		for (Transaction transaction : transactions) {
			putToCache(transaction);
		}
		return transactions;
	}

	public CachableTransactionRepository() {
		transactionXidCompensableTransactionCache = CacheBuilder.newBuilder()
				.expireAfterAccess(expireDuration, TimeUnit.SECONDS)
				.maximumSize(1000).build();
	}

	/**
	 * 放入缓存.
	 * 
	 * @param transaction
	 */
	protected void putToCache(Transaction transaction) {
		transactionXidCompensableTransactionCache.put(transaction.getXid(),
				transaction);
	}

	/**
	 * 从缓存中删除.
	 * 
	 * @param transaction
	 */
	protected void removeFromCache(Transaction transaction) {
		transactionXidCompensableTransactionCache.invalidate(transaction
				.getXid());
	}

	/**
	 * 从缓存中查找.
	 * 
	 * @param transactionXid
	 * @return
	 */
	protected Transaction findFromCache(TransactionXid transactionXid) {
		return transactionXidCompensableTransactionCache
				.getIfPresent(transactionXid);
	}

	public final void setExpireDuration(int durationInSeconds) {
		this.expireDuration = durationInSeconds;
	}

	/**
	 * 创建事务日志记录
	 * 
	 * @param transaction
	 * @return
	 */
	protected abstract int doCreate(Transaction transaction);

	protected abstract int doUpdate(Transaction transaction);

	protected abstract int doDelete(Transaction transaction);

	protected abstract Transaction doFindOne(Xid xid);

	protected abstract List<Transaction> doFindAllUnmodifiedSince(Date date);
}
