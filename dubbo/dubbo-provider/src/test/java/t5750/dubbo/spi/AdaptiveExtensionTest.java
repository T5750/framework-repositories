package t5750.dubbo.spi;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import t5750.dubbo.spi.service.WheelMaker;
import t5750.dubbo.spi.service.impl.RaceCarMaker;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

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
		String protocol = "dubbo";
		String host = "127.0.0.1";
		int port = 8080;
		String path = "XxxService";
		Map<String, String> parameters = new HashMap<>(2);
		parameters.put("wheel.maker", "michelinWheelMaker");
		// dubbo://127.0.0.1:8080/XxxService?wheel.maker=michelinWheelMaker
		URL url = new URL(protocol, host, port, path, parameters);
		raceCarMaker.makeCar(url);
	}
}
