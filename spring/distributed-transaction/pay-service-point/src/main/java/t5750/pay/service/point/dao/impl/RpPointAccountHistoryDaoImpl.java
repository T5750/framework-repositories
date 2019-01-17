package t5750.pay.service.point.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.point.dao.RpPointAccountHistoryDao;
import t5750.pay.service.point.entity.RpPointAccountHistory;

/**
 * <b>功能说明: </b>
 */
@Repository
public class RpPointAccountHistoryDaoImpl extends
		BaseDaoImpl<RpPointAccountHistory> implements RpPointAccountHistoryDao {
	/**
	 * 根据请求号获取账户历史
	 *
	 * @param requestNo
	 **/
	@Override
	public RpPointAccountHistory getByRequestNo(String requestNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return super.getBy(paramMap);
	}
}
