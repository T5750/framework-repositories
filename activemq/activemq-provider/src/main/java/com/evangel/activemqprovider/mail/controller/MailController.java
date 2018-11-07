package com.evangel.activemqprovider.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evangel.activemqprovider.mail.mq.MQProducer;
import com.evangel.activemqprovider.mail.util.MailUtil;

@RestController
@RequestMapping("/mail")
public class MailController {
	@Autowired
	private MQProducer mqProducer;

	@RequestMapping("/send")
	public String send() {
		String result = MailUtil.send(mqProducer);
		return result;
	}
}
