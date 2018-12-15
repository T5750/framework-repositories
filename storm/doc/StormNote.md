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

## 3.1 Storm集群环境搭建
- [Storm集群安装配置](StormCluster.md)

## 4.1 Storm Hello World
引入gradle依赖：
```
compile group: 'org.apache.storm', name: 'storm-core', version: '1.2.2'
```

![storm-process-min](http://www.wailian.work/images/2018/12/14/storm-process-min.png)

- 首先，编写数据源类：Spout。可以使用2种方式：
    - 继承`BaseRichSpout`类
    - 实现`IRichSpout`接口
    - 重点需要几个方法进行重写或实现：`open`、`nextTuple`、`declareOutputFields`
- 继续编写数据处理类：Bolt。可以使用2种方式：
    - 继承`BaseBasicBolt`类
    - 实现`IRichBolt`接口
    - 重点需要几个方法进行重写或实现：`execute`、`declareOutputFields`
- 最后，编写主函数（Topology）去进行提交一个任务。
    - 在使用Topology的时候，Storm框架为我们提供了2种模式：本地模式和集群模式
    - 本地模式：（无需Storm集群，直接在java中即可运行，一般用于测试和开发阶段）执行运行main函数即可。
    - 集群模式：（需要Storm集群，把实现的java程序打包，然后Topology进行提交）需要把应用打成jar，使用storm命令把Topology提交到集群中去。
- 提交topology命令：`storm jar storm-1.0.jar t5750.storm.printwrite.topology.PWTopologyCluster`
- 查看任务命令：`storm list`
- 另外，2个supervisor节点`jps`显示：`Supervisor`
- 最后，看2个工作节点的`/usr/local/temp/`下的文件信息是否有内容

## 5.1 Storm API
- Topology：拓扑
- Stream Groupings：流分组、数据的分发方式
- Spout：喷口、消息源
- Bolt：螺栓、处理器
- Worker：工作进程JVM
- Executor：执行器、Task的线程
- Task：具体的执行任务
- Configuration：配置

## 5.2 Storm拓扑
- 拓扑是一个有向图的计算。（也就是说在计算的过程中，是有流向地去处理业务逻辑，节点之间的连接显示数据该如何进入下一个节点，它们是进行连接传递的）
- 拓扑运行很简单，只需要使用storm命令，把一个jar提交给nimbus节点，nimbus就会把任务分配给具体的子节点（supervisor）去工作。

创建拓扑非常简单：
1. 构建`TopologyBuilder`对象
1. 设置Spout（喷口）数据源对象（可以设置多个）
1. 设置Bolt（螺栓）数据处理对象（可以设置多个）
1. 构建`Config`对象
1. 提交拓扑

## 5.3 Storm拓扑配置
- 工作进程、并行度、任务数设置：
	```
	Config cfg = new Config();
	cfg.setNumWorkers(2);
	cfg.setDebug(false);
	TopologyBuilder builder = new TopologyBuilder();
	builder.setSpout("spout", new PWSpout(), 2);
	builder.setBolt("print-bolt", new PrintBolt(), 2).shuffleGrouping("spout").setNumTasks(4);
	builder.setBolt("write-bolt", new WriteBolt(), 6).shuffleGrouping("print-bolt");
	```
- 首先，设置2个工作进程（也就是2个JVM）
- 然后，设置spout的并行度为2 （产生2个执行器和2个任务）
- 第一个bolt的并行度为2，并且指定任务数为4 （产生2个执行器和4个任务）
- 第二个bolt的并行度为6（产生6个执行器和6个任务）
- 因此，该拓扑程序共有2个工作进程（worker），`2+2+6=10`个执行器（executor），`2+4+6=12`个任务（task）。每个工作进程可以领取到`12/2=6`个任务。默认情况下一个执行器执行一个任务，但如果指定了任务的数目，则任务会平均分配到执行器中。

## 5.4 Storm流分组
Stream Groupings：为每个bolt指定应该接受哪个流作为输入，流分组定义了如何在bolt的任务直接进行分发。

![storm-stream-groupings](https://www.wailian.work/images/2018/12/15/storm-stream-groupings.jpg)

- Shuffle Grouping随机分组：保证每个bolt接收到的tuple数目相同。
- Fields Grouping按字段分组：比如按userid来分组，具有同样userid的tuple会被分到相同的Bolts，而不同的userid则会被分配到不同的Bolts。
- All Grouping广播发送：对于每一个tuple，所有的Bolts都会收到。
- Global Grouping全局分组：这个tuple被分配到storm中的一个bolt的其中一个task。再具体一点就是分配给id值最低的那个task。
- Non Grouping无分组：假设你不关心流式如何分组的煤科院使用这种方式，目前这种分组和随机分组是一样的效果，不同的是Storm会把这个Bolt放到Bolt的订阅者的同一个线程中执行。
- Direct Grouping直接分组：这种分组意味着消息的发送者指定由消息接收者的哪个task处理这个消息。只有被声明为Direct Stream的消息流可以声明这种分组方法。而且这种消息tuple必须使用`emitDirect`方法来发射。消息处理者可以通过`TopologyContext`来获取处理它的消息的taskid（`OutputCollector.emit`方法也会返回taskid）
- 本地分组：如果目标bolt在同一工作进程存在一个或多个任务，会随机分配给执行任务，否则该分组方式与随机分组方式是一样的。


## References
- [Storm](http://storm.apache.org/)