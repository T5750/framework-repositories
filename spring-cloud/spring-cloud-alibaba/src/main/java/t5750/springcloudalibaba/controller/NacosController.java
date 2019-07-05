package t5750.springcloudalibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/nacos")
public class NacosController {
	@Value("${user.name}")
	private String userName;
	@Value("${user.age}")
	private int age;

	@RequestMapping("/user")
	public String user() {
		return "Hello Nacos Config! Hello " + userName + " " + age + "!";
	}
}