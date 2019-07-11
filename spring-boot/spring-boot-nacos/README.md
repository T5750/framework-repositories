# Spring Boot Nacos

## Nacos Config
```
Data Id: mysql.properties
Group: DEFAULT_GROUP
spring.datasource.url=jdbc:mysql://localhost:3306/spring-boot-nacos?useSSL=false
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.initial-size=10
spring.datasource.max-active=20
```

## Nacos Discovery
### Nacos Open API
```
curl -X PUT 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=example&ip=127.0.0.1&port=8841'
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Framework 4.3.8.RELEASE](http://projects.spring.io/spring-framework)
- [Spring Boot 1.5.3.RELEASE](https://projects.spring.io/spring-boot)
- [MySQL 5.6](http://www.mysql.com/)
- [Nacos 0.2.1](https://github.com/alibaba/Nacos)

## References
- [Nacos Spring Boot 快速开始](https://nacos.io/zh-cn/docs/quick-start-spring-boot.html)
- [nacos-examples](https://github.com/nacos-group/nacos-examples)