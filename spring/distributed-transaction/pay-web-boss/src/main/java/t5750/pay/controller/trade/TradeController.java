package t5750.pay.controller.trade;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import t5750.pay.common.core.enums.PayTypeEnum;
import t5750.pay.common.core.enums.PayWayEnum;
import t5750.pay.common.core.enums.TrxTypeEnum;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.trade.api.RpTradePaymentQueryService;
import t5750.pay.service.trade.enums.TradeStatusEnum;
import t5750.pay.service.trade.vo.PaymentOrderQueryVo;
import t5750.pay.service.user.enums.FundInfoTypeEnum;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Controller
@RequestMapping("/trade")
public class TradeController {
	@Autowired
	private RpTradePaymentQueryService rpTradePaymentQueryService;

	@RequestMapping(value = "/listPaymentOrder", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String listPaymentOrder(HttpServletRequest request,
			PaymentOrderQueryVo paymentOrderQueryVo, PageParam pageParam,
			Model model) {
		PageBean pageBean = rpTradePaymentQueryService
				.listPaymentOrderPage(pageParam, paymentOrderQueryVo);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("rpTradePaymentOrder", paymentOrderQueryVo);// 查询条件
		model.addAttribute("statusEnums", TradeStatusEnum.toMap());// 状态
		model.addAttribute("payWayNameEnums", PayWayEnum.toMap());// 支付方式
		model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());// 支付类型
		model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());// 支付类型
		return "trade/listPaymentOrder";
	}

	@RequestMapping(value = "/listPaymentRecord", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String listPaymentRecord(HttpServletRequest request,
			PaymentOrderQueryVo paymentOrderQueryVo, PageParam pageParam,
			Model model) {
		PageBean pageBean = rpTradePaymentQueryService
				.listPaymentRecordPage(pageParam, paymentOrderQueryVo);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("pageParam", pageParam);
		model.addAttribute("rpUserInfo", paymentOrderQueryVo);
		model.addAttribute("statusEnums", TradeStatusEnum.toMap());// 状态
		model.addAttribute("payWayNameEnums", PayWayEnum.toMap());// 支付方式
		model.addAttribute("payTypeNameEnums", PayTypeEnum.toMap());// 支付类型
		model.addAttribute("fundIntoTypeEnums", FundInfoTypeEnum.toMap());// 支付类型
		model.addAttribute("trxTypeEnums", TrxTypeEnum.toMap());// 支付类型
		return "trade/listPaymentRecord";
	}
}
