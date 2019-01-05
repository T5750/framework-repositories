package t5750.pay.service.account.dao;

import java.util.Map;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.account.entity.RpAccount;

/**
 * @类功能说明： 账户dao @版本：V1.0
 */
public interface RpAccountDao extends BaseDao<RpAccount> {
	RpAccount getByAccountNo(String accountNo);

	RpAccount getByUserNo(Map<String, Object> map);
}