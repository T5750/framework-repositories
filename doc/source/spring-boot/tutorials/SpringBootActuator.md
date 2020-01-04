# Actuator

## Enabling Spring Boot Actuator
```
compile group: 'org.springframework.boot', name: 'spring-boot-starter-actuator'

management.security.enabled=false

management.port=9000
```

ENDPOINTS | USAGE
---|------
/metrics | To view the application metrics such as memory used, memory free, threads, classes, system uptime etc.
/env | To view the list of Environment variables used in the application.
/beans | To view the Spring beans and its types, scopes and dependency.
/health | To view the application health
/info | To view the information about the Spring Boot application.
/trace | To view the list of Traces of your Rest endpoints.

## References
- [Spring Boot - Actuator](https://www.tutorialspoint.com/spring_boot/spring_boot_actuator.htm)