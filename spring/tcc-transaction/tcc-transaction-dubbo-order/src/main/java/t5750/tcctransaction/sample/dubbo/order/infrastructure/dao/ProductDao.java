package t5750.tcctransaction.sample.dubbo.order.infrastructure.dao;

import java.util.List;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Product;

/**
 */
public interface ProductDao {
	Product findById(long productId);

	List<Product> findByShopId(long shopId);
}