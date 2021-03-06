# Redis网摘笔记

## 数据淘汰策略
在Redis中，允许用户设置最大使用内存大小通过配置`redis.conf`中的`maxmemory`这个值来开启内存淘汰功能，在内存限定的情况下是很有用的。设置最大内存大小可以保证Redis对外提供稳健服务。

Redis内存数据集大小上升到一定大小的时候，就会施行数据淘汰策略。Redis提供6种数据淘汰策略通过`maxmemory-policy`设置策略：
1. `volatile-lru`：从已设置过期时间的数据集（`server.db[i].expires`）中挑选最近最少使用的数据淘汰
1. `volatile-ttl`：从已设置过期时间的数据集（`server.db[i].expires`）中挑选将要过期的数据淘汰
1. `volatile-random`：从已设置过期时间的数据集（`server.db[i].expires`）中任意选择数据淘汰
1. `allkeys-lru`：从数据集（`server.db[i].dict`）中挑选最近最少使用的数据淘汰
1. `allkeys-random`：从数据集（`server.db[i].dict`）中任意选择数据淘汰
1. `no-enviction`（驱逐）：禁止驱逐数据

### 适用场景
几种策略的适用场景：
- `allkeys-lru`： 如果我们的应用对缓存的访问符合幂律分布（也就是存在相对热点数据），或者我们不太清楚我们应用的缓存访问分布状况，可以选择`allkeys-lru`策略。
- `allkeys-random`： 如果我们的应用对于缓存key的访问概率相等，则可以使用这个策略。
- `volatile-ttl`： 这种策略使得我们可以向Redis提示哪些key更适合被eviction。
- `volatile-lru`策略和`volatile-random`策略适合我们将一个Redis实例既应用于缓存和又应用于持久化存储的时候，然而我们也可以通过使用两个Redis实例来达到相同的效果，值得一提的是将key设置过期时间实际上会消耗更多的内存，因此我们建议使用`allkeys-lru`策略从而更有效率的使用内存。

## Redis分布式锁
分布式锁一般有数据库乐观锁、基于Redis的分布式锁以及基于ZooKeeper的分布式锁三种实现方式，而下边是第二种基于Redis的分布式锁正确的实现方法

### 加锁
```
jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
```
`set()`方法一共有五个形参：
- 第一个为`key`，我们使用`key`来当锁，因为`key`是唯一的。
- 第二个为`value`，我们传的是`requestId`，很多童鞋可能不明白，有`key`作为锁不就够了吗，为什么还要用到`value`？原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给`value`赋值为`requestId`，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。`requestId`可以使用`UUID.randomUUID().toString()`方法生成。
- 第三个为`nxxx`，这个参数我们填的是`NX`，意思是SET IF NOT EXIST，即当`key`不存在时，我们进行`set`操作；若`key`已经存在，则不做任何操作；
- 第四个为`expx`，这个参数我们传的是`PX`，意思是我们要给这个`key`加一个过期的设置，具体时间由第五个参数决定。
- 第五个为`time`，与第四个参数相呼应，代表`key`的过期时间。

总的来说，执行上面的`set()`方法就只会导致两种结果：
1. 当前没有锁（`key`不存在），那么就进行加锁操作，并对锁设置个有效期，同时`value`表示加锁的客户端。
1. 已有锁存在，不做任何操作。

### 解锁
```
String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
```
- 第一行代码，我们写了一个简单的Lua脚本代码。Lua代码的功能：
    - 首先获取锁对应的`value`值，检查是否与`requestId`相等，如果相等则删除锁（解锁）。
    - 使用Lua语言来确保`eval()`方法是原子性的。在`eval`命令执行Lua代码的时候，Lua代码将被当成一个命令去执行，并且直到`eval`命令执行完成，Redis才会执行其他命令。
    - `Eval`命令的基本语法如下：`EVAL script numkeys key [key ...] arg [arg ...]`
- 第二行代码，我们将Lua代码传到`jedis.eval()`方法里，并使参	数`KEYS[1]`赋值为`lockKey`，`ARGV[1]`赋值为`requestId`。`eval()`方法是将Lua代码交给Redis服务端执行。

### 示例
- `RedisTool`

## ZooKeeper分布式锁
ZooKeeper中几个关于节点的有趣的性质：
1. 有序节点：假如当前有一个父节点为/lock，我们可以在这个父节点下面创建子节点；ZooKeeper提供了一个可选的有序特性，例如我们可以创建子节点“/lock/node-”并且指明有序，那么ZooKeeper在生成子节点时会根据当前的子节点数量自动添加整数序号，也就是说如果是第一个创建的子节点，那么生成的子节点为/lock/node-0000000000，下一个节点则为/lock/node-0000000001，依次类推。
1. 临时节点：客户端可以建立一个临时节点，在会话结束或者会话超时后，ZooKeeper会自动删除该节点。
1. 事件监听：在读取数据时，我们可以同时对节点设置事件监听，当节点数据或结构变化时，ZooKeeper会通知客户端。当前ZooKeeper有如下四种事件：1）节点创建；2）节点删除；3）节点数据修改；4）子节点变更。

使用ZooKeeper实现分布式锁的算法流程，假设锁空间的根节点为/lock：
1. 客户端连接ZooKeeper，并在/lock下创建**临时的**且**有序的**子节点，第一个客户端对应的子节点为/lock/lock-0000000000，第二个为/lock/lock-0000000001，以此类推。
1. 客户端获取/lock下的子节点列表，判断自己创建的子节点是否为当前子节点列表中**序号最小**的子节点，如果是则认为获得锁，否则监听/lock的子节点变更消息，获得子节点变更通知后重复此步骤直至获得锁；
1. 执行业务代码；
1. 完成业务流程后，删除对应的子节点释放锁。

调整后的分布式锁算法流程如下：
1. 客户端连接ZooKeeper，并在/lock下创建临时的且有序的子节点，第一个客户端对应的子节点为/lock/lock-0000000000，第二个为/lock/lock-0000000001，以此类推。
1. 客户端获取/lock下的子节点列表，判断自己创建的子节点是否为当前子节点列表中序号最小的子节点，如果是则认为获得锁，否则监听刚好在自己之前一位的子节点删除消息，获得子节点变更通知后重复此步骤直至获得锁；
1. 执行业务代码；
1. 完成业务流程后，删除对应的子节点释放锁。

>ZooKeeper提供的API中设置监听器的操作与读操作是**原子执行**的，也就是说在读子节点列表时同时设置监听器，保证不会丢失事件。

### 示例
- `ZooKeeperTest`

## 从RDB持久化切换到AOF持久化
1. 为最新的`dump.rdb`文件创建一个备份。
1. 将备份放到一个安全的地方。
1. 执行以下两条命令：
	```
	redis-cli> CONFIG SET appendonly yes
	redis-cli> CONFIG SET save ""
	```
1. 确保命令执行之后，数据库的键的数量没有改变。
1. 确保写命令会被正确地追加到AOF文件的末尾。

### AOF配置
`redis.conf`
```
appendonly yes #aof方式默认关闭
appendfilename "appendonly.aof" #文件名
appendfsync always #同步模式(可选no always everysec)
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
```

## Redis数据备份方案
### 数据备份方案
1. 写一个crontab定时去调度脚本去做数据备份。
1. 每小时都copy一份当时的RDB文件，到一个目录中。仅仅保留48小时的备份。
1. 每天都备份一份当天的RDB文件，到一个目录中。仅仅保留最近一个月的备份。
1. 每次copy备份之前都把太久的备份删掉。
1. 每天将当前服务器的备份保存下来，例如保存到阿里云。

### 每小时备份
`redis-rdb-hour.sh`
```
#!/bin/sh
cur_date=`date +%Y%m%d%H`   #当前时间精确到小时
rm -rf /usr/local/bin/redis/snapshotting/$cur_date   #删除当前时间的目录
mkdir /usr/local/bin/redis/snapshotting/$cur_date  #新建当前时间的目录
cp /usr/local/bin/dump.rdb /usr/local/bin/redis/snapshotting/$cur_date   #将rdb文件copy到当前时间创建的目录
del_date=`date -d -48hour +%Y%m%d%H`   #48小时之前的时间
rm -rf /usr/local/bin/redis/snapshotting/$del_date   #删除48小时之前的目录
```
`chmod 777 redis-rdb-day.sh`

执行命令：`crontab -e`
```
5 * * * * sh /usr/local/bin/redis-rdb-hour.sh #每小时执行脚本
```

### 每天备份
`redis-rdb-day.sh`
```
#!/bin/sh
cur_date=`date +%Y%m%d`  #当前时间精确到天
rm -rf /usr/local/bin/redis/snapshotting/$cur_date   #删除当前时间的目录
mkdir /usr/local/bin/redis/snapshotting/$cur_date  #新建当前时间的目录
cp /usr/local/bin/dump.rdb /usr/local/bin/redis/snapshotting/$cur_date   #将rdb文件copy到当前时间创建的目录
del_date=`date -d -1month +%Y%m%d`  #一个月之前的时间
rm -rf /usr/local/bin/redis/snapshotting/$del_date   #删除一个月之前的目录
```
`chmod 777 redis-rdb-day.sh`

执行命令：`crontab -e`
```
5 0 * * * sh /usr/local/bin/redis-rdb-day.sh #每天0点执行脚本
```

### 数据恢复方案
1. 如果Redis进程挂掉，那么重启Redis即可，直接基于AOF日志文件恢复数据
1. 如果Redis中的RDB和AOF文件都丢失（可能认为原因）

- 停止Redis进程
- 找到最新一小时RDB文件copy到Redis存入RDB文件目录
- 并且将`redis.conf`中的`appendonly yes`改为`no`
- 重启Redis，数据恢复。
- 这个时候使用Redis的热修改将`appendonly no`改为`yes`
- 停掉Redis，将`redis.conf`中的`appendonly no`改为`yes`
- 重启Redis数据彻底恢复。

## References
- [Redis学习笔记（三）Redis数据淘汰策略](https://blog.csdn.net/liubenlong007/article/details/53690103)
- [Redis分布式锁正确的实现方法](https://www.w3cschool.cn/redis/redis-yj3f2p0c.html)
- [基于Zookeeper的分布式锁](http://www.dengshenyu.com/java/%E5%88%86%E5%B8%83%E5%BC%8F%E7%B3%BB%E7%BB%9F/2017/10/23/zookeeper-distributed-lock.html)
- [持久化（persistence）](http://doc.redisfans.com/topic/persistence.html)
- [Redis数据备份方案](https://www.jianshu.com/p/0233d476bb14)