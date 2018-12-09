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

## 7.1 最佳实践（数据通信）
需要了解在真正项目应用中如何去考虑Netty的使用，大体上对于一些参数设置都是根据服务器性能决定的，这个不是最主要的。

我们需要考虑的问题是两台机器（甚至多台）使用Netty怎样进行通信，大体上把它分为三种：
- 第一种，使用长连接通道不断开的形式进行通信，也就是服务器和客户端的通道一直处于开启状态。如果服务器性能足够好，且客户端数量也比较少的情况下，推荐这种方式。
- 第二种，一次性批量提交数据，采用短连接方式。也就是会把数据保存在本地临时缓冲区或者临时表里，当达到临值时进行一次性批量提交。又或者根据定时任务轮询提交，这种情况弊端是做不到实时性传输，在对实时性不高的应用程序中可以推荐使用。
- 第三种，可以使用一种特殊的长连接，在指定某一时间内，服务器与某台客户端没有任何通信，则断开连接。下次连接则是客户端向服务器发送请求的时候，再次建立连接。但这种模式需要考虑2个因素：
    1. 如何在超时（即服务器和客户端没有任何通信）后关闭通道？关闭通道后，又如何再次建立连接？
    1. 客户端宕机时，我们无需考虑，下次客户端重启后就可以与服务器建立连接。但是服务器宕机时，客户端如何与服务器进行连接呢？

## 7.2 最佳实践（心跳监控）
使用Socket通信一般经常会处理多个服务器之间的心跳检测，一般维护服务器集群，肯定要有一台或（几台）服务器主机（Master），然后还应该有N台（Slave），那么主机肯定要时时刻刻知道自己下面的从服务器的各方面情况，然后进行实时监控的功能，这个在分布式架构里叫做心跳检测或者说心跳监控。最佳处理方案是使用一些通信框架进行实现，Netty就可以去做这样一件事。
- 安装到本地maven库`mvn install:install-file -Dfile=C:\Users\Administrator\.m2\repository\org\hyperic\sigar\1.6.4\sigar.jar -DgroupId=org.hyperic -DartifactId=sigar -Dversion=1.6.4 -Dpackaging=jar`
- gradle使用maven本地缓存库`mavenLocal()`
- 复制`hyperic-sigar-1.6.4\sigar-bin\lib\sigar-x86-winnt.dll`到`C:\Program Files\Java\jdk1.8.0_131\bin`

## 8.1 Http协议
HTTP（超文本传输协议）是建立在TCP传输协议之上的应用层协议，它目前主流是针对于WEB开发，HTTP协议应用非常广泛，因此掌握HTTP协议的开发非常重要。使用Netty的Http协议也是异步非阻塞的。

Http协议特点：
- 简单：客户端请求服务器时，只需要指定URL和携带必要的参数即可
- 灵活：Http协议允许传输任意类型的数据对象，传输内容由HTTP消息头中的`Content-Type`加以标记。
- 无状态：Http协议是无状态的，无状态指的是协议对事务处理没有记忆能力。这意味着如果后续处理需要之前的信息，则它必须重新获取。也从侧面体现Http的设计是为了使网络传输更加的轻量级、敏捷，负载较轻。

Http协议组成部分：
- 请求行
- 请求头
- 请求正文（实体内容）

## 8.2 Http协议请求方式
- GET：获取Request-URI所标识的资源
- POST：在Request-URI所标识的资源附加新的提交数据
- HEAD：请求获取由Request-URI所标识的资源的响应消息头
- PUT：请求服务器存储一个资源，并用Request-URI作为标识
- DELETE：请求服务器删除Request-URI所标识的资源
- TRACE：请求服务器回送收到的请求信息，主要是测试和诊断使用（@trace）
- CONNECT：保留将来使用
- OPTIONS：请求查询服务器的性能忙活着查询相关资源的选项和需求。

## 8.3 Http协议响应消息
响应消息也是由三部分组成的：
- 状态行
- 消息头
- 响应正文

响应状态种类：
- `1xx`：提示信息。表示请求已经接收继续处理
- `2xx`：成功。表示请求已经接收成功
- `3xx`：重定向。要完成的请求必须进行更进一步的操作
- `4xx`：客户端错误。可能是请求语法错误或者请求无法实现
- `5xx`：服务器端错误。服务器未能处理请求（可能内部出现异常）

常见相应状态码：
- 200 OK：成功
- 400 Bad Reuqest：错误的请求语法，不能被服务器理解
- 401 Unauthorized：请求未经授权
- 403 Forbidden：服务器收到请求，但请求被服务器拒绝
- 404 Not Found：请求资源不存在
- 405 Method Not Allowed：请求方式不被允许，如只支持get请求，但客户端使用了post请求）
- 500 Inernal Server Error：服务器发送不可预期的错误
- 503 Server Unavailable：服务器当前不能处理客户端请求，一段时间后可能恢复正常

## 8.4 Netty Http文件服务器开发
Netty的Http协议无论从性能上还是可靠性上都表现优异，非常适合WEB容器的场景下引用，相比于传统的Tomcat、Jetty等Web容器，它更加的轻量和小巧，灵活性和制定性也更好。

## Tips
- 以前程序员：我能用记事本去写代码（我能用很底层的东西去编写，做出来一个文件上传下载系统）
- 现在程序员：我虽然不能用底层写出来一个中间件，但是，能看懂别人的写法。会用API，解决互联网行业的一系列问题

## References
- Netty