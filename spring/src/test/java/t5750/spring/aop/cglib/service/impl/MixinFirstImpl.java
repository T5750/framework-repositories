package t5750.spring.aop.cglib.service.impl;

import t5750.spring.aop.cglib.service.MixinFirst;

public class MixinFirstImpl implements MixinFirst {
	@Override
	public String first() {
		return "first behaviour";
	}
}