package t5750.tcctransactionboot.sample.dubbo.order.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.order.domain.entity.OrderLine;

/**
 */
public interface OrderLineDao {
	void insert(OrderLine orderLine);
}
