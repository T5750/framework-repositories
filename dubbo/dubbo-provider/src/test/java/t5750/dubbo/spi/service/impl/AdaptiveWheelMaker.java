package t5750.dubbo.spi.service.impl;

import t5750.dubbo.spi.service.WheelMaker;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class AdaptiveWheelMaker implements WheelMaker {
	@Override
	public Object makeWheel(URL url) {
		if (url == null) {
			throw new IllegalArgumentException("url == null");
		}
		// 1.从 URL 中获取 WheelMaker 名称
		String wheelMakerName = url.getParameter("wheel.maker");
		if (wheelMakerName == null) {
			throw new IllegalArgumentException("wheelMakerName == null");
		}
		// 2.通过 SPI 加载具体的 WheelMaker
		WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(
				WheelMaker.class).getExtension(wheelMakerName);
		// 3.调用目标方法
		return wheelMaker.makeWheel(url);
	}
}