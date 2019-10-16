# ZooKeeper网摘笔记

## Leader选举
在分布式计算中，leader election是很重要的一个功能，这个选举过程是这样子的：指派一个进程作为组织者，将任务分发给各节点。在任务开始前，哪个节点都不知道谁是leader或者coordinator。当选举算法开始执行后，每个节点最终会得到一个唯一的节点作为任务leader。除此之外，选举还经常会发生在leader意外宕机的情况下，新的leader要被选举出来。

### Leader latch
`LeaderLatch`类的构造函数如下：
```
public LeaderLatch(CuratorFramework client, String latchPath)
public LeaderLatch(CuratorFramework client, String latchPath, String id)
```
必须启动`LeaderLatch`: `leaderLatch.start();`一旦启动，`LeaderLatch`会和其它使用相同latch path的其它`LeaderLatch`交涉，然后随机的选择其中一个作为leader。查看一个给定的实例是否是leader:
```
public boolean hasLeadership()
```
类似JDK的`CountDownLatch`，`LeaderLatch`在请求成为leadership时有`block`方法：
```
public void await() throws InterruptedException, EOFException
public boolean await(long timeout, TimeUnit unit) throws InterruptedException
```
一旦不使用`LeaderLatch`了，必须调用`close`方法。如果它是leader,会释放leadership，其它的参与者将会选举一个leader。

示例：`LeaderLatchExample`

### Leader Election
Curator还提供了另外一种选举方法。 注意涉及以下四个类：
- `LeaderSelector`
- `LeaderSelectorListener`
- `LeaderSelectorListenerAdapter`
- `CancelLeadershipException`
重要的是`LeaderSelector`类，它的构造函数为：
```
public LeaderSelector(CuratorFramework client, String mutexPath,LeaderSelectorListener listener)
public LeaderSelector(CuratorFramework client, String mutexPath, ThreadFactory threadFactory, Executor executor, LeaderSelectorListener listener)
```
类似`LeaderLatch`，必须`start`:`leaderSelector.start();`一旦启动，当实例取得领导权时你的`listener`的`takeLeadership()`方法被调用。而`takeLeadership()`方法只有领导权被释放时才返回。当你不再使用`LeaderSelector`实例时，应该调用它的`close`方法。

>重要:推荐处理方式是当收到`SUSPENDED`或`LOST`时抛出`CancelLeadershipException`异常。这会导致`LeaderSelector`实例中断并取消执行`takeLeadership`方法的异常。这非常重要，你必须考虑扩展`LeaderSelectorListenerAdapter`提供了推荐的处理逻辑。

示例：`LeaderSelectorExample`

## Curator框架应用
Curator框架提供了一种流式接口。操作通过builder串联起来，这样方法调用类似语句一样。
```
client.create().forPath("/head", new byte[0]);
client.delete().inBackground().forPath("/head");
client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/head/child", new byte[0]);
client.getData().watched().inBackground().forPath("/test");
```
`CuratorFramework`提供的方法：

方法名 | 描述 
---- | -------
`create()` | 开始创建操作，可以调用额外的方法（比如方式mode或者后台执行background）并在最后调用`forPath()`指定要操作的ZNode
`delete()` | 开始删除操作.可以调用额外的方法（版本或者后台处理version or background）并在最后调用`forPath()`指定要操作的ZNode
`checkExists()` | 开始检查ZNode是否存在的操作.可以调用额外的方法（监控或者后台处理）并在最后调用`forPath()`指定要操作的ZNode
`getData()` | 开始获得ZNode节点数据的操作.可以调用额外的方法（监控、后台处理或者获取状态watch,background or get stat）并在最后调用`forPath()`指定要操作的ZNode
`setData()` | 开始设置ZNode节点数据的操作.可以调用额外的方法（版本或者后台处理） 并在最后调用`forPath()`指定要操作的ZNode
`getChildren()` | 开始获得ZNode的子节点列表。以调用额外的方法（监控、后台处理或者获取状态watch,background or get stat）并在最后调用`forPath()`指定要操作的ZNode
`inTransaction()` | 开始是原子ZooKeeper事务.可以复合create, setData, check, and/or delete等操作然后调用commit()作为一个原子操作提交

事件类型以及事件的方法如下：

Event Type | Event Methods
---- | -------
CREATE | `getResultCode()` and `getPath()`
DELETE | `getResultCode()` and `getPath()`
EXISTS | `getResultCode()`, `getPath()` and `getStat()`
GETDATA | `getResultCode()`, `getPath()`, `getStat()` and `getData()`
SETDATA | `getResultCode()`, `getPath()` and `getStat()`
CHILDREN | `getResultCode()`, `getPath()`, `getStat()`, `getChildren()`
WATCHED | `getWatchedEvent()`

### 示例
- `CreateClientExample`
- `CrudExample`
- `TransactionExample`

## Curator扩展库
Curator把一些其它的Recipe放在单独的包中，命名方式就是`curator-x-<name>`
- [Service Discovery](http://curator.apache.org/curator-x-discovery/index.html)（`curator-x-discovery`）：一个服务发现的Recipe
- [Curator RPC Proxy](http://curator.apache.org/curator-x-rpc/index.html)（`curator-x-rpc`）扩展和[Service Discovery Server](http://curator.apache.org/curator-x-discovery-server/index.html)（`curator-x-discovery-server`）是为了桥接非Java应用的扩展

### 示例
- `DiscoveryExample`

## References
- [跟着实例学习ZooKeeper的用法： Leader选举](http://ifeve.com/zookeeper-leader/)
- [跟着实例学习ZooKeeper的用法： Curator框架应用](http://ifeve.com/zookeeper-curato-framework/)
- [跟着实例学习ZooKeeper的用法： Curator扩展库](http://ifeve.com/zookeeper-curator-ext/)