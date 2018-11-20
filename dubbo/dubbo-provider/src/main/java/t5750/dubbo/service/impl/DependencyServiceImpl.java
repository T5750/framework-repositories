package t5750.dubbo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import t5750.dubbo.service.DependencyService;
import t5750.dubbo.service.SampleService;

import com.alibaba.dubbo.config.annotation.Service;

@Service(version = "1.0.0")
public class DependencyServiceImpl implements DependencyService {
	@Autowired
	private SampleService sampleService;

	@Override
	public String dependency() {
		// 这里我们可能需要调用SampleService，也可能不需要...
		System.out.println(sampleService.sayHello("jack"));
		return "dependency exec";
	}
}
