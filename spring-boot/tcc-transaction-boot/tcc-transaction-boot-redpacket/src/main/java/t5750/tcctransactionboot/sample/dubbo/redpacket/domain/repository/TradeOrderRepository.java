package t5750.tcctransactionboot.sample.dubbo.redpacket.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import t5750.tcctransactionboot.sample.dubbo.redpacket.domain.entity.TradeOrder;
import t5750.tcctransactionboot.sample.dubbo.redpacket.infrastructure.dao.TradeOrderDao;

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
		tradeOrder.updateVersion();
		int effectCount = tradeOrderDao.update(tradeOrder);
		if (effectCount < 1) {
			throw new OptimisticLockingFailureException(
					"update trade order failed");
		}
	}

	public TradeOrder findByMerchantOrderNo(String merchantOrderNo) {
		return tradeOrderDao.findByMerchantOrderNo(merchantOrderNo);
	}
}
