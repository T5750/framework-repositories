package t5750.tcctransaction.sample.dubbo.order.web.controller;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import t5750.tcctransaction.sample.dubbo.order.api.PlaceOrderService;
import t5750.tcctransaction.sample.dubbo.order.domain.entity.Product;
import t5750.tcctransaction.sample.dubbo.order.web.controller.vo.PlaceOrderRequest;

/**
 */
@Controller
@RequestMapping("")
public class OrderController {
	@Autowired
	PlaceOrderService placeOrderService;

	/**
	 * 用户（userId）进入商店（shopId）选择商品.
	 * 
	 * @param userId
	 * @param shopId
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}/shop/{shopId}", method = RequestMethod.GET)
	public ModelAndView getProductsInShop(@PathVariable long userId,
			@PathVariable long shopId) {
		List<Product> products = placeOrderService.findProductByShopId(shopId);
		ModelAndView mv = new ModelAndView("/shop");
		mv.addObject("products", products);
		mv.addObject("userId", userId);
		mv.addObject("shopId", shopId);
		return mv;
	}

	/**
	 * 用户（userId）选择商店（shopId）中的商品（productId）后进入支付页面，可以选择使用多数红包金额进行支付.
	 * 
	 * @param userId
	 * @param shopId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/user/{userId}/shop/{shopId}/product/{productId}/confirm", method = RequestMethod.GET)
	public ModelAndView productDetail(@PathVariable long userId,
			@PathVariable long shopId, @PathVariable long productId) {
		ModelAndView mv = new ModelAndView("product_detail");
		mv.addObject("capitalAmount",
				placeOrderService.getCapitalAccountByUserId(userId));
		mv.addObject("redPacketAmount",
				placeOrderService.getRedPacketAccountByUserId(userId));
		mv.addObject("product", placeOrderService.findProductById(productId));
		mv.addObject("userId", userId);
		mv.addObject("shopId", shopId);
		return mv;
	}

	/**
	 * 输入红包金额后，提交支付.
	 * 
	 * @param redPacketPayAmount
	 * @param shopId
	 * @param payerUserId
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/placeorder", method = RequestMethod.POST)
	public ModelAndView placeOrder(@RequestParam String redPacketPayAmount,
			@RequestParam long shopId, @RequestParam long payerUserId,
			@RequestParam long productId) {
		PlaceOrderRequest request = buildRequest(redPacketPayAmount, shopId,
				payerUserId, productId);
		String merchantOrderNo = placeOrderService
				.placeOrder(request.getPayerUserId(), request.getShopId(),
						request.getProductQuantities(),
						request.getRedPacketPayAmount());
		ModelAndView mv = new ModelAndView("pay_success");
		String payResultTip = null;
		String status = placeOrderService
				.getOrderStatusByMerchantOrderNo(merchantOrderNo);
		if ("CONFIRMED".equals(status))
			payResultTip = "支付成功";
		else if ("PAY_FAILED".equals(status))
			payResultTip = "支付失败";
		mv.addObject("payResult", payResultTip);
		mv.addObject("product", placeOrderService.findProductById(productId));
		mv.addObject("capitalAmount",
				placeOrderService.getCapitalAccountByUserId(payerUserId));
		mv.addObject("redPacketAmount",
				placeOrderService.getRedPacketAccountByUserId(payerUserId));
		return mv;
	}

	private PlaceOrderRequest buildRequest(String redPacketPayAmount,
			long shopId, long payerUserId, long productId) {
		BigDecimal redPacketPayAmountInBigDecimal = new BigDecimal(
				redPacketPayAmount);
		if (redPacketPayAmountInBigDecimal.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidParameterException("invalid red packet amount :"
					+ redPacketPayAmount);
		PlaceOrderRequest request = new PlaceOrderRequest();
		request.setPayerUserId(payerUserId);
		request.setShopId(shopId);
		request.setRedPacketPayAmount(new BigDecimal(redPacketPayAmount));
		request.getProductQuantities().add(
				new ImmutablePair<Long, Integer>(productId, 1));
		return request;
	}
}