package com.evangel.activemqprovider.mail.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.evangel.activemqprovider.mail.entity.Mail;

/**
 *
 */
@Service
public class MQProducer {
	private JmsTemplate jmsTemplate;
	@Autowired
	private Queue queueMail;

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	@Autowired
	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * <B>方法名称：</B>发送邮件信息对象<BR>
	 * <B>概要说明：</B>发送邮件信息对象<BR>
	 * 
	 * @param mail
	 */
	public void sendMessage(final Mail mail) {
		jmsTemplate.setDefaultDestination(queueMail);
		jmsTemplate.send(new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(JSONObject.toJSONString(mail));
			}
		});
	}
}