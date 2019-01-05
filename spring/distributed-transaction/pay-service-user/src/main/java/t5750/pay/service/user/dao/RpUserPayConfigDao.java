package t5750.pay.service.user.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.user.entity.RpUserPayConfig;

/**
 * @类功能说明： 用户支付配置dao @版本：V1.0
 */
public interface RpUserPayConfigDao extends BaseDao<RpUserPayConfig> {
	/**
	 * 根据用户编号获取用户支付信息
	 * 
	 * @param userNo
	 * @return
	 */
	RpUserPayConfig getByUserNo(String userNo, String auditStatus);
}