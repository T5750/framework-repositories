package t5750.pay.service.point.api;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.point.entity.RpPointAccount;
import t5750.pay.service.point.exceptions.PointBizException;
import t5750.tcctransaction.api.TransactionContext;

/**
 * @类功能说明： 账户service接口
 * @版本：V1.0
 */
public interface RpPointAccountService {
	/**
	 * 保存
	 */
	void saveData(RpPointAccount rpPointAccount) throws PointBizException;

	/**
	 * 更新
	 */
	void updateData(RpPointAccount rpPointAccount) throws PointBizException;

	void creditToPointAccountTcc(TransactionContext transactionContext,
			String userNo, Integer pointAmount, String requestNo,
			String bankTrxNo, String trxType, String remark)
			throws PointBizException;

	void creditToPointAccount(String userNo, Integer pointAmount,
			String requestNo, String bankTrxNo, String trxType, String remark)
			throws PointBizException;

	/**
	 * 根据id获取数据
	 * 
	 * @param id
	 * @return
	 */
	RpPointAccount getDataById(String id) throws PointBizException;

	/**
	 * 获取分页数据
	 * 
	 * @param pageParam
	 * @return
	 */
	PageBean listPage(PageParam pageParam, RpPointAccount rpPointAccount)
			throws PointBizException;
}