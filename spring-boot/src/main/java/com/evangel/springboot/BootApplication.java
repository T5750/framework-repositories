package com.evangel.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.evangel.springboot.beanlifecycle.service.BeanLifeCycleService;

@SpringBootApplication
public class BootApplication {
	public static void main(String[] args) {
		SpringApplication.run(BootApplication.class, args);
	}

	/**
	 * Spring Bean的生命周期
	 */
	@Bean(initMethod = "initMethod", destroyMethod = "destroyMethod")
	public BeanLifeCycleService beanLifeCycleService() {
		BeanLifeCycleService beanLifeCycleService = new BeanLifeCycleService();
		beanLifeCycleService.setName("beanLifeCycle");
		return beanLifeCycleService;
	}
}
