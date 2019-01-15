package t5750.tcctransaction.sample.dubbo.capital.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.capital.domain.entity.TradeOrder;

/**
 */
public interface TradeOrderDao {
	void insert(TradeOrder tradeOrder);

	void update(TradeOrder tradeOrder);

	TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}