# ZooKeeper网摘笔记

## ZooKeeper典型的应用场景
ZooKeeper从设计模式角度来看，是一个基于观察者模式设计的分布式服务管理框架，它负责存储和管理大家都关心的数据，然后接受观察者的注册，一旦这些数据的状态发生变化，ZooKeeper就将负责通知已经在ZooKeeper上注册的那些观察者做出相应的反应，从而实现集群中类似Master/Slave管理模式

### 统一命名服务（Name Service）
分布式应用中，通常需要有一套完整的命名规则，既能够产生唯一的名称又便于人识别和记住，通常情况下用树形的名称结构是一个理想的选择，树形的名称结构是一个有层次的目录结构，既对人友好又不会重复。说到这里你可能想到了JNDI，没错ZooKeeper的Name Service与JNDI能够完成的功能是差不多的，它们都是将有层次的目录结构关联到一定资源上，但是ZooKeeper的Name Service更加是广泛意义上的关联，也许你并不需要将名称关联到特定资源上，你可能只需要一个不会重复名称，就像数据库中产生一个唯一的数字主键一样。

Name Service已经是ZooKeeper内置的功能，你只要调用ZooKeeper的API就能实现。如调用`create`接口就可以很容易创建一个目录节点。

### 配置管理（Configuration Management）
配置的管理在分布式应用环境中很常见，例如同一个应用系统需要多台PC Server运行，但是它们运行的应用系统的某些配置项是相同的，如果要修改这些相同的配置项，那么就必须同时修改每台运行这个应用系统的PC Server，这样非常麻烦而且容易出错。

像这样的配置信息完全可以交给ZooKeeper来管理，将配置信息保存在ZooKeeper的某个目录节点中，然后将所有需要修改的应用机器监控配置信息的状态，一旦配置信息发生变化，每台应用机器就会收到ZooKeeper的通知，然后从ZooKeeper获取新的配置信息应用到系统中。

![ZooKeeperConfigurationManagement](https://s1.wailian.download/2019/12/25/ZooKeeperConfigurationManagement.gif)

### 集群管理（Group Membership）
ZooKeeper能够很容易的实现集群管理的功能，如有多台Server组成一个服务集群，那么必须要一个“总管”知道当前集群中每台机器的服务状态，一旦有机器不能提供服务，集群中其它集群必须知道，从而做出调整重新分配服务策略。同样当增加集群的服务能力时，就会增加一台或多台Server，同样也必须让“总管”知道。

ZooKeeper不仅能够帮你维护当前的集群中机器的服务状态，而且能够帮你选出一个“总管”，让这个总管来管理集群，这就是ZooKeeper的另一个功能Leader Election。

它们的实现方式都是在ZooKeeper上创建一个`EPHEMERAL`类型的目录节点，然后每个Server在它们创建目录节点的父目录节点上调用`getChildren(String path, boolean watch)`方法并设置`watch`为`true`，由于是EPHEMERAL目录节点，当创建它的Server死去，这个目录节点也随之被删除，所以Children将会变化，这时`getChildren`上的`watch`将会被调用，所以其它Server就知道已经有某台Server死去了。新增Server也是同样的原理。

ZooKeeper如何实现Leader Election，也就是选出一个Master Server。和前面的一样每台Server创建一个`EPHEMERAL`目录节点，不同的是它还是一个`SEQUENTIAL`目录节点，所以它是个`EPHEMERAL_SEQUENTIAL`目录节点。之所以它是`EPHEMERAL_SEQUENTIAL`目录节点，是因为我们可以给每台Server编号，我们可以选择当前是最小编号的Server为Master，假如这个最小编号的Server死去，由于是EPHEMERAL节点，死去的Server对应的节点也被删除，所以当前的节点列表中又出现一个最小编号的节点，我们就选择这个节点为当前Master。这样就实现了动态选择Master，避免了传统意义上单Master容易出现单点故障的问题。

![ZooKeeperGroupMembership](https://s1.wailian.download/2019/12/25/ZooKeeperGroupMembership.gif)

### 共享锁（Locks）
共享锁在同一个进程中很容易实现，但是在跨进程或者在不同Server之间就不好实现了。ZooKeeper却很容易实现这个功能，实现方式也是需要获得锁的Server创建一个`EPHEMERAL_SEQUENTIAL`目录节点，然后调用`getChildren`方法获取当前的目录节点列表中最小的目录节点是不是就是自己创建的目录节点，如果正是自己创建的，那么它就获得了这个锁，如果不是那么它就调用`exists(String path, boolean watch)`方法并监控ZooKeeper上目录节点列表的变化，一直到自己创建的节点是列表中最小编号的目录节点，从而获得锁，释放锁很简单，只要删除前面它自己所创建的目录节点就行了。

![ZooKeeperLocks](https://s1.wailian.download/2019/12/25/ZooKeeperLocks.gif)

### 队列管理
ZooKeeper可以处理两种类型的队列：
1. 当一个队列的成员都聚齐时，这个队列才可用，否则一直等待所有成员到达，这种是同步队列。
1. 队列按照FIFO方式进行入队和出队操作，例如实现生产者和消费者模型。

同步队列用ZooKeeper实现的实现思路如下：

创建一个父目录`/synchronizing`，每个成员都监控标志（Set Watch）位目录`/synchronizing/start`是否存在，然后每个成员都加入这个队列，加入队列的方式就是创建`/synchronizing/member_i`的临时目录节点，然后每个成员获取`/synchronizing`目录的所有目录节点，也就是`member_i`。判断`i`的值是否已经是成员的个数，如果小于成员个数等待`/synchronizing/start`的出现，如果已经相等就创建`/synchronizing/start`。

![ZooKeeperQueue](https://s1.wailian.download/2019/12/25/ZooKeeperQueue.gif)

## References
- [分布式服务框架 Zookeeper -- 管理分布式环境中的数据](https://www.ibm.com/developerworks/cn/opensource/os-cn-zookeeper/)