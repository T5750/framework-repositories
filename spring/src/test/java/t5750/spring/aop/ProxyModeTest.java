package t5750.spring.aop;

import t5750.spring.aop.service.Coffee;
import t5750.spring.aop.service.impl.CoffeeWithSugar;

/**
 * 代理模式
 */
public class ProxyModeTest {
	public static void main(String[] args) {
		Coffee coffee = new CoffeeWithSugar();
		coffee.printMaterial();
	}
}
