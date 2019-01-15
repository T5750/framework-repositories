package t5750.tcctransaction.sample.dubbo.capital.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.capital.domain.entity.TradeOrder;
import t5750.tcctransaction.sample.dubbo.capital.infrastructure.dao.TradeOrderDao;

/**
 */
@Repository
public class TradeOrderRepository {
	@Autowired
	TradeOrderDao tradeOrderDao;

	public void insert(TradeOrder tradeOrder) {
		tradeOrderDao.insert(tradeOrder);
	}

	public void update(TradeOrder tradeOrder) {
		tradeOrderDao.update(tradeOrder);
	}

	public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
		return tradeOrderDao.findByMerchantOrderNo(merchantOrderNo);
	}
}