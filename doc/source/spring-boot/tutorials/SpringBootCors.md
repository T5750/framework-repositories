# CORS Support

Cross-Origin Resource Sharing (CORS) is a security concept that allows restricting the resources implemented in web browsers. It prevents the JavaScript code producing or consuming the requests against different origin.

## Cross-Origin Requests
Cross-origin requests, in short, are HTTP requests where the origin and the target of the request are different.

To manage cross-origin requests, the server needs to enable a particular mechanism known as CORS, or Cross-Origin Resource Sharing.

The first step in CORS is an `OPTIONS` request to determine whether the target of the request supports it. **This is called a pre-flight request**.

The server can then respond to the pre-flight request with a collection of headers:
- `Access-Control-Allow-Origin`: Defines which origins may have access to the resource. A `*` represents any origin
- `Access-Control-Allow-Methods`: Indicates the allowed HTTP methods for cross-origin requests
- `Access-Control-Allow-Headers`: Indicates the allowed request headers for cross-origin requests
- `Access-Control-Max-Age`: Defines the expiration time of the result of the cached preflight request

If configured incorrectly, this pre-flight request will always fail with a 401.

## Enable CORS in Controller Method
```
@RequestMapping(value = "/products")
@CrossOrigin(origins = "http://localhost:8080")
public ResponseEntity<Object> getProduct() {
   return null;
}
```
The `@CrossOrigin` annotation makes sure that our APIs are accessible only from the origin mentioned in its argument.

属性 | 含义
---|---
`value` | 指定所支持域的集合， 表示所有域都支持，默认值为 。这些值对应于 HTTP 请求头中的 `Access-Control-Allow-Origin`
`origins` | `@AliasFor(“value”)`，与 `value` 属性一样
`allowedHeaders` | 允许请求头中的 headers，在预检请求 `Access-Control-Allow-Headers` 响应头中展示
`exposedHeaders` | 响应头中允许访问的 headers，在实际请求的 `Access-Control-Expose-Headers` 响应头中展示
`methods` | 支持的 HTTP 请求方法列表，默认和 Controller 中的方法上标注的一致。
`allowCredentials` | 表示浏览器在跨域请求中是否携带凭证，比如 cookies。在预检请求的 `Access-Control-Allow-Credentials` 响应头中展示
`maxAge` | 预检请求响应的最大缓存时间，单位为秒。在预检请求的 `Access-Control-Max-Age` 响应头中展示

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

## Making a Pre-flight Request
```
curl -v -H "Access-Control-Request-Method: GET" -H "Origin: http://localhost:4200" -X OPTIONS http://localhost:8071/products-cors
...
< HTTP/1.1 401
...
< WWW-Authenticate: Basic realm="Realm"
...
< Vary: Origin
< Vary: Access-Control-Request-Method
< Vary: Access-Control-Request-Headers
< Access-Control-Allow-Origin: http://localhost:4200
< Access-Control-Allow-Methods: POST
< Access-Control-Allow-Credentials: true
< Allow: GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH
...
curl -v -H "Access-Control-Request-Method: GET" -H "Origin: http://localhost:8072" -X OPTIONS http://localhost:8071/products-cors
curl -v -H "Access-Control-Request-Method: GET" -H "Origin: http://localhost:4200" -X OPTIONS http://localhost:8072/user-cors
```

## The Solution
**We haven't explicitly excluded the preflight requests from authorization in our Spring Security configuration**. Remember that Spring Security secures all endpoints by default.

As a result, **our API expects an authorization token in the OPTIONS request as well**.

Spring provides an out of the box solution to exclude OPTIONS requests from authorization checks:
```java
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors();
    }
}
```
The `cors()` method will add the Spring-provided `CorsFilter` to the application context which in turn bypasses the authorization checks for OPTIONS requests.

## How does it work?
CORS requests ([including preflight ones with an OPTIONS method](https://github.com/spring-projects/spring-framework/blob/master/spring-webmvc/src/main/java/org/springframework/web/servlet/FrameworkServlet.java#L906)) are automatically dispatched to the various `HandlerMapping`s registered. They handle CORS preflight requests and intercept CORS simple and actual requests thanks to a [CorsProcessor](https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/web/cors/CorsProcessor.html) implementation ([DefaultCorsProcessor](https://github.com/spring-projects/spring-framework/blob/master/spring-web/src/main/java/org/springframework/web/cors/DefaultCorsProcessor.java) by default) in order to add the relevant CORS response headers (like `Access-Control-Allow-Origin`). [CorsConfiguration](https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/web/cors/CorsConfiguration.html) allows you to specify how the CORS requests should be processed: allowed origins, headers, methods, etc. It can be provided in various ways:
- [AbstractHandlerMapping#setCorsConfiguration()](https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/web/servlet/handler/AbstractHandlerMapping.html) allows to specify a `Map` with several [CorsConfiguration](https://docs.spring.io/spring/docs/4.2.x/javadoc-api/org/springframework/web/cors/CorsConfiguration.html) mapped on path patterns like `/api/**`
- Subclasses can provide their own `CorsConfiguration` by overriding `AbstractHandlerMapping#getCorsConfiguration(Object, HttpServletRequest)` method
- Handlers can implement `CorsConfigurationSource` interface (like `ResourceHttpRequestHandler` now does) in order to provide a `CorsConfiguration` for each request.

## Filter based CORS support
In that case, instead of using `@CrossOrigin` or `WebMvcConfigurer#addCorsMappings(CorsRegistry)`
```java
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:4200");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

## Tips
URI 例子及它们的组成部分：
```
                     权限                 路径
      ┌───────────────┴───────────────┐┌───┴────┐
abc://username:password@example.com:123/path/data?key=value&key2=value2#fragid1
└┬┘   └───────┬───────┘ └────┬────┘ └┬┘           └─────────┬─────────┘ └──┬──┘
协议        用户信息         主机名    端口                  查询参数          片段
```

### 浏览器的同源策略
只要 **协议**，**主机名**，**端口** 这三项组成部分中有一项不同，就可以认为是不同的域，不同的域之间互相访问资源，就被称之为跨域

同源策略会限制以下几种行为：
- Cookie、LocalStorage 和 IndexDB 无法读取
- DOM 和 JS 对象无法获得
- AJAX 请求不能发送，被浏览器拦截了

### 跨域解决方案
1. JSONP 跨域
2. 跨域资源共享（CORS）
3. Nginx 反向代理
4. Node.js 中间件代理
5. document.domain + iframe
6. location.hash + iframe 跨域
7. window.name + iframe 跨域
8. postMessage 跨域
9. WebSocket 协议跨域

## References
- [Spring Boot - CORS Support](https://www.tutorialspoint.com/spring_boot/spring_boot_cors_support.htm)
- [Fixing 401s with CORS Preflights and Spring Security](https://www.baeldung.com/spring-security-cors-preflight)
- [CORS support in Spring Framework](https://spring.io/blog/2015/06/08/cors-support-in-spring-framework)
- [Spring Boot 2.x 跨域处理方案之 Cors](https://www.tuicool.com/articles/VR7VvuJ)