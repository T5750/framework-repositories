package t5750.dubbo.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class DependencyDubboConsumerService {
	@Reference(version = "1.0.0")
	DependencyService dependencyService;

	public void printDependency() {
		String result = dependencyService.dependency();
		System.out.println(result);
	}
}