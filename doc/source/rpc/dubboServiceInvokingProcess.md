# Dubbo Service Invoking Process

## 简介
Dubbo 服务调用过程比较复杂，包含众多步骤，比如发送请求、编解码、服务降级、过滤器链处理、序列化、线程派发以及响应请求等

## 源码分析
![send-request-process-min](https://s1.wailian.download/2020/01/23/send-request-process-min.jpg)

- 服务消费者通过代理对象 `Proxy` 发起远程调用
- 通过网络客户端 `Client` 将编码后的请求发送给服务提供方的网络层上，也就是 `Server`
- `Server` 在收到请求后，对数据包进行解码
- 将解码后的请求发送至分发器 `Dispatcher`
- 由分发器将请求派发到指定的线程池上
- 由线程池调用具体的服务

### 1 服务调用方式
Dubbo 支持同步和异步两种调用方式
- 默认同步调用
- 异步调用分为“有返回值”的异步调用和“无返回值”的异步调用
- “无返回值”异步调用，直接返回一个空的 `RpcResult`

Dubbo 默认使用 Javassist 框架为服务接口生成动态代理类

### 2 服务消费方发送请求


#### 2.1 发送请求


#### 2.2 请求编码


### 3 服务提供方接收请求


#### 3.1 请求解码


#### 3.2 调用服务


##### 3.2.1 线程派发模型


##### 3.2.2 调用服务


### 4 服务提供方返回调用结果


### 5 服务消费方接收调用结果


#### 5.1 响应数据解码


#### 5.2 向用户线程传递调用结果


## References
- [服务调用过程](http://dubbo.apache.org/zh-cn/docs/source_code_guide/service-invoking-process.html)