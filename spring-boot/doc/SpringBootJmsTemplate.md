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
- `JmsController`
- `JmsReceiver`

### Consuming Messages from JMS Queue
The `@JmsListener` annotation marks a method to be the target of a JMS message listener on the specified `destination`. Annotated JMS listener methods are allowed to have flexible signatures.
- `javax.jms.Session` to get access to the JMS session.
- `javax.jms.Message` or one of its subclasses to get access to the raw JMS message.
- `org.springframework.messaging.Message` to use Spring’s messaging abstraction counterpart.
- `org.springframework.messaging.handler.annotation.Payload` `@Payload`-annotated method arguments, including support for validation.
- `org.springframework.messaging.handler.annotation.Header` `@Header` – annotated method arguments to extract specific header values, including standard JMS headers defined by `org.springframework.jms.support.JmsHeaders`.
- `org.springframework.messaging.handler.annotation.Headers` `@Headers` – annotated method argument that must also be assignable to `java.util.Map` for obtaining access to all headers.
- `org.springframework.messaging.MessageHeaders` arguments for obtaining access to all headers.
- `org.springframework.messaging.support.MessageHeaderAccessor` or `org.springframework.jms.support.JmsMessageHeaderAccessor` for convenient access to all method arguments.

## References
- [Spring Boot JMSTemplate with Embedded ActiveMQ](https://howtodoinjava.com/spring-boot/spring-boot-jmstemplate-activemq/)
- [Spring Boot Embedded ActiveMQ Configuration Example](https://memorynotfound.com/spring-boot-embedded-activemq-configuration-example/)