package t5750.spring.aop.service.impl;

import t5750.spring.aop.service.Coffee;

public class BitterCoffee implements Coffee {
	@Override
	public void printMaterial() {
		System.out.println("咖啡");
	}
}