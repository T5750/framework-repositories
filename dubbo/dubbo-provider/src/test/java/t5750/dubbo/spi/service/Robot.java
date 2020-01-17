package t5750.dubbo.spi.service;

import com.alibaba.dubbo.common.extension.SPI;

@SPI
public interface Robot {
	void sayHello();
}