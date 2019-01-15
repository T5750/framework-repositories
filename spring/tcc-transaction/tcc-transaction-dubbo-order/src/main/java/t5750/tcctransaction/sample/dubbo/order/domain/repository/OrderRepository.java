package t5750.tcctransaction.sample.dubbo.order.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Order;
import t5750.tcctransaction.sample.dubbo.order.domain.entity.OrderLine;
import t5750.tcctransaction.sample.dubbo.order.infrastructure.dao.OrderDao;
import t5750.tcctransaction.sample.dubbo.order.infrastructure.dao.OrderLineDao;

/**
 */
@Repository
public class OrderRepository {
	@Autowired
	OrderDao orderDao;
	@Autowired
	OrderLineDao orderLineDao;

	/**
	 * 创建订单记录.
	 * 
	 * @param order
	 */
	public void createOrder(Order order) {
		orderDao.insert(order);
		for (OrderLine orderLine : order.getOrderLines()) {
			orderLineDao.insert(orderLine);
		}
	}

	/**
	 * 更新订单记录.
	 * 
	 * @param order
	 */
	public void updateOrder(Order order) {
		orderDao.update(order);
	}

	public Order findByMerchantOrderNo(String merchantOrderNo) {
		return orderDao.findByMerchantOrderNo(merchantOrderNo);
	}
}
