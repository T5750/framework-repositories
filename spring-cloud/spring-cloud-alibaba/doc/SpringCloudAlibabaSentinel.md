## Spring Cloud Alibaba Sentinel

### @SentinelResource
>注：1.6.0 之前的版本 fallback 函数只针对降级异常（DegradeException）进行处理，不能针对业务异常进行处理。

- `SentinelController`

### Sentinel Dashboard
[Download Sentinel Dashboard](https://github.com/alibaba/Sentinel/releases)
```
java -Dserver.port=8080 -Dcsp.sentinel.dashboard.server=localhost:8080 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.6.2.jar
```

### RestTemplate
- `RestTemplateConfig`
- `ExceptionUtil`

Sentinel RestTemplate 限流的资源规则提供两种粒度：
- `httpmethod:schema://host:port/path`：协议、主机、端口和路径
- `httpmethod:schema://host:port`：协议、主机和端口

### Endpoint
```
compile('org.springframework.boot:spring-boot-starter-actuator')

management.security.enabled=false
```

## References
- [Spring Cloud Alibaba Sentinel](https://github.com/spring-cloud-incubator/spring-cloud-alibaba/wiki/Sentinel)
- [注解支持](https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81)
- [sentinel-demo](https://github.com/alibaba/Sentinel/tree/release-1.5/sentinel-demo)