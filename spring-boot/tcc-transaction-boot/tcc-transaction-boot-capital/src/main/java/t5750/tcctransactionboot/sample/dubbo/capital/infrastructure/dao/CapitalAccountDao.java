package t5750.tcctransactionboot.sample.dubbo.capital.infrastructure.dao;

import t5750.tcctransactionboot.sample.dubbo.capital.domain.entity.CapitalAccount;

/**
 */
public interface CapitalAccountDao {
	CapitalAccount findByUserId(long userId);

	int update(CapitalAccount capitalAccount);
}
