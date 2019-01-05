package t5750.pay.service.trade.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import t5750.pay.common.core.dao.impl.BaseDaoImpl;
import t5750.pay.service.trade.dao.RpTradePaymentOrderDao;
import t5750.pay.service.trade.entity.RpTradePaymentOrder;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Repository("rpTradePaymentOrderDao")
public class RpTradePaymentOrderDaoImpl extends BaseDaoImpl<RpTradePaymentOrder>
		implements RpTradePaymentOrderDao {
	/**
	 * 根据商户编号及商户订单号获取支付订单信息
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @param merchantOrderNo
	 *            商户订单号
	 * @return
	 */
	@Override
	public RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(
			String merchantNo, String merchantOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		return super.getBy(paramMap);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return 0;
	}

	@Override
	public int insertSelective(RpTradePaymentOrder record) {
		return 0;
	}

	@Override
	public RpTradePaymentOrder selectByPrimaryKey(String id) {
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(RpTradePaymentOrder record) {
		return 0;
	}

	@Override
	public int updateByPrimaryKey(RpTradePaymentOrder record) {
		return 0;
	}
}
