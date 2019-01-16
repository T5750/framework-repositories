package t5750.tcctransaction.unittest.service;

import t5750.tcctransaction.api.TransactionContext;

/**
 */
public interface AccountService {
	/** 向子账户转账，状态为“转账中” */
	void transferTo(TransactionContext transactionContext, long accountId,
			int amount);

	/** 向子账户转账确认，状态改为“常规状态：NORMAL” */
	void transferToConfirm(TransactionContext transactionContext,
			long accountId, int amount);

	/** 向子账户转账取消，状态改为“常规状态：NORMAL” */
	void transferToCancel(TransactionContext transactionContext,
			long accountId, int amount);

	void transferToWithNoTransactionContext(long accountId, int amount);

	void transferToConfirmWithNoTransactionContext(long accountId, int amount);

	void transferToCancelWithNoTransactionContext(long accountId, int amount);

	void transferFrom(TransactionContext transactionContext, long accountId,
			int amount);

	void transferFromConfirm(TransactionContext transactionContext,
			long accountId, int amount);

	void transferFromCancel(TransactionContext transactionContext,
			long accountId, int amount);

	void transferToWithMultipleTier(TransactionContext transactionContext,
			long accountId, int amount);

	void transferFromWithMultipleTier(TransactionContext transactionContext,
			long accountId, int amount);
}
