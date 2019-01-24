package t5750.tcctransactionboot.sample.dubbo.order.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import t5750.tcctransactionboot.sample.dubbo.order.domain.entity.Order;
import t5750.tcctransactionboot.sample.dubbo.order.domain.entity.OrderLine;
import t5750.tcctransactionboot.sample.dubbo.order.infrastructure.dao.OrderDao;
import t5750.tcctransactionboot.sample.dubbo.order.infrastructure.dao.OrderLineDao;

/**
 */
@Repository
public class OrderRepository {
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderLineDao orderLineDao;

	public void createOrder(Order order) {
		orderDao.insert(order);
		for (OrderLine orderLine : order.getOrderLines()) {
			orderLineDao.insert(orderLine);
		}
	}

	public void updateOrder(Order order) {
		order.updateVersion();
		int effectCount = orderDao.update(order);
		if (effectCount < 1) {
			throw new OptimisticLockingFailureException("update order failed");
		}
	}

	public Order findByMerchantOrderNo(String merchantOrderNo) {
		return orderDao.findByMerchantOrderNo(merchantOrderNo);
	}
}
