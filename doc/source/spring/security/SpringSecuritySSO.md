# Spring Security SSO

## Benefits of Single sign-on
1. Reduced IT support cost
1. Improved User experience
1. Better understanding of the customer
1. Better security

## How does SSO authentication works?
![spring-boot-sso-min-min](https://s0.wailian.download/2019/06/21/spring-boot-sso-min-min.png)

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

## References
- [Single sign-on in Spring Boot applications with Spring Security OAuth](https://shekhargulati.com/2018/02/15/single-sign-on-in-spring-boot-applications-with-spring-security-oauth/)
- [spring-boot-sso GitHub](https://github.com/shekhargulati/spring-boot-sso)