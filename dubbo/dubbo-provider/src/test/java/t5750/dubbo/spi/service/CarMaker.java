package t5750.dubbo.spi.service;

import com.alibaba.dubbo.common.URL;

public interface CarMaker {
	/**
	 * Car -> Object
	 */
	Object makeCar(URL url);
}
