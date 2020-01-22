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

### 1 处理配置
`ReferenceConfig-init()`: 配置解析逻辑
1. 检测 `ConsumerConfig` 实例是否存在，如不存在则创建一个新的实例，然后通过系统变量或 `dubbo.properties` 配置文件填充 `ConsumerConfig` 的字段。接着是检测泛化配置，并根据配置设置 `interfaceClass` 的值
2. 从系统属性或配置文件中加载与接口名相对应的配置，并将解析结果赋值给 `url` 字段。`url` 字段的作用一般是用于点对点调用
3. 检测几个核心配置类是否为空，为空则尝试从其他配置类中获取
4. 收集各种配置，并将配置存储到 `map` 中
5. 处理 `MethodConfig` 实例。该实例包含了事件通知配置
6. 解析服务消费者 ip，以及调用 `createProxy` 创建代理对象

### 2 引用服务
`ReferenceConfig-createProxy(Map<String, String> map)`: 创建代理对象，调用其他方法构建以及合并 `Invoker` 实例
- 本地引用
- 远程引用
	* 点对点调用
	* 加载注册中心 `url`
- 通过 `Cluster` 合并多个 `Invoker`
- 调用 `ProxyFactory` 生成代理类

#### 2.1 创建 Invoker
`Invoker` 是 Dubbo 的核心模型，代表一个可执行体。在服务提供方，`Invoker` 用于调用服务提供类。在服务消费方，`Invoker` 用于执行远程调用

`DubboProtocol`
- `DubboProtocol+refer(Class<T> serviceType, URL url)`
- `DubboProtocol-getClients(URL url)`: 用于获取客户端实例，实例类型为 `ExchangeClient`。`ExchangeClient` 实际上并不具备通信能力，它需要基于更底层的客户端实例进行通信。默认 `NettyClient`
- `DubboProtocol-getSharedClient(URL url)`: 获取共享客户端
- `DubboProtocol-initClient(URL url)`: 初始化新的客户端
- `Exchangers+connect(URL url, ExchangeHandler handler)`: 创建 `ExchangeClient` 客户端
- `Exchangers+getExchanger(URL url)`: 通过 SPI 加载 `HeaderExchangeClient` 实例
- `HeaderExchanger+connect(URL url, ExchangeHandler handler)`:
	1. 创建 `HeaderExchangeHandler` 对象
	2. 创建 `DecodeHandler` 对象
	3. 通过 `Transporters` 构建 `Client` 实例
	4. 创建 `HeaderExchangeClient` 对象
- `Transporters+connect(URL url, ChannelHandler... handlers)`
- `Transporters+getTransporter()`: 在运行时根据客户端类型加载指定的 `Transporter` 实现类。默认加载 `NettyTransporter`

`RegistryProtocol`
- `RegistryProtocol+refer(Class<T> type, URL url)`
	* `url` 设置协议头
	* 根据 `url` 参数加载注册中心实例
	* 获取 `group` 配置，根据 `group` 配置决定 `doRefer` 第一个参数的类型
- `RegistryProtocol-doRefer(Cluster cluster, Registry registry, Class<T> type, URL url)`
	* 创建一个 `RegistryDirectory` 实例，然后生成服务者消费者链接，并向注册中心进行注册
	* 注册完毕后，紧接着订阅 `providers`、`configurators`、`routers` 等节点下的数据
	* 完成订阅后，`RegistryDirectory` 会收到这几个节点下的子节点信息
	* 由于一个服务可能部署在多台服务器上，这样就会在 `providers` 产生多个节点，这个时候就需要 `Cluster` 将多个服务节点合并为一个，并生成一个 `Invoker`

#### 2.2 创建代理
- `ProxyFactory#getProxy(Invoker<T> invoker)`
- `AbstractProxyFactory+getProxy(Invoker<T> invoker, boolean generic)`: 用来获取 `interfaces` 数组
- `JavassistProxyFactory+getProxy(Invoker<T> invoker, Class<?>[] interfaces)`: 生成 `Proxy` 子类（`Proxy` 是抽象类）。并调用 `Proxy` 子类的 `newInstance` 方法创建 `Proxy` 实例
- `InvokerInvocationHandler+InvokerInvocationHandler(Invoker<?> handler)`: 实现 JDK 的 `InvocationHandler` 接口，用于拦截接口类调用
- `Proxy+getProxy(Class<?>... ics)`: 调用重载方法
- `Proxy+getProxy(ClassLoader cl, Class<?>... ics)`:
	* `ccp`: 用于为服务接口生成代理类
	* `ccm`: 用于为 `org.apache.dubbo.common.bytecode.Proxy` 抽象类生成子类

```
package org.apache.dubbo.common.bytecode;

public class proxy0 implements org.apache.dubbo.demo.DemoService {
    public static java.lang.reflect.Method[] methods;
    private java.lang.reflect.InvocationHandler handler;

    public proxy0() {
    }

    public proxy0(java.lang.reflect.InvocationHandler arg0) {
        handler = $1;
    }

    public java.lang.String sayHello(java.lang.String arg0) {
        Object[] args = new Object[1];
        args[0] = ($w) $1;
        Object ret = handler.invoke(this, methods[0], args);
        return (java.lang.String) ret;
    }
}
```

## References
- [服务引入](http://dubbo.apache.org/zh-cn/docs/source_code_guide/refer-service.html)