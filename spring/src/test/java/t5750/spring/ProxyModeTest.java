package t5750.spring;

import t5750.spring.service.Coffee;
import t5750.spring.service.impl.CoffeeWithSugar;

/**
 * 代理模式
 */
public class ProxyModeTest {
	public static void main(String[] args) {
		Coffee coffee = new CoffeeWithSugar();
		coffee.printMaterial();
	}
}
