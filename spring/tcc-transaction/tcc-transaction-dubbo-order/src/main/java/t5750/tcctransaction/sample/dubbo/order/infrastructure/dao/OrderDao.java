package t5750.tcctransaction.sample.dubbo.order.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Order;

/**
 */
public interface OrderDao {
	public void insert(Order order);

	public void update(Order order);

	Order findByMerchantOrderNo(String merchantOrderNo);
}
