package t5750.dubbo.spi;

import org.junit.Before;
import org.junit.Test;

import t5750.dubbo.spi.service.WheelMaker;
import t5750.dubbo.spi.service.impl.RaceCarMaker;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

/**
 * 自适应拓展
 */
public class AdaptiveExtensionTest {
	private RaceCarMaker raceCarMaker;

	@Before
	public void setup() {
		ExtensionLoader<WheelMaker> extensionLoader = ExtensionLoader
				.getExtensionLoader(WheelMaker.class);
		WheelMaker wheelMaker = extensionLoader
				.getExtension("adaptiveWheelMaker");
		raceCarMaker = new RaceCarMaker();
		raceCarMaker.setWheelMaker(wheelMaker);
	}

	@Test
	public void makeCar() throws Exception {
		URL url = URL
				.valueOf("dubbo://127.0.0.1:8080/XxxService?wheel.maker=michelinWheelMaker");
		raceCarMaker.makeCar(url);
	}
}
