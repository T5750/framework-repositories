package t5750.pay.service.user.api;

import java.util.List;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.entity.RpUserPayConfig;
import t5750.pay.service.user.exceptions.PayBizException;

/**
 * @类功能说明： 用户支付配置service接口 @版本：V1.0
 */
public interface RpUserPayConfigService {
	/**
	 * 保存
	 */
	void saveData(RpUserPayConfig rpUserPayConfig) throws PayBizException;

	/**
	 * 更新
	 */
	void updateData(RpUserPayConfig rpUserPayConfig) throws PayBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserPayConfig getDataById(String id) throws PayBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserPayConfig rpUserPayConfig)
			throws PayBizException;

	/**
	 * 根据商户编号获取已生效的支付配置
	 * 
	 * @param userNo
	 * @return
	 */
	RpUserPayConfig getByUserNo(String userNo) throws PayBizException;

	/**
	 * 根据商户编号获取支付配置
	 * 
	 * @param userNo
	 * @param auditStatus
	 * @return
	 */
	RpUserPayConfig getByUserNo(String userNo, String auditStatus)
			throws PayBizException;

	/**
	 * 根据支付产品获取已生效数据
	 */
	List<RpUserPayConfig> listByProductCode(String productCode)
			throws PayBizException;

	/**
	 * 根据支付产品获取数据
	 */
	List<RpUserPayConfig> listByProductCode(String productCode,
			String auditStatus) throws PayBizException;

	/**
	 * 创建用户支付配置
	 */
	void createUserPayConfig(String userNo, String userName, String productCode,
			String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId,
			String partnerKey, String ali_partner, String ali_sellerId,
			String ali_key) throws PayBizException;

	/**
	 * 删除支付产品
	 * 
	 * @param userNo
	 */
	void deleteUserPayConfig(String userNo) throws PayBizException;

	/**
	 * 修改用户支付配置
	 */
	void updateUserPayConfig(String userNo, String productCode,
			String productName, Integer riskDay, String fundIntoType,
			String isAutoSett, String appId, String merchantId,
			String partnerKey, String ali_partner, String ali_sellerId,
			String ali_key) throws PayBizException;

	/**
	 * 审核
	 * 
	 * @param userNo
	 * @param auditStatus
	 */
	void audit(String userNo, String auditStatus) throws PayBizException;

	/**
	 * 根据商户key获取已生效的支付配置
	 * 
	 * @param payKey
	 * @return
	 */
	RpUserPayConfig getByPayKey(String payKey) throws PayBizException;
}