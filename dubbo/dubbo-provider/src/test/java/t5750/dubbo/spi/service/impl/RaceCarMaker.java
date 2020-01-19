package t5750.dubbo.spi.service.impl;

import java.math.BigDecimal;

import t5750.dubbo.spi.service.CarMaker;
import t5750.dubbo.spi.service.WheelMaker;

import com.alibaba.dubbo.common.URL;

public class RaceCarMaker implements CarMaker {
	WheelMaker wheelMaker;

	// 通过 setter 注入 AdaptiveWheelMaker
	public void setWheelMaker(WheelMaker wheelMaker) {
		this.wheelMaker = wheelMaker;
	}

	public Object makeCar(URL url) {
		Object wheel = wheelMaker.makeWheel(url);
		// return new RaceCar(wheel, ...);
		return BigDecimal.ONE;
	}
}