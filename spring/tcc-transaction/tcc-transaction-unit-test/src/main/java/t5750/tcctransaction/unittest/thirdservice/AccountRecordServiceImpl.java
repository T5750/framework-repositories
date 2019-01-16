package t5750.tcctransaction.unittest.thirdservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.tcctransaction.Compensable;
import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.unittest.entity.AccountRecord;
import t5750.tcctransaction.unittest.entity.AccountStatus;
import t5750.tcctransaction.unittest.repository.AccountRecordRepository;
import t5750.tcctransaction.unittest.utils.UnitTest;

/**
 * 账户记录服务接口实现.
 */
@Service
public class AccountRecordServiceImpl implements AccountRecordService {
	@Autowired
	AccountRecordRepository accountRecordRepository;

	@Compensable(confirmMethod = "recordConfirm", cancelMethod = "recordCancel")
	public void record(TransactionContext transactionContext, long accountId,
			int amount) {
		System.out.println("record");
		AccountRecord accountRecord = accountRecordRepository
				.findById(accountId);
		accountRecord.setBalanceAmount(amount);
		accountRecord.setStatusId(AccountStatus.TRANSFERING.getId());
		if (UnitTest.TRYING_EXCEPTION) {
			throw new RuntimeException("record try failed.");
		}
	}

	public void recordConfirm(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("recordConfirm");
		AccountRecord accountRecord = accountRecordRepository
				.findById(accountId);
		accountRecord.setStatusId(AccountStatus.NORMAL.getId());
	}

	public void recordCancel(TransactionContext transactionContext,
			long accountId, int amount) {
		System.out.println("recordCancel");
		if (UnitTest.TRYING_EXCEPTION) {
			throw new RuntimeException("record cancel failed.");
		}
		AccountRecord accountRecord = accountRecordRepository
				.findById(accountId);
		accountRecord.setBalanceAmount(accountRecord.getBalanceAmount()
				- amount);
		accountRecord.setStatusId(AccountStatus.NORMAL.getId());
	}
}
