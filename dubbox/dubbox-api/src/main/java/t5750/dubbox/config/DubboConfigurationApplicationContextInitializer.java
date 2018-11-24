package t5750.dubbox.config;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import com.alibaba.dubbo.config.spring.AnnotationBean;

public class DubboConfigurationApplicationContextInitializer implements
		ApplicationContextInitializer<ConfigurableApplicationContext> {
	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		Environment env = applicationContext.getEnvironment();
		String scan = env.getProperty("spring.dubbo.scan");
		if (scan != null) {
			AnnotationBean scanner = BeanUtils
					.instantiate(AnnotationBean.class);
			scanner.setPackage(scan);
			scanner.setApplicationContext(applicationContext);
			applicationContext.addBeanFactoryPostProcessor(scanner);
			applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
			applicationContext.getBeanFactory()
					.registerSingleton("annotationBean", scanner);
		}
	}
}
