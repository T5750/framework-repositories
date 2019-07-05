package t5750.springcloudalibaba.consumer.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import t5750.springcloudalibaba.consumer.util.Globals;

@FeignClient(name = Globals.SPRING_CLOUD_ALIBABA)
public interface EchoService {
	@RequestMapping(value = "/nacos/echo/{str}", method = RequestMethod.GET)
	String echo(@PathVariable("str") String str);
}
