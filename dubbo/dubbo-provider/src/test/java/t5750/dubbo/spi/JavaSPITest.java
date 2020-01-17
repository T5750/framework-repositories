package t5750.dubbo.spi;

import java.util.ServiceLoader;

import org.junit.Test;

import t5750.dubbo.spi.service.Robot;

public class JavaSPITest {
	@Test
	public void sayHello() throws Exception {
		ServiceLoader<Robot> serviceLoader = ServiceLoader.load(Robot.class);
		System.out.println("Java SPI");
		serviceLoader.forEach(Robot::sayHello);
	}
}