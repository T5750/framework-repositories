# Spring Security OAuth

## 运行流程
![oauth2](https://www.wailian.work/images/2019/06/20/oauth2.png)
```
（A）用户打开客户端以后，客户端要求用户给予授权。
（B）用户同意给予客户端授权。
（C）客户端使用上一步获得的授权，向认证服务器申请令牌。
（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。
（E）客户端使用令牌，向资源服务器申请获取资源。
（F）资源服务器确认令牌无误，同意向客户端开放资源。
```

## 客户端的授权模式
![spring-security-oauth2-min-min](https://www.wailian.work/images/2019/06/20/spring-security-oauth2-min-min.png)

OAuth2根据使用场景不同，分成了4种模式
- 授权码模式（authorization code）
- 简化模式（implicit）
- 密码模式（resource owner password credentials）
- 客户端模式（client credentials）

## spring-security-oauth-password-client
### password credentials
![oauth2-password](https://www.wailian.work/images/2019/06/20/oauth2-password.png)
```
（A）用户向客户端提供用户名和密码。
（B）客户端将用户名和密码发给认证服务器，向后者请求令牌。
（C）认证服务器确认无误后，向客户端提供访问令牌。
```
```
http://localhost:8060/product/1
http://localhost:8060/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456
http://localhost:8060/order/1?access_token=a207c3c9-9490-43a1-bd04-b32a159f3cc1
```

### client credentials
![oauth2-client](https://www.wailian.work/images/2019/06/20/oauth2-client.png)
```
（A）客户端向认证服务器进行身份认证，并要求一个访问令牌。
（B）认证服务器确认无误后，向客户端提供访问令牌。
```
```
http://localhost:8060/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret=123456
```

关键的几个类：
```
ClientCredentialsTokenEndpointFilter
DaoAuthenticationProvider
TokenEndpoint
TokenGranter
```

## spring-security-oauth-code
### authorization code
![oauth2-code](https://www.wailian.work/images/2019/06/20/oauth2-code.png)
```
（A）用户访问客户端，后者将前者导向认证服务器。
（B）用户选择是否给予客户端授权。
（C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
（D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
（E）认证服务器核对了授权码和重定向URI，确认无误后，向客户端发送访问令牌（access token）和更新令牌（refresh token）。
```
```
OAuthQQApp
OAuthAiqiyiApp
http://localhost:8061/oauth/authorize?client_id=aiqiyi&response_type=code&redirect_uri=http://localhost:8062/aiqiyi/qq/redirect
250577914, 123456
http://localhost:8061/qq/info/250577914?access_token=6e017b4b-61ec-4093-aaaf-456e0a6b4ca8
```

![OAuth2AuthenticationManager-min-min](https://www.wailian.work/images/2019/06/20/OAuth2AuthenticationManager-min-min.png)

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Framework 4.3.8.RELEASE](http://projects.spring.io/spring-framework)
- [Spring Boot 1.5.3.RELEASE](https://projects.spring.io/spring-boot)
- [Spring Security 4.2.2.RELEASE](https://spring.io/projects/spring-security)
- [Spring Security OAuth 2.0.13.RELEASE](https://spring.io/projects/spring-security-oauth)

## References
- [oauth2-demo](https://github.com/lexburner/oauth2-demo)
- [理解OAuth 2.0](http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html)