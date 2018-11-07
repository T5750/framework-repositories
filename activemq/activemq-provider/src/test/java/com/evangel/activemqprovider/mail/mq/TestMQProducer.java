package com.evangel.activemqprovider.mail.mq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.evangel.activemqprovider.ActiveMQProviderApplication;
import com.evangel.activemqprovider.mail.util.MailUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActiveMQProviderApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMQProducer {
	@Autowired
	private MQProducer mqProducer;

	@Test
	public void send() {
		String result = MailUtil.send(mqProducer);
		System.out.println(result);
	}
}
