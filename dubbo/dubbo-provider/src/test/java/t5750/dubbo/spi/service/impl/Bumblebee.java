package t5750.dubbo.spi.service.impl;

import t5750.dubbo.spi.service.Robot;

public class Bumblebee implements Robot {
	@Override
	public void sayHello() {
		System.out.println("Hello, I am Bumblebee.");
	}
}