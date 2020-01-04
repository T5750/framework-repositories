package t5750.spring.aop;

import t5750.spring.aop.service.SmsService;
import t5750.spring.aop.service.impl.SmsServiceImpl;

public class SmsServiceTest {
	public static void main(String[] args) {
		SmsService smsService = new SmsServiceImpl();
		smsService.sendMessage();
		smsService.sendMessage();
	}
}
