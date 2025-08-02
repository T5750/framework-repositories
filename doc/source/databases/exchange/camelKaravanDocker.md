# Camel Karavan Docker

Apache Camel Karavan is a Low-Code Data Integration Platform

## Docker
```sh
docker run -it -p 8080:8080 --restart always -v $(pwd):/deployments/integrations ghcr.io/apache/camel-karavan:latest
docker run -it --rm --name karavan -p 8080:8080 -e KARAVAN_GIT_INSTALL_GITEA=true -e KARAVAN_IMAGE_REGISTRY_INSTALL=true ghcr.io/apache/camel-karavan
docker run -it --rm --name karavan -p 8080:8080 -e KARAVAN_GIT_INSTALL_GITEA=true -e KARAVAN_IMAGE_REGISTRY_INSTALL=true -e KARAVAN_ENVIRONMENT=prod -e KARAVAN_KUBERNETES_ENABLED=false -e TZ=Asia/Shanghai ghcr.io/apache/camel-karavan:3.21.0
```
[http://localhost:8080/](http://localhost:8080/)

## Enterprise Integration Patterns
Messaging Systems:
- Message Channel - 消息通道
- Message - 消息
- Pipes and Filters - 管道和过滤器
- Message Router - 消息路由器
- Message Translator - 消息转换器
- Message Endpoint - 消息端点

## Kamelets
Kamelets 有三种不同类型：
- `Source` - 生成数据的路由。例如：`Kafka Source` - Receive data from Kafka topics
- `Sink` - 消耗数据的路由。例如：`Kafka Sink`- Send data to Kafka topics
- `Action` - 对数据执行操作的路由。例如：`Kafka Manual Commit Action` - Manually commit Kafka Offset

## Camel
Camel is an Open Source integration framework that empowers you to quickly and easily integrate various systems consuming or producing data.

### CamelContext
- `CamelContext`: Camel运行时的核心部分(routes, endpoints, components, etc)
- `Routes`: 路由定义了消息从一个端点(`from`)流向另一个端点(`to`)的路径
- `Domain Specific Language (DSL)`: Java, XML, YAML
- `Processors`: 处理器用于在端点之间对消息进行处理
- `Endpoints`: 端点代表消息的源或目的地
- `Components`: 组件负责创建端点，用于处理不同的协议或技术

### Architecture
![](https://camel.apache.org/manual/_images/images/camel-architecture.png)

![](https://camel.apache.org/manual/_images/images/camel-context.png)

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)
- [Quarkus](https://quarkus.io/)
- [TypeScript](https://www.typescriptlang.org/)
- [Camel 3.22.x](https://camel.apache.org/download/)

## Screenshots
![](https://gitee.com/mirrors_apache/camel-karavan/raw/main/images/karavan-clouds-large.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-about.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-ipaas-1.png)

![](https://gitee.com/mirrors_apache/camel-karavan/raw/3.18.4/images/karavan-vscode.png)

## References
- [Camel Karavan GitHub](https://github.com/apache/camel-karavan)
- [Camel Karavan Examples](https://github.com/apache/camel-karavan/tree/main/karavan-demo)
- [Camel Karavan Gitee](https://gitee.com/mirrors_apache/camel-karavan)
- [Camel Enterprise Integration Patterns](https://camel.apache.org/components/3.22.x/eips/enterprise-integration-patterns.html)
- [Camel Architecture](https://camel.apache.org/manual/architecture.html)
- [CamelContext](https://camel.apache.org/manual/camelcontext.html)
- [Kamelets 概述](https://docs.redhat.com/zh-cn/documentation/red_hat_integration/2023.q2/html/integrating_applications_with_kamelets/overview-kamelets)
- [EIP Messaging Patterns](https://www.enterpriseintegrationpatterns.com/patterns/messaging/)