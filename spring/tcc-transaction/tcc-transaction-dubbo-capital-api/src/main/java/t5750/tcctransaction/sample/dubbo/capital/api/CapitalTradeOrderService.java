package t5750.tcctransaction.sample.dubbo.capital.api;

import t5750.tcctransaction.api.TransactionContext;
import t5750.tcctransaction.sample.dubbo.capital.api.dto.CapitalTradeOrderDto;

/**
 */
public interface CapitalTradeOrderService {
	/**
	 * 创建资金帐户变更记录.
	 * 
	 * @param transactionContext
	 * @param tradeOrderDto
	 */
	public String record(TransactionContext transactionContext,
			CapitalTradeOrderDto tradeOrderDto);
}
