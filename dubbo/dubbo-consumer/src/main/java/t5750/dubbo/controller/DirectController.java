package t5750.dubbo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import t5750.dubbo.service.DirectDubboConsumerService;

@RestController
@RequestMapping("/direct")
public class DirectController {
	@Resource
	private DirectDubboConsumerService directDubboConsumerService;

	@RequestMapping("/print")
	public String print() {
		return directDubboConsumerService.printDirect();
	}
}
