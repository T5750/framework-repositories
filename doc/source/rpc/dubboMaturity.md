# Dubbo Maturity

## 功能成熟度

|Feature | Maturity | Strength | Problem | Advise | User |
| -------- |---------|---------|---------|---------|---------|
|分组聚合 | Tested | 分组聚合返回值，用于菜单聚合等服务 | 特殊场景使用 | 可用于生产环境 | |
|泛化引用 | Stable | 泛化调用，无需业务接口类进行远程调用，用于测试平台，开放网关桥接等 |   | 可用于生产环境 | Alibaba |
|泛化实现 | Stable | 泛化实现，无需业务接口类实现任意接口，用于Mock平台 |   | 可用于生产环境 | Alibaba |
|隐式传参 | Stable | 附加参数 |   | 可用于生产环境 | | 
|本地存根 | Stable | 在客户端执行部分逻辑 |   | 可用于生产环境 | Alibaba |
|本地伪装 | Stable | 伪造返回结果，可在失败时执行，或直接执行，用于服务降级 | 需注册中心支持 | 可用于生产环境 | Alibaba |
|延迟暴露 | Stable | 延迟暴露服务，用于等待应用加载warmup数据，或等待spring加载完成 |   | 可用于生产环境 | Alibaba |
 
## 策略成熟度

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Zookeeper注册中心 | Stable | 支持基于网络的集群方式，有广泛周边开源产品，建议使用dubbo-2.3.3以上版本（推荐使用） | 依赖于Zookeeper的稳定性 | 可用于生产环境 |  |
|Redis注册中心 | Stable | 支持基于客户端双写的集群方式，性能高 | 要求服务器时间同步，用于检查心跳过期脏数据 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Simple监控中心 | Stable | 支持JFreeChart统计报表 | 没有集群支持，可能单点故障，但故障后不影响RPC运行 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Dubbo协议 | Stable | 采用NIO复用单一长连接，并使用线程池并发处理请求，减少握手和加大并发效率，性能较好（推荐使用） | 在大文件传输时，单一连接会成为瓶颈 | 可用于生产环境 | Alibaba|
|Rmi协议 | Stable | 可与原生RMI互操作，基于TCP协议 | 偶尔会连接失败，需重建Stub | 可用于生产环境 | Alibaba|
|Hessian协议 | Stable | 可与原生Hessian互操作，基于HTTP协议 | 需hessian.jar支持，http短连接的开销大 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Netty Transporter | Stable | JBoss的NIO框架，性能较好（推荐使用） | 一次请求派发两种事件，需屏蔽无用事件 | 可用于生产环境 | Alibaba|
|Mina Transporter | Stable | 老牌NIO框架，稳定 | 待发送消息队列派发不及时，大压力下，会出现FullGC | 可用于生产环境 | Alibaba|

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Hessian Serialization | Stable | 性能较好，多语言支持（推荐使用） | Hessian的各版本兼容性不好，可能和应用使用的Hessian冲突，Dubbo内嵌了hessian3.2.1的源码 | 可用于生产环境 | Alibaba|
|Java Serialization | Stable | Java原生支持 | 性能较差 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Javassist ProxyFactory | Stable | 通过字节码生成代替反射，性能比较好（推荐使用） | 依赖于javassist.jar包，占用JVM的Perm内存，Perm可能要设大一些：java -XX:PermSize=128m | 可用于生产环境 | Alibaba|
|Jdk ProxyFactory | Stable | JDK原生支持 | 性能较差 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Failover Cluster | Stable | 失败自动切换，当出现失败，重试其它服务器，通常用于读操作（推荐使用） | 重试会带来更长延迟 | 可用于生产环境 | Alibaba|
|Failfast Cluster | Stable | 快速失败，只发起一次调用，失败立即报错,通常用于非幂等性的写操作 | 如果有机器正在重启，可能会出现调用失败 | 可用于生产环境 | Alibaba|
|Failsafe Cluster | Stable | 失败安全，出现异常时，直接忽略，通常用于写入审计日志等操作 | 调用信息丢失 | 可用于生产环境 | Monitor|
|Failback Cluster | Tested | 失败自动恢复，后台记录失败请求，定时重发，通常用于消息通知操作 | 不可靠，重启丢失 | 可用于生产环境 | Registry|
|Forking Cluster | Tested | 并行调用多个服务器，只要一个成功即返回，通常用于实时性要求较高的读操作 | 需要浪费更多服务资源 | 可用于生产环境 |  |
|Broadcast Cluster | Tested | 广播调用所有提供者，逐个调用，任意一台报错则报错，通常用于更新提供方本地状态 | 速度慢，任意一台报错则报错 | 可用于生产环境 | | 

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Random LoadBalance | Stable | 随机，按权重设置随机概率（推荐使用） | 在一个截面上碰撞的概率高，重试时，可能出现瞬间压力不均 | 可用于生产环境 | Alibaba|
|RoundRobin LoadBalance | Stable | 轮询，按公约后的权重设置轮询比率 | 存在慢的机器累积请求问题，极端情况可能产生雪崩 | 可用于生产环境 |  |
|LeastActive LoadBalance | Stable | 最少活跃调用数，相同活跃数的随机，活跃数指调用前后计数差，使慢的机器收到更少请求 | 不支持权重，在容量规划时，不能通过权重把压力导向一台机器压测容量 | 可用于生产环境 |  |
|ConsistentHash LoadBalance | Stable | 一致性Hash，相同参数的请求总是发到同一提供者，当某一台提供者挂时，原本发往该提供者的请求，基于虚拟节点，平摊到其它提供者，不会引起剧烈变动 | 压力分摊不均 | 可用于生产环境 |  |

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|条件路由规则 | Stable | 基于条件表达式的路由规则，功能简单易用 | 有些复杂多分支条件情况，规则很难描述 | 可用于生产环境 | Alibaba|

|Feature | Maturity | Strength | Problem | Advise | User|
| -------- |---------|---------|---------|---------|---------|
|Spring Container | Stable | 自动加载META-INF/spring目录下的所有Spring配置 |   | 可用于生产环境 | Alibaba|
|Jetty Container | Stable | 启动一个内嵌Jetty，用于汇报状态 | 大量访问页面时，会影响服务器的线程和内存 | 可用于生产环境 | Alibaba|
|Log4j Container | Stable | 自动配置log4j的配置，在多进程启动时，自动给日志文件按进程分目录 | 用户不能控制log4j的配置，不灵活 | 可用于生产环境 | Alibaba|

## References
- [成熟度](http://dubbo.apache.org/zh-cn/docs/user/maturity.html)