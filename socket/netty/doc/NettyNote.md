# Netty笔记

## 1.1 为什么选择Netty
- 很经典的TCP读包写包问题，或数据接收的大小，实际通信读取与应答的处理逻辑等问题，都需要大量的时间和精力，以及丰富的经验。
- 为什么选择Netty，原因无它，简单！也不必去编写复杂的代码逻辑去实现通信，不需要去考虑性能问题，不需要考虑编解码问题，半包读写问题等，这些强大的Netty已实现好了，我们只需要使用即可。
- Netty是最流行的NIO框架，它的健壮性、功能、性能、可定制性和可扩展性在同类框架都是首屈一指的。它已经得到成百上千的商业/商用项目验证，如Hadoop的RPC框架Avro、以及JMS框架、RocketMQ、Dubbox等。

## 1.2 Netty简介
- Netty是基于Java NIO的网络应用框架。
- Netty是一个NIO client-server（客户端服务器）框架，使用Netty可以快速开发网络应用，例如服务器和客户端协议。Netty提供了一种新的方式来使开发网络应用程序，这种新的方式使得它很容易使用和有很强的扩展性。Netty的内部实现是很复杂的，但是Netty提供了简单易用的api从网络处理代码中解耦业务逻辑。Netty是完全基于NIO实现的，所以整个Netty都是异步的。
- 网络应用程序通常需要有较高的可扩展性，无论是Netty还是其它的基于JavaNIO的框架，都会提供可扩展性的解决方案。Netty中一个关键组成部分是它的异步特性。

## 1.3 Netty架构组成
![components](https://netty.io/images/components.png)

## 1.4 Netty特性
Developpent Area | Netty Festures
----|-------
Design（设计） | 各种传输类型，阻塞和非阻塞套接字统一的API使用灵活简单，但功能强大的线程模型无连接的`DatagramSocket`支持链逻辑，易于重用
Ease of Use（易于使用） | 提供大量的文档和例子除了依赖jdk1.6+，没有额外的依赖关系。某些功能依赖jdk1.7+，其它特性可能有相关依赖，但都是可选的
Performance（性能） | 比Java APIS更好的吞吐量和更低的延退，因为线程池和重用所有消耗较少的资源尽量减少不必要的内存拷贝
Robustness（鲁棒性） | 鲁棒性，可以理解为健壮性，链接快或慢或超载不会导致更多的`OutOfMemoryError`，在高速的网络程序中不会有不公平的read/write
Security（安全性） | 完整的SSL/TLS和StartTLS支持，可以在如Applet或OSCI这些受限制的环境中运行
Community（社区） | 版本发布频繁社区活跃

## 2.1 NIO通信
NIO通信步骤：
1. 创建`ServerSocketChannel`，为它配置非阻塞模式
2. 绑定监听，配置TCP参数，录入`backlog`大小等
3. 创建一个独立的IO线程，用于轮询多路复用器`Selector`
4. 创建`Selector`，将之前创建的`ServerSocketChannel`注册到`Selector`上，并设置监听标识位`SelectionKey.ACCEPT`
5. 启动IO线程，在循环体中执行`Selector.select()`方法，轮询就绪的通道
6. 当轮询到处于就绪的通道时，需要进行判断操作位，如果是`ACCEPT`状态，说明是新的客户端接入，则调用`accept`方法接受新的客户端。
7. 设置新接入客户端的一些参数，如非阻塞、并将其通道继续注册到`Selector`之中，设置监听标识位等
8. 如果轮询的通道操作位是`READ`，则进行读取，构造`Buffer`对象等
9. 更细节的还有数据没发送完成继续发送的问题...

## 2.2 Helloworld
安装JDK7，下载地址：[http://www.oracle.com/technetwork/java/javase/archive-139210.html](http://www.oracle.com/technetwork/java/javase/archive-139210.html)

下载Netty包，下载地址：[http://netty.io](http://netty.io)

Netty实现通信的步骤：
1. 创建两个的NIO线程组，一个专门用于网络事件处理（接受客户端的连接），另一个则进行网络通信读写。
2. 创建一个`ServerBootstrap`对象，配置Netty的一系列参数，例如接受传出数据的缓存大小等。
3. 创建一个实际处理数据的类`Channellnitializer`，进行初始化的准备工作，比如设置接受传出数据的字符集、格式、已经实际处理数据的接口。
4. 绑定端口，执行同步阻塞方法等待服务器端启动即可。

如此简单的四个步骤，服务器端就编写完成了，几十行代码就可以把它完成的健壮、性能稳定。

解读Netty示例：[http://ifeve.com/netty5-user-guide/](http://ifeve.com/netty5-user-guide/)

## 3.1 TCP粘包、拆包处理
熟悉TCP编程的可能都知道，无论是服务器端还是客户端，当读取或者发送数据时，都需要考虑TCP底层的粘包/拆包机制。

TCP是一个“流”协议，所谓流就是没有界限的遗传数据。大家可以想象下如果河里的水就好比数据，它们是连成一片的，没有分界线，TCP底层并不了解上层的业务数据具体的含义，它会根据TCP缓冲区的实际情况进行包的划分，也就是说，在业务上，一个完整的包可能会被TCP分成多个包进行发送，也可能把多个小包封装成一个大的数据包发送出去，这就是所谓的TCP粘包、拆包问题。

分析TCP粘包、拆包问题的产生原因：
1. 应用程序write写入的字节大小大于套接口发送缓冲区的大小
2. 进行MSS大小的TCP分段
3. 以太网帧的payload大于MTU进行IP分片

## 3.2 TCP粘包、拆包问题解决方案
粘包、拆包问题的解决方案，根据业界主流协议，有三种方案：
1. 消息定长，例如每个报文的大小固定为200个字节，如果不够，空位补空格
1. 在包尾部增加特殊字符进行分割，例如加回车等
1. 将消息分为消息头和消息体，在消息头中包含表示消息总长度的字段，然后进行业务逻辑的处理

## 3.3 Netty如何去解决粘包、拆包问题
1. 分隔符类`DelimiterBasedFrameDecoder`（自定义分隔符）
1. `FixedLengthFrameDecoder`（定长）

## 4.1 Netty编解码技术
编解码技术，说白了就是Java序列化技术，序列化目的就两个：第一进行网络传输，第二对象持久化。

虽然可以使用Java进行对象序列化，Netty去传输，但是Java序列化的硬伤太多，比如Java序列化没法跨语言、序列化后码流太大、序列化性能太低等

主流的编解码框架：
- JBoss的Marshalling包
- Google的Protobuf
- 基于Protobuf的Kryo
- MessagePack框架

## 4.2 JBoss Marshalling
- JBoss Marshalling是一个Java对象序列化包，对JDK默认的序列化框架进行了优化，但又保持跟`java.io.Serializable`接口的兼容，同是增加了一些可调的参数和附加特性
- 类库：`jboss-marshalling-1.3.0`、`jboss-marshalling-serial-1.3.0`
- 下载地址：[https://www.jboss.org/jbossmarshalling/downloads](https://www.jboss.org/jbossmarshalling/downloads)

## 5.x Netty的UDP协议
UDP是User Datagram Protocol的简称，中文名是用户数据报协议，是OSI（Open System Interconnection，开放式系统互联）参考模型中一种无连接的传输层协议，提供面向事务的简单不可靠信息传送服务。

UDP协议全称是用户数据报协议，在网络中它与TCP协议一样用于处理数据包，是一种无连接的协议。在OSI模型中，在第四层——传输层，处于IP协议的上一层。UDP有不提供数据包分组、组装和不能对数据包进行排序的缺点，也就是说，当报文发送之后，是无法得知其是否安全完整到达的。

UDP用来支持那些需要在计算机之间传输数据的网络应用。包括网络视频会议系统在内的众多的客户/服务器模式的网络应用都需要使用UDP协议。UDP协议从问世至今已经被使用了很多年，虽然其最初的光彩已经被一些类似协议所掩盖，但是即使是在今天UDP仍然不失为一项非常实用和可行的网络传输层协议。

## 6.x Netty的WebSocket
WebSocket将网络套接字引入到客户端和服务端，众所周知，之前实现聊天功能，可能需要古老的Socket技术，亦或者是古老的DWR框架，反向Ajax技术，再有可能就是Comet服务器推技术。H5的WebSocket很轻松地实现聊天功能，Netty和H5的WebSocket结合非常的简单，Netty们封装了其协议类，我们可以很方便地进行使用。

WS特点：
- 单一的TCP连接，双方可通信
- 对代理、防火墙和路由器透明
- 无头部信息、Cookie和身份验证
- 无安全开销
- 通过`ping/pong`帧保持链路激活
- 服务器可主动传递消息给客户端，不再需要客户端轮询


## References
- Netty