# Spring Boot JMS Topic

## ActiveMQ Publish Subscribe Topic
### Spring Boot ActiveMQ Configuration
>**Note:** we can create a publish subscribe broker by setting the `spring.jms.pub-sub-domain` to `true`. This specifies that the default destination is a topic. Another thing to notice is we created concurrent listeners. We configured this concurrent listeners using the `spring.jms.concurrency` and `spring.jms.max-concurrency` properties. The first property configures the minimum number of concurrent consumers. The latter configures the maximum number of concurrent consumers.

### Results
- `JmsController`
- `JmsReceiver`

## References
- [Spring Boot ActiveMQ Publish Subscribe Topic Configuration Example](https://memorynotfound.com/spring-boot-activemq-publish-subscribe-topic-configuration-example/)