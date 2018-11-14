# ZooKeeper笔记

## 1.1 ZooKeeper简介
ZooKeeper是一个高效的分布式协调服务，它暴露了一些公用服务，比如命名/配置管理/同步控制/群组服务等。我们可以使用ZK来实现比如达成共识/集群管理/leader选举等。

ZooKeeper是一个高可用的分布式管理与协调框架，基于ZAB算法（原子消息广播协议）的实现。该框架能够很好地保证分布式环境中数据的一致性。也正是基于这样的特性，使得ZooKeeper 成为了解决分布式一致性问题的利器。
- 顺序一致性：从一个客户端发起的事务请求，最终将会严格地按照其发起的顺序被应用到ZooKeeper中去。
- 原子性：所有事务请求的处理结果在整个集群中所有机器上的应用情况是一致的，也就是说，要么整个集群所有的机器都成功应用了某一事务，要么有没有应用，一定不会出现部分机器应用了该事务，而另一部分没有应用的情况。
- 单一视图：无论客户端连接的是哪一个ZooKeeper服务器，其看到的服务器端数据模型都是一致的。
- 可靠性：一旦服务器成功地应用了一个事务，并完成对客户端的响应，那么该事务所引起的服务器端状态将会被一致保留下来。除非有另外一个事务对其更改。
- 实时性：通常所说的实时性就是指一旦事务被成功应用，那么客户端就能立刻从服务器上获取变更后的新数据，ZooKeeper仅仅能保证在一段时间内，客户端最终一定能从服务器端读敢最新的数据状态。

ZooKeeper分布式一致性算法：
- ZAB协议：高可用的分布式数据主备系统
- Paxos协议：分布式的一致性状态机系统，CAS

## 1.2 ZooKeeper设计目标
1. 简单的数据结构。ZooKeeper就是以简单的树形结构来进行相互协调的（也叫树形名字空间）。
1. 可以构建集群。一般ZooKeeper集群通常由一组机器构成，一般3~5台机器就可以组成一个ZooKeeper集群了。只要集群中超过半数以上的机器能够正常工作，那么整个集群就能够正常对外提供服务。
1. 顺序访问。对于来自每一个客户端的每一个请求，ZooKeeper都会分配一个全局唯的递增编号，这个编号反应了所有事务操作的先后顺序，应用程序可以使用ZooKeeper的这个特性来实现更高层次的同步。
1. 高性能。由于ZooKeeper将全量数据存储在内存中，并直接服务于所有的非事务请求，因此尤其是在读操作为主的场景下性能非常突出。在JMater压力测试下（100%读请求场景下），其结果大约在12~13W的QPS。

## 1.3 ZooKeeper的结构
ZooKeeper会维护一个具有层次关系的数据结构，它非常类似于一个标准的文件系统。

![ZooKeeper-node-min](http://www.wailian.work/images/2018/11/14/ZooKeeper-node-min.png)
![ZooKeeper-stru-min](http://www.wailian.work/images/2018/11/14/ZooKeeper-stru-min.png)

## 1.4 ZooKeeper的数据模型
1. 每个子目录项如`NameService`都被称作为znode，这个znode是被它所在的路径唯一标识，如`Server1`这个znode的标识为`/NameService/Server1`
1. znode可以有子节点目录，并且每个znode可以存储数据，注意`EPHEMERAL`类型的目录节点不能有子节点目录
1. znode是有版本的，每个znode中存储的数据可以有多个版本，也就是一个访问路径中可以存储多份数据
1. znode可以是临时节点，一旦创建这个znode的客户端与服务器失去联系，这个znode也将自动删除，ZooKeeper的客户端和服务器通信采用长连接方式，每个客户端和服务器通过心跳来保持连接，这个连接状态称为session，如果znode是临时节点，这个session失效，znode也就删除了
1. znode的目录名可以自动编号，如`App1`已经存在，再创建的话，将会自动命名为`App2`
1. znode可以被监控，包括这个目录节点中存储的数据的修改，子节点目录的变化等，一旦变化可以通知设置监控的客户端，这个是ZooKeeper的核心特性，ZooKeeper的很多功能都是基于这个特性实现的

## 1.5 ZooKeeper组成
ZK server根据其身份特性分为三种：leader，follower，Observer，其中follower和Observer又统称Learner（学习者）。
- leader：负责客户端的writer类型请求
- follower：负责客户端的reader类型请求，参与leader选举等。
- Observer：特殊的“follower”，其可以接受客户端reader请求，但不参与选举。（扩容系统支撑能力，提高了读取速度。因为它不接受任何同步的写入请求，只负责与leader同步数据）

## 1.6 ZooKeeper应用场景
ZooKeeper从设计模式角度来看，是一个基于观察者模式设计的分布式服务管理框架，它负责存储和管理大家都关心的数据，然后接受观察者的注册，一旦这些数据的状态发生变化，ZooKeeper就将负责通知已经在ZooKeeper上注册的那些观察者做出相应的反应，从而实现集群中类似Master/Slave管理模式。
- 配置管理
- 集群管理
- 发布与订阅
- 数据库切换
- 分布式日志的收集
- 分布式锁、队列管理等等

## 1.7 ZooKeeper应用场景说明
1. 配置管理：配置的管理在分布式应用环境中很常见，比如我们在平常的应用系统中，经常会碰到这样的需求：如机器的配置列表、运行时的开关配置、数据库配置信息等。这些全局配置信息通常具备以下3个特性：
    1. 数据量比较小。
    1. 数据内容在运行时动态发生变化。
    1. 集群中各个集群共享信息，配置一致。
1. 集群管理：ZooKeeper不仅能够帮你维护当前的集群中机器的服务状态，而且能够帮你选出一个“总管”，让这个总管来管理集群，这就是ZooKeeper的另一个功能leader，并实现集群容错功能。
    1. 希望知道当前集群中究竟有多少机器工作。
    1. 对集群中每天集群的运行时状态进行数据收集。
    1. 对集群中每台集群进行上下线操作。
1. 发布与订阅：ZooKeeper是一个典型的发布/订阅模式的分布式数控管理与协调框架，开发人员可以使用它来进行分布式数据的发布与订阅。
1. 数据库切换：比如我们初始化ZooKeeper时，读取其节点上的数据库配置文件，当配置一旦发生变更时，ZooKeeper就能帮助我们把变更的通知发送到各个客户端，每个互动在接收到这个变更通知后，就可以从新进行最新数据的获取。
1. 分布式日志收集：我们可以做一个日志系统收集集群中所有的日志信息，进行统一管理。
1. ZooKeeper的特性就是在分布式场景下高可用，但是原生的API实现分布式功能非常困难，团队去实现也太浪费时间，即使实现了也未必稳定。那么可以采用第三方的客户端的完美实现，比如Curator框架，它是Apache的顶级项目

## 1.8 ZooKeeper开源框架应用
ZooKeeper使用场景非常广泛：如Hadoop、Storm。消息中间件、RPC服务框架、数据库增量订阅与消费组件（如MySQL Binlog）、分布式数据库同步系统，淘宝的Otter

## 2.1 ZooKeeper集群搭建
1. 一共三个节点（zk服务器集群规模不小于3个节点），要求服务器之间系统时间保持一致。
1. 进行解压：`tar zookeeper-3.4.5.tar.gz`
1. 重命名：`mv zookeeper-3.4.5 zookeeper`
1. 修改环境变量：`vi /etc/profile`
    ```
    export ZOOKEEPER_HOME=/usr/local/zookeeper
    export PATH=.:$HADOOP_HOME/bin:$ZOOKEEPER_HOME/bin:$JAVA_HOME/...
    ```
1. 刷新：`source /etc/profile`
1. 到zookeeper下修改配置文件
    ```
    cd /usr/local/zookeeper/conf
    mv zoo_sample.cfg zoo.cfg
    ```
1. 修改conf：`vi zoo.cfg`修改两处
    ```
    dataDir=/usr/local/zookeeper/data
    
    server.0=bhz:2888:3888
    server.1=hadoop1:2888:3888
    server.2=hadoop2:2888:3888
    ```
1. 服务器标识配置：
    - 创建文件夹：`mkdir data`
    - 创建文件`myid`，并填写内容为`0`：`vi myid`（内容为服务器标识：0）
1. 进行复制zookeeper目录到hadoop01和hadoop02还有/etc/profile文件
1. 把hadoop01和hadoop02中的`myid`文件里的值修改为`1`和`2`路径（`vi /usr/local/zookeeper/data/myid`）
1. 启动zookeeper：
    - 路径：`/usr/local/zookeeper/bin`
    - 执行：`zkserver.sh start`（往意这里3台机器都要进行启动）
    - 状态：`zkserver.sh status`（在三个节点上检验zk的mode，一个leader和两个follower）

## 2.2 ZooKeeper操作Shell
`zkCli.sh`进入ZooKeeper客户端根据提示命令进行操作：
- 查找：`ls /`，`ls /zookeeper`
- 创建并赋值：`create /bhz hadoop`
- 获取：`get /bhz`
- 设值：`set /bhz baihezhuo`
- 可以看到ZooKeeper集群的数据一致性
- `rmr /path`递归删除节点
- `delete /path/child`删除指定某个节点

创建节点有两种类型：短暂（ephemeral）、持久（persistent）

## 2.3 配置文件zoo.cfg详解
- `tickTime`：基本事件单元，以毫秒为单位。这个时间是作为ZooKeeper服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每隔`tickTime`时间就会发送一个心跳。
- `dataDir`：存储内存中数据库快照的位置，顾名思义就是ZooKeeper保存数据的目录，默认情况下，ZooKeeper将写数据的日志文件也保存在这个目录里。
- `clientPort`：客户端连接ZooKeeper服务器的端口，ZooKeeper会监听这个端口，接受客户端的访问请求。
- `initLimit`：用来配置ZooKeeper接受客户端初始化连接时最长能忍受多少个心跳时间间隔数，当已经超过10个心跳的时间（也就是`tickTime`）长度后ZooKeeper服务器还没有收到客户端的返回信息，那么表明这个客户端连接失败。总的时间长度就是`10*2000=20`秒。
- `syncLimit`：标识leader与follower之间发送消息，请求和应答时间长度，最长不能超过多少个`tickTime`的时间长度，总的时间长度就是`5*2000=10`秒。
- `server.A=B:C:D`：
    - A表示这个是第几号服务器
    - B是这个服务器的ip地址
    - C表示这个服务器与集群中的leader服务器交换信息的端口
    - D表示万一集群中的leader服务器挂了，需要一个端口来重新进行选举，选出一个新的leader

## 3.1 Java操作ZooKeeper


## 3.2 Watcher、ZK状态、事件类型


## 3.3 ZooKeeper的Watcher


## 3.4 ZooKeeper的ACL（AUTH）


## 4.1 查看ZooKeeper及管理工具


## 4.2 实际应用场景


## 5.1 zkClient使用


## 6.1 Curator框架


## 6.2 Curator框架使用


## 6.3 Curator的监听


## References
- ZooKeeper