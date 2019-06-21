# Spring Boot SSO

## Benefits of Single sign-on
1. Reduced IT support cost
1. Improved User experience
1. Better understanding of the customer
1. Better security

## How does SSO authentication works?
![spring-boot-sso-min-min](https://www.wailian.work/images/2019/06/21/spring-boot-sso-min-min.png)

## Setting your own SSO server with Spring Boot and Spring Security OAuth
1. spring-boot-sso-app-foo and spring-boot-sso-app-bar will be the two applications using SSO
1. spring-boot-sso-server will be the centeralized login system

## Results
- `SsoServerApplication`
- `AppFooApplication`
- `AppBarApplication`
- http://localhost:8051/spring-boot-sso-app-foo/
- http://localhost:8052/spring-boot-sso-app-bar/
- user, pass

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Framework 4.3.8.RELEASE](http://projects.spring.io/spring-framework)
- [Spring Boot 1.5.3.RELEASE](https://projects.spring.io/spring-boot)
- [Spring Security 4.2.2.RELEASE](https://spring.io/projects/spring-security)
- [Spring Security OAuth 2.0.13.RELEASE](https://spring.io/projects/spring-security-oauth)

## References
- [Single sign-on in Spring Boot applications with Spring Security OAuth](https://shekhargulati.com/2018/02/15/single-sign-on-in-spring-boot-applications-with-spring-security-oauth/)
- [spring-boot-sso](https://github.com/shekhargulati/spring-boot-sso)