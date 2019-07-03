package t5750.springcloudalibaba.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

@RestController
public class SentinelController {
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping(value = "/hello")
	@SentinelResource("hello")
	public String hello() {
		return "Hello Sentinel";
	}

	@RequestMapping(value = "/template", method = RequestMethod.GET)
	public String template() {
		return restTemplate.getForObject("http://www.taobao.com/test",
				String.class);
	}
}