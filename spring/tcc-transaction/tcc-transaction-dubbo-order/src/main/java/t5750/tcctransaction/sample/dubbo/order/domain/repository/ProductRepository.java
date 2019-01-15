package t5750.tcctransaction.sample.dubbo.order.domain.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Product;
import t5750.tcctransaction.sample.dubbo.order.infrastructure.dao.ProductDao;

/**
 */
@Repository
public class ProductRepository {
	@Autowired
	ProductDao productDao;

	public Product findById(long productId) {
		return productDao.findById(productId);
	}

	public List<Product> findByShopId(long shopId) {
		return productDao.findByShopId(shopId);
	}
}