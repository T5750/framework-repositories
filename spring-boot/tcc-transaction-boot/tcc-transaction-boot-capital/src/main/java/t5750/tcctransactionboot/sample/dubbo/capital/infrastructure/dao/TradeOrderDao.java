package t5750.tcctransactionboot.sample.dubbo.capital.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.capital.domain.entity.TradeOrder;

/**
 */
public interface TradeOrderDao {
	int insert(TradeOrder tradeOrder);

	int update(TradeOrder tradeOrder);

	TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
