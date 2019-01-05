package t5750.pay.service.account.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.common.core.enums.PublicStatusEnum;
import t5750.pay.service.account.dao.RpAccountDao;
import t5750.pay.service.account.entity.RpAccount;

/**
 * @类功能说明： 账户dao实现类 @版本：V1.0
 */
@Repository
public class RpAccountDaoImpl extends BaseDaoImpl<RpAccount>
		implements RpAccountDao {
	public RpAccount getByAccountNo(String accountNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return this.getBy(paramMap);
	}

	public RpAccount getByUserNo(Map<String, Object> map) {
		return this.getSessionTemplate().selectOne(getStatement("getByUserNo"),
				map);
	}
}