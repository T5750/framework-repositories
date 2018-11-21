package t5750.activemqprovider.mail.util;

import t5750.activemqprovider.mail.entity.Mail;
import t5750.activemqprovider.mail.mq.MQProducer;

/**
 *
 */
public class MailUtil {
	/**
	 * 发送到mq
	 */
	public static String send(MQProducer mqProducer) {
		// 发送超过4份邮件会报错
		int mailCounter = 4;
		for (int i = 0; i < mailCounter; i++) {
			Mail mail = new Mail();
			mail.setTo("username@qq.com");
			mail.setSubject("异步发送邮件" + i);
			mail.setContent("Hi, This is a message!");
			mqProducer.sendMessage(mail);
		}
		String result = "mails 发送成功";
		return result;
	}
}
