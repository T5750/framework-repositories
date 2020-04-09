package t5750.springcloud.dubboserver.service;

import org.apache.dubbo.config.annotation.Service;

import t5750.dubbo.api.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
	@Override
	public String sayHello(String message) {
		return "[echo] Hello, " + message;
	}
}