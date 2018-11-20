package t5750.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import t5750.dubbo.service.DependencyDubboConsumerService;
import t5750.dubbo.service.DirectDubboConsumerService;
import t5750.dubbo.service.SampleDubboConsumerService;

@SpringBootApplication
public class DubboConsumerApplication {
	public static void main(String[] args) {
		ConfigurableApplicationContext run = SpringApplication.run(
				DubboConsumerApplication.class, args);
		SampleDubboConsumerService sampleDubboConsumerService = run
				.getBean(SampleDubboConsumerService.class);
		sampleDubboConsumerService.printHello();
		sampleDubboConsumerService.printUsers();
		System.out.println("---------------------------------------");
		DependencyDubboConsumerService dependencyDubboConsumerService = run
				.getBean(DependencyDubboConsumerService.class);
		dependencyDubboConsumerService.printDependency();
		System.out.println("---------------------------------------");
		DirectDubboConsumerService directDubboConsumerService = run
				.getBean(DirectDubboConsumerService.class);
		directDubboConsumerService.printDirect();
	}
}
