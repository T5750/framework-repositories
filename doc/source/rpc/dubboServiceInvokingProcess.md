# Dubbo Service Invoking Process

## 简介


## 源码分析


### 1 服务调用方式


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