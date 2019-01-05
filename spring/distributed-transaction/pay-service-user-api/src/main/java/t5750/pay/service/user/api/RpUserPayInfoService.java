package t5750.pay.service.user.api;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.entity.RpUserPayInfo;
import t5750.pay.service.user.exceptions.UserBizException;

/**
 * @类功能说明： 用户第三方支付信息service接口 @版本：V1.0
 */
public interface RpUserPayInfoService {
	/**
	 * 保存
	 */
	void saveData(RpUserPayInfo rpUserPayInfo) throws UserBizException;

	/**
	 * 更新
	 */
	void updateData(RpUserPayInfo rpUserPayInfo) throws UserBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserPayInfo getDataById(String id) throws UserBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserPayInfo rpUserPayInfo)
			throws UserBizException;

	/**
	 * 通过商户编号获取商户支付配置信息
	 * 
	 * @param userNo
	 * @param payWayCode
	 * @return
	 */
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode)
			throws UserBizException;
}