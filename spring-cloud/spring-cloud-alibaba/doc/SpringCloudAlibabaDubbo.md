# Spring Cloud Alibaba Dubbo

## 简介
Dubbo Spring Cloud 基于 Dubbo Spring Boot 2.7.1 和 Spring Cloud 2.x 开发

## 功能
功能组件 | Spring Cloud | Dubbo Spring Cloud
---|---|---
分布式配置（Distributed configuration） | Git、Zookeeper、Consul、JDBC | Spring Cloud 分布式配置 + Dubbo 配置中心
服务注册与发现（Service registration and discovery） | Eureka、Zookeeper、Consul | Spring Cloud 原生注册中心 + Dubbo 原生注册中心
负载均衡（Load balancing） | Ribbon（随机、轮询等算法） | Dubbo 内建实现（随机、轮询等算法 + 权重等特性）
服务熔断（Circuit Breakers） | Spring Cloud Hystrix | Spring Cloud Hystrix + Alibaba Sentinel 等
服务调用（Service-to-service calls） | Open Feign、`RestTemplate` | Spring Cloud 服务调用 + Dubbo `@Reference`
链路跟踪（Tracing） | Spring Cloud Sleuth + Zipkin | Zipkin、opentracing 等

### 高亮特性
1. Dubbo 使用 Spring Cloud 服务注册与发现
    - 应用需增添外部化配置属性：`dubbo.registry.address = spring-cloud://localhost`
2. Dubbo 作为 Spring Cloud 服务调用
    - 默认情况，Spring Cloud Open Feign 以及 `@LoadBalanced` `RestTemplate` 作为 Spring Cloud 的两种服务调用方式
    - 可通过 Apache Dubbo 注解 `@Service` 和 `@Reference` 暴露和引用 Dubbo 服务
3. Dubbo 服务自省
    - 服务自省（Service Introspection），其设计目的在于最大化减轻注册中心负载，去 Dubbo 注册元信息中心化
4. Dubbo 迁移 Spring Cloud 服务调用
    - Dubbo Spring Cloud 提供了方案，即 `@DubboTransported` 注解
    - 如果迁移时间充分的话，建议使用 Dubbo 服务重构系统中的原生 Spring Cloud 服务的定义

## Tips

### dubbo.cloud.subscribed-services
- `dubbo.cloud.subscribed-services`: 用于服务消费方订阅服务提供方的应用名称的列表，若需订阅多应用，使用 `,` 分割
- 不推荐使用默认值为 `*`，它将订阅所有应用
- 当应用使用默认值时，日志中将会输出一行警告：
>Current application will subscribe all services(size:x) in registry, a lot of memory and CPU cycles may be used, thus it's strongly recommend you using the externalized property 'dubbo.cloud.subscribed-services' to specify the services

## References
- [Dubbo Spring Cloud 示例工程](https://github.com/alibaba/spring-cloud-alibaba/blob/greenwich/spring-cloud-alibaba-examples/spring-cloud-alibaba-dubbo-examples/README_CN.md)
- [Dubbo spring cloud 重塑微服务治理](https://mercyblitz.github.io/2019/04/26/Dubbo-Spring-Cloud-%E9%87%8D%E5%A1%91%E5%BE%AE%E6%9C%8D%E5%8A%A1%E6%B2%BB%E7%90%86/)