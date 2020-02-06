package t5750.spring.aop.cglib.domain;

import t5750.spring.aop.cglib.service.DelegatationProvider;

public class SimpleMulticastBean implements DelegatationProvider {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}