# Building RESTful Web Services

## Rest Controller
The @RestController annotation is used to define the RESTful web services.
```
@RestController
public class ProductServiceController { 
}
```

## Request Mapping
The @RequestMapping annotation is used to define the Request URI to access the REST Endpoints.
```
@RequestMapping(value = "/products")
public ResponseEntity<Object> getProducts() { }
```

## Request Body
The @RequestBody annotation is used to define the request body content type.
```
public ResponseEntity<Object> createProduct(@RequestBody Product product) {
}
```

## Path Variable
The @PathVariable annotation is used to define the custom or dynamic request URI.
```
public ResponseEntity<Object> updateProduct(@PathVariable("id") String id) {
}
```

## Request Parameter
The @RequestParam annotation is used to read the request parameters from the Request URL.
```
public ResponseEntity<Object> getProduct(
   @RequestParam(value = "name", required = false, defaultValue = "honey") String name) {
}
```

## GET API
http://localhost:8071/products

## POST API
http://localhost:8071/products
```
{
"id":3,
"name":"T5750"
}
```

## PUT API
http://localhost:8071/products/1
```
{
"name":"T5750"
}
```

## DELETE API
http://localhost:8071/products/3

## Rest Template
Rest Template is used to create applications that consume RESTful Web Services. You can use the `exchange()` method to consume the web services for all HTTP methods.

### GET
http://localhost:8071/template/products

### POST
http://localhost:8071/template/products
```
{
"id":3,
"name":"T5750"
}
```

### PUT
http://localhost:8071/template/products/1
```
{
"name":"T5750"
}
```

### DELETE
http://localhost:8071/template/products/3

## Service Components
Service Components are the class file which contains @Service annotation.

## References
- [Spring Boot - Building RESTful Web Services](https://www.tutorialspoint.com/spring_boot/spring_boot_building_restful_web_services.htm)
- [Spring Boot - Rest Template](https://www.tutorialspoint.com/spring_boot/spring_boot_rest_template.htm)
- [Spring Boot - Service Components](https://www.tutorialspoint.com/spring_boot/spring_boot_service_components.htm)