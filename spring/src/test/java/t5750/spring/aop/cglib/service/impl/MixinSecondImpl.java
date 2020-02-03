package t5750.spring.aop.cglib.service.impl;

import t5750.spring.aop.cglib.service.MixinSecond;

public class MixinSecondImpl implements MixinSecond {
	@Override
	public String second() {
		return "second behaviour";
	}
}