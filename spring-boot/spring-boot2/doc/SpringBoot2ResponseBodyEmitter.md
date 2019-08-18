# Spring Boot 2 ResponseBodyEmitter

[ResponseBodyEmitter](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseBodyEmitter.html) helps to collect and send the response to the client. It is a controller method return value type for asynchronous request processing where one or more objects are written to the response.

## Async controller example using ResponseBodyEmitter
- There is data service which return data sets from DB or any other source.
- Each data set is then processed (e.g. retrieve related information from other source) which takes time. This is simulated using an artificial delay by calling `thread.sleep()` method.
- Each data set is then added to ResponseBodyEmitter object using `emitter.send()` method.
- Finally `emitter.complete()` is called to mark that request processing is complete so that the thread responsible for sending the response can complete the request and be freed up for the next response to handle.
- If any error is encountered while request processing, complete the process by `emitter.completeWithError()`. The exception will pass through the normal exception handling of Spring MVC and after that the response is completed.

## Results
- `DataSetControllerTest`
- `CallableControllerTest`
- `CompletableFutureControllerTest`

## Spring boot 2 SseEmitter
### SseEmitter class
FIELD | DESCRIPTION
---|-----
id | The ID of the event
event | the type of event
data | The event data
retry | Reconnection time for the event stream

### Async controller example using SseEmitter
- There is data service which return datasets from DB or any other source.
- Each dataset is then processed (e.g. retrieve related information from other source) which takes time. This is simulated using an artificial delay by calling `thread.sleep()` method.
- Each data set is then added to `SseEmitter` object using `emitter.send()` method.
- Finally `emitter.complete()` is called to mark that request processing is complete so that the thread responsible for sending the response can complete the request and be freed up for the next response to handle.
- If any error is encountered while request processing, complete the process by `emitter.completeWithError()`. The exception will pass through the normal exception handling of Spring MVC and after that the response is completed.

## References
- [Spring boot async controller with ResponseBodyEmitter](https://howtodoinjava.com/spring-boot2/spring-async-controller-responsebodyemitter/)
- [Spring boot async controller with SseEmitter](https://howtodoinjava.com/spring-boot2/spring-async-controller-sseemitter/)