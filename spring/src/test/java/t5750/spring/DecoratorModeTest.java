package t5750.spring;

import t5750.spring.service.Coffee;
import t5750.spring.service.impl.BitterCoffee;
import t5750.spring.service.impl.SugarDecorator;

/**
 * 装饰器模式
 */
public class DecoratorModeTest {
	public static void main(String[] args) {
		Coffee coffee = new BitterCoffee();
		coffee = new SugarDecorator(coffee);
		coffee.printMaterial();
	}
}
