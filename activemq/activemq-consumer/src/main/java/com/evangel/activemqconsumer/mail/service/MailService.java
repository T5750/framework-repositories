package com.evangel.activemqconsumer.mail.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.evangel.activemqconsumer.mail.entity.Mail;

@Service
public class MailService {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private String mailUsername;

	/**
	 * <B>方法名称：</B>发送邮件<BR>
	 * <B>概要说明：</B>发送邮件<BR>
	 *
	 * @param mail
	 */
	@Async
	public void mailSend(final Mail mail) {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(mailUsername);
		simpleMailMessage.setTo(mail.getTo());
		simpleMailMessage.setSubject(mail.getSubject());
		simpleMailMessage.setText(mail.getContent());
		mailSender.send(simpleMailMessage);
	}
}