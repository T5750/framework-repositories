package t5750.pay.service.message.api.impl;

import java.util.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import t5750.pay.common.core.enums.PublicEnum;
import t5750.pay.common.core.page.PageBean;
import t5750.pay.common.core.page.PageParam;
import t5750.pay.common.core.utils.PublicConfigUtil;
import t5750.pay.common.core.utils.StringUtil;
import t5750.pay.service.message.api.RpTransactionMessageService;
import t5750.pay.service.message.dao.RpTransactionMessageDao;
import t5750.pay.service.message.entity.RpTransactionMessage;
import t5750.pay.service.message.enums.MessageStatusEnum;
import t5750.pay.service.message.exceptions.MessageBizException;

/**
 * <b>功能说明: </b>
 */
@Service("rpTransactionMessageService")
public class RpTransactionMessageServiceImpl
		implements RpTransactionMessageService {
	private static final Log log = LogFactory
			.getLog(RpTransactionMessageServiceImpl.class);
	@Autowired
	private RpTransactionMessageDao rpTransactionMessageDao;
	@Autowired
	private JmsTemplate notifyJmsTemplate;

	public int saveMessageWaitingConfirm(RpTransactionMessage message) {
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "保存的消息为空");
		}
		if (StringUtil.isEmpty(message.getConsumerQueue())) {
			throw new MessageBizException(
					MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,
					"消息的消费队列不能为空 ");
		}
		message.setEditTime(new Date());
		message.setStatus(MessageStatusEnum.WAITING_CONFIRM.name());
		message.setAreadlyDead(PublicEnum.NO.name());
		message.setMessageSendTimes(0);
		return rpTransactionMessageDao.insert(message);
	}

	public void confirmAndSendMessage(String messageId) {
		final RpTransactionMessage message = getMessageByMessageId(messageId);
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
		}
		message.setStatus(MessageStatusEnum.SENDING.name());
		message.setEditTime(new Date());
		rpTransactionMessageDao.update(message);
		notifyJmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.getMessageBody());
			}
		});
	}

	public int saveAndSendMessage(final RpTransactionMessage message) {
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "保存的消息为空");
		}
		if (StringUtil.isEmpty(message.getConsumerQueue())) {
			throw new MessageBizException(
					MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,
					"消息的消费队列不能为空 ");
		}
		message.setStatus(MessageStatusEnum.SENDING.name());
		message.setAreadlyDead(PublicEnum.NO.name());
		message.setMessageSendTimes(0);
		message.setEditTime(new Date());
		int result = rpTransactionMessageDao.insert(message);
		notifyJmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.getMessageBody());
			}
		});
		return result;
	}

	public void directSendMessage(final RpTransactionMessage message) {
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "保存的消息为空");
		}
		if (StringUtil.isEmpty(message.getConsumerQueue())) {
			throw new MessageBizException(
					MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,
					"消息的消费队列不能为空 ");
		}
		notifyJmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.getMessageBody());
			}
		});
	}

	public void reSendMessage(final RpTransactionMessage message) {
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "保存的消息为空");
		}
		if (StringUtil.isEmpty(message.getConsumerQueue())) {
			throw new MessageBizException(
					MessageBizException.MESSAGE_CONSUMER_QUEUE_IS_NULL,
					"消息的消费队列不能为空 ");
		}
		message.addSendTimes();
		message.setEditTime(new Date());
		rpTransactionMessageDao.update(message);
		notifyJmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.getMessageBody());
			}
		});
	}

	public void reSendMessageByMessageId(String messageId) {
		final RpTransactionMessage message = getMessageByMessageId(messageId);
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
		}
		int maxTimes = Integer
				.valueOf(PublicConfigUtil.readConfig("message.max.send.times"));
		if (message.getMessageSendTimes() >= maxTimes) {
			message.setAreadlyDead(PublicEnum.YES.name());
		}
		message.setEditTime(new Date());
		message.setMessageSendTimes(message.getMessageSendTimes() + 1);
		rpTransactionMessageDao.update(message);
		notifyJmsTemplate.setDefaultDestinationName(message.getConsumerQueue());
		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(message.getMessageBody());
			}
		});
	}

	public void setMessageToAreadlyDead(String messageId) {
		RpTransactionMessage message = getMessageByMessageId(messageId);
		if (message == null) {
			throw new MessageBizException(
					MessageBizException.SAVA_MESSAGE_IS_NULL, "根据消息id查找的消息为空");
		}
		message.setAreadlyDead(PublicEnum.YES.name());
		message.setEditTime(new Date());
		rpTransactionMessageDao.update(message);
	}

	public RpTransactionMessage getMessageByMessageId(String messageId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("messageId", messageId);
		return rpTransactionMessageDao.getBy(paramMap);
	}

	public void deleteMessageByMessageId(String messageId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("messageId", messageId);
		rpTransactionMessageDao.delete(paramMap);
	}

	@SuppressWarnings("unchecked")
	public void reSendAllDeadMessageByQueueName(String queueName,
			int batchSize) {
		log.info("==>reSendAllDeadMessageByQueueName");
		int numPerPage = 1000;
		if (batchSize > 0 && batchSize < 100) {
			numPerPage = 100;
		} else if (batchSize > 100 && batchSize < 5000) {
			numPerPage = batchSize;
		} else if (batchSize > 5000) {
			numPerPage = 5000;
		} else {
			numPerPage = 1000;
		}
		int pageNum = 1;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("consumerQueue", queueName);
		paramMap.put("areadlyDead", PublicEnum.YES.name());
		paramMap.put("listPageSortType", "ASC");
		Map<String, RpTransactionMessage> messageMap = new HashMap<String, RpTransactionMessage>();
		List<Object> recordList = new ArrayList<Object>();
		int pageCount = 1;
		PageBean pageBean = rpTransactionMessageDao
				.listPage(new PageParam(pageNum, numPerPage), paramMap);
		recordList = pageBean.getRecordList();
		if (recordList == null || recordList.isEmpty()) {
			log.info("==>recordList is empty");
			return;
		}
		pageCount = pageBean.getTotalPage();
		for (final Object obj : recordList) {
			final RpTransactionMessage message = (RpTransactionMessage) obj;
			messageMap.put(message.getMessageId(), message);
		}
		for (pageNum = 2; pageNum <= pageCount; pageNum++) {
			pageBean = rpTransactionMessageDao
					.listPage(new PageParam(pageNum, numPerPage), paramMap);
			recordList = pageBean.getRecordList();
			if (recordList == null || recordList.isEmpty()) {
				break;
			}
			for (final Object obj : recordList) {
				final RpTransactionMessage message = (RpTransactionMessage) obj;
				messageMap.put(message.getMessageId(), message);
			}
		}
		recordList = null;
		pageBean = null;
		for (Map.Entry<String, RpTransactionMessage> entry : messageMap
				.entrySet()) {
			final RpTransactionMessage message = entry.getValue();
			message.setEditTime(new Date());
			message.setMessageSendTimes(message.getMessageSendTimes() + 1);
			rpTransactionMessageDao.update(message);
			notifyJmsTemplate
					.setDefaultDestinationName(message.getConsumerQueue());
			notifyJmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session)
						throws JMSException {
					return session.createTextMessage(message.getMessageBody());
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	public PageBean<RpTransactionMessage> listPage(PageParam pageParam,
			Map<String, Object> paramMap) {
		return rpTransactionMessageDao.listPage(pageParam, paramMap);
	}
}
