package t5750.springcloudalibaba.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.*;
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
	private LoadBalancerClient loadBalancerClient;

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

	/**
	 * loadBalance by spring-cloud-commons
	 */
	@GetMapping("/loadBalance")
	public String loadBalance() {
		ServiceInstance serviceInstance = loadBalancerClient
				.choose(Globals.SPRING_CLOUD_ALIBABA);
		String url = serviceInstance.getUri() + "/nacos/user";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		return "Invoke : " + url + ", return : " + result;
	}
}
