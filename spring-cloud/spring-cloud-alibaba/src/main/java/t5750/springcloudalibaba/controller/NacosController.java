package t5750.springcloudalibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
@RequestMapping("/nacos")
public class NacosController {
	@Value("${user.name}")
	private String userName;
	@Value("${user.age}")
	private int age;
	@Value("${nacos.version}")
	private String version;

	/**
	 * for Nacos config
	 */
	@RequestMapping("/user")
	public String user() {
		return "Hello Nacos Config! Hello " + userName + " " + age + "!";
	}

	/**
	 * for Nacos service-provider
	 */
	@RequestMapping(value = "/echo/{string}", method = RequestMethod.GET)
	public String echo(@PathVariable String string) {
		return "Hello Nacos Discovery " + string;
	}

	/**
	 * by spring.cloud.nacos.config.shared-dataids
	 */
	@RequestMapping(value = "/version")
	public String version() {
		return "Nacos version " + version;
	}
}