package t5750.springbootjms.config;

import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListenerConfigurer;
import org.springframework.jms.config.JmsListenerEndpointRegistrar;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class JmsListenerConfig implements JmsListenerConfigurer {
	@Override
	public void configureJmsListeners(JmsListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(methodFactory());
	}

	@Bean
	public DefaultMessageHandlerMethodFactory methodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setValidator(validatorFactory());
		// factory.setValidator(new OrderValidator());
		return factory;
	}

	@Bean
	public Validator validatorFactory() {
		LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
		factory.setProviderClass(HibernateValidator.class);
		return factory;
	}
}
