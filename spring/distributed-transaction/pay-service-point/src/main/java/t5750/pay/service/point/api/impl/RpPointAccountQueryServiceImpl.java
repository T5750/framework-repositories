package t5750.pay.service.point.api.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.enums.PublicStatusEnum;
import t5750.pay.common.core.exception.BizException;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.common.core.utils.DateUtils;
import t5750.pay.service.point.api.RpPointAccountQueryService;
import t5750.pay.service.point.dao.RpPointAccountDao;
import t5750.pay.service.point.entity.RpPointAccount;
import t5750.pay.service.point.exceptions.PointBizException;

/**
 * @类功能说明： 账户查询service实现类
 * @版本：V1.0
 */
@Service("rpPointAccountQueryService")
public class RpPointAccountQueryServiceImpl implements
		RpPointAccountQueryService {
	@Autowired
	private RpPointAccountDao rpPointAccountDao;
	private static final Logger LOG = LoggerFactory
			.getLogger(RpPointAccountQueryServiceImpl.class);

	/**
	 * 根据用户编号编号获取账户信息
	 * 
	 * @param userNo
	 *            用户编号
	 * @return
	 */
	public RpPointAccount getAccountByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		LOG.info("根据用户编号查询账户信息");
		RpPointAccount account = this.rpPointAccountDao.getBy(map);
		if (account == null) {
			throw PointBizException.ACCOUNT_NOT_EXIT;
		}
		// 不是同一天直接清0
		if (!DateUtils.isSameDayWithToday(account.getEditTime())) {
			account.setEditTime(new Date());
			rpPointAccountDao.update(account);
		}
		return account;
	}

	/**
	 * 根据参数分页查询账户.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param params
	 *            查询参数，可以为null.
	 * @return AccountList.
	 * @throws BizException
	 */
	public PageBean queryAccountListPage(PageParam pageParam,
			Map<String, Object> params) {
		return rpPointAccountDao.listPage(pageParam, params);
	}

	/**
	 * 获取所有账户
	 * 
	 * @return
	 */
	@Override
	public List<RpPointAccount> listAll() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpPointAccountDao.listBy(paramMap);
	}
}