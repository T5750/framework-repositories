package t5750.tcctransaction.unittest.thirdservice;

import t5750.tcctransaction.api.TransactionContext;

/**
 * 账户记录服务接口.
 */
public interface AccountRecordService {
	public void record(TransactionContext transactionContext, long accountId,
			int amount);

	void recordConfirm(TransactionContext transactionContext, long accountId,
			int amount);

	void recordCancel(TransactionContext transactionContext, long accountId,
			int amount);
}
