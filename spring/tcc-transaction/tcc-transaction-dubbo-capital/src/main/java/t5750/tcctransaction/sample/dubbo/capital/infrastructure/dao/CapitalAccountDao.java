package t5750.tcctransaction.sample.dubbo.capital.infrastructure.dao;

import t5750.tcctransaction.sample.dubbo.capital.domain.entity.CapitalAccount;

/**
 */
public interface CapitalAccountDao {
	CapitalAccount findByUserId(long userId);

	void update(CapitalAccount capitalAccount);
}
