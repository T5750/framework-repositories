package t5750.springboot.beanlifecycle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import t5750.springboot.beanlifecycle.service.BeanLifeCycleService;

/**
 *
 */
@Configuration
public class BeanLifeCycleConfig {
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
