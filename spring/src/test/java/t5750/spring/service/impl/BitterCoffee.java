package t5750.spring.service.impl;

import t5750.spring.service.Coffee;

public class BitterCoffee implements Coffee {
	@Override
	public void printMaterial() {
		System.out.println("咖啡");
	}
}