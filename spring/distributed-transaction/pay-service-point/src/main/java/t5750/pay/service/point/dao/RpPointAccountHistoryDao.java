package t5750.pay.service.point.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.point.entity.RpPointAccountHistory;

/**
 * <b>功能说明: </b>
 */
public interface RpPointAccountHistoryDao extends
		BaseDao<RpPointAccountHistory> {
	/** 根据请求号获取账户历史 **/
	public RpPointAccountHistory getByRequestNo(String requestNo);
}