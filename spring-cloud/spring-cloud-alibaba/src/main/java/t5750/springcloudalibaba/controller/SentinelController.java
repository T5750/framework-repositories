package t5750.springcloudalibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import t5750.springcloudalibaba.service.SentinelService;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

@RestController
@RequestMapping("/sentinel")
public class SentinelController {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private SentinelService sentinelService;

	@GetMapping(value = "/helloSentinel")
	@SentinelResource("helloSentinel")
	public String helloSentinel() {
		return "Hello Sentinel";
	}

	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String template() {
		return restTemplate.getForObject("http://www.taobao.com/test",
				String.class);
	}

	@GetMapping(value = "/testHandler")
	public String testHandler() {
		return sentinelService.testHandler(System.currentTimeMillis());
	}

	@GetMapping("/testFallback")
	public String testFallback() {
		return sentinelService.testFallback(System.currentTimeMillis());
	}

	@GetMapping(value = "/datasourceNacos")
	@SentinelResource("datasourceNacos")
	public String datasourceNacos() {
		return "Datasource Nacos";
	}
}