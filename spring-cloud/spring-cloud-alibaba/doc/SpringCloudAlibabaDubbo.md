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
服务调用（Service-to-service calls） | Open Feign、RestTemplate | Spring Cloud 服务调用 + Dubbo @Reference
链路跟踪（Tracing） | Spring Cloud Sleuth + Zipkin | Zipkin、opentracing 等

## References
- [Dubbo Spring Cloud](https://nacos.io/zh-cn/docs/architecture.html)