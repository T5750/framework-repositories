package t5750.pay.service.point.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.point.dao.RpPointAccountDao;
import t5750.pay.service.point.entity.RpPointAccount;

/**
 * @类功能说明： 账户dao实现类
 * @版本：V1.0
 */
@Repository
public class RpPointAccountDaoImpl extends BaseDaoImpl<RpPointAccount>
		implements RpPointAccountDao {
	public RpPointAccount getByUserNo(String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("isPessimist", true);
		return this.getSessionTemplate().selectOne(getStatement("getByUserNo"),
				paramMap);
	}
}