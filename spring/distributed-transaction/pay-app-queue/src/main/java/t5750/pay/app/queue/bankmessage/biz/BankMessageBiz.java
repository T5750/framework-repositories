package t5750.pay.app.queue.bankmessage.biz;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import t5750.pay.service.message.api.RpTransactionMessageService;
import t5750.pay.service.trade.api.RpTradePaymentManagerService;

/**
 * <b>功能说明: </b>
 */
public class BankMessageBiz {
	private static final Log LOG = LogFactory.getLog(BankMessageBiz.class);
	@Autowired
	private RpTradePaymentManagerService rpTradePaymentManagerService;
	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;

	public void completePay(Map<String, String> notifyMessageMap) {
		String messageId = notifyMessageMap.get("messageId");
		String payWayCode = notifyMessageMap.get("payWayCode");
		// 调用业务方法,完成交易
		try {
			rpTradePaymentManagerService.completeScanPay(payWayCode,
					notifyMessageMap);
			// 删除消息
			rpTransactionMessageService.deleteMessageByMessageId(messageId);
		} catch (Exception e) {
			LOG.error("完成支付结果异常:", e);
		}
	}

	public RpTradePaymentManagerService getRpTradePaymentManagerService() {
		return rpTradePaymentManagerService;
	}

	public void setRpTradePaymentManagerService(
			RpTradePaymentManagerService rpTradePaymentManagerService) {
		this.rpTradePaymentManagerService = rpTradePaymentManagerService;
	}

	public RpTransactionMessageService getRpTransactionMessageService() {
		return rpTransactionMessageService;
	}

	public void setRpTransactionMessageService(
			RpTransactionMessageService rpTransactionMessageService) {
		this.rpTransactionMessageService = rpTransactionMessageService;
	}
}
