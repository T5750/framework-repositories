package t5750.pay.service.point.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.point.entity.RpPointAccount;

/**
 * @类功能说明： 账户dao
 * @版本：V1.0
 */
public interface RpPointAccountDao extends BaseDao<RpPointAccount> {
	RpPointAccount getByUserNo(String userNo);
}