package t5750.pay.service.user.aip.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.user.api.RpUserPayInfoService;
import t5750.pay.service.user.dao.RpUserPayInfoDao;
import t5750.pay.service.user.entity.RpUserPayInfo;

/**
 * @类功能说明： 用户第三方支付信息service实现类 @版本：V1.0
 */
@Service("rpUserPayInfoService")
public class RpUserPayInfoServiceImpl implements RpUserPayInfoService {
	@Autowired
	private RpUserPayInfoDao rpUserPayInfoDao;

	@Override
	public void saveData(RpUserPayInfo rpUserPayInfo) {
		rpUserPayInfoDao.insert(rpUserPayInfo);
	}

	@Override
	public void updateData(RpUserPayInfo rpUserPayInfo) {
		rpUserPayInfoDao.update(rpUserPayInfo);
	}

	@Override
	public RpUserPayInfo getDataById(String id) {
		return rpUserPayInfoDao.getById(id);
	}

	@Override
	public PageBean listPage(PageParam pageParam, RpUserPayInfo rpUserPayInfo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		return rpUserPayInfoDao.listPage(pageParam, paramMap);
	}

	/**
	 * 通过商户编号获取商户支付配置信息
	 *
	 * @param userNO
	 * @return
	 */
	@Override
	public RpUserPayInfo getByUserNo(String userNo, String payWayCode) {
		return rpUserPayInfoDao.getByUserNo(userNo, payWayCode);
	}
}