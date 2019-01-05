package t5750.pay.service.user.aip.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import t5750.pay.common.core.enums.PublicStatusEnum;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.common.core.utils.StringUtil;
import t5750.pay.service.account.api.RpAccountService;
import t5750.pay.service.account.entity.RpAccount;
import t5750.pay.service.user.api.RpUserInfoService;
import t5750.pay.service.user.dao.RpUserInfoDao;
import t5750.pay.service.user.entity.RpUserInfo;

/**
 * @类功能说明： 用户信息service实现类 @版本：V1.0
 */
@Service("rpUserInfoService")
public class RpUserInfoServiceImpl implements RpUserInfoService {
	@Autowired
	private RpUserInfoDao rpUserInfoDao;
	@Autowired
	private RpAccountService rpAccountService;

	@Override
	public void saveData(RpUserInfo rpUserInfo) {
		rpUserInfoDao.insert(rpUserInfo);
	}

	@Override
	public void updateData(RpUserInfo rpUserInfo) {
		rpUserInfoDao.update(rpUserInfo);
	}

	@Override
	public RpUserInfo getDataById(String id) {
		return rpUserInfoDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpUserInfo rpUserInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rpUserInfoDao.listPage(pageParam, paramMap);
	}

	/**
	 * 用户线下注册
	 * 
	 * @param userName
	 *            用户名
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void registerOffline(String userName) {
		String userNo = rpUserInfoDao.buildUserNo();
		String accountNo = rpUserInfoDao.buildAccountNo();
		// 生成用户信息
		RpUserInfo rpUserInfo = new RpUserInfo();
		rpUserInfo.setAccountNo(accountNo);
		rpUserInfo.setCreateTime(new Date());
		rpUserInfo.setId(StringUtil.get32UUID());
		rpUserInfo.setStatus(PublicStatusEnum.ACTIVE.name());
		rpUserInfo.setUserName(userName);
		rpUserInfo.setUserNo(userNo);
		rpUserInfo.setVersion(0);
		this.saveData(rpUserInfo);
		// 生成账户信息
		RpAccount rpAccount = new RpAccount();
		rpAccount.setAccountNo(accountNo);
		rpAccount.setAccountType("0");
		rpAccount.setCreateTime(new Date());
		rpAccount.setEditTime(new Date());
		rpAccount.setId(StringUtil.get32UUID());
		rpAccount.setStatus(PublicStatusEnum.ACTIVE.name());
		rpAccount.setUserNo(userNo);
		rpAccount.setBalance(new BigDecimal("0"));
		rpAccount.setSecurityMoney(new BigDecimal("0"));
		rpAccount.setSettAmount(new BigDecimal("0"));
		rpAccount.setTodayExpend(new BigDecimal("0"));
		rpAccount.setTodayIncome(new BigDecimal("0"));
		rpAccount.setUnbalance(new BigDecimal("0"));
		rpAccount.setTotalExpend(new BigDecimal("0"));
		rpAccount.setTotalIncome(new BigDecimal("0"));
		rpAccountService.saveData(rpAccount);
	}

	/**
	 * 根据商户编号获取商户信息
	 *
	 * @param merchantNo
	 * @return
	 */
	@Override
	public RpUserInfo getDataByMerchentNo(String merchantNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", merchantNo);
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpUserInfoDao.getBy(paramMap);
	}

	/**
	 * 获取所有用户
	 * 
	 * @return
	 */
	@Override
	public List<RpUserInfo> listAll() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", PublicStatusEnum.ACTIVE.name());
		return rpUserInfoDao.listBy(paramMap);
	}
}