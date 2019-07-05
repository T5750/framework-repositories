package t5750.springcloudalibaba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class NacosRunner implements ApplicationRunner {
	@Value("${user.name}")
	private String userName;
	@Value("${user.age}")
	private int userAge;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("Nacos user.name=" + userName + ", user.age="
				+ userAge);
	}
}