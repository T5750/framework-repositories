# ActiveMQ网摘笔记

## Spring Boot与ActiveMQ整合
ActiveMQ管控台：[http://localhost:8161/admin/](http://localhost:8161/admin/)

`build.gradle`：
```
compile('org.springframework.boot:spring-boot-starter-activemq')
```

`application.yml`修改`port`和`context-path`：
```
server:
  port: 8080
  context-path: /activemq-provider
spring:
  activemq:
    user: admin
    password: admin
    broker-url: tcp://localhost:61616
    pool:
      enabled: true
      max-connections: 10

queueName: publish.queue
topicName: publish.topic
```

`ActiveMQConfig`：
```
@Configuration
public class ActiveMQConfig {
	@Value("${queueName}")
	private String queueName;
	@Value("${topicName}")
	private String topicName;
	@Value("${spring.activemq.user}")
	private String usrName;
	@Value("${spring.activemq.password}")
	private String password;
	@Value("${spring.activemq.broker-url}")
	private String brokerUrl;

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(queueName);
	}

	@Bean
	public Topic topic() {
		return new ActiveMQTopic(topicName);
	}

	@Bean
	public ActiveMQConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory(usrName, password, brokerUrl);
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerQueue(
			ActiveMQConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		bean.setConnectionFactory(connectionFactory);
		return bean;
	}

	@Bean
	public JmsListenerContainerFactory<?> jmsListenerContainerTopic(
			ActiveMQConnectionFactory connectionFactory) {
		DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
		// 设置为发布订阅方式, 默认情况下使用的生产消费者方式
		bean.setPubSubDomain(true);
		bean.setConnectionFactory(connectionFactory);
		return bean;
	}
}
```

### Results
- 示例：`ActiveMQProviderApplication`，`ActiveMQConsumerApplication`
- queue测试：[http://localhost:8080/activemq-provider/publish/queue](http://localhost:8080/activemq-provider/publish/queue)
- topic测试：[http://localhost:8080/activemq-provider/publish/topic](http://localhost:8080/activemq-provider/publish/topic)

## References
- [springboot与ActiveMQ整合](https://www.cnblogs.com/elvinle/p/8457596.html)