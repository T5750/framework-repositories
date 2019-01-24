package t5750.tcctransactionboot.sample.dubbo.order.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.order.domain.entity.Order;

/**
 */
public interface OrderDao {
	public int insert(Order order);

	public int update(Order order);

	Order findByMerchantOrderNo(String merchantOrderNo);
}
