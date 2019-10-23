package t5750.dubbox.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;

import t5750.dubbox.service.hello.HelloService;
import t5750.dubbox.service.user.User;

@RestController
public class HelloController {
	@Reference
	private HelloService helloService;

	@RequestMapping("/hello")
	public User hello(String name) {
		return helloService.hello(name);
	}
}
