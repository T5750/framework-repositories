package t5750.tcctransactionboot.sample.dubbo.capital.domain.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import t5750.tcctransactionboot.sample.dubbo.capital.domain.entity.CapitalAccount;
import t5750.tcctransactionboot.sample.dubbo.capital.infrastructure.dao.CapitalAccountDao;
import t5750.tcctransactionboot.sample.exception.InsufficientBalanceException;

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
		int effectCount = capitalAccountDao.update(capitalAccount);
		if (effectCount < 1) {
			throw new InsufficientBalanceException();
		}
	}
}
