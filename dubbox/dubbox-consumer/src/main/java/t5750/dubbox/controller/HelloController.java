package t5750.dubbox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import t5750.dubbox.service.HelloService;

@Controller
public class HelloController {
	@Reference
	private HelloService helloService;

	@RequestMapping("/hello")
	@ResponseBody
	public String hello(String name) {
		return helloService.hello(name);
	}
}
