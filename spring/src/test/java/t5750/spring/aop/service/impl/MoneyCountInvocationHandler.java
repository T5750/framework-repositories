package t5750.spring.aop.service.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;

public class MoneyCountInvocationHandler implements InvocationHandler {
	/**
	 * 被代理的目标
	 */
	private final Object target;
	/**
	 * 内部存储钱的总数
	 */
	private BigDecimal moneyCount;

	public MoneyCountInvocationHandler(Object target) {
		this.target = target;
		this.moneyCount = BigDecimal.ZERO;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object result = method.invoke(target, args);
		moneyCount = moneyCount.add(new BigDecimal("0.07"));
		System.out.println("发送短信成功，共花了：" + moneyCount + "元");
		return result;
	}
}