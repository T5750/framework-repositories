package t5750.tcctransaction.spring.support;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import t5750.tcctransaction.support.BeanFactory;

/**
 * TCC应用上下文.
 */
@Component
public class TccApplicationContext implements BeanFactory,
		ApplicationContextAware {
	private ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public Object getBean(Class<?> aClass) {
		return this.applicationContext.getBean(aClass);
	}
}
