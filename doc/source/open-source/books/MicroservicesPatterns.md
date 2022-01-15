# 微服务架构设计模式

1. 要记住微服务不是解决所有问题的万能“银弹”
2. 编写整洁的代码和使用自动化测试至关重要，因为这是现代软件开发的基础
3. 关注微服务的本质，即服务的分解和定义，而不是技术，如容器和其他工具
4. 确保你的服务松耦合，并且可以独立开发、测试和部署，不要搞成分布式单体，那将会是巨大的灾难
5. 也是最重要的，不能只是在技术上采用微服务架构。拥抱DevOps的原则和实践，在组织结构上实现跨职能的自治团队，这必不可少

还必须记住，实现微服务架构并不是你的目标。你的目标是加速大型复杂应用程序的开发。

## 第1章 逃离单体地狱
### 1.1 迈向单体地狱的漫长旅程
图1-1

### 1.2 为什么本书与你有关

### 1.3 你会在本书中学到什么

### 1.4 拯救之道：微服务架构
图1-3

图1-7

表1-1

### 1.5 微服务架构的好处和弊端
“两个比萨”原则是指某个事情的参与人数不能多到两个比萨饼还不够他们吃饱的地步。并非参与人数越多越好

### 1.6 微服务架构的模式语言

### 1.7 微服务之上：流程和组织

## 第2章 服务的拆分策略
### 2.1 微服务架构到底是什么
> 计算机系统的软件架构是构建这个系统所需要的一组结构，包括软件元素、它们之间的关系以及两者的属性。

图2-3

图2-5～2-6

### 2.2 为应用程序定义微服务架构
根据业务能力进行服务拆分

根据子域进行服务拆分

> 单一职责原则：改变一个类应该只有一个理由

> 闭包原则：在包中包含的所有类应该是对同类的变化的一个集合，也就是说，如果对包做出修改，需要调整的类应该都在这个包之内

## 第3章 微服务架构中的进程间通信
### 3.1 微服务架构中的进程间通信概述
表3-1

#### 3.1.4 消息的格式
* 基于文本的消息格式：JSON和XML
* 二进制消息格式：Protocol和Avro

### 3.2 基于同步远程过程调用模式的通信
* REST
* gRPC

图3-6

### 3.3 基于异步消息模式的通信
图3-7

#### 3.3.2 使用消息机制实现交互方式
* 实现请求 / 响应和异步请求 / 响应
* 实现单向通知
* 实现发布 / 订阅
* 实现发布 / 异步响应

图3-8

#### 3.3.4 使用消息代理
表3-2

#### 3.3.5 处理并发和消息顺序
消息代理使用的常见解决方案是使用分片（分区）通道

图3-11

每个Order事件消息都将orderId作为其分片键。特定订单的每个事件都发布到同一个分片，而且该分片中的消息始终由同一个接收方实例读取

#### 3.3.6 处理重复消息
* 编写幂等消息处理器
* 跟踪消息并丢弃重复消息
    - 消息接收方使用message id跟踪它已处理的消息并丢弃任何重复项
    - 消息处理程序在应用程序表，而不在专用表中记录message id

图3-12

#### 3.3.7 事务性消息
* 使用数据库表作为消息队列
* 通过轮询模式发布事件
* 使用事务日志拖尾模式发布事件
    - [Debezium](https://debezium.io/)
    - [LinkedIn Databus](https://github.com/linkedin/databus)
    - [DynamoDB streams](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Streams.html)
    - [Eventuate Tram](https://github.com/eventuate-tram/eventuate-tram-core)

### 3.4 使用异步消息提高可用性
* 使用异步交互模式
* 复制数据
* 先返回响应，在完成处理

图3-16～3-18

## 第4章 使用Saga管理事务
### 4.1 微服务架构下的事务管理
Saga是一种在微服务架构中维护数据一致性的机制，避免分布式事务带来的问题

### 4.2 Saga的协调模式
#### 4.2.1 协同式Saga
图4-4～4-5

#### 4.2.2 编排式Saga
编排器类的唯一职责就是告诉Saga的参与方该做什么事情

图4-6～4-7

### 4.3 解决隔离问题
Saga只满足ACD三个属性

#### 4.3.2 Saga模式下实现隔离的对策
* 语义锁：应用程序级的锁
* 交换式更新：把更新操作设计成可以按任何顺序执行
* 悲观视图：重新排序Saga的步骤，以最大限度地降低业务风险
* 重读值：通过重写数据来防止脏读，以在覆盖数据之前验证它是否保持不变
* 版本文件：将更新记录下来，以便可以对它们重新排序
* 业务风险评级：使用每个请求的业务风险来动态选择并发机制

### 4.4 Order Service和Create Order Saga的设计
图4-9

#### 4.4.2 Create Order Saga实现
图4-11～4-12

#### 4.4.3 OrderCommandHandlers类
图4-15

简单的Saga可以使用协同式，但编排式通常是复杂Saga的更好选择

## 第5章 微服务架构中的业务逻辑设计
聚合是一组对象，可以作为一个单元来处理

### 5.1 业务逻辑组织模式
#### 5.1.3 关于领域驱动设计
DDD

### 5.2 使用聚合模式设计领域模型
#### 5.2.3 聚合的规则
* 规则一：只引用聚合根
* 规则二：聚合间的引用必须使用主键
* 规则三：在一个事务中，只能创建或更新一个聚合

图5-6

图5-9

### 5.3 发布领域事件
[韦博词典](https://www.merriam-webster.com/dictionary/event)

事件风暴三个步骤：
1. 头脑风暴
2. 识别事件触发器
3. 识别聚合

[Spring Data Ingalls](https://spring.io/blog/2017/01/30/what-s-new-in-spring-data-release-ingalls)实现了一种自动发布事件到Spring ApplicationContext的机制

### 5.4 Kitchen Service的业务逻辑
图5-11

### 5.5 Order Service的业务逻辑
图5-11～5-12

## 第6章 使用事件溯源开发业务逻辑
### 6.1 使用事件溯源开发业务逻辑概述
事件溯源将聚合以一系列事件的方式持久化保存

事件溯源是一种以事件为中心的技术，用于实现业务逻辑和聚合的持久化

图6-2

#### 6.1.7 领域事件的演化
表6-1

[Flyway](https://flywaydb.org/)

### 6.2 实现事件存储库
* [Event Store](https://www.eventstore.com/)
* [Lagom](https://www.lagomframework.com/)
* [Axon](https://axoniq.io/)
* [Eventuate](https://eventuate.io/)

#### 6.2.1 Eventuate Local事件存储库的工作原理
图6-9

#### 6.2.2 Eventuate的Java客户端框架
图6-10

### 6.3 同时使用Saga和事件溯源
集成事件溯源和基于编排的Saga的难易程度，关键取决于事件存储库是使用关系型数据库还是NoSQL数据库

#### 6.3.2 创建编排式Saga
图6-11

#### 6.3.3 实现基于事件溯源的Saga参与方
图6-12

#### 6.3.4 实现基于事件溯源的Saga编排器
图6-13

## 第7章 在微服务架构中实现查询
### 7.1 使用API组合模式进行查询
> API组合：通过查询每个服务的API并组合结果，实现从多个服务检索数据的查询

#### 7.1.3 使用API组合模式实现findOrder()查询操作
图7-3

#### 7.1.4 API组合模式的设计缺陷
图7-4～7-6

### 7.2 使用CQRS模式
> 命令查询职责隔离CQRS：使用事件来维护从多个服务复制数据的只读视图，借此实现对来自多个服务的数据的查询

[DynamoDB](https://github.com/awslabs/dynamodb-geo)

#### 7.2.2 什么是CQRS
图7-8～7-9

### 7.3 设计CQRS视图
图7-10

#### 7.3.1 选择视图存储库
表7-1

### 7.4 实现基于AWS DynamoDB的CQRS视图
#### 7.4.2 DynamoDB中的数据建模和查询设计
图7-13～7-14

## 第8章 外部API模式
### 8.1 外部API的设计难题

### 8.2 API Gateway模式
> API Gateway：实现一个服务，该服务是外部API客户端进入基于微服务应用程序的入口

#### 8.2.1 什么是API Gateway模式
API Gateway负责请求路由、API组合和协议转换

图8-3～8-7

> 后端前置：为每种类型的客户端实现单独的API Gateway

#### 8.2.4 API Gateway的设计难题
JVM的响应式抽象包括：
* Java 8 CompletableFutures
* Project Reactor Monos
* Netflix RxJava Observable
* Scala Futures

### 8.3 实现一个API Gateway

#### 8.3.2 开放自己的API Gateway
* [Netflix Zuul](https://github.com/Netflix/zuul)
* [Spring Cloud Gateway](https://spring.io/projects/spring-cloud-gateway)

图8-8

#### 8.3.3 使用GraphQL实现API Gateway
图8-9

* [GraphQL](https://graphql.org/)：API由基于图形的模式组成
* [Netflix Falcor](https://github.com/Netflix/falcor)：将服务器端数据建模为虚拟JSON对象图
* Netflix Falcor是一种实现，GraphQL是一种标准
* [Apollo GraphQL](https://www.apollographql.com/)：实现了GraphQL规范的一些强大扩展

图8-10

客户端可以准确指定要返回的数据，包括转换相关对象的字段

图8-11

[DataLoader](https://github.com/graphql/dataloader)

## 第9章 微服务架构中的测试策略（上）
### 9.1 微服务架构中的测试策略概述
> 测试用例是用于特定目标的一组测试输入、执行条件和预期结果，例如执行特定的程序路径或验证是否符合特定要求

图9-4～9-6

> 消费者驱动的契约测试：验证服务是否满足它的消费者的期望

* [Spring Cloud Contract](https://spring.io/projects/spring-cloud-contract)
* [Pact](https://github.com/pact-foundation)

### 9.2 为服务编写单元测试
图9-10～9-11

[Eventuate Tram Saga](https://github.com/eventuate-tram/eventuate-tram-sagas)

## 第10章 微服务架构中的测试策略（下）
### 10.1 编写集成测试
图10-1

表10-1

图10-3～10-5

### 10.2 编写组件测试
图10-6～10-7

### 10.3 端到端测试
图10-8

## 第11章 开发面向生产环境的微服务应用
### 11.1 开发安全的服务
[Passport](https://www.passportjs.org/)

图11-3～11-5

### 11.2 设计可配置的服务
图11-8

### 11.3 设计可观测的服务
图11-9

#### 11.3.2 使用日志聚合模式
* Elasticsearch：面向文本搜索的NoSQL数据库，用作日志记录服务器
* Logstash：聚合服务日志并将其写入Elasticsearch的日志流水线
* Kibana：Elasticsearch的可视化工具
* [Fluentd](https://www.fluentd.org/)
* [Apache Flume](https://flume.apache.org/)

#### 11.3.3 使用分布式追踪模式
图11-13

#### 11.3.5 使用异常追踪模式
* [Honeybadger](https://www.honeybadger.io/)
* [Sentry](https://sentry.io/)

### 11.4 使用微服务基底模式开发服务
图11-16

#### 11.4.1 使用微服务基底
* [Go kit](https://github.com/go-kit/kit)
* [Micro](https://github.com/micro/micro)

> 服务网格：把所有进出服务的网络流量通过一个网络层进行路由，这个网络层负责解决包括断路器、分布式追踪、服务发现、负载均衡和基于规则的流量路由等具有共性的需求

图11-17

* [Istio](https://istio.io/)
* [Linkerd](https://linkerd.io/)
* Conduit

## 第12章 部署微服务应用
图12-1

### 12.1 部署模式：编程语言特定的发布包格式
* [Netflix Aminator](https://github.com/Netflix/aminator)
* [Packer](https://www.packer.io/)

图12-6

### 12.2 部署模式：将服务部署为虚拟机

### 12.3 部署模式：将服务部署为容器
图12-7

### 12.4 使用Kubernetes部署FTGO应用程序
图12-9～12-12

[Istio](https://istio.io/)：一个连接、管理和保护微服务的开放平台
* 流量管理
* 通信安全
* 遥测
* 策略执行

[Envoy Proxy](https://www.envoyproxy.io/)

### 12.5 部署模式：Serverless部署
* [Google Cloud Functions](https://cloud.google.com/functions/)
* [Microsoft Azure](https://azure.microsoft.com/en-us/services/functions)
* [Apache OpenWhisk](https://openwhisk.apache.org/)
* [Fission for Kubernetes](https://fission.io/)

### 12.6 使用AWS Lambda和AWS Gateway部署RESTful服务
图12-13

[Serverless](https://github.com/serverless/serverless)

## 第13章 微服务架构的重构策略
### 13.1 重构到微服务需要考虑的问题

#### 13.1.2 绞杀单体应用
> 推倒重写的唯一保证，就是彻底搞砸一切

### 13.2 将单体应用重构为微服务架构的若干策略

#### 13.2.1 将新功能实现为服务
挖坑法则：如果你发现自己已经陷入了困境，就不要再给自己继续挖坑了

图13-2～13-4

图13-7

### 13.3 设计服务与单体的协作方式
图13-8

图13-10～13-13

> 反腐层：一个软件层，用于在两个不同的领域模型之间进行转换，防止一个模型的概念污染另一个模型

### 13.4 将新功能实现为服务：处理错误配送订单
图13-14～13-15

### 13.5 从单体中提取送餐管理功能
图13-16～13-20

## Microservice patterns
Microservices Patterns:With Examples in Java

![](https://microservices.io/i/Microservices-Patterns-Cover-published.png)

## References
- [Microservices patterns](https://microservices.io/book)
- [microservices-patterns/ftgo-application](https://github.com/microservices-patterns/ftgo-application)