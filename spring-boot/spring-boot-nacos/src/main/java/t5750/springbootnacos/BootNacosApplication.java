package t5750.springbootnacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;

@SpringBootApplication
@NacosPropertySource(dataId = "mysql.properties")
public class BootNacosApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootNacosApplication.class, args);
	}
}
