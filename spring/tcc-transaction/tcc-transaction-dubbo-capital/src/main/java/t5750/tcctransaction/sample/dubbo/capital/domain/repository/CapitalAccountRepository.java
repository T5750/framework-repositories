package t5750.tcctransaction.sample.dubbo.capital.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransaction.sample.dubbo.capital.domain.entity.CapitalAccount;
import t5750.tcctransaction.sample.dubbo.capital.infrastructure.dao.CapitalAccountDao;

/**
 */
@Repository
public class CapitalAccountRepository {
	@Autowired
	CapitalAccountDao capitalAccountDao;

	public CapitalAccount findByUserId(long userId) {
		return capitalAccountDao.findByUserId(userId);
	}

	public void save(CapitalAccount capitalAccount) {
		capitalAccountDao.update(capitalAccount);
	}
}
