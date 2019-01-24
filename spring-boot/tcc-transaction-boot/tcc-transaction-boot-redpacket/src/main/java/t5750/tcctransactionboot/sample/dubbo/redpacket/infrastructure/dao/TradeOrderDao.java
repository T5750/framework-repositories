package t5750.tcctransactionboot.sample.dubbo.redpacket.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.TradeOrder;

/**
 */
public interface TradeOrderDao {
	void insert(TradeOrder tradeOrder);

	int update(TradeOrder tradeOrder);

	TradeOrder findByMerchantOrderNo(String merchantOrderNo);
}
