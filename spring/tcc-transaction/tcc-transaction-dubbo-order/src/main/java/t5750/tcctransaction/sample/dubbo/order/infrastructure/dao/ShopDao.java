package t5750.tcctransaction.sample.dubbo.order.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Shop;

/**
 */
public interface ShopDao {
	Shop findById(long id);
}
