package t5750.pay.service.user.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.user.entity.RpUserPayInfo;

/**
 * @类功能说明： 用户第三方支付信息dao @版本：V1.0
 */
public interface RpUserPayInfoDao extends BaseDao<RpUserPayInfo> {
	/**
	 * 通过商户编号获取商户第三方支付信息
	 * 
	 * @param userNo
	 * @param payWayCode
	 * @return
	 */
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode);
}