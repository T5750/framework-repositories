# Spring Cloud Alibaba Sentinel

## Sentinel
### Introduction
>Sentinel: 分布式系统的流量防卫兵

![sentinel-min-min](https://www.wailian.work/images/2019/07/04/sentinel-min-min.png)

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

## Sentinel使用Nacos存储规则
1. `compile group: 'com.alibaba.csp', name: 'sentinel-datasource-nacos', version: '1.5.2'`
2. `application.properties`
	```
	spring.cloud.sentinel.datasource.ds.nacos.server-addr=localhost:8848
	spring.cloud.sentinel.datasource.ds.nacos.dataId=${spring.application.name}-sentinel
	spring.cloud.sentinel.datasource.ds.nacos.groupId=DEFAULT_GROUP
	spring.cloud.sentinel.datasource.ds.nacos.rule-type=flow
	```
3. `SentinelController#datasourceNacos()`
4. Nacos Config
	```
	Data Id: spring-cloud-alibaba-dev-sentinel
	Group: DEFAULT_GROUP
	[
		{
			"resource": "/sentinel/datasourceNacos",
			"limitApp": "default",
			"grade": 1,
			"count": 2,
			"strategy": 0,
			"controlBehavior": 0,
			"clusterMode": false
		}
	]
	```
5. `$ curl http://localhost:18083/sentinel/datasourceNacos`

### 接口流控规则
- Sentinel控制台中修改规则：仅存在于服务的内存中，不会修改Nacos中的配置值，重启后恢复原来的值
- Nacos控制台中修改规则：服务的内存中规则会更新，Nacos中持久化规则也会更新，重启后依然保持

## References
- [Spring Cloud Alibaba Sentinel](https://github.com/spring-cloud-incubator/spring-cloud-alibaba/wiki/Sentinel)
- [注解支持](https://github.com/alibaba/Sentinel/wiki/%E6%B3%A8%E8%A7%A3%E6%94%AF%E6%8C%81)
- [sentinel-demo](https://github.com/alibaba/Sentinel/tree/release-1.5/sentinel-demo)
- [Sentinel使用Nacos存储规则](http://blog.didispace.com/spring-cloud-alibaba-sentinel-2-1/)