package t5750.spring;

import t5750.spring.service.SmsService;
import t5750.spring.service.impl.SmsServiceImpl;

public class SmsServiceTest {
	public static void main(String[] args) {
		SmsService smsService = new SmsServiceImpl();
		smsService.sendMessage();
		smsService.sendMessage();
	}
}
