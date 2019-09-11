package t5750.dubbo.provider;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableAutoConfiguration
public class DubboProviderNacos {
	public static void main(String[] args) {
		new SpringApplicationBuilder(DubboProviderNacos.class).run(args);
	}
}
