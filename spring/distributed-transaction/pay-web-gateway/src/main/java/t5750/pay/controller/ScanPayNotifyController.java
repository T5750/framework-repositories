package t5750.pay.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import t5750.pay.common.core.enums.NotifyDestinationNameEnum;
import t5750.pay.common.core.enums.PayWayEnum;
import t5750.pay.common.core.utils.StringUtil;
import t5750.pay.service.message.api.RpTransactionMessageService;
import t5750.pay.service.message.entity.RpTransactionMessage;
import t5750.pay.service.trade.api.RpTradePaymentManagerService;
import t5750.pay.service.trade.utils.WeiXinPayUtils;
import t5750.pay.service.trade.utils.alipay.util.AliPayUtil;
import t5750.pay.service.trade.vo.OrderPayResultVo;

/**
 * @功能说明:
 * @版本:V1.0
 */
@Controller
@RequestMapping(value = "/scanPayNotify")
public class ScanPayNotifyController {
	private static final Log LOG = LogFactory.getLog(ScanPayController.class);
	@Autowired
	private RpTradePaymentManagerService rpTradePaymentManagerService;
	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;

	/**
	 * 支付平台异步通知.
	 * 
	 * @param payWayCode
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @throws Exception
	 */
	@RequestMapping("/notify/{payWayCode}")
	public void notify(@PathVariable("payWayCode") String payWayCode,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws Exception {
		LOG.info("into notify,payWayCode:" + payWayCode);
		String printStr = "";
		Map<String, String> notifyMap = new HashMap<String, String>();
		if (PayWayEnum.WEIXIN.name().equals(payWayCode)) {
			InputStream inputStream = httpServletRequest.getInputStream();// 从request中取得输入流
			notifyMap = WeiXinPayUtils.parseXml(inputStream);
			printStr = "<xml>\n"
					+ "  <return_code><![CDATA[SUCCESS]]></return_code>\n"
					+ "  <return_msg><![CDATA[OK]]></return_msg>\n" + "</xml>";
		} else if (PayWayEnum.ALIPAY.name().equals(payWayCode)) {
			Map<String, String[]> requestParams = httpServletRequest
					.getParameterMap();
			notifyMap = AliPayUtil.parseNotifyMsg(requestParams);
			printStr = "success";
		}
		if (PayWayEnum.TEST_PAY_HTTP_CLIENT.name().equals(payWayCode)) {// 模拟支付，不验证签名了
			notifyMap.put("result_code",
					httpServletRequest.getParameter("result_code"));
			notifyMap.put("time_end",
					httpServletRequest.getParameter("time_end"));
			notifyMap.put("out_trade_no",
					httpServletRequest.getParameter("out_trade_no"));
			notifyMap.put("transaction_id",
					httpServletRequest.getParameter("transaction_id"));
			printStr = "TEST_PAY_HTTP_CLIENT success";
		} else {
			// 验证签名
			rpTradePaymentManagerService.verifyNotify(payWayCode, notifyMap);
		}
		// 订单处理消息
		String messageId = StringUtil.get32UUID();
		notifyMap.put("payWayCode", payWayCode);
		notifyMap.put("messageId", messageId);
		String messageBody = JSONObject.toJSONString(notifyMap);
		RpTransactionMessage rpTransactionMessage = new RpTransactionMessage(
				messageId, messageBody,
				NotifyDestinationNameEnum.BANK_NOTIFY.name());
		int saveSendMessage = rpTransactionMessageService
				.saveAndSendMessage(rpTransactionMessage); // 保存并发送
		// 通知商户
		if (saveSendMessage > 0) {
			String merchantNotifyUrl = rpTradePaymentManagerService
					.getMerchantNotifyMessage(payWayCode, notifyMap);
			LOG.info("发送商户消息日志：" + merchantNotifyUrl);
			String notifyMessageId = StringUtil.get32UUID();
			RpTransactionMessage notifyTransactionMessage = new RpTransactionMessage(
					notifyMessageId, merchantNotifyUrl,
					NotifyDestinationNameEnum.MERCHANT_NOTIFY.name());
			rpTransactionMessageService
					.directSendMessage(notifyTransactionMessage);
		}
		if (!StringUtil.isEmpty(printStr)) {
			httpServletResponse.getWriter().print(printStr);
		}
	}

	/**
	 * 页面同步回调.
	 * 
	 * @param payWayCode
	 * @param httpServletRequest
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/result/{payWayCode}")
	public String result(@PathVariable("payWayCode") String payWayCode,
			HttpServletRequest httpServletRequest, Model model)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		Map requestParams = httpServletRequest.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			valueStr = new String(valueStr);
			resultMap.put(name, valueStr);
		}
		OrderPayResultVo scanPayByResult = rpTradePaymentManagerService
				.completeScanPayByResult(payWayCode, resultMap);
		model.addAttribute("scanPayByResult", scanPayByResult);
		return "PayResult";
	}
}
