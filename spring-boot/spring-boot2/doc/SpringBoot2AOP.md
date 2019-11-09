# Spring Boot 2 AOP

## Types of Advices
1. `@Before` : Advice that executes before a join point, but which does not have the ability to prevent execution flow proceeding to the join point (unless it throws an exception).
1. `@AfterReturning` : Advice to be executed after a join point completes normally.
1. `@AfterThrowing` : Advice to be executed if a method exits by throwing an exception.
1. `@After` : Advice to be executed regardless of the means by which a join point exits (normal or exceptional return).
1. `@Around` : Advice that surrounds a join point such as a method invocation.

## References
- [AOP with Spring Boot](https://howtodoinjava.com/spring-boot2/aop-aspectj/)