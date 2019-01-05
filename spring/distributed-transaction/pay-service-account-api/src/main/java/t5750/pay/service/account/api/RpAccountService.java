package t5750.pay.service.account.api;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.account.entity.RpAccount;
import t5750.pay.service.account.exceptions.AccountBizException;

/**
 * @类功能说明： 账户service接口 @版本：V1.0
 */
public interface RpAccountService {
	/**
	 * 保存
	 */
	void saveData(RpAccount rpAccount) throws AccountBizException;

	/**
	 * 更新
	 */
	void updateData(RpAccount rpAccount) throws AccountBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccount getDataById(String id) throws AccountBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpAccount rpAccount)
			throws AccountBizException;
}