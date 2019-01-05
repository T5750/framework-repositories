package t5750.pay.service.trade.dao;

import t5750.pay.common.core.dao.BaseDao;
import t5750.pay.service.trade.entity.RpTradePaymentOrder;

/**
 * @功能说明:
 * @版本:V1.0
 */
public interface RpTradePaymentOrderDao extends BaseDao<RpTradePaymentOrder> {
	/**
	 * 根据商户编号及商户订单号获取支付订单信息
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	RpTradePaymentOrder selectByMerchantNoAndMerchantOrderNo(String merchantNo,
			String merchantOrderNo);

	int deleteByPrimaryKey(String id);

	int insertSelective(RpTradePaymentOrder record);

	RpTradePaymentOrder selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RpTradePaymentOrder record);

	int updateByPrimaryKey(RpTradePaymentOrder record);
}
