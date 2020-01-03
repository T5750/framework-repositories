package t5750.spring;

import java.lang.reflect.Proxy;

import t5750.spring.service.SmsService;
import t5750.spring.service.impl.MoneyCountInvocationHandler;
import t5750.spring.service.impl.SmsServiceImpl;

/**
 * JDK动态代理
 */
public class SmsJdkProxyTest {
	public static void main(String[] args) {
		SmsService smsService = new SmsServiceImpl();
		// newProxyInstance(ClassLoader loader, Class<?>[] interfaces,
		// InvocationHandler h)
		smsService = (SmsService) Proxy.newProxyInstance(
				SmsJdkProxyTest.class.getClassLoader(),
				new Class[] { SmsService.class },
				new MoneyCountInvocationHandler(smsService));
		smsService.sendMessage();
		smsService.sendMessage();
		smsService.sendMessage();
	}
}
