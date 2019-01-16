package t5750.tcctransaction.unittest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.tcctransaction.Compensable;
import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.unittest.client.AccountRecordServiceProxy;
import t5750.tcctransaction.unittest.entity.AccountStatus;
import t5750.tcctransaction.unittest.entity.SubAccount;
import t5750.tcctransaction.unittest.repository.SubAccountRepository;
import t5750.tcctransaction.unittest.utils.UnitTest;

/**
 */
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	AccountRecordServiceProxy accountRecordServiceProxy;
	@Autowired
	SubAccountRepository subAccountRepository;

	@Override
	@Compensable(confirmMethod = "transferFromConfirm", cancelMethod = "transferFromCancel")
	public void transferFrom(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferFrom called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.TRANSFERING.getId());
		subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
		accountRecordServiceProxy.record(null, accountId, amount);
	}

	/** 向子账户转账，状态为“转账中：TRANSFERING” */
	@Override
	@Compensable(confirmMethod = "transferToConfirm", cancelMethod = "transferToCancel")
	public void transferTo(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferTo called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.TRANSFERING.getId());
		subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
	}

	@Override
	@Compensable(confirmMethod = "transferFromConfirm", cancelMethod = "transferFromCancel")
	public void transferFromWithMultipleTier(
			TransactionContext transactionContext, long accountId, int amount) {
		System.out.println("transferFromWithMultipleTier called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.TRANSFERING.getId());
		subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
		accountRecordServiceProxy.record(null, accountId, amount);
	}

	@Override
	@Compensable(confirmMethod = "transferToConfirm", cancelMethod = "transferToCancel")
	public void transferToWithMultipleTier(
			TransactionContext transactionContext, long accountId, int amount) {
		System.out.println("transferToWithMultipleTier called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.TRANSFERING.getId());
		subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
		accountRecordServiceProxy.record(null, accountId, amount);
	}

	@Override
	@Compensable(confirmMethod = "transferToConfirmWithNoTransactionContext", cancelMethod = "transferToCancelWithNoTransactionContext")
	public void transferToWithNoTransactionContext(long accountId, int amount) {
		System.out.println("transferToWithNoTransactionContext called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.TRANSFERING.getId());
		subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
		accountRecordServiceProxy.record(null, accountId, amount);
	}

	public void transferFromConfirm(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferFromConfirm called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.NORMAL.getId());
	}

	public void transferFromCancel(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferFromCancel called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {
			subAccount.setStatus(AccountStatus.NORMAL.getId());
			subAccount.setBalanceAmount(subAccount.getBalanceAmount() + amount);
		}
	}

	/** 向子账户转账确认，状态改为“常规状态：NORMAL” */
	public void transferToConfirm(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferToConfirm called");
		if (UnitTest.CONFIRMING_EXCEPTION) {
			throw new RuntimeException("transferToConfirm confirm failed.");
		}
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.NORMAL.getId());
	}

	/** 向子账户转账取消，状态改为“常规状态：NORMAL” */
	public void transferToCancel(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("transferToCancel called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {
			subAccount.setStatus(AccountStatus.NORMAL.getId());
			subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
		}
	}

	public void transferToConfirmWithNoTransactionContext(long accountId,
			int amount) {
		System.out.println("transferToConfirmWithNoTransactionContext called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		subAccount.setStatus(AccountStatus.NORMAL.getId());
	}

	public void transferToCancelWithNoTransactionContext(long accountId,
			int amount) {
		System.out.println("transferToCancelWithNoTransactionContext called");
		SubAccount subAccount = subAccountRepository.findById(accountId);
		if (subAccount.getStatus() == AccountStatus.TRANSFERING.getId()) {
			subAccount.setStatus(AccountStatus.NORMAL.getId());
			subAccount.setBalanceAmount(subAccount.getBalanceAmount() - amount);
		}
	}
}
