# Spring Session REST

## Spring Configuration
```java
@EnableRedisHttpSession 
public class HttpSessionConfig {
	@Bean
	public LettuceConnectionFactory connectionFactory() {
		return new LettuceConnectionFactory(); 
	}

	@Bean
	public HttpSessionIdResolver httpSessionIdResolver() {
		return HeaderHttpSessionIdResolver.xAuthToken(); 
	}
}
```
We customize Spring Session’s `HttpSession` integration to use HTTP headers to convey the current session information instead of cookies.

## rest Sample Application

### Exploring the rest Sample Application
We have a header with the name of **X-Auth-Token** which contains a new session id
```
$ curl -v http://localhost:18096/header/hello -u user:password
$ curl -v http://localhost:18096/header/hello -H "X-Auth-Token: 4fee6933-18b9-4f60-bb24-55a15e51645e"
$ curl -v http://localhost:18096/header/findByUsername?username=user -H "X-Auth-Token: 4fee6933-18b9-4f60-bb24-55a15e51645e"
$ curl -v http://localhost:18096/header/logout -H "X-Auth-Token: 4fee6933-18b9-4f60-bb24-55a15e51645e"
```

### How does it work?
Spring Security interacts with the standard `HttpSession` in `SecurityContextPersistenceFilter`.

Instead of using Tomcat’s `HttpSession`, Spring Security is now persisting the values in Redis. Spring Session creates a header named X-Auth-Token in your browser that contains the id of your session.

## References
- [Spring Session - REST](https://docs.spring.io/spring-session/docs/2.1.1.RELEASE/reference/html5/guides/java-rest.html)