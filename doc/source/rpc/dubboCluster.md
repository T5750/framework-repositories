# Dubbo Cluster

## 简介
- 集群 `Cluster` 用途是将多个服务提供者合并为一个 `Cluster Invoker`，并将这个 `Invoker` 暴露给服务消费者
- 集群模块是服务提供者和服务消费者的中间层，为服务消费者屏蔽了服务提供者的情况，这样服务消费者就可以专心处理远程调用相关事宜

## 集群容错
![cluster](https://s1.wailian.download/2020/01/22/cluster.jpg)

集群工作过程可分为两个阶段：
1. 在服务消费者初始化期间，集群 `Cluster` 实现类为服务消费者创建 `Cluster Invoker` 实例，即上图中的 `merge` 操作
2. 在服务消费者进行远程调用时。以 `FailoverClusterInvoker` 为例，该类型 `Cluster Invoker`
	- 首先会调用 `Directory` 的 `list` 方法列举 `Invoker` 列表（可将 `Invoker` 简单理解为服务提供者）。`Directory` 的用途是保存 `Invoker`，可简单类比为 `List<Invoker>`
	- 注册中心内容变化后，`RegistryDirectory` 会动态增删 `Invoker`，并调用 `Router` 的 `route` 方法进行路由，过滤掉不符合路由规则的 `Invoker`
	- 当 `FailoverClusterInvoker` 拿到 `Directory` 返回的 `Invoker` 列表后，它会通过 `LoadBalance` 从 `Invoker` 列表中选择一个 `Invoker`
	- 最后 `FailoverClusterInvoker` 会将参数传给 `LoadBalance` 选择出的 `Invoker` 实例的 `invoke` 方法，进行真正的远程调用

Dubbo 主要提供了这样几种容错方式：
- Failover Cluster - 失败自动切换
- Failfast Cluster - 快速失败
- Failsafe Cluster - 失败安全
- Failback Cluster - 失败自动恢复
- Forking Cluster - 并行调用多个服务提供者

## 源码分析

### 1 Cluster 实现类分析
`Cluster` 是接口，而 `Cluster Invoker` 是一种 `Invoker`。服务提供者的选择逻辑，以及远程调用失败后的的处理逻辑均是封装在 `Cluster Invoker` 中
- `FailoverCluster`: 用于创建 `FailoverClusterInvoker` 对象
- `FailbackCluster`: 用于创建 `FailbackClusterInvoker` 对象

### 2 Cluster Invoker 分析
- `AbstractClusterInvoker`: 各种 `Cluster Invoker` 的父类
- `AbstractClusterInvoker+invoke(final Invocation invocation)`: 用于列举 `Invoker`，以及加载 `LoadBalance`。最后再调 `doInvoke` 进行后续操作
- `AbstractClusterInvoker#doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`: 抽象方法，由子类实现
- `AbstractClusterInvoker#list(Invocation invocation)`: 调用 `Directory` 的 `list` 方法

#### 2.1 FailoverClusterInvoker
`FailoverClusterInvoker` 在调用失败时，会自动切换 `Invoker` 进行重试。默认配置下，Dubbo 会使用这个类作为缺省 `Cluster Invoker`
- `doInvoke(Invocation invocation, final List<Invoker<T>> invokers, LoadBalance loadbalance)`:
	* 获取重试次数
	* 根据重试次数进行循环调用，失败后进行重试。在 `for` 循环内：
		+ 通过负载均衡组件选择一个 `Invoker`
		+ 再通过这个 `Invoker` 的 `invoke` 方法进行远程调用
		+ 如果失败了，记录下异常，并进行重试。重试时会再次调用父类的 `list` 方法列举 `Invoker`
- `select(LoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected)`:
	* 获取 `sticky` 配置，`sticky` 表示粘滞连接。所谓粘滞连接是指让服务消费者尽可能的调用同一个服务提供者，除非该提供者挂了再进行切换
	* 检测 `invokers` 列表中是否包含 `stickyInvoker`，如果不包含，则认为该 `stickyInvoker` 不可用，此时将其置空。这里的 `invokers` 列表可以看做是**存活着的服务提供者**列表
    * 如果 `stickyInvoker` 存在于 `invokers` 列表中，检测 `selected` 中是否包含 `stickyInvoker`
    	+ 如果包含，说明 `stickyInvoker` 在此之前没有成功提供服务（但其仍然处于存活状态）
    	+ 如果不包含，此时还需要进行可用性检测，比如检测服务提供者网络连通性等
    * 当可用性检测通过，才可返回 `stickyInvoker`，否则调用 `doSelect` 方法选择 `Invoker`
    * 如果 `sticky` 为 `true`，此时会将 `doSelect` 方法选出的 `Invoker` 赋值给 `stickyInvoker`
- `doSelect(LoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected)`:
	1. 通过负载均衡组件选择 `Invoker`
	2. 如果选出来的 `Invoker` 不稳定，或不可用，此时需要调用 `reselect` 方法进行重选
- `reselect(LoadBalance loadbalance, Invocation invocation, List<Invoker<T>> invokers, List<Invoker<T>> selected, boolean availablecheck)`:
    1. 查找可用的 `Invoker`，并将其添加到 `reselectInvokers` 集合中
    2. 如果 `reselectInvokers` 不为空，则通过负载均衡组件再次进行选择

#### 2.2 FailbackClusterInvoker
`FailbackClusterInvoker`
- `doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`: 负责初次的远程调用
- `addFailed(Invocation invocation, AbstractClusterInvoker<?> router)`: 在开始阶段会根据 `retryFuture` 为空与否，来决定是否开启定时任务
- `retryFailed()`: 包含了失败重试的逻辑
	* 对 `failed` 进行遍历
	* 依次对 `Invoker` 进行调用
	* 调用成功则将 `Invoker` 从 `failed` 中移除，调用失败则忽略失败原因

#### 2.3 FailfastClusterInvoker
`FailfastClusterInvoker`
- `doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`:
	* 通过 `select` 方法选择 `Invoker`
	* 进行远程调用。如果调用失败，则立即抛出异常

#### 2.4 FailsafeClusterInvoker
失败安全是指，当调用过程中出现异常时，`FailsafeClusterInvoker` 仅会打印异常，而不会抛出异常
- 应用场景：写入审计日志等操作
- `doInvoke(Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`

#### 2.5 ForkingClusterInvoker
`ForkingClusterInvoker`: 在运行时通过线程池创建多个线程，并发调用多个服务提供者。只要有一个服务提供者成功返回了结果，`doInvoke` 方法就会立即结束运行
- 应用场景：在一些对实时性要求比较高**读操作**（注意是读操作，并行写操作可能不安全）下使用，但这将会耗费更多的资源
- `doInvoke(final Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`:
	* 选出 `forks` 个 `Invoker`，为接下来的并发调用提供输入
	* 通过线程池并发调用多个 `Invoker`，并将结果存储在阻塞队列中
	* 从阻塞队列中获取返回结果，并对返回结果类型进行判断。如果为异常类型，则直接抛出，否则返回

#### 2.6 BroadcastClusterInvoker
`BroadcastClusterInvoker` 会逐个调用每个服务提供者，如果其中一台报错，在循环调用结束后，`BroadcastClusterInvoker` 会抛出异常
- 应用场景：通知所有提供者更新缓存或日志等本地资源信息
- `doInvoke(final Invocation invocation, List<Invoker<T>> invokers, LoadBalance loadbalance)`

## References
- [集群](http://dubbo.apache.org/zh-cn/docs/source_code_guide/cluster.html)