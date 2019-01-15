package t5750.tcctransaction.sample.dubbo.order.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.OrderLine;

/**
 */
public interface OrderLineDao {
	void insert(OrderLine orderLine);
}
