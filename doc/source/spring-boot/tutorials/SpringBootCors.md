# CORS Support

Cross-Origin Resource Sharing (CORS) is a security concept that allows restricting the resources implemented in web browsers. It prevents the JavaScript code producing or consuming the requests against different origin.

## Enable CORS in Controller Method
```
@RequestMapping(value = "/products")
@CrossOrigin(origins = "http://localhost:8080")
public ResponseEntity<Object> getProduct() {
   return null;
}
```

## Global CORS Configuration
```
@Bean
public WebMvcConfigurer corsConfigurer() {
   return new WebMvcConfigurerAdapter() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
         registry.addMapping("/products").allowedOrigins("http://localhost:9000");
      }
   };
}
```

## References
- [Spring Boot - CORS Support](https://www.tutorialspoint.com/spring_boot/spring_boot_cors_support.htm)