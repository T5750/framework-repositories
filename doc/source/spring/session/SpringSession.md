# Spring Session

## Features
- `HttpSession` - allows replacing the HttpSession in an application container (i.e. Tomcat) neutral way, with support for providing session IDs in headers to work with RESTful APIs
- `WebSocket` - provides the ability to keep the HttpSession alive when receiving WebSocket messages
- `WebSession` - allows replacing the Spring WebFlux’s WebSession in an application container neutral way

## Minimum Requirements

spring-session-data-redis | 1.3.x | 2.1.1.RELEASE
---|---|---
Java | Java 5+ | Java 8+
Servlet (not required) | Servlet 2.5+ | Servlet 3.1+
Spring (not required) | Spring 3.2.14+ | Spring 5.0.x+
Redis | Redis 2.8+ | Redis 2.8+

## Upgrading to 2.x

### Baseline update
Spring Session 2.0 requires Java 8 and Spring Framework 5.0 as a baseline, since its entire codebase is now based on Java 8 source code. Refer to guide for [Upgrading to Spring Framework 5.x](https://github.com/spring-projects/spring-framework/wiki/Upgrading-to-Spring-Framework-5.x) for reference on upgrading Spring Framework.

## HttpSession Integration

### Why Spring Session & HttpSession?
- **Clustered Sessions** - Spring Session makes it trivial to support clustered sessions without being tied to an application container specific solution.
- **RESTful APIs** - Spring Session allows providing session IDs in headers to work with RESTful APIs

### HttpSession with Redis
```
compile('org.springframework.boot:spring-boot-starter-web')
compile group: 'org.springframework.session', name: 'spring-session-data-redis', version: '2.1.1.RELEASE'
compile group: 'redis.clients', name: 'jedis', version: '2.9.0'
```
```java
@EnableRedisHttpSession
public class HttpSessionConfig {
	@Bean
	public JedisConnectionFactory connectionFactory() {
		return new JedisConnectionFactory();
	}
}
```
```
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=
spring.redis.database=0
```
`CookieController`

### HttpSession & RESTful APIs
[Spring Session REST](SpringSessionRest.md)

## Tips
- `java -jar spring-boot2-security-1.0-boot.jar --server.port=8088`

## References
- [Spring Session](https://spring.io/projects/spring-session)
- [Spring Session docs 1.3.x](https://docs.spring.io/spring-session/docs/1.3.x/reference/html5/)
- [Spring Session docs 2.1.1.RELEASE](https://docs.spring.io/spring-session/docs/2.1.1.RELEASE/reference/html5/)
- [Spring Session blog](http://blog.didispace.com/tags/Spring-Session/)