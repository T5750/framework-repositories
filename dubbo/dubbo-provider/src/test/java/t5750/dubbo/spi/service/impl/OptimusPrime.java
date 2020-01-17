package t5750.dubbo.spi.service.impl;

import t5750.dubbo.spi.service.Robot;

public class OptimusPrime implements Robot {
	@Override
	public void sayHello() {
		System.out.println("Hello, I am Optimus Prime.");
	}
}