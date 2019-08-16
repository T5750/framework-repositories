package t5750.springboot.beanlifecycle.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * Spring同样可以针对容器中的所有Bean，或者某些Bean定制初始化过程，只需提供一个实现BeanPostProcessor接口的类即可
 */
@Component
public class CustomerBeanPostProcessor implements BeanPostProcessor {
	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof BeanLifeCycleService) {
			System.out
					.println("执行BeanPostProcessor的postProcessBeforeInitialization方法,beanName="
							+ beanName);
		}
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof BeanLifeCycleService) {
			System.out
					.println("执行BeanPostProcessor的postProcessAfterInitialization方法,beanName="
							+ beanName);
		}
		return bean;
	}
}
