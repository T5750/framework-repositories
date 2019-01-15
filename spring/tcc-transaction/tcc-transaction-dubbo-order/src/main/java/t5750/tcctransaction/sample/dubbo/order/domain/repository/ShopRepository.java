package t5750.tcctransaction.sample.dubbo.order.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Shop;
import t5750.tcctransaction.sample.dubbo.order.infrastructure.dao.ShopDao;

/**
 */
@Repository
public class ShopRepository {
	@Autowired
	ShopDao shopDao;

	public Shop findById(long id) {
		return shopDao.findById(id);
	}
}
