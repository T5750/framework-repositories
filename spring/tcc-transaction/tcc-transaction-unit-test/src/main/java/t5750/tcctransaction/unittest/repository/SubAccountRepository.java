package t5750.tcctransaction.unittest.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.tcctransaction.unittest.entity.SubAccount;

/**
 * 子账户库.
 */
@Repository
public class SubAccountRepository {
	private Map<Long, SubAccount> subAccountMap = new HashMap<Long, SubAccount>();
	{
		/* 初始化3个子账户：余额为0 * */
		subAccountMap.put(1L, new SubAccount(1, 100));
		subAccountMap.put(2L, new SubAccount(2, 200));
		subAccountMap.put(3L, new SubAccount(3, 300));
	}

	/** 根据ID获取子账户信息 **/
	public SubAccount findById(Long id) {
		return subAccountMap.get(id);
	}
}
