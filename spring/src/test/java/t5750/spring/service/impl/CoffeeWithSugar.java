package t5750.spring.service.impl;

import t5750.spring.service.Coffee;

public class CoffeeWithSugar implements Coffee {
	private final Coffee coffee;

	public CoffeeWithSugar() {
		this.coffee = new BitterCoffee();
	}

	@Override
	public void printMaterial() {
		System.out.println("糖");
		this.coffee.printMaterial();
	}
}