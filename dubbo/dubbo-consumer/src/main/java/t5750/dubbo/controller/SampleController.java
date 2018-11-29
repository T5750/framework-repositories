package t5750.dubbo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.dubbo.domain.User;
import t5750.dubbo.service.SampleDubboConsumerService;

@RestController
@RequestMapping("/sample")
public class SampleController {
	@Resource
	private SampleDubboConsumerService sampleDubboConsumerService;

	@RequestMapping("/hello")
	public String printHello() {
		return sampleDubboConsumerService.printHello();
	}

	@RequestMapping("/users")
	public List<User> printUsers() {
		return sampleDubboConsumerService.printUsers();
	}
}
