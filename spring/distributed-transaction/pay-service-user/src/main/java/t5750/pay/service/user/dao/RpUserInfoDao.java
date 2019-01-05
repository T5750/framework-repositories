package t5750.pay.service.user.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.user.entity.RpUserInfo;

/**
 * @类功能说明： 用户dao @版本：V1.0
 */
public interface RpUserInfoDao extends BaseDao<RpUserInfo> {
	/** 获取用户编号 **/
	String buildUserNo();

	/** 获取账户编号 **/
	String buildAccountNo();
}