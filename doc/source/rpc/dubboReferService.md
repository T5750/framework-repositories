# Dubbo Refer Service

## 简介
通过两种方式引用远程服务：
1. 使用服务直连的方式引用服务，仅适合在调试或测试服务的场景下使用，不适合在线上环境使用
2. 基于注册中心进行引用

## 服务引用原理
Dubbo 服务引用的时机有两个：
1. 饿汉式：在 Spring 容器调用 `ReferenceBean` 的 `afterPropertiesSet` 方法时引用服务。可通过配置 `<dubbo:reference>` 的 `init` 属性开启
2. 懒汉式：在 `ReferenceBean` 对应的服务被注入到其他类中时引用。默认情况下，Dubbo 使用懒汉式引用服务

按照 Dubbo 默认配置，整个分析过程从 `ReferenceBean` 的 `getObject` 方法开始。
- 先进行配置检查与收集工作
- 根据收集到的信息决定服务用的方式，有三种：
	1. 引用本地 (JVM) 服务
	2. 通过直连方式引用远程服务
	3. 通过注册中心引用远程服务
    * 最后都会得到一个 `Invoker` 实例
- 多个注册中心，多个服务提供者，通过集群管理类 `Cluster` 将多个 `Invoker` 合并成一个实例
- 通过代理工厂类 (`ProxyFactory`) 为服务接口生成代理类，并让代理类去调用 `Invoker` 逻辑

## 源码分析
`ReferenceBean+getObject()`: 服务引用的入口方法

### 处理配置
`ReferenceConfig-init()`: 配置解析逻辑
1. 检测 `ConsumerConfig` 实例是否存在，如不存在则创建一个新的实例，然后通过系统变量或 `dubbo.properties` 配置文件填充 `ConsumerConfig` 的字段。接着是检测泛化配置，并根据配置设置 `interfaceClass` 的值
2. 从系统属性或配置文件中加载与接口名相对应的配置，并将解析结果赋值给 `url` 字段。`url` 字段的作用一般是用于点对点调用
3. 检测几个核心配置类是否为空，为空则尝试从其他配置类中获取
4. 收集各种配置，并将配置存储到 `map` 中
5. 处理 `MethodConfig` 实例。该实例包含了事件通知配置
6. 解析服务消费者 ip，以及调用 `createProxy` 创建代理对象

### 引用服务
1. 创建 `Invoker`
2. 创建代理


## References
- [服务引入](http://dubbo.apache.org/zh-cn/docs/source_code_guide/refer-service.html)