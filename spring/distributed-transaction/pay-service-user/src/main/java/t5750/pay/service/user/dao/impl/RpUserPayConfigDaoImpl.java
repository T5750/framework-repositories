package t5750.pay.service.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.common.core.enums.PublicStatusEnum;
import t5750.pay.service.user.dao.RpUserPayConfigDao;
import t5750.pay.service.user.entity.RpUserPayConfig;

/**
 * @类功能说明： 用户支付配置dao实现类 @版本：V1.0
 */
@Repository
public class RpUserPayConfigDaoImpl extends BaseDaoImpl<RpUserPayConfig>
		implements RpUserPayConfigDao {
	@Override
	public RpUserPayConfig getByUserNo(String userNo, String auditStatus) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		paramMap.put("auditStatus", auditStatus);
		return super.getBy(paramMap);
	}
}