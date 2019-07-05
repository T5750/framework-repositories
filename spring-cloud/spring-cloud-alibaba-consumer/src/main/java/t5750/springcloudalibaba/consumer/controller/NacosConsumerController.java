package t5750.springcloudalibaba.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import t5750.springcloudalibaba.consumer.service.EchoService;
import t5750.springcloudalibaba.consumer.util.Globals;

@RestController
@RequestMapping("/nacos")
public class NacosConsumerController {
	private final RestTemplate restTemplate;
	@Autowired
	private EchoService echoService;

	@Autowired
	public NacosConsumerController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@RequestMapping(value = "/echo/{str}", method = RequestMethod.GET)
	public String echo(@PathVariable String str) {
		return restTemplate.getForObject("http://"
				+ Globals.SPRING_CLOUD_ALIBABA + "/nacos/echo/" + str,
				String.class);
	}

	@RequestMapping(value = "/echo-feign/{str}", method = RequestMethod.GET)
	public String feign(@PathVariable String str) {
		return echoService.echo(str);
	}
}
