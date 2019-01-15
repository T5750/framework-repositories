package t5750.tcctransaction.sample.dubbo.order.domain.service;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import t5750.tcctransaction.sample.dubbo.order.domain.entity.Order;
import t5750.tcctransaction.sample.dubbo.order.domain.factory.OrderFactory;
import t5750.tcctransaction.sample.dubbo.order.domain.repository.OrderRepository;

/**
 */
@Service
public class OrderServiceImpl {
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderFactory orderFactory;

	/**
	 * 下订单.
	 * 
	 * @param payerUserId
	 *            付款者ID.
	 * @param payeeUserId
	 *            收款者ID.
	 * @param productQuantities
	 *            商品列表（数量，单价）
	 * @return
	 */
	@Transactional
	public Order createOrder(long payerUserId, long payeeUserId,
			List<Pair<Long, Integer>> productQuantities) {
		Order order = orderFactory.buildOrder(payerUserId, payeeUserId,
				productQuantities);
		orderRepository.createOrder(order);
		return order;
	}

	public String getOrderStatusByMerchantOrderNo(String orderNo) {
		return orderRepository.findByMerchantOrderNo(orderNo).getStatus();
	}
}
