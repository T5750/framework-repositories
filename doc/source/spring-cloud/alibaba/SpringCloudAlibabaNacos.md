# Spring Cloud Alibaba Nacos

## Introduction
>一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。
Nacos 致力于帮助您发现、配置和管理微服务。

## Architecture
![nacos-architecture-min](https://www.wailian.work/images/2019/07/05/nacos-architecture-min.jpg)

## Nacos Config
### Quickstart
1. `compile('org.springframework.cloud:spring-cloud-starter-alibaba-nacos-config')`
1. `/src/main/resources/bootstrap.properties`
1. `NacosController`

### Nacos Server
- [Download Nacos Server](https://github.com/alibaba/nacos/releases)
- `sh startup.sh -m standalone` or `cmd startup.cmd`
- http://localhost:8848/nacos
- Username, Password: nacos, nacos

```
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud-alibaba-dev.properties&group=DEFAULT_GROUP&content=user.id=1%0Auser.name=t5750%0Auser.age=17"
```
```
Data Id=spring-cloud-alibaba-dev.properties
Group=DEFAULT_GROUP

user.id=1
user.name=t5750
user.age=17
```

### Validate
```
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud-alibaba-dev.properties&group=DEFAULT_GROUP&content=user.id=1%0Auser.name=t5750%0Auser.age=18"
```

### Endpoint
- http://localhost:18083/nacos_config

### 配置的优先级
当三种方式共同使用时，优先级关系是: A < B < C
- A: 通过 `spring.cloud.nacos.config.shared-dataids` 支持多个共享 Data Id 的配置
- B: 通过 `spring.cloud.nacos.config.ext-config[n].data-id` 的方式支持多个扩展 Data Id 的配置
- C: 通过内部相关规则(应用名、应用名+ Profile )自动生成相关的 Data Id 配置

## Nacos Discovery
### Quickstart
1. `compile('org.springframework.cloud:spring-cloud-starter-alibaba-nacos-discovery')`
1. `application.properties`: `spring.cloud.nacos.discovery.server-addr=127.0.0.1:8848`
1. `@EnableDiscoveryClient`

### Validate
- http://127.0.0.1:8848/nacos/v1/ns/catalog/instances?serviceName=spring-cloud-alibaba-dev&clusterName=DEFAULT&pageSize=10&pageNo=1&namespaceId=

### RestTemplate
```
compile('org.springframework.cloud:spring-cloud-starter-alibaba-nacos-discovery')
compile('org.springframework.cloud:spring-cloud-starter-netflix-ribbon')
compile('org.springframework.cloud:spring-cloud-starter-openfeign')

@EnableDiscoveryClient
@EnableFeignClients
CloudAlibabaConsumer

@FeignClient(name = "spring-cloud-alibaba-dev")
EchoService
```

### Endpoint
- http://localhost:18083/nacos_discovery

## References
- [Nacos 架构](https://nacos.io/zh-cn/docs/architecture.html)
- [Nacos Config Example](https://github.com/alibaba/spring-cloud-alibaba/blob/1.x/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)
- [Nacos config](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)
- [nacos-examples](https://github.com/nacos-group/nacos-examples)
- [版本说明](https://github.com/alibaba/spring-cloud-alibaba/wiki/%E7%89%88%E6%9C%AC%E8%AF%B4%E6%98%8E)
- [Nacos Discovery Example](https://github.com/alibaba/spring-cloud-alibaba/blob/1.x/spring-cloud-alibaba-examples/nacos-example/nacos-discovery-example/readme-zh.md)