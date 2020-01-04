package t5750.spring.aop.service.impl;

import t5750.spring.aop.service.SmsService;

public class SmsServiceImpl implements SmsService {
	@Override
	public void sendMessage() {
		System.out.println("【AOP】您正在执行重置密码操作，您的验证码为：1234，5分钟内有效，请不要将验证码转发给他人。");
	}
}