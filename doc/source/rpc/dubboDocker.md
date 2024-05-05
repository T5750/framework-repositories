# Dubbo Docker

Apache Dubbo 是一款易用、高性能的 WEB 和 RPC 框架，同时为构建企业级微服务提供服务发现、流量治理、可观测、认证鉴权等能力、工具与最佳实践。

"Dubbo3 已在阿里巴巴内部微服务集群全面落地，用于升级运行多年的 HSF2 框架。"

## 核心优势
### 快速易用
[Dubbo Initializer](https://start.dubbo.apache.org/bootstrap.html) 可用来快速生成 Java 项目脚手架

### 超高性能
Dubbo 内置支持 Dubbo2、Triple 两款高性能通信协议。其中
- Dubbo2 是基于 TCP 传输协议之上构建的二进制私有 RPC 通信协议，是一款非常简单、紧凑、高效的通信协议。
- Triple 是基于 HTTP/2 的新一代 RPC 通信协议，在网关穿透性、通用性以及 Streaming 通信上具备优势，Triple 完全兼容 gRPC 协议。

### 服务治理
#### 流量管控
场景二：搭建一套完全隔离的线上灰度环境用来部署新版本服务。

![](https://cn.dubbo.apache.org/imgs/v3/tasks/gray/gray1.png)

场景三：金丝雀发布

![](https://cn.dubbo.apache.org/imgs/v3/tasks/weight/weight1.png)

场景四：同区域优先。当应用部署在多个不同机房/区域的时候，优先调用同机房/区域的服务提供者，避免了跨区域带来的网络延时，从而减少了调用的响应时间。

![](https://cn.dubbo.apache.org/imgs/v3/tasks/region/region1.png)

#### 微服务生态
![](https://cn.dubbo.apache.org/imgs/v3/what/governance.png)

#### 服务网格
基于 Dubbo 开发的服务可以透明的接入 Istio 等服务网格体系，Dubbo 支持基于 Envoy 的流量拦截方式，也支持更加轻量的 Proxyless Mesh 部署模式。

### 生产环境验证
Dubbo 设计用于解决阿里巴巴内部复杂的电商微服务集群的开发和治理问题

![](https://cn.dubbo.apache.org/imgs/v3/advantages/production-ready.png)

## Dubbo Admin
`dubbo-admin.yml`

[http://localhost:8080/](http://localhost:8080/)

## Architecture
![](https://cn.dubbo.apache.org/imgs/v3/reference/admin/admin-core-components.png)

## Screenshots
![](https://cn.dubbo.apache.org/imgs/v3/tasks/observability/admin/1-search-by-service.png)

![](https://cn.dubbo.apache.org/imgs/v3/tasks/observability/admin/1-service-detail.png)

![](https://cn.dubbo.apache.org/imgs/v3/tasks/observability/admin/2-service-test2.png)

![](https://cn.dubbo.apache.org/imgs/v3/tasks/observability/admin/2-service-test.png)

![](https://cn.dubbo.apache.org/imgs/v3/tasks/observability/admin/2-service-doc.png)

## References
- [Apache Dubbo](https://cn.dubbo.apache.org/zh-cn/)
- [Apache Dubbo GitHub](https://github.com/apache/dubbo)
- [Apache Dubbo Admin Docker](https://hub.docker.com/r/apache/dubbo-admin)
- [Admin 整体架构与安装步骤](https://cn.dubbo.apache.org/zh-cn/overview/reference/admin/architecture/)
- [使用 Admin 可视化查看集群状态](https://cn.dubbo.apache.org/zh-cn/overview/tasks/observability/admin/)