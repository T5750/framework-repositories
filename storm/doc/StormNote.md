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

### 示例
- `PWTopologyLocal`, `PWTopologyCluster`

![storm-topology-execution-min](https://www.wailian.work/images/2018/12/16/storm-topology-execution-min.png)

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

### 示例
- `PWTopologyLocalMulti`, `PWTopologyClusterMulti`

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

常见的流分组：

![storm-grouping-min](https://www.wailian.work/images/2018/12/16/storm-grouping-min.png)

### 示例
- `PWTopologyLocalFieldsGrouping`, `PWTopologyLocalAllGrouping`, `PWTopologyLocalGlobalGrouping`

## 5.5 Storm WordCount
以一个统计单词的小程序来说明问题

![storm-word-count-topology-min](https://www.wailian.work/images/2018/12/16/storm-word-count-topology-min.png)

上面的示意图中有4个组件，分别为一个spout和3个bolt，当数据源spout取得数据（可以是一个句子，里面包含多个单词）以后，发送给SolitBolt进行切分，然后由CountBolt进行统计结果，最终由ReportBolt记录结果。

### 示例
- `WordTopology`

## 5.6 Storm Spout的可靠性
- Spout是Storm数据流的入口，在设计拓扑时，一件很重要的事情就是需要考虑消息的可靠性，如果消息不能被处理而丢失是很严重的问题。
- 我们继续做实验，以一个传递消息并且实时处理的例子，来说明这个问题。
- 新建maven项目
- 通过示例，如果在第一个bolt处理的时候出现异常，我们可以让整个数据进行重发，但是如果在第二个bolt处理的时候出现了异常，那么也会让对应的spout里的数据重发，这样就会出现事务的问题，我们就需要进行判断或者是进行记录。
- 如果是数据入库的话，可以与原ID进行比对。
- 如果是事务的话在编写代码时，尽量就不要进行拆分tuple。
- 或者使用storm的Trident框架
- Storm系统中有一组叫做“acker”的特殊的任务，它们负责跟踪DAG（有向无环图）中的每个消息。
- acker任务保存了spout消息id到一对值的映射。第一个值就是spout的任务id，通过这个id，acker就知道消息处理完成时该通知哪个spout任务。第二个值是一个64bit的数字，我们称之为“ack val”，它是树中所有消息的随机id的异或结果。ack val表示了整棵树的的状态，无论这棵树多大，另需要这个固定大小的数字就可以跟踪整棵树。当消息被创建和被应答的时候都会有相同的消息id发送过来做异或。
- 每当acker发现一棵树的ack val值为0的时候，它就知道这棵树已经被完全处理了。因为消息的随机ID是一个64bit的值，因此ack val在树处理完之前被置为0的概率非常小。假设你每秒钟发送一万个消息，从概率上说，至少需要50,000,000年才会有机会发生一次错误。即使如此，也另有在这个消息确实处理失败的情况下才会有数据的丢失！

下图是spout处理可靠性的示意图：当spout发送一个消息时，分配给2个bolt分别处理，那么在最后一个bolt接受的时候会做**异或运算**。

![storm-message-min](https://www.wailian.work/images/2018/12/16/storm-message-min.jpg)

### 示例
- `MessageTopology`
- `SpliterBolt`: Success, `WriterBolt`: Success
	```
	【消息发送成功！！！】（msgId = 0）
	【消息发送成功！！！】（msgId = 1）
	【消息发送成功！！！】（msgId = 2）
	【消息发送成功！！！】（msgId = 3）
	【消息发送成功！！！】（msgId = 4）
	```
- `SpliterBolt`: Exception, `WriterBolt`: Success
	```
	【消息发送失败！！！】（msgId = 2）
	【重发进行中...】
	【重发成功！！！】
	【消息发送成功！！！】（msgId = 0）
	【消息发送成功！！！】（msgId = 1）
	【消息发送成功！！！】（msgId = 3）
	【消息发送成功！！！】（msgId = 4）
	【消息发送成功！！！】（msgId = 2）
	```
- `SpliterBolt`: Success, `WriterBolt`: Exception
	```
	【消息发送成功！！！】（msgId = 0）
	【消息发送成功！！！】（msgId = 1）
	【消息发送成功！！！】（msgId = 2）
	【消息发送失败！！！】（msgId = 3）
	【重发进行中...】
	【重发成功！！！】
	【消息发送成功！！！】（msgId = 4）
	【消息发送成功！！！】（msgId = 3）
	```

## 6.1 RPC介绍
- 调用客户端句柄，执行传送参数
- 调用本地系统内核发送网络消息
- 消息传送到远程主机
- 服务器句柄得到消息并取得参数
- 执行远程过程
- 执行的过程将结果返回服务器句柄
- 服务器句柄返回结果，调用远程系统内核
- 消息传回本地主机
- 客户句柄由内核接收消息
- 客户接收句柄返回的数据

## 6.2 Storm DRPC介绍
- 分布式RPC（Distributed RPC，DRPC）
- Storm里面引入DRPC主要是利用storm的实时计算能力来并行化CPU密集型（CPU intensive）的计算任务。DRPC的storm topology以函数的参数流作为输入，而把这些函数调用的返回值作为topology的输出流。
- DRPC其实不能算是storm本身的一个特性，它是通过组合storm的元素stream、spout、bolt、topology而成的一种模式（pattern）。本来应该把DRPC单独打成一个包的，但是DRPC实在是太有用了，所以把它和storm捆绑在一起。
- Distributed RPC是通过一个”DRPC Server”来实现
- DRPC Server的整体工作过程如下：
1. 接收一个RPC请求
1. 发送请求到storm topology
1. 从storm topology接收结果
1. 把结果发回给等待的客户端

## 6.3 Storm DRPC配置和示例
- Storm提供了一个称作`LinearDRPCTopologyBuilder`的topology builder，它把实现DRPC的几乎所有步骤都简化了
- 相关代码地址：[https://github.com/nathanmarz/storm-starter/blob/master/src/jvm/storm/starter/BasicDRPCTopology.java](https://github.com/nathanmarz/storm-starter/blob/master/src/jvm/storm/starter/BasicDRPCTopology.java)
- 实现DRPC步骤：
1. 需要修改配置文件内容为（分別修改每台机器配置）：
	```
	vim /usr/local/apache-storm-1.2.2/conf/storm.yaml

	drpc.servers:
		- "192.168.100.163"
	```
2. 需要启动storm的drpc：服务，命令：`storm drpc &`
3. 把相关的topology代码上传到storm服务器上
	```
	storm jar storm-1.0.jar t5750.storm.drpc.basic.BasicDRPCTopology exc
	```
4. 在本地调用远程topology即可

### 示例
- `BasicDRPCTopology`, `DrpcExclam`

## 6.4 Storm DRPC实例场景
- 主要使用storm的并行计算能力来进行，我们在微博、论坛进行转发帖子的时候，是对url进行转发，分享给粉丝（关注我的人），那么每一个人的粉丝（关注者可能会有重复的情况），这个例子就是统计一下帖子（url）的转发人数
- 相关代码地址：[https://github.com/nathanmarz/storm-starter/blob/master/src/jvm/storm/starter/ReachTopology.java](https://github.com/nathanmarz/storm-starter/blob/master/src/jvm/storm/starter/ReachTopology.java)
- 实现步骤如下：
1. 获取当前转发帖子的人
1. 获取当前人的粉丝（关注者）
1. 进行粉丝去重
1. 统计人数
1. 最后使用drpc远程调用topology返回执行结果

### 示例
- `ReachTopology`, `DrpcReach`

## 7.1 Storm Trident介绍
- Trident是在Storm基础上，一个以实时计算为目标的高度抽象。它在提供处理大吞吐量数据能力（每秒百万次消息）的同时，也提供了低延时分布式查询和有状态流式处理的能力。如果你对Pig和Cascading这种高级批处理工具很了解的话，那么应该很容易理解Trident，因为它们之间很多的概念和思想都是类似的。Tident提供了joins，aggregations，grouping，functions，以及filters等能力。除此之外，Trident还提供了一些与门的原语，从而在基于数据库或者其它存储的前提下来应付有状态的递增式处理。Trident也提供一致性（consistent）、有且仅有一次（exactly-once）等语义，这使得我们在使用trident toplogy时变得容易。
- "Stream"是Trident中的核心数据模型，它被当做一系列的batch来处理。在Storm集群的节点之间，一个stream被划分成很多partition（分区），对流的操作（operation）是在每个partition上并行执行的。
- 对每个partition的局部操作包括：function、filter、partitionAggregate、stateQuery、partitionPersist、project等。

## 7.2 Storm Trident functions
Trident's function contain the logic to modify the original tuple. A function gets a set of fields of a tuple as input and emits one or more tuples as output. The output fields of the tuple are merged with the input fields of a tuple to form the complete tuple, which will pass to the next action in the topology. If the function emits a zero tuple that corresponds to the input tuple, then that tuple is removed from the stream.

We can write a custom Trident function by extending the `storm.trident.operation.BaseFunction` class and implementing the `execute(TridentTuple tuple, TridentCollector collector)` method.

Let's write a sample Trident function that will calculate the sum of first two fields and emit the new `sum` field. The following is the code of the `SumFunction` class:

```
public class SumFunction extends BaseFunction {
 private static final long serialVersionUID = 5L;
 public void execute(TridentTuple tuple, TridentCollector
 collector) {
 int number1 = tuple.getInteger(0);
 int number2 = tuple.getInteger(1);
 int sum = number1+number2;
 // emit the sum of first two fields
 collector.emit(new Values(sum));
 }
}
```
Suppose we are getting the `dummyStream` stream as an input that contains four fields, a, b, c, and d, and only the a and b fields are passed as input fields to the `SumFunction` class. The `SumFunction` class emits the new `sum` field. The `sum` field emitted by the execute method of the `SumFunction` class is merged with the input tuple to form the complete tuple. Hence, the total number of fields in the output tuple is 5 (a, b, c, d, and `sum`). The following is a sample piece of code that shows how we can pass the input fields and the name of a new field to the Trident function:

```
dummyStream.each(new Fields("a","b"), new SumFunction (), new Fields("sum"))
```
执行的结果如下：

![trident-sum-function-min](https://www.wailian.work/images/2018/12/19/trident-sum-function-min.png)

### 示例
- `TridentFunction`

## 7.3 Storm Trident filters
A Trident filter gets a set of fields as input and returns either `true` or `false` depending on whether certain conditions are satisfied or not. If `true` is returned, then the tuple is kept in the output stream; otherwise, the tuple is removed from the stream.

We can write a custom Trident filter by extending the `storm.trident.operation.BaseFilter` class and implementing the `isKeep(TridentTuple tuple)` method.

Let's write a sample Trident filter that will check whether the sum of the input fields is even or odd. If the sum is even, then the Trident filter emits `true`; otherwise, it emits `false`. The following is the code of the `CheckEvenSumFilter` class:

```
public static class CheckEvenSumFilter extends BaseFilter{
 private static final long serialVersionUID = 7L;
 public boolean isKeep(TridentTuple tuple) {
 int number1 = tuple.getInteger(0);
 int number2 = tuple.getInteger(1);
 int sum = number1+number2;
 if(sum % 2 == 0) {
 return true;
 }
 return false;
 }
}
```

### 示例
- `TridentFilter`

## 7.4 Storm Trident projections
Trident projections keep only those fields in the stream that are specified in the projection operation. Suppose an input stream contains three fields, x, y, and z, and we are passing the x field in the projection operation. Then, the output stream will contain tuples with the single `field x`. The following is the piece of code that shows how we can use the projection operation:

```
mystream.project(new Fields("x"))
```
The following diagram shows the projection operation:

![trident-projection-min](https://www.wailian.work/images/2018/12/19/trident-projection-min.png)

## 7.5 Trident repartitioning operations
### The shuffle operation
The `shuffle` repartitioning operation partitions the tuples in a uniform, random way across multiple tasks. This repartitioning operation is generally used when we want to distribute our processing load uniformly across tasks. The following diagram shows how the input tuples are repartitioned using the `shuffle` operation:

![trident-shuffle-min](https://www.wailian.work/images/2018/12/19/trident-shuffle-min.png)

The following piece of code shows how we can use the `shuffle` operation:
```
mystream.shuffle().each(new Fields("a","b"), new myFilter()).parallelismHint(2)
```

### The partitionBy operation
The `partitionBy` repartitioning operation enables you to partition a stream on the basis of some fields in the tuples. For example, if you want all tweets from a particular user to be delivered to the same target partition, then you can partition the tweet stream by applying the `partitionBy` operation on the `username` field in the following manner:
```
mystream.partitionBy(new Fields("username")).each(new Fields("username","text"), new myFilter()).parallelismHint(2)
```
The `partitionBy` operation applies the `target partition = hash (fields) % (number of target partition)` formula to decide the target partition. As the preceding formula shows, the `partitionBy` operation calculates the hash of input fields to decide the target partition. Hence, it does not guarantee that all the tasks will get tuples to process. For example, if you have applied a `partitionBy` operation on a field, say X, with only two possible values, A and B, and created two tasks for the `myFilter` filter, then it is possible that both `hash (A) % 2` and `hash (B) % 2` are equal. This will result in all the tuples being routed to a single task and the other being completely idle. The following diagram shows how the input tuples are repartitioned using the `partitionBy` operation:

![trident-partition-by-min](http://www.wailian.work/images/2018/12/20/trident-partition-by-min.png)

### The global operation
The `global` repartitioning operation routes all tuples to the same partition. Hence, the same target partition is selected for all the batches in the stream. The following diagram shows how the tuples are repartitioned using the `global` operation:

![trident-global-min](http://www.wailian.work/images/2018/12/20/trident-global-min.png)

The following piece of code shows how we can use the `global` operation:
```
mystream.global().each(new Fields("a","b"), new myFilter()).parallelismHint(2)
```

### The broadcast operation
The `broadcast` operation is a special repartitioning operation that does not partition the tuples but replicates them to all partitions. The following is a diagram that shows how the tuples are sent over the network:

![trident-global-min](http://www.wailian.work/images/2018/12/20/trident-global-min.png)

The following piece of code shows how we can use the `broadcast` operation:
```
mystream.broadcast().each(new Fields("a","b"), new myFilter()).parallelismHint(2)
```

### The batchGlobal operation
This repartitioning operation routes all tuples that belong to one batch to the same target partition. The other batches of the same stream may go to a different partition. As the name suggests, this repartition is global at the batch level. The following diagram shows how the tuples are repartitioned using the `batchGlobal` operation:

![trident-batch-global-min](http://www.wailian.work/images/2018/12/20/trident-batch-global-min.png)

The following piece of code shows how we can use the `batchGlobal` operation:
```
mystream.batchGlobal().each(new Fields("a","b"), new myFilter()).parallelismHint(2)
```

### The partition operation
If none of the preceding repartitioning operations fit your use case, you can define your own custom repartition function by implementing the `backtype.storm.grouping.CustomStreamGrouping` interface. The following is a sample custom repartition that partitions the stream on the basis of the values of the `country` field:
```
public class CountryRepartition implements CustomStreamGrouping, Serializable {
	private static final long serialVersionUID = 1L;
	private static final Map<String, Integer> countries =
	ImmutableMap.of(
		"India", 0,
		"Japan", 1,
		"United State", 2,
		"China", 3,
		"Brazil", 4
	);
	private int tasks = 0;
	public void prepare(WorkerTopologyContext context, GlobalStreamId stream, List<Integer> targetTasks){
		tasks = targetTasks.size();
	}
	public List<Integer> chooseTasks(int taskId, List<Object> values) {
		String country = (String) values.get(0);
		return ImmutableList.of(countries.get(country) % tasks);
	}
}
```
The `CountryRepartition` class implements the `backtype.storm.grouping.CustomStreamGrouping` interface. The `chooseTasks()` method contains the repartitioning logic to identify the next task in the topology for the input tuple. The `prepare()` method calls at the start and performs the initialization activity.

## 7.6 Trident Partition aggregate


## References
- [Storm](http://storm.apache.org/)
- Learning Storm