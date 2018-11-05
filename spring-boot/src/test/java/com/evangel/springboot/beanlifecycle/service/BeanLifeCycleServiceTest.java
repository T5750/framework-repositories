package com.evangel.springboot.beanlifecycle.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.evangel.springboot.BootApplication;

/**
 * Spring Bean的生命周期
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BootApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeanLifeCycleServiceTest {
	@Autowired
	private BeanLifeCycleService beanLifeCycleService;

	@Test
	public void testBeanLifeCycle() {
		System.out.println("从容器中获取Bean");
		System.out.println("BeanLifeCycle Name="
				+ beanLifeCycleService.getName());
	}
}
