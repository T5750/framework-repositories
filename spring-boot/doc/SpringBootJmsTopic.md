# Spring Boot JMS Topic

## ActiveMQ Publish Subscribe Topic
### Spring Boot ActiveMQ Configuration
>**Note:** we can create a publish subscribe broker by setting the `spring.jms.pub-sub-domain` to `true`. This specifies that the default destination is a topic. Another thing to notice is we created concurrent listeners. We configured this concurrent listeners using the `spring.jms.concurrency` and `spring.jms.max-concurrency` properties. The first property configures the minimum number of concurrent consumers. The latter configures the maximum number of concurrent consumers.

### Results
- `JmsController`
- `JmsReceiver`

## JMS Validate Messages
### JMS Validate Messages using JSR-303 Bean Validation
- JSR-303 Bean Validation
    - `Order -> @NotNull`
    - `JmsListenerConfig#validatorFactory -> HibernateValidator`
- Custom External Bean Validation
    - `OrderValidator`
    - `JmsListenerConfig#methodFactory`

## JMS Error Handling
- `JmsConsumerConfig#queueListenerFactory -> setErrorHandler`
- `OrderProcessingException`

## JMS Message Header Properties
### Setting JMS Message Header Properties
In this `MessagePostProcessor` we are able to access the core JMS `Message` object which we can set the default JMS headers or add custom headers to the message.
- `JMSDestination` – the destination where the message is sent.
- `JMSReplyTo` – the JMS destination where the reply message should be sent.
- `JMSDeliveryMode` – the delivery mode of the message. can be one of the following:
    - `PERSISTENT` – signifies the messages are stored and forwarded
    - `NON_PERSISTENT` – messages are not stored and may be lost due to failures in transmission.
- `JMSMessageID` – the unique ID of the message.
- `JMSTimestamp` – the time a message was handed off to a JMS provider to be sent. The time expressed at the amount of time, in milliseconds.
- `JMSExpiration` – the expiration time of the message.
- `JMSRedelivered` – typically this item is set when the JMS provider has delivered the message at least once before.
- `JMSPriority` – the priority of the message. Priority is a value from 0-9. Higher numbers signify a higher priority (that is, 9 is a higher priority than 8).
- `JMSCorrelationID` – this ID is used to link a response message with its related request message. This is usually the message ID of a request message when this field is found in a reply message.
- `JMSType` – the JMS provider-supplied string to describe the type of the message. Some JMS providers use this property to define messages in the provider’s repository. See the JMS provider documentation for more information about the use of this field.

### Accessing JMS Header Information
The `@JmsListener` annotation marks a method to be the target of a JMS message listener on the specified `destination`. We can access the JMS Message Headers using one of the following.
- `@Header`-annotated method arguments to extract specific header values, including standard JMS headers defined by `JmsHeaders`.
- `@Headers`-annotated method argument that must also be assignable to `Map` for obtaining access to all headers.
- `MessageHeaders` arguments for obtaining access to all headers.
- `MessageHeaderAccessor` or `JmsMessageHeaderAccessor` for convenient access to all method arguments.

## References
- [Spring Boot ActiveMQ Publish Subscribe Topic Configuration Example](https://memorynotfound.com/spring-boot-activemq-publish-subscribe-topic-configuration-example/)
- [Spring JMS Validate Messages using JSR-303 Bean Validation](https://memorynotfound.com/spring-jms-validate-messages-jsr-303-bean-validation/)
- [Spring JMS Error Handling Configuration Example](https://memorynotfound.com/spring-jms-error-handling-configuration-example/)
- [Setting and Reading Spring JMS Message Header Properties Example](https://memorynotfound.com/spring-jms-setting-reading-header-properties-example/)