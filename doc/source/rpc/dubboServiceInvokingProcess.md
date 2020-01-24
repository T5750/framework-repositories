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

- `InvokerInvocationHandler+invoke(Object proxy, Method method, Object[] args)`
- `MockClusterInvoker+invoke(Invocation invocation)`
- `AbstractInvoker+invoke(Invocation inv)`:
	* 添加信息到 `RpcInvocation#attachment` 变量中
	* 添加完毕后，调用 `doInvoke` 执行后续的调用
- `AbstractInvoker#doInvoke(Invocation invocation)`
- `DubboInvoker#doInvoke(final Invocation invocation)`: Dubbo 实现同步和异步调用比较关键的一点就在于由谁调用 `ResponseFuture` 的 `get` 方法
	* 同步调用模式下，由框架自身调用 `ResponseFuture` 的 `get` 方法
	* 异步调用模式下，则由用户调用该方法
	* `ResponseFuture` 是一个接口，默认实现类 `DefaultFuture`
- `DefaultFuture`:
	* 同步模式下，框架获得 `DefaultFuture` 对象后，会立即调用 `get` 方法进行等待
	* 异步模式下，将该对象封装到 `FutureAdapter` 实例中，并将 `FutureAdapter` 实例设置到 `RpcContext` 中，供用户使用
	* `FutureAdapter` 用于将 Dubbo 中的 `ResponseFuture` 与 JDK 中的 Future 进行适配

### 2 服务消费方发送请求
#### 2.1 发送请求
- `ReferenceCountExchangeClient`: 实现了引用计数的功能。内部定义了一个引用计数变量 `referenceCount`
	* 每当该对象被引用一次 `referenceCount` 都会进行自增
	* 每当 `close` 方法被调用时，`referenceCount` 进行自减
- `HeaderExchangeClient`: 封装了一些关于心跳检测的逻辑
- `HeaderExchangeChannel`:
	* 定义了一个 `Request` 对象
	* 再将该对象传给 `NettyClient` 的 `send` 方法，进行后续的调用
	* 需要说明的是，`NettyClient` 中并未实现 `send` 方法，该方法继承自父类 `AbstractPeer`
- `AbstractPeer+send(Object message)`
- `AbstractClient+send(Object message, boolean sent)`
- `NettyClient#getChannel()`
- `NettyChannel-NettyChannel(org.jboss.netty.channel.Channel channel, URL url, ChannelHandler handler)`
- `NettyChannel#getOrAddChannel(org.jboss.netty.channel.Channel ch, URL url, ChannelHandler handler)`
- `NettyChannel+send(Object message, boolean sent)`

以 `DemoService` 为例，`sayHello` 方法的整个调用路径
```
proxy0#sayHello(String)
  —> InvokerInvocationHandler#invoke(Object, Method, Object[])
    —> MockClusterInvoker#invoke(Invocation)
      —> AbstractClusterInvoker#invoke(Invocation)
        —> FailoverClusterInvoker#doInvoke(Invocation, List<Invoker<T>>, LoadBalance)
          —> Filter#invoke(Invoker, Invocation)  // 包含多个 Filter 调用
            —> ListenerInvokerWrapper#invoke(Invocation)
              —> AbstractInvoker#invoke(Invocation)
                —> DubboInvoker#doInvoke(Invocation)
                  —> ReferenceCountExchangeClient#request(Object, int)
                    —> HeaderExchangeClient#request(Object, int)
                      —> HeaderExchangeChannel#request(Object, int)
                        —> AbstractPeer#send(Object)
                          —> AbstractClient#send(Object, boolean)
                            —> NettyChannel#send(Object, boolean)
                              —> NioClientSocketChannel#write(Object)
```

#### 2.2 请求编码
![data-format-min](https://s1.wailian.download/2020/01/24/data-format-min.jpg)

Dubbo 数据包分为消息头和消息体：
- 消息头用于存储一些元信息，比如魔数（Magic），数据包类型（Request/Response），消息体长度（Data Length）等
- 消息体中用于存储具体的调用消息，比如方法名称，参数列表等

偏移量(Bit) | 字段 | 取值
---|---|---
0 ~ 7 | 魔数高位 | 0xda00
8 ~ 15 | 魔数低位 | 0xbb
16 | 数据包类型 | 0 - Response, 1 - Request
17 | 调用方式 | 仅在第16位被设为1的情况下有效，0 - 单向调用，1 - 双向调用
18 | 事件标识 | 0 - 当前数据包是请求或响应包，1 - 当前数据包是心跳包
19 ~ 23 | 序列化器编号 | 2 - Hessian2Serialization, 3 - JavaSerialization, 4 - CompactedJavaSerialization, 6 - FastJsonSerialization, 7 - NativeJavaSerialization, 8 - KryoSerialization, 9 - FstSerialization
24 ~ 31 | 状态 | 20 - OK, 30 - CLIENT_TIMEOUT, 31 - SERVER_TIMEOUT, 40 - BAD_REQUEST, 50 - BAD_RESPONSE, ......
32 ~ 95 | 请求编号 | 共8字节，运行时生成
96 ~ 127 | 消息体长度 | 运行时计算

- `ExchangeCodec+encode(Channel channel, ChannelBuffer buffer, Object msg)`
- `ExchangeCodec#encodeRequest(Channel channel, ChannelBuffer buffer, Request req)`:
	* 通过位运算将消息头写入到 `header` 数组中
	* 对 `Request` 对象的 `data` 字段执行序列化操作
	* 序列化后的数据最终会存储到 `ChannelBuffer` 中
	* 序列化操作执行完后，可得到数据序列化后的长度 `len`
	* 将 `len` 写入到 `header` 指定位置处
	* 将消息头字节数组 `header` 写入到 `ChannelBuffer` 中
- `DubboCodec#encodeRequestData(Channel channel, ObjectOutput out, Object data, String version)`

### 3 服务提供方接收请求
默认情况下 Dubbo 使用 Netty 作为底层的通信框架。Netty 检测到有数据入站后，首先会通过解码器对数据进行解码，并将解码后的数据传递给下一个入站处理器的指定方法

#### 3.1 请求解码
- `ExchangeCodec+decode(Channel channel, ChannelBuffer buffer)`
- `ExchangeCodec#decode(Channel channel, ChannelBuffer buffer, int readable, byte[] header)`:
	* 通过检测消息头中的魔数是否与规定的魔数相等，提前拦截掉非常规数据包，比如通过 telnet 命令行发出的数据包
	* 再对消息体长度，以及可读字节数进行检测
	* 调用 `decodeBody` 方法进行后续的解码工作
- `DubboCodec#decodeBody(Channel channel, InputStream is, byte[] header)`:
	* 对部分字段进行了解码，并将解码得到的字段封装到 `Request` 中
	* 调用 `DecodeableRpcInvocation` 的 `decode` 方法进行后续的解码工作
	* 此工作完成后，可将调用方法名、`attachment`、以及调用参数解析出来
- `DecodeableRpcInvocation+decode()`:
	* 通过反序列化将诸如 `path`、`version`、调用方法名、参数列表等信息依次解析出来，并设置到相应的字段中
	* 最终得到一个具有完整调用信息的 `DecodeableRpcInvocation` 对象

#### 3.2 调用服务
- 解码器将数据包解析成 `Request` 对象后，`NettyHandler` 的 `messageReceived` 方法紧接着会收到这个对象，并将这个对象继续向下传递
- 这期间该对象会被依次传递给 `NettyServer`、`MultiMessageHandler`、`HeartbeatHandler` 以及 `AllChannelHandler`
- 最后由 `AllChannelHandler` 将该对象封装到 `Runnable` 实现类对象中，并将 `Runnable` 放入线程池中执行后续的调用逻辑

整个调用栈如下：
```
NettyHandler#messageReceived(ChannelHandlerContext, MessageEvent)
  —> AbstractPeer#received(Channel, Object)
    —> MultiMessageHandler#received(Channel, Object)
      —> HeartbeatHandler#received(Channel, Object)
        —> AllChannelHandler#received(Channel, Object)
          —> ExecutorService#execute(Runnable)    // 由线程池执行后续的调用逻辑
```

`NettyHandler+messageReceived(ChannelHandlerContext ctx, MessageEvent e)`:
- 根据一些信息获取 `NettyChannel` 实例
- 将 `NettyChannel` 实例以及 `Request` 对象向下传递

##### 3.2.1 线程派发模型
Dubbo 将底层通信框架中接收请求的线程称为 IO 线程
- 如果一些事件处理逻辑可以很快执行完，比如只在内存打一个标记，此时直接在 IO 线程上执行该段逻辑即可
- 但如果事件的处理逻辑比较耗时，比如该段逻辑会发起数据库查询或者 HTTP 请求。此时我们就不应该让事件处理逻辑在 IO 线程上执行，而是应该派发到线程池中去执行

`Dispatcher` 真实的职责创建具有线程派发能力的 `ChannelHandler`，比如 `AllChannelHandler`、`MessageOnlyChannelHandler` 和 `ExecutionChannelHandler` 等，其本身并不具备线程派发能力
- 默认配置下，Dubbo 使用 `all` 派发策略，即将所有的消息都派发到线程池中
- Dubbo 支持 5 种不同的线程派发策略

策略 | 用途
---|---
all | 所有消息都派发到线程池，包括请求，响应，连接事件，断开事件等
direct | 所有消息都不派发到线程池，全部在 IO 线程上直接执行
message | 只有**请求**和**响应**消息派发到线程池，其它消息均在 IO 线程上执行
execution | 只有**请求**消息派发到线程池，不含响应。其它消息均在 IO 线程上执行
connection | 在 IO 线程上，将连接断开事件放入队列，有序逐个执行，其它消息派发到线程池

- `AllChannelHandler+connected(Channel channel)`: 处理连接事件
- `AllChannelHandler+disconnected(Channel channel)`: 处理断开事件
- `AllChannelHandler+received(Channel channel, Object message)`: 处理请求和响应消息，这里的 `message` 变量类型可能是 `Request`，也可能是 `Response`
- `AllChannelHandler+caught(Channel channel, Throwable exception)`: 处理异常信息
- 请求对象会被封装 `ChannelEventRunnable` 中

##### 3.2.2 调用服务
- `ChannelEventRunnable+run()`: 仅是一个中转站，它的 `run` 方法中并不包含具体的调用逻辑，仅用于将参数传给其他 `ChannelHandler` 对象进行处理，该对象类型为 `DecodeHandler`
- `DecodeHandler+received(Channel channel, Object message)`
- `DecodeHandler-decode(Object message)`: `DecodeHandler` 存在的意义就是保证请求或响应对象可在线程池中被解码
- `HeaderExchangeHandler+received(Channel channel, Object message)`
- `HeaderExchangeHandler#handleRequest(ExchangeChannel channel, Request req)`: 将调用结果封装到 `Response` 对象中，再将该对象返回给服务消费方
- `DubboProtocol.requestHandler`:
	* 获取与指定服务对应的 `Invoker` 实例
	* 通过 `Invoker` 的 `invoke` 方法调用服务逻辑
- `DubboProtocol#getInvoker(Channel channel, Invocation inv)`
- `AbstractProxyInvoker+invoke(Invocation invocation)`
- `AbstractProxyInvoker#doInvoke(T proxy, String methodName, Class<?>[] parameterTypes, Object[] arguments)`: 是一个抽象方法，这个需要由具体的 `Invoker` 实例实现
- `JavassistProxyFactory+getInvoker(T proxy, Class<T> type, URL url)`:
	* `Wrapper` 是一个抽象类，其中 `invokeMethod` 是一个抽象方法
	* Dubbo 会在运行时通过 Javassist 框架为 `Wrapper` 生成实现类，并实现 `invokeMethod` 方法，该方法最终会根据调用信息调用具体的服务

整个服务调用过程，如下：
```
ChannelEventRunnable#run()
  —> DecodeHandler#received(Channel, Object)
    —> HeaderExchangeHandler#received(Channel, Object)
      —> HeaderExchangeHandler#handleRequest(ExchangeChannel, Request)
        —> DubboProtocol.requestHandler#reply(ExchangeChannel, Object)
          —> Filter#invoke(Invoker, Invocation)
            —> AbstractProxyInvoker#invoke(Invocation)
              —> Wrapper0#invokeMethod(Object, String, Class[], Object[])
                —> DemoServiceImpl#sayHello(String)
```

### 4 服务提供方返回调用结果
服务提供方调用指定服务后，会将调用结果封装到 `Response` 对象中，并将该对象返回给服务消费方。服务提供方也是通过 `NettyChannel` 的 `send` 方法将 `Response` 对象返回
- `ExchangeCodec+encode(Channel channel, ChannelBuffer buffer, Object msg)`
- `ExchangeCodec#encodeResponse(Channel channel, ChannelBuffer buffer, Response res)`
- `DubboCodec#encodeResponseData(Channel channel, ObjectOutput out, Object data, String version)`

### 5 服务消费方接收调用结果
服务消费方在收到响应数据后
- 对响应数据进行解码，得到 `Response` 对象
- 将该对象传递给下一个入站处理器，这个入站处理器就是 `NettyHandler`
- `NettyHandler` 会将这个对象继续向下传递
- `AllChannelHandler` 的 `received` 方法会收到这个对象，并将这个对象派发到线程池中

#### 5.1 响应数据解码
- `DubboCodec#decodeBody(Channel channel, InputStream is, byte[] header)`: 响应数据的解码过程
- `DecodeableRpcResult+decode()`: 调用结果的反序列化过程
- `DecodeableRpcResult+decode(Channel channel, InputStream input)`

#### 5.2 向用户线程传递调用结果
响应数据解码完成后，Dubbo 会将响应对象从线程池线程传递到用户线程上。当响应对象到来后，用户线程会被唤醒，并通过**调用编号**获取属于自己的响应对象
- `HeaderExchangeHandler+received(Channel channel, Object message)`
- `HeaderExchangeHandler#handleResponse(Channel channel, Response response)`
- `DefaultFuture+received(Channel channel, Response response)`: 将响应对象保存到相应的 `DefaultFuture` 实例中
- `DefaultFuture-doReceived(Response res)`: 唤醒用户线程，随后用户线程即可从 `DefaultFuture` 实例中获取到相应结果

通过调用编号，将每个响应对象传递给相应的 `DefaultFuture` 对象，且不出错。整个过程大致如下图：

![request-id-application-min](https://s1.wailian.download/2020/01/24/request-id-application-min.jpg)

- `DefaultFuture` 被创建时，会要求传入一个 `Request` 对象。此时 `DefaultFuture` 可从 `Request` 对象中获取调用编号
- 将 `<调用编号, DefaultFuture 对象>` 映射关系存入到静态 `Map` 中，即 `FUTURES`
- 线程池中的线程在收到 `Response` 对象后，会根据 `Response` 对象中的调用编号到 `FUTURES` 集合中取出相应的 `DefaultFuture` 对象
- 将 `Response` 对象设置到 `DefaultFuture` 对象中
- 最后再唤醒用户线程，这样用户线程即可从 `DefaultFuture` 对象中获取调用结果了

## References
- [服务调用过程](http://dubbo.apache.org/zh-cn/docs/source_code_guide/service-invoking-process.html)