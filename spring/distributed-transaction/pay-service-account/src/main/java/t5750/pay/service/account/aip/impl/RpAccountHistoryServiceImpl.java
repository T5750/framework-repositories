package t5750.pay.service.account.aip.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.account.api.RpAccountHistoryService;
import t5750.pay.service.account.dao.RpAccountHistoryDao;
import t5750.pay.service.account.entity.RpAccountHistory;

/**
 * @类功能说明： 账户历史service实现类 @版本：V1.0
 */
@Service("rpAccountHistoryService")
public class RpAccountHistoryServiceImpl implements RpAccountHistoryService {
	@Autowired
	private RpAccountHistoryDao rpAccountHistoryDao;

	@Override
	public void saveData(RpAccountHistory rpAccountHistory) {
		rpAccountHistoryDao.insert(rpAccountHistory);
	}

	@Override
	public void updateData(RpAccountHistory rpAccountHistory) {
		rpAccountHistoryDao.update(rpAccountHistory);
	}

	@Override
	public RpAccountHistory getDataById(String id) {
		return rpAccountHistoryDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam,
			RpAccountHistory rpAccountHistory) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rpAccountHistoryDao.listPage(pageParam, paramMap);
	}
}