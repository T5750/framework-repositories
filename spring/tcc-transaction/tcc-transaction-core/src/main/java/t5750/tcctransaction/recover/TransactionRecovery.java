package t5750.tcctransaction.recover;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import t5750.tcctransaction.Transaction;
import t5750.tcctransaction.TransactionRepository;
import t5750.tcctransaction.api.TransactionStatus;
import t5750.tcctransaction.support.TransactionConfigurator;

/**
 * 事务恢复.
 */
public class TransactionRecovery {
	static final Logger logger = Logger.getLogger(TransactionRecovery.class
			.getSimpleName());
	/**
	 * TCC事务配置器.
	 */
	private TransactionConfigurator transactionConfigurator;

	/**
	 * 设置事务配置器.
	 * 
	 * @param transactionConfigurator
	 */
	public void setTransactionConfigurator(
			TransactionConfigurator transactionConfigurator) {
		this.transactionConfigurator = transactionConfigurator;
	}

	/**
	 * 启动事务恢复操作(被RecoverScheduledJob定时任务调用).
	 */
	public void startRecover() {
		List<Transaction> transactions = loadErrorTransactions(); // 找出所有执行错误的事务信息
		recoverErrorTransactions(transactions);
	}

	/**
	 * 找出所有执行错误的事务信息
	 * 
	 * @return
	 */
	private List<Transaction> loadErrorTransactions() {
		TransactionRepository transactionRepository = transactionConfigurator
				.getTransactionRepository();
		long currentTimeInMillis = Calendar.getInstance().getTimeInMillis();
		List<Transaction> transactions = transactionRepository
				.findAllUnmodifiedSince(new Date(currentTimeInMillis
						- transactionConfigurator.getRecoverConfig()
								.getRecoverDuration() * 1000));
		List<Transaction> recoverTransactions = new ArrayList<Transaction>();
		for (Transaction transaction : transactions) {
			// 检验记录是否已经被修改（版本校验）
			int result = transactionRepository.update(transaction);
			if (result > 0) {
				recoverTransactions.add(transaction);
			}
		}
		// 日志输出，调试用
		if (!transactions.isEmpty()) {
			logger.debug("==>loadErrorTransactions transactions size:"
					+ transactions.size());
		}
		return recoverTransactions;
	}

	/**
	 * 恢复错误的事务.
	 * 
	 * @param transactions
	 */
	private void recoverErrorTransactions(List<Transaction> transactions) {
		for (Transaction transaction : transactions) {
			if (transaction.getRetriedCount() > transactionConfigurator
					.getRecoverConfig().getMaxRetryCount()) {
				// 超过次数的，跳过
				logger.error(String
						.format("recover failed with max retry count,will not try again. txid:%s, status:%s,retried count:%d",
								transaction.getXid(), transaction.getStatus()
										.getId(), transaction.getRetriedCount()));
				continue;
			}
			try {
				transaction.addRetriedCount(); // 重试次数+1
				if (transaction.getStatus()
						.equals(TransactionStatus.CONFIRMING)) {
					// 如果是CONFIRMING(2)状态，则将事务往前执行
					transaction.changeStatus(TransactionStatus.CONFIRMING);
					transactionConfigurator.getTransactionRepository().update(
							transaction);
					transaction.commit();
				} else {
					// 其他情况，把事务状态改为CANCELLING(3)，然后执行回滚
					transaction.changeStatus(TransactionStatus.CANCELLING);
					transactionConfigurator.getTransactionRepository().update(
							transaction);
					transaction.rollback();
				}
				// 其他情况下，超时没处理的事务日志直接删除
				transactionConfigurator.getTransactionRepository().delete(
						transaction);
			} catch (Throwable e) {
				logger.warn(String.format(
						"recover failed, txid:%s, status:%s,retried count:%d",
						transaction.getXid(), transaction.getStatus().getId(),
						transaction.getRetriedCount()), e);
			}
		}
	}
}
