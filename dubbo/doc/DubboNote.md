# Dubbo笔记

## 1.1 Dubbo
Apache Dubbo™ (incubating)是一款高性能Java RPC框架。

## 1.2 Dubbo简介
Apache Dubbo (incubating)是一款高性能、轻量级的开源Java RPC框架，它提供了三大核心能力：
- 面向接口的远程方法调用
- 智能容错和负载均衡
- 服务自动注册和发现

### 特性一览
- 面向接口代理的高性能RPC调用：提供高性能的基于代理的远程调用能力，服务以接口为粒度，为开发者屏蔽远程调用底层细节。
- 智能负载均衡：内置多种负载均衡策略，智能感知下游节点健康状况，显著减少调用延迟，提高系统吞吐量。
- 服务自动注册与发现：支持多种注册中心服务，服务实例上下线实时感知。
- 高度可扩展能力：遵循微内核+插件的设计原则，所有核心能力如Protocol、Transport、Serialization被设计为扩展点，平等对待内置实现和第三方实现。
- 运行期流量调度：内置条件、脚本等路由策略，通过配置不同的路由规则，轻松实现灰度发布，同机房优先等功能。
- 可视化的服务治理与运维：提供丰富服务治理、运维工具：随时查询服务元数据、服务健康状态及调用统计，实时下发路由策略、调整配置参数。

## 1.3 Dubbo用途
1. 透明化的远程方法调用，就像调用本地方法一样调用远程方法，只需简单配置，没有任何API侵入。
1. 软负载均衡及容错机制，可在内网替代F5等硬件负载均衡器，降低成本，减少单点。
1. 服务自动注册与发现，不再需要写死服务提供方地址，注册中心基于接口名查询服务提供者的IP地址，并且能够平滑添加或删除服务提供者。
1. Dubbo采用全Spring配置方式，透明化接入应用，对应用没有任何API侵入，只需用Spring加载Dubbo的配置即可，Dubbo基于Spring的Schema扩展进行加载。

## 1.4 Dubbo架构
![architecture](http://dubbo.incubator.apache.org/img/architecture.png)

### 节点角色说明
节点 | 角色说明
----|------
Provider | 暴露服务的服务提供方
Consumer | 调用远程服务的服务消费方
Registry | 服务注册与发现的注册中心
Monitor | 统计服务的调用次数和调用时间的监控中心
Container | 服务运行容器

### 调用关系说明
1. 服务容器负责启动，加载，运行服务提供者。
1. 服务提供者在启动时，向注册中心注册自己提供的服务。
1. 服务消费者在启动时，向注册中心订阅自己所需的服务。
1. 注册中心返回服务提供者地址列表给消费者，如果有变更，注册中心将基于长连接推送变更数据给消费者。
1. 服务消费者，从提供者地址列表中，基于软负载均衡算法，选一台提供者进行调用，如果调用失败，再选另一台调用。
1. 服务消费者和提供者，在内存中累计调用次数和调用时间，定时每分钟发送一次统计数据到监控中心。

## 1.5 Dubbo入门
- [第一个 Dubbo 应用](http://dubbo.apache.org/zh-cn/blog/dubbo-101.html) - A 101 tutorial to reveal more details, with the same code above.
- [Dubbo 用户文档](http://dubbo.apache.org/zh-cn/docs/user/preface/background.html) - How to use Dubbo and all its features.
- [Dubbo 开发者指南](http://dubbo.apache.org/zh-cn/docs/dev/build.html) - How to invovle in Dubbo development.
- [Dubbo 运维管理](http://dubbo.apache.org/zh-cn/docs/admin/install/provider-demo.html) - How to admin and manage Dubbo services.

Dubbo下载地址：[https://github.com/apache/incubator-dubbo](https://github.com/apache/incubator-dubbo)

### Dubbo管控台
[管理控制台安装](http://dubbo.incubator.apache.org/zh-cn/docs/admin/install/admin-console.html)
- 下载`dubbo-admin-2.5.3.war`
- 上传到服务器中的`tomcat/webapps`下
- 新建文件夹：`dubbo-admin`
- 解压：`unzip dubbo-admin.war -d dubbo-admin`
- 删除：`rm -rf dubbo-admin.war`
- 修改配置文件：`vim dubbo-admin/WEB-INF/dubbo.properties`
- 修改内容：ZooKeeper注册中心地址以及用户名密码

## 1.6 monitor监控中心
- [dubbo-admin](https://github.com/apache/dubbo-admin)

## 2 Dubbo 2.6.x
### Results
- [http://localhost:8082/dubbo-consumer/sample/hello](http://localhost:8082/dubbo-consumer/sample/hello)
- [http://localhost:8082/dubbo-consumer/sample/users](http://localhost:8082/dubbo-consumer/sample/users)
- [http://localhost:8082/dubbo-consumer/direct/print](http://localhost:8082/dubbo-consumer/direct/print)
- [http://localhost:8082/dubbo-consumer/dependency/print](http://localhost:8082/dubbo-consumer/dependency/print)

## 3 Dubbo Admin
1. 下载代码：`git clone https://github.com/apache/dubbo-admin.git`
2. 在`dubbo-admin-server/src/main/resources/application.properties`中指定注册中心地址
3. 构建：
    > - `mvn clean package`
4. 启动：`mvn --projects dubbo-admin-server spring-boot:run`
5. 访问：[http://localhost:8080](http://localhost:8080)

### Swagger support
- [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## References
- [Apache Dubbo](http://dubbo.incubator.apache.org/zh-cn/)
- [Dubbo 2.6.x](https://github.com/apache/dubbo/releases)
- [dubbo-spring-boot-starter 0.1.x](https://github.com/apache/dubbo-spring-boot-project)
- [dubbo-admin](https://github.com/apache/dubbo-admin)