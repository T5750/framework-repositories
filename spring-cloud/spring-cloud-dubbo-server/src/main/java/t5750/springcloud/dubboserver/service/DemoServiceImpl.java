package t5750.springcloud.dubboserver.service;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import t5750.dubbo.api.DemoService;

@Service
@RestController
public class DemoServiceImpl implements DemoService {
	@Override
	@GetMapping("/echo")
	public String sayHello(@RequestParam String message) {
		return "[echo] Hello, " + message;
	}
}