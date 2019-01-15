package t5750.tcctransaction.spring.support;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import t5750.tcctransaction.support.BeanFactory;
import t5750.tcctransaction.support.BeanFactoryAdapter;

/**
 * TCC Bean后置处理程序
 */
@Component
public class TccBeanPostProcessor implements
		ApplicationListener<ContextRefreshedEvent> {
	/**
	 * Spring启动时加载.
	 */
	@Override
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		ApplicationContext applicationContext = contextRefreshedEvent
				.getApplicationContext();
		if (applicationContext.getParent() == null) {
			BeanFactoryAdapter.setBeanFactory(applicationContext
					.getBean(BeanFactory.class));
		}
	}
}
