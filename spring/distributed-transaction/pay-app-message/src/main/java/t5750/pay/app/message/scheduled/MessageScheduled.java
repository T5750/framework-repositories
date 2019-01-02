package t5750.pay.app.message.scheduled;

/**
 * 消息定时器接口
 */
public interface MessageScheduled {
	/**
	 * 处理状态为“待确认”但已超时的消息.
	 */
	public void handleWaitingConfirmTimeOutMessages();

	/**
	 * 处理状态为“发送中”但超时没有被成功消费确认的消息
	 */
	public void handleSendingTimeOutMessage();
}
