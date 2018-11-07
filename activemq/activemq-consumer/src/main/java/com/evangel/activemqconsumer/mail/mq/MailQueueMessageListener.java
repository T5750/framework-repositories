package com.evangel.activemqconsumer.mail.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.evangel.activemqconsumer.mail.entity.Mail;
import com.evangel.activemqconsumer.mail.service.MailService;

/**
 *
 */
@Component
public class MailQueueMessageListener {
	@Autowired
	private MailService mailService;

	@JmsListener(destination = "mailqueue", containerFactory = "jmsListenerContainerQueue")
	public void receive(String text) {
		System.out.println("收到消息：" + text);
		// 转换成相应的对象
		Mail mail = JSONObject.parseObject(text, Mail.class);
		if (null != mail) {
			try {
				// 执行发送业务
				mailService.mailSend(mail);
			} catch (Exception e) {
				// 发送异常，重新放回队列
				// jmsTemplate.send(mailQueue, new MessageCreator() {
				// @Override
				// public Message createMessage(Session session) throws
				// JMSException {
				// return session.createTextMessage(ms);
				// }
				// });
				e.printStackTrace();
			}
		}
	}
}