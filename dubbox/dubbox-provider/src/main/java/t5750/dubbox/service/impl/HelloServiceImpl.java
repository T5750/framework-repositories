package t5750.dubbox.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import t5750.dubbox.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {
	public String hello(String name) {
		return "hello " + name;
	}
}
