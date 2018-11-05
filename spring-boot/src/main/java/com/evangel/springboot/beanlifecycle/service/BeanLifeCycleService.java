package com.evangel.springboot.beanlifecycle.service;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.*;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Service;

/**
 * Spring Bean的生命周期
 */
@Service
public class BeanLifeCycleService implements InitializingBean, DisposableBean,
		ApplicationContextAware, ApplicationEventPublisherAware,
		BeanClassLoaderAware, BeanFactoryAware, BeanNameAware,
		EnvironmentAware, ImportAware, ResourceLoaderAware {
	private String name;

	public String getName() {
		return name;
	}

	public BeanLifeCycleService setName(String name) {
		System.out.println("BeanLifeCycleService中利用set方法设置属性值");
		this.name = name;
		return this;
	}

	public BeanLifeCycleService() {
		System.out.println("调用BeanLifeCycleService无参构造函数");
	}

	@Override
	public void afterPropertiesSet() {
		System.out.println("执行InitializingBean接口的afterPropertiesSet方法");
	}

	@PreDestroy
	public void preDestroy() {
		System.out.println("调用preDestroy注解标注的方法");
	}

	@Override
	public void destroy() {
		System.out.println("执行DisposableBean接口的destroy方法");
	}

	/**
	 * 通过<bean>的destroy-method属性指定的销毁方法
	 */
	public void destroyMethod() {
		System.out.println("执行配置的destroy-method");
	}

	/**
	 * 通过<bean>的init-method属性指定的初始化方法
	 */
	public void initMethod() {
		System.out.println("执行配置的init-method");
	}

	@PostConstruct
	public void initPostConstruct() {
		System.out.println("调用PostConstruct注解标注的方法");
	}

	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		System.out.println("调用setBeanClassLoader,ClassLoader Name = "
				+ classLoader.getClass().getName());
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		System.out
				.println("调用setBeanFactory,setBeanFactory:: BeanLifeCycle bean singleton="
						+ beanFactory.isSingleton("beanLifeCycleService"));
	}

	@Override
	public void setBeanName(String s) {
		System.out.println("调用setBeanName:: Bean Name defined in context=" + s);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		System.out.println("调用setApplicationContext:: Bean Definition Names="
				+ Arrays.toString(applicationContext.getBeanDefinitionNames()));
	}

	@Override
	public void setApplicationEventPublisher(
			ApplicationEventPublisher applicationEventPublisher) {
		System.out.println("调用setApplicationEventPublisher");
	}

	@Override
	public void setEnvironment(Environment environment) {
		System.out.println("调用setEnvironment");
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		Resource resource = resourceLoader
				.getResource("classpath:application.properties");
		System.out.println("调用setResourceLoader:: Resource File Name="
				+ resource.getFilename());
	}

	@Override
	public void setImportMetadata(AnnotationMetadata annotationMetadata) {
		System.out.println("调用setImportMetadata");
	}
}
