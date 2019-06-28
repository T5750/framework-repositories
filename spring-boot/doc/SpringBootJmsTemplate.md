# Spring Boot JMSTemplate

## JMSTemplate with Embedded ActiveMQ
### @EnableJms and JmsListenerContainerFactory Configuration
```
@EnableJms
public class JmsConfig {
	@Bean
	public JmsListenerContainerFactory<?> myFactory(
			ConnectionFactory connectionFactory,
			DefaultJmsListenerContainerFactoryConfigurer configurer) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		// This provides all boot's default to this factory, including the message converter
		configurer.configure(factory, connectionFactory);
		// You could still override some of Boot's default if necessary.
		return factory;
	}

	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}
}
```

- `@EnableJms` enable the `@JmsListener` annotated endpoints that are created under the cover by a `JmsListenerContainerFactory`.
- The `JmsListenerContainerFactory` is responsible to create the listener container responsible for a particular endpoint. Typical implementations, as the `DefaultJmsListenerContainerFactory`, provides the necessary configuration options that are supported by the underlying `MessageListenerContainer`.
- `MappingJackson2MessageConverter` is used to turn the payload of a `Message` from serialized form to a typed Object and vice versa.
- We have configured `MessageType.TEXT`. This message type can be used to transport text-based messages. When a client receives a `TextMessage`, it is in read-only mode. If a client attempts to write to the message at this point, a `MessageNotWriteableException` is thrown.

### JMS Message Receiver with @JmsListener
>`@JmsListener` is a repeatable annotation so you can use it multiple times on same method to register several JMS destinations to the same method.

### Results
- `MessageController`

## References
- [Spring Boot JMSTemplate with Embedded ActiveMQ](https://howtodoinjava.com/spring-boot/spring-boot-jmstemplate-activemq/)