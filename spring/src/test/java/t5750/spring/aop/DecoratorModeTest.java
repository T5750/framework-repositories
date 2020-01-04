package t5750.spring.aop;

import t5750.spring.aop.service.Coffee;
import t5750.spring.aop.service.impl.BitterCoffee;
import t5750.spring.aop.service.impl.SugarDecorator;

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
