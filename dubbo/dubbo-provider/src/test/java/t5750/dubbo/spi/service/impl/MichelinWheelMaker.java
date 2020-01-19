package t5750.dubbo.spi.service.impl;

import java.math.BigDecimal;

import t5750.dubbo.spi.service.WheelMaker;

import com.alibaba.dubbo.common.URL;

public class MichelinWheelMaker implements WheelMaker {
	@Override
	public Object makeWheel(URL url) {
		System.out.println("Hello, I am MichelinWheel.");
		return BigDecimal.ONE;
	}
}
