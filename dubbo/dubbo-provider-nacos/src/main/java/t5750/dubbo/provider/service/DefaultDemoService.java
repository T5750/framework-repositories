package t5750.dubbo.provider.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import t5750.dubbo.api.DemoService;

@Service(version = "${demo.service.version}")
public class DefaultDemoService implements DemoService {
	/**
	 * The default value of ${dubbo.application.name} is
	 * ${spring.application.name}
	 */
	@Value("${dubbo.application.name}")
	private String serviceName;

	@Override
	public String sayHello(String name) {
		return String.format("[%s] : Hello, %s", serviceName, name);
	}
}