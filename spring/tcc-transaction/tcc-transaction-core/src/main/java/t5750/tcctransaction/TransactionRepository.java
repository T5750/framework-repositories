package t5750.tcctransaction;

import java.util.Date;
import java.util.List;

import t5750.tcctransaction.api.TransactionXid;

/**
 * 事务库接口.
 */
public interface TransactionRepository {
	/**
	 * 创建事务日志记录.
	 * 
	 * @param transaction
	 */
	int create(Transaction transaction);

	/**
	 * 更新事务日志记录.
	 * 
	 * @param transaction
	 */
	int update(Transaction transaction);

	/**
	 * 删除事务日志记录.
	 * 
	 * @param transaction
	 */
	int delete(Transaction transaction);

	/**
	 * 根据xid查找事务日志记录.
	 * 
	 * @param xid
	 * @return
	 */
	Transaction findByXid(TransactionXid xid);

	/**
	 * 找出所有未处理事务日志（从某一时间点开始）.
	 * 
	 * @return
	 */
	List<Transaction> findAllUnmodifiedSince(Date date);
}
