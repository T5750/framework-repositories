package t5750.tcctransaction.server.dao;

import java.util.List;

import t5750.tcctransaction.server.vo.TransactionVo;

/**
 */
public interface TransactionDao {
	public List<TransactionVo> findTransactions(String domain, Integer pageNum,
			int pageSize);

	public Integer countOfFindTransactions(String domain);

	public boolean resetRetryCount(String domain, byte[] globalTxId,
			byte[] branchQualifier);
}
