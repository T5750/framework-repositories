package t5750.pay.service.user.api;

import java.util.List;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.entity.RpUserInfo;
import t5750.pay.service.user.exceptions.PayBizException;

/**
 * @类功能说明： 用户信息service接口 @版本：V1.0
 */
public interface RpUserInfoService {
	/**
	 * 保存
	 */
	void saveData(RpUserInfo rpUserInfo) throws PayBizException;

	/**
	 * 更新
	 */
	void updateData(RpUserInfo rpUserInfo) throws PayBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpUserInfo getDataById(String id) throws PayBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpUserInfo rpUserInfo)
			throws PayBizException;

	/**
	 * 用户线下注册
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */
	void registerOffline(String userName) throws PayBizException;

	/**
	 * 根据商户编号获取商户信息
	 * 
	 * @param merchantNo
	 * @return
	 */
	RpUserInfo getDataByMerchentNo(String merchantNo) throws PayBizException;

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	List<RpUserInfo> listAll() throws PayBizException;
}