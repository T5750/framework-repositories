# ZooKeeper笔记

## 1.1 ZooKeeper简介
ZooKeeper是一个高效的分布式协调服务，它暴露了一些公用服务，比如命名/配置管理/同步控制/群组服务等。我们可以使用ZK来实现比如达成共识/集群管理/leader选举等。

ZooKeeper是一个高可用的分布式管理与协调框架，基于ZAB算法（原子消息广播协议）的实现。该框架能够很好地保证分布式环境中数据的一致性。也正是基于这样的特性，使得ZooKeeper成为了解决分布式一致性问题的利器。
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

![ZooKeeper-node-min](https://www.wailian.work/images/2018/11/14/ZooKeeper-node-min.png)
![ZooKeeper-stru-min](https://www.wailian.work/images/2018/11/14/ZooKeeper-stru-min.png)

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
ZooKeeper的javaclient使我们更轻松地进行ZooKeeper各种操作，引入`zookeeper-3.3.4.jar`和`zkclient-0.1.jar`即可。
- `zookeeper-3.3.4.jar`为官方提供的javaAPI
- `zkclient-0.1.jar`为在原生api基础之上进行扩展的开源Java客户端

### 3.1.1 创建会话
创建会话方法：客户端可以通过创建一个ZooKeeper实例来连接ZooKeeper服务器。`ZooKeeper(Arguments)`方法（4个构造方法），参数说明如下：
- `connectString`：连接服务器列表，已","分割。
- `sessionTimeout`：心跳检测时间周期（毫秒）
- `wather`：事件处理通知器。
- `canBeReadOnly`：标识当前会话是否支持只读。
- `sessionId`和`sessionPasswd`：提供连接ZooKeeper的`sessionId`和密码，通过这两个确定唯一台客户端，目的是可以提供重复会话。

注意：ZooKeeper客户端和服务器端会话的建立是一个异步的过程，也就是说在程序中，我们程序方法在处理完客户端初始化后立即返回（也就是说程序往下执行代码，这样，大多数情况下我们并没有真正构建好一个可用会话，在会话的生命周期处于`CONNECTING`时才算真正建立完毕）

### 3.1.2 创建节点
创建节点（znode）方法：`create`
- 同步方式：
    - 参数1，节点路径（名称）：/nodeName（不允许递归创建节点，也就是说在父节点不存在的情况下，不允许创建子节点）
    - 参数2，节点内容：要求类型是字节数组（不支持序列化方式，如果需要实现序列化，可使用Java相关序列化框架，如Hessian、Kryo框架）
    - 参数3，节点权限：使用`Ids.OPEN_ACL_UNSAFE`开放权限即可。（这个参数一般在权限没有太高要求的场景下，没必要关注）
    - 参数4，节点类型：创建节点的类型：`CreateMode.*`，提供四种节点类型
        - `PERSISTENT`（持久节点）
        - `PERSISTENT_SEQUENTIAL`（持久顺序节点）
        - `EPHEMERAL`（临时节点）
        - `EPHEMERAL_SEQUENTIAL`（临时顺序节点）
- 异步方式：（在同步参数基础上增加两个参数）
    - 参数5，注册一个异步回调函数，要实现`AsynCallBack.StringCallBack`接口，重写`processResult(int rc, string path, Object ctx, String name)`方法，当节点创建完毕后执行此方法。
        - `rc`：为服务端响应码，`0`表示调用成功、`-4`表示端口连接、`-110`表示指定节点存在、`-112`表示会话已经过期。
        - `path`：接口调用时传入API的数据节点的路径参数
        - `ctx`：为调用接口传入API的`ctx`值
        - `name`：实际在服务器端创建节点的名称
    - 参数6，传递给回调函数的参数，一般为上下文（Context）信息

### 3.1.3 删除节点
删除节点：`delete`方法
- 同步方式：
    - 参数1，节点名称/deletePath
    - 参数2，版本号，即表明本次删除操作是针对该数据的某个版本进行的操作。
- 异步方式：（和`create`方法一致）
    - 参数3：一个异步回调函数
    - 参数4：用于传递上下文信息的对象。

注意：在ZooKeeper中，只允许删除叶子节点信息，也就是说如果当前节点不是叶子节点则无法删除，或必须先删除其下所有子节点。

### 3.1.4 读取数据
`getChildren`读取数据方法：包括子节点列表的获取和子节点数据的获取。
- 参数1，`path`：获取指定节点的下的数据（获取子节点列表）
- 参数2，`watcher`：注册的`watcher`，一旦在本次子节点获取后，子节点列表发生变更的话，那么就会向客户端发送通知。该参数允许为`null`。
- 参数3，`watch`：表明是否需要注册一个`watcher`；如果为`true`，则会使用默认`watcher`。如果为`false`，则表明不需要注册`watcher`。
- 参数4，`cb`：回调函数。
- 参数5，`ctx`：上下文信息对象。
- 参数6，`stat`：指定数据节点的节点状态信息。

注意：当我们获取指定节点的子节点列表后，还需要订阅这个子节点列表的变化通知，这时候就可以通过注册一个`watcher`来实现
- 当子节点被添加或删除时，服务器端就会触发一个`NodeChildrenChanged`类型的事件通知，需要注意的是服务器端发送给客户端的事件通知中，是不包含最新的节点列表的，客户端必须主动从新进行获取，通常在客户端收到这个事件通知后，就可以再次主动获取最新的子节点列表了。
- 也就是说，ZooKeeper服务端在向客户端发送`NodeChildrenChanged`事件通知的时候，仅仅只发了一个通知，不会把节点变化情况发给客户端，需要客户端自己重新获取。
- 另外，`watcher`通知是一次性的，即触发后失效，因此客户端需要反复注册`watcher`才行。

`getData`方法：获取指定节点的数据内容。
- 参数1，`path`：路径
- 参数2，`watcher`：注册的`watcher`对象。一旦之后节点内容有变更，则会向客户端发送通知，该参数允许为`null`。
- 参数3，`stat`：指定节点的状态信息。
- 参数4，`watch`：是否使用`watcher`，如果为`true`则使用默认`watcher`，`false`则不使用`watcher`。
- 参数5，`cb`：回调函数。
- 参数6，`ctx`：用于传递的下文信息对象。

注意：该方法和`getChildren`方法基本相同，主要是注册的`watcher`有所不同，客户端在获取一个阶段数据内容时，是可以进行`watcher`注册的，一旦节点发生变更，则服务器端会发送给客户端一个`NodeDataChanged`的事件通知。

### 3.1.5 修改数据
`setData`方法：修改指定节点的数据内容。
- 参数1，`path`：路径。
- 参数2，`data`：数据内容。
- 参数3，版本号（`-1`：覆盖之前所有的版本）
- 参数4，`cb`：回调函数。
- 参数5，`ctx`：用于传递的下文信息对象。

### 3.1.6 是否存在
`exists`方法：检测节点是否存在。
- 参数1，`path`：路径
- 参数2，`watcher`：注册的`watcher`对象。一旦之后节点内容有变更，则会像客户端发送通知，该参数允许为`null`。（用于三类事件监听：节点的创建、删除、更新）
- 参数3，`watch`：是否使用`watcher`，如果为`true`则使用默认`watcher`，`false`则不使用`watcher`。
- 参数4，`cb`：回调函数。
- 参数5，`ctx`：用于传递的下文信息对象。

注意：`exists`方法意义在于无论节点是否存在，都可以进行注册`watcher`，能够对节点的创建、删除和修改进行监听，但是其子节点发送各种变化，都不会通知客户端。

## 3.2 Watcher、ZK状态、事件类型
ZooKeeper有`watch`事件，是一次性触发的，当`watch`监视的数据发生变化时，通知设置了该`watch`的client，即`watcher`。同样，其`watcher`是监听数据发送了某些变化，那就一定会有对应的事件类型和状态类型。

事件类型：（znode节点相关的）
- `EventType.NodeCreated`
- `EventType.NodeDataChanged`
- `EventType.NodeChildrenChanged`
- `EventType.NodeDeleted`

状态类型：（跟客户端实例相关的）
- `KeeperState.Disconnected`
- `KeeperState.SyncConnected`
- `KeeperState.AuthFailed`
- `KeeperState.Expired`

## 3.3 ZooKeeper的Watcher
`watcher`的特性：一次性、客户端串行执行、轻量。
- **一次性**：对于ZK的`watcher`，只需记住一点：ZooKeeper有`watch`事件，是一次性触发的，当`watch`监视的数据发生变化时，通知设置了该`watch`的client，即`watcher`，由于ZooKeeper的监控都是一次性的，所以每次必须设置监控。
- **客户端串行执行**：客户端`watcher`回调的过程是一个串行同步的过程，这为我们保证了顺序，同时需要开发人员注意一点，千万不要因为一个`watcher`的处理逻辑影响了整个客户端的`watcher`回调。
- **轻量**：`WatchedEvent`是ZooKeeper整个`watcher`通知机制的最小通知单元，整个数据结构只包含三部分：通知状态、事件类型和节点路径。也就是说`watcher`通知非常的简单，只会告诉客户端发生了事件，而不会告知其具体内容，需要客户自己去进行获取。比如`NodeDataChanged`事件，ZooKeeper只会通知客户端指定节点的数据发生了变更，而不会直接提供具体的数据内容。

示例：`ZooKeeperWatcher`

## 3.4 ZooKeeper的ACL（AUTH）
ACL（Access Control List），ZooKeeper作为一个分布式协调框架，其内部存储的都是一些关乎分布式系统运行时状态的元数据，尤其是设计到一些分布式锁、Master选举和协调等应用场景。我们需要有效地保障ZooKeeper中的数据安全，ZooKeeper提供一套完善的ACL权限控制机制来保障数据的安全。ZK提供了三种模式。权限模式、授权对象、权限。

权限模式：Scheme，开发人员最多使用的如下四种权限模式：
- IP：ip模式通过ip地址粒度来进行控制权限，例如配置ip：`192.168.1.107`即表示权限控制都是针对这个ip地址的，同时也支持按网段分配，比如`192.168.1.*`
- Digest：digest是最常用的权限控制模式，也更符合我们对权限控制的认识，其类似于"username:password"形式的权限标识进行权限配置。ZK会对形成的权限标识先后进行两次编码处理，分别是SHA-1加密算法、BASE64编码。
- World：World是一直最开放的权限控制模式。这种模式可以看做为特殊的Digest，它仅仅是一个标识而已。
- Super：超级用户模式，在超级用户模式下可以对ZK任意进行操作。

权限对象：指的是权限赋予的用户或者一个指定的实体，例如ip地址或机器等。在不同的模式下，授权对象是不同的。这种模式和权限对象一一对应。

权限：权限就是指那些通过权限检测后可以被允许执行的操作，在ZK中，对数据的操作权限分为以下五大类：
```
CREATE、DELETE、READ、WRITE、ADMIN
```
示例：`ZooKeeperAuth`

## 4.1 查看ZooKeeper及管理工具
与eclipse集成的管理ZooKeeper工具：zookeeperBrowser

[http://www.massedynamic.org/eclipse/updates/](http://www.massedynamic.org/eclipse/updates/)

## 4.2 实际应用场景
我们希望ZooKeeper对分布式系统的配置文件进行管理，也就是说多个服务器进行`watcher`，ZooKeeper节点发送变化，则我们实时更新配置文件。

我们要完成多个应用服务器注册`watcher`，实时观察数据的变化，然后，反馈给每个服务器变更的数据信息，观察ZooKeeper节点

## 5.1 ZkClient使用
ZkClient是由Datameer的工程师StefanGroschupf和Peter Voss一起开发的。在原生API接口基础上进行了封装，简化了ZK的复杂性。
1. 创建客户端方法：`ZkClient(Arguments)`
    - 参数1：`zkServers`ZooKeeper服务器的地址，用","分割。
    - 参数2：`sessionTimeout`超时会话，为毫秒，默认为30000ms
    - 参数3：`connectionTimeout`连接超时会话。
    - 参数4：`IZkConnection`接口的实现类。
    - 参数5：`zkSerializer`自定义序列化实现。
1. 创建节点方法：`create`、`createEphemeral`、`createEphemeralSequential`、`createPersistent`、`createPersistentSequential`
    - 参数1：`path`，路径
    - 参数2：`data`，数据内容，可以传入`null`
    - 参数3：`mode`，节点类型，为一个枚举类型，4种形式
    - 参数4：`acl`策略
    - 参数5：`callback`回调函数
    - 参数6：`context`上下文对象
    - 参数7：`createParents`是否创建父节点
1. 删除节点方法：`delete`、`deleteRecursive`
    - 参数1：`path`路径
    - 参数2：`callback`回调函数
    - 参数3：`context`上下文对象
1. 读取子节点数据方法：`getChildren`
    - 参数1：`path`路径
1. 读取节点数据方法：`readData`
    - 参数1：`path`路径
    - 参数2：`returnNullfPathNotExists`(避免为空节点抛出异常，直接返回`null`)
    - 参数3：节点状态
1. 更新数据方法：`writeData`
    - 参数1：`path`路径
    - 参数2：`data`数据信息
    - 参数3：`version`版本号
1. 检测节点是否存在方法：`exists`
    - 参数1：`path`路径

## 5.2 ZkClient使用Listener
ZkClient里并没有类似的`watcher`、`watch`参数，无需关心反复注册`watcher`的问题，ZkClient提供了一套监听方式，可以使用监听节点的方式进行操作，剔除了繁琐的反复`wather`操作，减化了代码的复杂程度。
1. `subscribeChildChanges`方法
    - 参数1：`path`路径
    - 参数2：实现了`IZkChildListener`接口的类(如：实例化`IZkChildListener`类)只需要重写`handleChildChange(String parentPath, List<String>currentChilds)`方法。其中参数`parentPath`为所监听节点全路径，`currentChilds`为最新的子节点列表(相对路径)
    - `IZkChildListener`事件说明针对于下面三个事件触发：新增子节点、减少子节点、删除节点
    - `IZkChildListener`有以下特点：
        1. 客户端可以对一个不存在的节点进行变更的监听。
        1. 一旦客户端对一个节点注册了子节点列表变更监听后，那么，当前节点的子节点列表发送变更时，服务器端都会通知客户端，并将最新的子节点列表发送给客户端。
        1. 该节点本身创建或删除也会通知到客户端。
        1. 另外，最重要的是这个监听是一直存在的，不是单次监听，相比较原生API提供的要简单的多了。
1. `IZkDataListener`接口，需重写两个方法：
    - `handleDataChange(String dataPath, Object data)`：节点变更事件
    - `handleDataDeleted(String dataPath)`：节点删除事件

## 6.1 Curator框架
为了更好的实现Java操作ZooKeeper服务器，后来出现Curator框架，非常的强大，目前已经是Apache的顶级项目，里面提供了更多丰富的操作，例如：session超时重连、主从选举、分布式计数器、分布式锁等，适用于各种复杂的ZooKeeper场景的API封装。
```
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-framework</artifactId>
    <version>2.12.0</version>
</dependency>
```
相关资料地址：[http://curator.apache.org/curator-recipes/index.html](http://curator.apache.org/curator-recipes/index.html)

## 6.2 Curator框架使用
Curator框架中使用链式编程风格，易读性更强，使用工程方法创建连接对象。
1. 使用`CuratorFrameworkFactory`的两个静态工厂方法(参数不同)来实现：
    - 参数1：`connectString`，连接串
    - 参数2：`retryPolicy`，重试连接策略。有四种实现分别为：`ExponentialBackoffRetry`、`RetryNTimes`、`RetryOneTimes`、`RetryUntilElapsed`
    - 参数3：`sessionTimeoutMs`会话超时时间默认为60000ms
    - 参数4：`connectionTimeoutMs`连接超时时间，默认为15000ms

注意：对于`retryPolicy`策略通过一个接口来让用户自定义实现。

## 6.3 Curator的监听
如果要使用类似`wather`的监听功能Curator必须依赖一个jar包，Maven依赖：
```
<dependency>
    <groupId>org.apache.curator</groupId>
    <artifactId>curator-recipes</artifactId>
    <version>2.12.0</version>
</dependency>
```
使用`NodeCache`的方式去客户端实例中注册一个监听缓存，然后实现对应的监听方法即可，这里主要有两种监听方式：
- `NodeCacheListener`：监听节点的新增、修改操作。
- `PathChildrenCacheListener`：监听子节点的新增、修改、删除操作。

## References
- ZooKeeper