package t5750.tcctransaction.sample.dubbo.order.api;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Product;

/**
 * 下订单接口.
 */
public interface PlaceOrderService {
	/**
	 * 下订单。
	 * 
	 * @param payerUserId
	 *            付款者ID.
	 * @param shopId
	 *            店铺ID.
	 * @param productQuantities
	 *            产品数量
	 * @param redPacketPayAmount
	 *            红包支付金额。
	 */
	public String placeOrder(long payerUserId, long shopId,
			List<Pair<Long, Integer>> productQuantities,
			BigDecimal redPacketPayAmount);

	Product findProductById(long productId);

	List<Product> findProductByShopId(long shopId);

	BigDecimal getRedPacketAccountByUserId(long userId);

	BigDecimal getCapitalAccountByUserId(long userId);

	String getOrderStatusByMerchantOrderNo(String orderNo);
}
