package t5750.pay.service.user.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.user.dao.RpUserPayInfoDao;
import t5750.pay.service.user.entity.RpUserPayInfo;

/**
 * @类功能说明： 用户第三方支付信息dao实现类 @版本：V1.0
 */
@Repository
public class RpUserPayInfoDaoImpl extends BaseDaoImpl<RpUserPayInfo>
		implements RpUserPayInfoDao {
	/**
	 * 通过商户编号获取商户第三方支付信息
	 *
	 * @param userNo
	 * @param payWayCode
	 * @return
	 */
	@Override
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("payWayCode", payWayCode);
		return super.getBy(paramMap);
	}
}