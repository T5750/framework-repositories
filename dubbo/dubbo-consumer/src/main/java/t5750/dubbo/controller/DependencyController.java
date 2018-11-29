package t5750.dubbo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.dubbo.service.DependencyDubboConsumerService;

@RestController
@RequestMapping("/dependency")
public class DependencyController {
	@Resource
	private DependencyDubboConsumerService dependencyDubboConsumerService;

	@RequestMapping("/print")
	public String print() {
		return dependencyDubboConsumerService.printDependency();
	}
}
