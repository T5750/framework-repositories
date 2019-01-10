package t5750.pay.service.account.api.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.account.api.RpAccountService;
import t5750.pay.service.account.dao.RpAccountDao;
import t5750.pay.service.account.entity.RpAccount;

/**
 * @类功能说明： 账户service实现类 @版本：V1.0
 */
@Service("rpAccountService")
public class RpAccountServiceImpl implements RpAccountService {
	@Autowired
	private RpAccountDao rpAccountDao;

	@Override
	public void saveData(RpAccount rpAccount) {
		rpAccountDao.insert(rpAccount);
	}

	@Override
	public void updateData(RpAccount rpAccount) {
		rpAccountDao.update(rpAccount);
	}

	@Override
	public RpAccount getDataById(String id) {
		return rpAccountDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpAccount rpAccount) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", rpAccount.getAccountNo());
		return rpAccountDao.listPage(pageParam, paramMap);
	}
}