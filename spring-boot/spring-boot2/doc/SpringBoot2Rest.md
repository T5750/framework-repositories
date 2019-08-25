# Spring Boot 2 REST API

## REST API Controller
- In Spring, a controller class, which is capable of serving REST API requests, is called rest controller. It should be annotated with `@RestController` annotation.
- The resource uris are specified in `@RequestMapping` annotations. It can be applied at class level and method level both. Complete URI for an API is resolved after adding class level path and method level path.
- We should always write `produces` and `consumes` attributes to specify the mediatype attributes for the API. Never reply on assumptions.

### HTTP GET /employees
http://localhost:18090/employees/

### HTTP POST /employees
http://localhost:18090/employees/
```
{
	"firstName": "T5750",
	"lastName": "T5750",
	"email": "T5750@gmail.com"
}
```

## Exception handling
### Results
1. HTTP GET /employees/vo/1 [VALID]
1. HTTP GET /employees/vo/23 [INVALID]
1. HTTP POST /employees/vo [INVALID]
	```
	{
		"lastName": "T5750",
		"email": "T5750@gmail.com"
	}
	```
1. HTTP POST /employees/vo [INVALID]
	```
	{
		"email": "T5750@gmail.com"
	}
	```
1. HTTP POST /employees/vo [INVALID]
	```
	{
		"firstName": "T5750",
		"email": "T5750_gmail.com" //invalid email in request
	}
	```

### REST request validation annotations
ANNOTATION | USAGE
---|------
`@AssertFalse` | The annotated element must be false.
`@AssertTrue` | The annotated element must be true.
`@DecimalMax` | The annotated element must be a number whose value must be lower or equal to the specified maximum.
`@DecimalMin` | The annotated element must be a number whose value must be higher or equal to the specified minimum.
`@Future` | The annotated element must be an instant, date or time in the future.
`@Max` | The annotated element must be a number whose value must be lower or equal to the specified maximum.
`@Min` | The annotated element must be a number whose value must be higher or equal to the specified minimum.
`@Negative` | The annotated element must be a strictly negative number.
`@NotBlank` | The annotated element must not be null and must contain at least one non-whitespace character.
`@NotEmpty` | The annotated element must not be null nor empty.
`@NotNull` | The annotated element must not be null.
`@Null` | The annotated element must be null.
`@Pattern` | The annotated CharSequence must match the specified regular expression.
`@Positive` | The annotated element must be a strictly positive number.
`@Size` | The annotated element size must be between the specified boundaries (included).

## References
- [Spring Boot 2 REST API Example](https://howtodoinjava.com/spring-boot2/rest-api-example/)
- [Spring Boot 2 REST POST with Headers](https://howtodoinjava.com/spring-boot2/spring-boot2-rest-post-example/)
- [Spring boot exception handling – @ExceptionHandler example](https://howtodoinjava.com/spring-boot2/spring-rest-request-validation/)
- [Spring Boot HATEOAS Example](https://howtodoinjava.com/spring-boot2/rest-with-spring-hateoas-example/)