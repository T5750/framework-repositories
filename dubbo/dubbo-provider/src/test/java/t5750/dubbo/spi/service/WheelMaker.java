package t5750.dubbo.spi.service;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface WheelMaker {
	/**
	 * Wheel -> Object
	 */
	Object makeWheel(URL url);
}