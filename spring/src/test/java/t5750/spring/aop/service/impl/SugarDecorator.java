package t5750.spring.aop.service.impl;

import t5750.spring.aop.service.Coffee;

/**
 * 糖装饰器，用来给咖啡加糖
 */
public class SugarDecorator implements Coffee {
	/**
	 * 持有的咖啡对象
	 */
	private final Coffee coffee;

	public SugarDecorator(Coffee coffee) {
		this.coffee = coffee;
	}

	@Override
	public void printMaterial() {
		System.out.println("糖");
		this.coffee.printMaterial();
	}
}