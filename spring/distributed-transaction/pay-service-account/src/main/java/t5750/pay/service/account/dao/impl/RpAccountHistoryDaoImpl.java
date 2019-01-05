package t5750.pay.service.account.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.account.dao.RpAccountHistoryDao;
import t5750.pay.service.account.entity.RpAccountHistory;
import t5750.pay.service.account.vo.DailyCollectAccountHistoryVo;

/**
 * @类功能说明： 账户历史dao实现类 @版本：V1.0
 */
@Repository
public class RpAccountHistoryDaoImpl extends BaseDaoImpl<RpAccountHistory>
		implements RpAccountHistoryDao {
	public List<RpAccountHistory> listPageByParams(Map<String, Object> params) {
		return this.listBy(params);
	}

	public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(
			Map<String, Object> params) {
		return this.getSessionTemplate().selectList(
				getStatement("listDailyCollectAccountHistoryVo"), params);
	}

	@Override
	public RpAccountHistory getByRequestNo(String requestNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("requestNo", requestNo);
		return super.getBy(paramMap);
	}

	/** 更新账户风险预存期外的账户历史记录记为结算完成 **/
	public void updateCompleteSettTo100(Map<String, Object> params) {
		this.getSessionTemplate()
				.update(getStatement("updateCompleteSettTo100"), params);
	}
}