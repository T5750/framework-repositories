# Storm笔记

## 1.1 Storm简介
Storm是Twitter开源的一个分布式的实时计算系统，用于数据的实时分析，持续计算，分布式RPC等。
- 官网地址：[http://storm.apache.org/](http://storm.apache.org/)
- 源码地址：[https://github.com/nathanmarz/storm](https://github.com/nathanmarz/storm)

实时计算需要解决一些什么问题
- 最显而易见的就是实时推荐系统，比如在淘宝等电商购物网站去买东西，我们会在网页旁边或者底端看到与自己所需要商品相关的系列产品。这就是使类似Storm实时计算去做的，我们非常熟悉的Hadoop只是做离线的数据分析，无法做到实时分析计算。
- 比如车流量实时的计算，每天北京的交通情况非常拥挤，可以利用Storm实时计算每一个路段的拥挤度等相关路况信息。
- 再比如股票系统也是一种实时计算的机制，利用Storm完全可以实现。

## 1.2 Storm特性
实现一个实时计算系统
- 低延迟：实时计算系统，延迟一定要低。
- 高性能：可以使用几台普通的服务器建立环境，节约成本。
- 分布式：Storm非常适合于分布式场景，大数据的实时计算；你的数据和计算单机就能搞定，那么不用考虑这些复杂的问题了。我们所说的是单机搞不定的情况。
- 可扩展：伴随着业务的发展，我们的数据量、计算量可能会越来越大，所以希望这个系统是可扩展的。
- 容错：这是分布式系统中通用问题，一个节点挂了不能影响应用，Storm可以轻松做到在节点挂了的时候实现任务转移，并且在节点重启的时候（也就是重新投入生产环境时，自助平衡任务）
- 可靠性：可靠的消息处理。Storm保证每个消息至少能得到一次完整处理。任务失败时，它会负责从消息源重试消息。
- 快速：系统的设计保证了消息能得到快速的处理，使用ZeroMQ作为其底层消息队列。
- 本地模式：Storm有一个“本地模式”，可以在处理过程中完全模拟Storm集群。可以快速进行开发和单元测试。

## 2.1 Storm体系结构
首先拿Hadoop和Storm进行一个简单的对比：

对比项 | Hadoop | Storm
---|-----|-----
系统角色 | JobTracker，TaskTracker，Child | Nimbus，Supervisor，Worker
应用名称 | Job | Topology
组件接口 | Mapper/Reducer | Spout/Bolt

Storm是一个开源的分布式实时计算系统，可以简单、可靠的处理大量的数据流。Storm有很多使用场景：如实时分析，在线机器学习，持续计算，分布式RPC，ETL等等。Storm支持水平扩展，具有高容错性，保证每个消息都会得到处理，而且处理速度很快（在一个小集群中，每个结点每秒可以处理数以百万计的消息）。Storm的部署和运维都很便捷，而且更为重要的是可以使用任意编程询言来开发应用。

## 2.2 Storm架构图
![storm-stru-min](http://www.wailian.work/images/2018/12/13/storm-stru-min.jpg)

Nimbus主节点：
- 主节点通常运行一个后台程序——Nimbus，用于响应分布在集群中的节点，分配任务和监测故障。这个很类似于Hadoop中的Job Tracker。

Supervisor工作节点：
- 工作节点同样会运行一个后台程序——Supervisor，用于收听工作指派并基于要求运行工作进程。每个工作节点都是topology中一个子集的实现。而Nimbus和Supervisor之间的协调则通过Zookeeper系统或集群。

Zookeeper
- Zookeeper是完成Supervisor和Nimbus之间协调的服务。而应用程序实现实时的逻辑则被封装到Storm中的“topology”。topology则是一组由Spouts（数据源）和Bolts（数据操作）通过Stream Groupings迪行连接的图。

Topology（拓扑）
- Storm中运行的一个实时应用程序，因为各个组件间的消息流动形成逻辑上的一个拓扑结构。一个topology是spouts和bolts组成的图，通过Stream Groupings将图中的spouts和bolts连接起来，如下图：

![storm-flow-min](http://www.wailian.work/images/2018/12/13/storm-flow-min.png)

## References
- Storm