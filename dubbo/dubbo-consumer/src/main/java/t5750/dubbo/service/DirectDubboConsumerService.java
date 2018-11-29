package t5750.dubbo.service;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Reference;

@Component
public class DirectDubboConsumerService {
	@Reference(version = "1.0.0", url = "dubbo://localhost:20880")
	private DirectService directService;

	public String printDirect() {
		String result = directService.direct();
		System.out.println(result);
		return result;
	}
}