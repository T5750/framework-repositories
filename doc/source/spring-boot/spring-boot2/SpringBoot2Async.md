# Spring Boot 2 Async

## @Async rest controller
Spring comes with [@EnableAsync](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/EnableAsync.html) annotation and can be applied on application classes for asynchronous behavior. This annotation will look for methods marked with [@Async](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/scheduling/annotation/Async.html) annotation and run in background thread pools. The `@Async` annotated methods can return `CompletableFuture` to hold the result of an asynchronous computation.

## References
- [Spring @Async rest controller example – Spring @EnableAsync](https://howtodoinjava.com/spring-boot2/enableasync-async-controller/)
- [Spring boot async rest controller with Callable interface](https://howtodoinjava.com/spring-boot2/async-rest-controller-callable/)