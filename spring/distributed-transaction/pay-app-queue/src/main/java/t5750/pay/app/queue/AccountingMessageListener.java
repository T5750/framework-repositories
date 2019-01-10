package t5750.pay.app.queue;

import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.SessionAwareMessageListener;

import t5750.pay.common.core.exception.BizException;
import t5750.pay.service.accounting.api.RpAccountingVoucherService;
import t5750.pay.service.accounting.entity.RpAccountingVoucher;
import t5750.pay.service.message.api.RpTransactionMessageService;

import com.alibaba.fastjson.JSONObject;

public class AccountingMessageListener implements
		SessionAwareMessageListener<Message> {
	private static final Log LOG = LogFactory
			.getLog(AccountingMessageListener.class);
	/**
	 * 会计队列模板(由Spring创建并注入进来)
	 */
	@Autowired
	private JmsTemplate notifyJmsTemplate;
	@Autowired
	private RpAccountingVoucherService rpAccountingVoucherService;
	@Autowired
	private RpTransactionMessageService rpTransactionMessageService;

	@Override
	public synchronized void onMessage(Message message, Session session) {
		RpAccountingVoucher param = null;
		String strMessage = null;
		try {
			ActiveMQTextMessage objectMessage = (ActiveMQTextMessage) message;
			strMessage = objectMessage.getText();
			LOG.info("strMessage1 accounting:" + strMessage);
			param = JSONObject.parseObject(strMessage,
					RpAccountingVoucher.class); // 这里转换成相应的对象还有问题
			if (param == null) {
				LOG.info("param参数为空");
				return;
			}
			int entryType = param.getEntryType();
			double payerChangeAmount = param.getPayerChangeAmount();
			String voucherNo = param.getVoucherNo();
			String payerAccountNo = param.getPayerAccountNo();
			int fromSystem = param.getFromSystem();
			int payerAccountType = 0;
			if (param.getPayerAccountType() != null
					&& !param.getPayerAccountType().equals("")) {
				payerAccountType = param.getPayerAccountType();
			}
			double payerFee = param.getPayerFee();
			String requestNo = param.getRequestNo();
			double bankChangeAmount = param.getBankChangeAmount();
			double receiverChangeAmount = param.getReceiverChangeAmount();
			String receiverAccountNo = param.getReceiverAccountNo();
			String bankAccount = param.getBankAccount();
			String bankChannelCode = param.getBankChannelCode();
			double profit = param.getProfit();
			double income = param.getIncome();
			double cost = param.getCost();
			String bankOrderNo = param.getBankOrderNo();
			int receiverAccountType = 0;
			double payAmount = param.getPayAmount();
			if (param.getReceiverAccountType() != null
					&& !param.getReceiverAccountType().equals("")) {
				receiverAccountType = param.getReceiverAccountType();
			}
			double receiverFee = param.getReceiverFee();
			String remark = param.getRemark();
			rpAccountingVoucherService.createAccountingVoucher(entryType,
					voucherNo, payerAccountNo, receiverAccountNo,
					payerChangeAmount, receiverChangeAmount, income, cost,
					profit, bankChangeAmount, requestNo, bankChannelCode,
					bankAccount, fromSystem, remark, bankOrderNo,
					payerAccountType, payAmount, receiverAccountType, payerFee,
					receiverFee);
			// 删除消息
			rpTransactionMessageService.deleteMessageByMessageId(param
					.getMessageId());
		} catch (BizException e) {
			// 业务异常，不再写会队列
			LOG.error("==>BizException", e);
		} catch (Exception e) {
			// 不明异常不再写会队列
			LOG.error("==>Exception", e);
		}
	}

	public JmsTemplate getNotifyJmsTemplate() {
		return notifyJmsTemplate;
	}

	public void setNotifyJmsTemplate(JmsTemplate notifyJmsTemplate) {
		this.notifyJmsTemplate = notifyJmsTemplate;
	}

	public RpAccountingVoucherService getRpAccountingVoucherService() {
		return rpAccountingVoucherService;
	}

	public void setRpAccountingVoucherService(
			RpAccountingVoucherService rpAccountingVoucherService) {
		this.rpAccountingVoucherService = rpAccountingVoucherService;
	}
}
