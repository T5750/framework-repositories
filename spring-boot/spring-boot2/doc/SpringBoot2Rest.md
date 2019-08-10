# Spring Boot 2 REST API

## REST API Controller
- In Spring, a controller class, which is capable of serving REST API requests, is called rest controller. It should be annotated with `@RestController` annotation.
- The resource uris are specified in `@RequestMapping` annotations. It can be applied at class level and method level both. Complete URI for an API is resolved after adding class level path and method level path.
- We should always write `produces` and `consumes` attributes to specify the mediatype attributes for the API. Never reply on assumptions.

## HTTP GET /employees
http://localhost:18090/employees/

## HTTP POST /employees
http://localhost:18090/employees/
```
{
	"firstName": "T5750",
	"lastName": "T5750",
	"email": "T5750@gmail.com"
}
```

## References
- [Spring Boot 2 REST API Example](https://howtodoinjava.com/spring-boot2/rest-api-example/)