# Dubbo LoadBalance

## 简介
LoadBalance 负载均衡，职责是将网络请求，或者其他形式的负载“均摊”到不同的机器上

Dubbo 提供了4种负载均衡实现：
- `RandomLoadBalance`: 加权随机算法
- `LeastActiveLoadBalance`: 最少活跃调用数算法
- `ConsistentHashLoadBalance`: 一致性 hash 算法
- `RoundRobinLoadBalance`: 加权轮询算法

## 源码分析
在 Dubbo 中，所有负载均衡实现类均继承自 `AbstractLoadBalance`，该类实现了 `LoadBalance` 接口，并封装了一些公共的逻辑
- `select(List<Invoker<T>> invokers, URL url, Invocation invocation)`
	* 检测 `invokers` 集合的合法性
	* 检测 `invokers` 集合元素数量
		+ 如果只包含一个 `Invoker`，直接返回该 `Inovker` 即可
		+ 如果包含多个 `Invoker`，此时需要通过负载均衡算法选择一个 `Invoker`
- `getWeight(Invoker<?> invoker, Invocation invocation)`:
	* 用于保证当服务运行时长小于服务预热时间时，对服务进行降权，避免让服务在启动之初就处于高负载状态
	* 服务预热是一个优化手段，与此类似的还有 JVM 预热
	* 主要目的是让服务启动后“低功率”运行一段时间，使其效率慢慢提升至最佳状态
- `calculateWarmupWeight(int uptime, int warmup, int weight)`: 计算权重，代码逻辑上形似于 `(uptime / warmup) * weight`

### 1 RandomLoadBalance
`RandomLoadBalance`: 经过多次请求后，能够将调用请求按照权重值进行“均匀”分配
- `doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation)`
	* 如果所有服务提供者权重值相同，此时直接随机返回一个即可
	* `int offset = random.nextInt(totalWeight);`: 随机获取一个 [0, totalWeight) 区间内的数字
	* 例如 servers = [A, B, C]，weights = [5, 3, 2]，offset = 7
	* 第一次循环，`offset - 5 = 2 > 0`，即 `offset > 5`，表明其不会落在服务器 A 对应的区间上
	* 第二次循环，`offset - 3 = -1 < 0`，即 `5 < offset < 8`，表明其会落在服务器 B 对应的区间上
- 缺点，当调用次数比较少时，`Random` 产生的随机数可能会比较集中，此时多数请求会落到同一台服务器上。这个缺点并不是很严重，多数情况下可以忽略

### 2 LeastActiveLoadBalance
`LeastActiveLoadBalance`: 加权最小活跃数算法
- 活跃调用数越小，表明该服务提供者效率越高，单位时间内可处理更多的请求。此时应优先将请求分配给该服务提供者
- 初始情况下，所有服务提供者活跃数均为0。每收到一个请求，活跃数加1，完成请求后则将活跃数减1
- `doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation)`:
	1. 遍历 `invokers` 列表，寻找活跃数最小的 `Invoker`
	2. 如果有多个 `Invoker` 具有相同的最小活跃数，此时记录下这些 `Invoker` 在 `invokers` 集合中的下标，并累加它们的权重，比较它们的权重值是否相等
	3. 如果只有一个 `Invoker` 具有最小的活跃数，此时直接返回该 `Invoker` 即可
	4. 如果有多个 `Invoker` 具有最小活跃数，且它们的权重不相等，此时处理方式和 `RandomLoadBalance` 一致
	5. 如果有多个 `Invoker` 具有最小活跃数，但它们的权重相等，此时随机返回一个即可

### 3 ConsistentHashLoadBalance
一致性 hash 算法提出之初是用于大规模缓存系统的负载均衡。它的工作过程：
- 根据 ip 或者其他的信息为缓存节点生成一个 hash，并将这个 hash 投射到 `[0, 2^32 - 1]` 的圆环上
- 当有查询或写入请求时，则为缓存项的 key 生成一个 hash 值。
- 查找第一个大于或等于该 hash 值的缓存节点，并到这个节点中查询或写入缓存项

![consistent-hash-data-incline-min](https://s1.wailian.download/2020/01/26/consistent-hash-data-incline-min.jpg)

- 相同颜色的节点均属于同一个服务提供者，目的是通过引入虚拟节点，让 Invoker 在圆环上分散开来，避免数据倾斜问题
- 数据倾斜是指，由于节点不够分散，导致大量请求落到了同一个节点上，而其他节点只会接收到了少量请求的情况
- 由于 Invoker-1 和 Invoker-2 在圆环上分布不均，导致系统中75%的请求都会落到 Invoker-1 上，只有 25% 的请求会落到 Invoker-2 上。解决这个问题办法是引入虚拟节点，通过虚拟节点均衡各个节点的请求量
- `ConsistentHashLoadBalance#doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation)`
	* 做了一些前置工作，比如检测 `invokers` 列表是不是变动过，以及创建 `ConsistentHashSelector`
	* 调用 `ConsistentHashSelector` 的 `select` 方法执行负载均衡逻辑
- `ConsistentHashSelector#ConsistentHashSelector(List<Invoker<T>> invokers, String methodName, int identityHashCode)`:
	* 构造方法执行了一系列的初始化逻辑，比如从配置中获取虚拟节点数以及参与 `hash` 计算的参数下标，默认情况下只使用第一个参数进行 `hash`
	* `ConsistentHashLoadBalance` 的负载均衡逻辑只受参数值影响，具有相同参数值的请求将会被分配给同一个服务提供者
- `ConsistentHashSelector+select(Invocation invocation)`: 对参数进行 `md5` 以及 `hash` 运算，得到一个 `hash` 值
- `ConsistentHashSelector-selectForKey(long hash)`: 拿这个值到 `TreeMap` 中查找目标 `Invoker` 即可

### 4 RoundRobinLoadBalance
`RoundRobinLoadBalance`
- `doSelect(List<Invoker<T>> invokers, URL url, Invocation invocation)`
- `WeightedRoundRobin`

平滑加权轮询，每个服务器对应两个权重：
- `weight` 是固定的
- `currentWeight` 会动态调整，初始值为0
- 当有新的请求进来时，遍历服务器列表，让它的 `currentWeight` 加上自身权重
- 遍历完成后，找到最大的 `currentWeight`，并将其减去权重总和，然后返回相应的服务器即可

比如，服务器 `[A, B, C]` 对应权重 `[5, 1, 1]`，现在有7个请求依次进入负载均衡逻辑，选择过程如下：

请求编号 | currentWeight 数组 | 选择结果 | 减去权重总和后的 currentWeight 数组
---|---|---|---
1 | [5, 1, 1] | A | [-2, 1, 1]
2 | [3, 2, 2] | A | [-4, 2, 2]
3 | [1, 3, 3] | B | [1, -4, 3]
4 | [6, -3, 4] | A | [-1, -3, 4]
5 | [4, -2, 5] | C | [4, -2, -2]
6 | [9, -1, -1] | A | [2, -1, -1]
7 | [7, 0, 0] | A | [0, 0, 0]

- 如上，经过平滑性处理后，得到的服务器序列为 `[A, A, B, A, C, A, A]`，相比之前的序列 `[A, A, A, A, A, B, C]`，分布性要好一些
- 初始情况下 `currentWeight = [0, 0, 0]`，第7个请求处理完后，`currentWeight` 再次变为 `[0, 0, 0]`

## References
- [负载均衡](http://dubbo.apache.org/zh-cn/docs/source_code_guide/loadbalance.html)