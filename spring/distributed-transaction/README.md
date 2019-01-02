# Distributed Transaction

## pay-common-config
- `pay_dubbo_message.sql`
- `jdbc.properties`
- `mq_config.properties`
- `public_system.properties`

## pay-service-message
- `DubboProvider`

## pay-web-message
Optimized files:
```
distributed-transaction\pay-web-message\src\main\webapp\jsp\common\dwz.jsp
distributed-transaction\pay-web-message\src\main\webapp\common
distributed-transaction\pay-web-message\src\main\webapp\dwz
```

## pay-app-message
- `MessageTask`

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Framework 4.3.8.RELEASE](http://projects.spring.io/spring-framework)
- [Dubbo 2.6.2](https://github.com/apache/incubator-dubbo/releases)
- [ZooKeeper 3.4.10](https://zookeeper.apache.org/)
- [ZkClient 0.10](https://github.com/sgroschupf/zkclient)
- [Curator 4.0.1](http://curator.apache.org/)
- [DWZ 1.5.2](https://gitee.com/dwzteam/dwz_jui/tree/v1.5.2)

## References
- [微服务架构的分布式事务解决方案](https://www.roncoo.com/view/20)