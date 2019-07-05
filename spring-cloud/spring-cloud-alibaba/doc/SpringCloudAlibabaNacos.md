# Spring Cloud Alibaba Nacos

## Architecture
![nacos-architecture-min](https://www.wailian.work/images/2019/07/05/nacos-architecture-min.jpg)

## Nacos Config
### Example
1. `compile('org.springframework.cloud:spring-cloud-starter-alibaba-nacos-config')`
1. `/src/main/resources/bootstrap.properties`
1. `NacosController`

### Nacos Server
- [Download Nacos Server](https://github.com/alibaba/nacos/releases)
- `sh startup.sh -m standalone` or `cmd startup.cmd`
- http://localhost:8848/nacos

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

### validate
```
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=spring-cloud-alibaba-dev.properties&group=DEFAULT_GROUP&content=user.id=1%0Auser.name=t5750%0Auser.age=18"
```

### Endpoint
- http://localhost:18083/nacos_config

## References
- [Nacos 架构](https://nacos.io/zh-cn/docs/architecture.html)
- [Nacos Config Example](https://github.com/alibaba/spring-cloud-alibaba/blob/master/spring-cloud-alibaba-examples/nacos-example/nacos-config-example/readme-zh.md)
- [Nacos config](https://github.com/alibaba/spring-cloud-alibaba/wiki/Nacos-config)
- [nacos-examples](https://github.com/nacos-group/nacos-examples)