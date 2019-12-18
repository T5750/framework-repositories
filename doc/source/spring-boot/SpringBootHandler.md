# Spring Boot

## Exception Handling
### Controller Advice
The `@ControllerAdvice` is an annotation, to handle the exceptions globally.

### Exception Handler
The `@ExceptionHandler` is an annotation used to handle the specific exceptions and sending the custom responses to the client.

Update URL: http://localhost:8071/products/9

## Interceptor
- preHandle() method − This is used to perform operations before sending the request to the controller. This method should return true to return the response to the client.
- postHandle() method − This is used to perform operations before sending the response to the client.
- afterCompletion() method − This is used to perform operations after completing the request and response.

## Servlet Filter
- `SimpleFilter`

## File Handling
### File Upload
```
@RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public String fileUpload(@RequestParam("file") MultipartFile file) {
   return null;
}
```

### File Download
- `FileDownloadController`
- http://localhost:8071/download

## References
- [Spring Boot - Exception Handling](https://www.tutorialspoint.com/spring_boot/spring_boot_exception_handling.htm)
- [Spring Boot - Interceptor](https://www.tutorialspoint.com/spring_boot/spring_boot_interceptor.htm)
- [Spring Boot - Servlet Filter](https://www.tutorialspoint.com/spring_boot/spring_boot_servlet_filter.htm)
- [Spring Boot - File Handling](https://www.tutorialspoint.com/spring_boot/spring_boot_file_handling.htm)