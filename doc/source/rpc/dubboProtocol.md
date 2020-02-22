# Dubbo Protocol

## dubbo://
Dubbo 缺省协议采用单一长连接和 NIO 异步通讯，适合于小数据量大并发的服务调用，以及服务消费者机器数远大于服务提供者机器数的情况

### 特性
缺省协议，使用基于 mina `1.1.7` 和 hessian `3.2.1` 的 tbremoting 交互
* 连接个数：单连接
* 连接方式：长连接
* 传输协议：TCP
* 传输方式：NIO 异步传输
* 序列化：Hessian 二进制序列化
* 适用范围：传入传出参数数据包较小（建议小于100K），消费者比提供者个数多，单一消费者无法压满提供者，尽量不要用 dubbo 协议传输大文件或超大字符串
* 适用场景：常规远程服务方法调用

### 约束
* 参数及返回值需实现 `Serializable` 接口
* 参数及返回值不能自定义实现 `List`, `Map`, `Number`, `Date`, `Calendar` 等接口，只能用 JDK 自带的实现，因为 hessian 会做特殊处理，自定义实现类中的属性值都会丢失
* Hessian 序列化，只传成员属性值和值的类型，不传方法或静态变量，兼容情况：

| 数据通讯 | 情况 | 结果 |
| ------------- | ------------- | ------------- |
| A->B  | 类A多一种 属性（或者说类B少一种 属性）| 不抛异常，A多的那 个属性的值，B没有， 其他正常 |
| A->B  | 枚举A多一种 枚举（或者说B少一种 枚举），A使用多 出来的枚举进行传输 | 抛异常 |
| A->B | 枚举A多一种 枚举（或者说B少一种 枚举），A不使用 多出来的枚举进行传输 | 不抛异常，B正常接 收数据 |
| A->B | A和B的属性 名相同，但类型不相同 | 抛异常 |
| A->B | serialId 不相同 | 正常传输 |

总结：服务器端和客户端对领域对象并不需要完全一致，而是按照最大匹配原则

## hessian://
Hessian 协议用于集成 Hessian 的服务，Hessian 底层采用 Http 通讯，采用 Servlet 暴露服务，Dubbo 缺省内嵌 Jetty 作为服务器实现

### 特性
* 连接个数：多连接
* 连接方式：短连接
* 传输协议：HTTP
* 传输方式：同步传输
* 序列化：Hessian二进制序列化
* 适用范围：传入传出参数数据包较大，提供者比消费者个数多，提供者压力较大，可传文件
* 适用场景：页面传输，文件传输，或与原生hessian服务互操作

### 约束
* 参数及返回值需实现 `Serializable` 接口
* 参数及返回值不能自定义实现 `List`, `Map`, `Number`, `Date`, `Calendar` 等接口，只能用 JDK 自带的实现，因为 hessian 会做特殊处理，自定义实现类中的属性值都会丢失

## rmi://
RMI 协议采用 JDK 标准的 `java.rmi.*` 实现，采用阻塞式短连接和 JDK 标准序列化方式

### 特性
* 连接个数：多连接
* 连接方式：短连接
* 传输协议：TCP
* 传输方式：同步传输
* 序列化：Java 标准二进制序列化
* 适用范围：传入传出参数数据包大小混合，消费者与提供者个数差不多，可传文件
* 适用场景：常规远程服务方法调用，与原生RMI服务互操作

### 约束
* 参数及返回值需实现 `Serializable` 接口
* dubbo 配置中的超时时间对 RMI 无效，需使用 java 启动参数设置：`-Dsun.rmi.transport.tcp.responseTimeout=3000`

## References
- [dubbo://](http://dubbo.apache.org/zh-cn/docs/user/references/protocol/dubbo.html)
- [hessian://](http://dubbo.apache.org/zh-cn/docs/user/references/protocol/hessian.html)
- [rmi://](http://dubbo.apache.org/zh-cn/docs/user/references/protocol/rmi.html)