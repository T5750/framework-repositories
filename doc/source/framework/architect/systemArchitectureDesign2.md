# 系统架构设计笔记 Part 2

_第二篇 软件架构设计知识体系_

_2 软件架构体系结构_

## 2.1 软件体系结构

## 2.2 架构风格
分布式
1. 消息传递（Messaging）
2. 发布-订阅（Publish-Subscribe, Pub-Sub）
3. Broker, RPC

表2-1 分布式风格对比

分布式风格 | 通信方式 | 通信关系 | 组件依赖性
---|---|---|---
Broker | 远程方法调用 | 一对一 | 组件接口
消息传递（Messaging） | 消息 | 一对一或一对多 | 消息格式
发布-订阅（Pub-Sub） | 事件 | 一对多 | 事件格式

事件驱动（Event Driven Architecture, EDA）
1. 事件处理系统的抽象和设计
2. IO操作与事件驱动
3. Reactor模式

图2-8 操作系统IO模型

图2-9 Reactor模式

消息总线（Message Bus）是系统的连接件，负责消息的分派、传递和过滤，并返回处理结果

图2-15 消息总线风格

图2-16 管道-过滤器风格

图2-18 管道与派发风格

## 2.3 架构模式
1. 数据访问
2. 服务定位
3. 异步化
4. 资源管理
5. 依赖管理

## 2.4 架构模型
1. 领域模型
2. 设计模型
3. 代码模型

_3 领域驱动设计_

## 3.1 面向领域思想
领域驱动设计思想的核心：认为系统架构应该是业务架构和技术架构想结合的一种过程，并提供了一系列的设计相关工具和模式确保实现这一过程

## 3.2 面向领域的策略设计

## 3.3 面向领域的技术设计

## 3.4 案例实现

_4 分布式系统架构设计_

## 4.1 分布式系统
图4-1 纵向拆分

图4-2 横向拆分

## 4.2 RPC架构
1. 网络通信
2. 序列化
3. 传输协议
4. 服务调用

图4-10 Java Future机制

图4-11 Future-Get串行模式时序图

图4-12 Future-Listener并行模式

图4-13 Future-Get VS Future-Listener时间成本

- Future-Get总时间：`T=T1+T2+T3`
- Future-Listener总时间：`T=Max(T1, T2, T3)`
- Future-Listener时间成本小于Future-Get

## 4.3 分布式服务架构
ZooKeeper
1. 模型。ZooKeeper中所有数据通过路径被引用，具备原子性、顺序性
2. 操作。ZNode是ZooKeeper程序控制的主要实体
3. 实现。ZAB协议（ZooKeeper Atomic Broadcast Protocol）包括两个可无限重复的阶段
    1. 领导者选举（Leader election），用于服务启动或Leader崩溃的场景
    2. 原子广播（Atomic broadcast），用于对每个更新操作进行结果状态同步

图4-19 ZAB协议示意图

图4-20 服务发布订阅流程

协议 | HTTP协议 | RMI协议 | Hessian协议 | Dubbo协议
---|---|---|---|---
连接个数 | 多连接 | 多连接 | 多连接 | 单连接
连接方式 | 短连接 | 短连接 | 短连接 | 长连接
传输协议 | HTTP协议 | TCP协议 | HTTP协议 | TCP协议
传输方式 | 同步传输 | 同步传输 | 同步传输 | NIO异步传输
序列化 | 表单序列化 | Java标准二进制序列化 | Hessian二进制序列化 | Hessian二进制序列化
适用范围 | 传入传出参数数据包大小混合，可用浏览器查看，可用表单或URL传入参数 | 传入传出参数数据包大小混合，消费者与提供者个数差不多，可传文件 | 传入传出参数数据包较大，提供者压力较大，可传文件 | 小数据量、消费者数远大于提供者
适用场景 | 需同时给应用程序/浏览器使用的服务 | 常规远程服务方法调用，与原生RMI服务互操作 | 页面传输，文件传输，或与原生hessian服务互操作 | 常规远程服务方法调用

## 4.4 分布式服务架构Dubbo剖析
图4-28 Dubbo组件分层

图4-29 Microkernel架构风格

微内核（Microkernel）和管道-过滤器风格一样同属于扩展性架构风格，包括两部分组件：
1. 内核系统（Core system）
2. 插件（Plugin Component）

图4-35 Dubbo集群实现方案

## 4.5 微服务架构
微服务架构（Microservice Architecture）具备业务独立、进程隔离、团队自主、技术无关轻量级通信和交付独立性等“微”特性。

_5 架构实现技术体系_

## 5.1 缓存与性能优化
图5-3 分布式缓存的两种表现形式

Memcached是高性能内存对象缓存系统，基于存储键/值对的HashMap进行内存数据结构组织

图5-7 Memcached分布式示意图

Memcached中的客户端分布式算法采用两阶段Hash的机制实现上述get和set服务

图5-8 两阶段Hash机制

图5-17 Redis Sentinel示意图

图5-19 nginx服务器架构 

## 5.2 消息传递系统
JMS（Java Messaging Service，Java消息服务）基于消息传递语义，提供了一整套经过抽象的公共API

图5-22 JMS公共API

图5-22 JMS Queue使用场景

JMS规范中的点对点模型表现为队列（Queue），队列提供了一对一顺序发送和消费机制。可应用于即发即弃场景

主题（Topic）是JMS规范中对发布-订阅模型的抽象，JMS同样提供了专门的`TopicPublisher`和`TopicSubscriber`。用于更新、事件、通知等非响应式请求场景

JMS中的消息确认有3种模式：
1. `AUTO_ACKNOWLEDGE`: 消息发送有且仅有一次
2. `DUPS_OK_ACKNOWLEDGE`: 消息可发送多次以避免额外开销
3. `CLIENT_ACKNOWLEDGE`: 消费者使用JMS提供的API进行显示确认

图5-24 `AUTO_ACKNOWLEDGE`模式示意图

AMQP，即高级消息队列规范（Advanced Message Queuing Protocol），是一个提供统一消息服务的应用层标准高级消息队列规范。指定了6种Exchange类型：
1. 直接式交换器类型（Direct Exchange）通过精确匹配消息的Routing Key，将消息路由到零个或者多个队列中，由Binding Key将队列和交换器绑定到一起
2. 广播式交换器类型（Fanout Exchange）比较简单：不论消息的Routing Key是什么，这条消息都会被路由到所有与该交换器绑定的队列中
3. 主题式交换器类型（Topic Exchange）通过消息的Routing Key和Binding Key的模式匹配，将消息路由到被绑定的队列中。用来支持经典的发布-订阅消息传输模型
4. 消息头式交换器类型（Header Exchange）提供了复杂的、多重部分表达式路由，它的路由机制基于AMQP消息头属性，而不依赖于Routing Key和Binding Key的匹配规则
5. 系统交换器类型
6. 自定义交换类型

Kafka面向的对象是海量日志和网站活跃数据，通过轻量、精炼的基础架构能够同时处理离线和在线数据
- Kafka认为处理海量数据的性能瓶颈在于大量的网络请求和过多的字节拷贝。解决消息数量过大的思路是把消息分组，把一组消息批量发给消费者，而字节拷贝则使用sendfile系统调用进行优化
- 持久化策略上，Kafka使用Segment File日志文件来保存消息，消息直接添加到文件尾部，属于顺序写磁盘，效率非常高

## 5.3 企业服务总线
图5-36 服务总线解决方案

表5-1 路由器总览

路由器 | 消费消息数 | 发布消息数 | 有无状态
---|---|---|---
内容路由器 | 1 | 1 | 无
过滤器 | 1 | 0或1 | 无
接收表 | 1 | 0或n | 无
分解器 | 1 | n | 有
聚合器 | n | 1 | 有
重排器 | n | n | 有

## 5.4 数据分享处理
图5-46 批处理时序图

表5-2 集成方式与Spring技术体系

集成方式 | Spring技术体系
---|---
文件传输 | Spring资源抽象组件、Spring Batch
共享数据库 | Spring数据访问组件（JDBC、ORM、事务）
RPC | Spring remoting(RMI、HttpInvoker、Hession)
消息传递 | Spring Jmstemplate, Spring Integration

## 5.5 安全性
图5-54 单向散列加密与密码校验

1. 加密算法
    1. 单向散列加密：MD5, SHA
    2. 对称加密：DES, AES
    3. 非对称加密：RSA
2. 认证
    1. 摘要认证
    2. 签名认证
3. 协议
    1. HTTPS
    2. OAuth

图5-56 摘要认证流程

图5-57 签名认证流程

图5-58 SSL/TLS协议握手阶段

图5-59 OAuth协议时序图

## References
- 《系统架构设计：程序员向架构师转型之路》