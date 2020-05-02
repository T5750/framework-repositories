# Spring Boot 2 JUnit

By default, Spring boot uses Junit 4. To write tests in Junit 5, read this migration guide : [Junit 5 with Spring boot 2](https://howtodoinjava.com/spring-boot2/junit5-with-spring-boot2/).

## JUnit Test Class
1. `@RunWith(SpringRunner.class)` – tells JUnit to run using Spring’s testing support. SpringRunner is the new name for `SpringJUnit4ClassRunner`. It enables full support of spring context loading and dependency injection of the beans in the tests.
1. `@SpringBootTest` – annotation that can be specified on a test class that runs Spring Boot based tests. It provides ability to start a fully running web server listening on any defined or random port.
1. `webEnvironment` – enables requests and responses to travel over network and mock servlet container is not involved.
1. `@LocalServerPort` – injects the HTTP port that got allocated at runtime.

## References
- [Spring boot JUnit example with RestTemplate](https://howtodoinjava.com/spring-boot2/spring-boot-junit-resttemplate/)