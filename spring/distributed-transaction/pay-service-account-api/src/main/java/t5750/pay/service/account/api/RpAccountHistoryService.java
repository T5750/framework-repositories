package t5750.pay.service.account.api;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.account.entity.RpAccountHistory;
import t5750.pay.service.account.exceptions.AccountBizException;

/**
 * @类功能说明： 账户历史service接口 @版本：V1.0
 */
public interface RpAccountHistoryService {
	/**
	 * 保存
	 */
	void saveData(RpAccountHistory rpAccountHistory) throws AccountBizException;

	/**
	 * 更新
	 */
	void updateData(RpAccountHistory rpAccountHistory)
			throws AccountBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpAccountHistory getDataById(String id) throws AccountBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpAccountHistory rpAccountHistory)
			throws AccountBizException;
}