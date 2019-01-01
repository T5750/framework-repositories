package t5750.pay.service.message.api;

import java.util.Map;

import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.service.message.entity.RpTransactionMessage;
import t5750.pay.service.message.exceptions.MessageBizException;

/**
 * <b>功能说明: </b>
 */
public interface RpTransactionMessageService {
	/**
	 * 预存储消息.
	 */
	public int saveMessageWaitingConfirm(
			RpTransactionMessage rpTransactionMessage)
			throws MessageBizException;

	/**
	 * 确认并发送消息.
	 */
	public void confirmAndSendMessage(String messageId)
			throws MessageBizException;

	/**
	 * 存储并发送消息.
	 */
	public int saveAndSendMessage(RpTransactionMessage rpTransactionMessage)
			throws MessageBizException;

	/**
	 * 直接发送消息.
	 */
	public void directSendMessage(RpTransactionMessage rpTransactionMessage)
			throws MessageBizException;

	/**
	 * 重发消息.
	 */
	public void reSendMessage(RpTransactionMessage rpTransactionMessage)
			throws MessageBizException;

	/**
	 * 根据messageId重发某条消息.
	 */
	public void reSendMessageByMessageId(String messageId)
			throws MessageBizException;

	/**
	 * 将消息标记为死亡消息.
	 */
	public void setMessageToAreadlyDead(String messageId)
			throws MessageBizException;

	/**
	 * 根据消息ID获取消息
	 */
	public RpTransactionMessage getMessageByMessageId(String messageId)
			throws MessageBizException;

	/**
	 * 根据消息ID删除消息
	 */
	public void deleteMessageByMessageId(String messageId)
			throws MessageBizException;

	/**
	 * 重发某个消息队列中的全部已死亡的消息.
	 */
	public void reSendAllDeadMessageByQueueName(String queueName, int batchSize)
			throws MessageBizException;

	/**
	 * 获取分页数据
	 */
	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws MessageBizException;
}
