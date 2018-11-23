# Redis笔记

## 1.1 NoSQL简介
NoSQL，泛指非关系型数据库，四大分类：
- 键值(Key-Value)存储数据库：这类数据库主要会用到一个哈希表，这个表中有一个特定的键和一个指针指向特定的数据。如Redis，Voldemort，Oracle BDB
- 列存储数据库：通常是用来应对分布式存储的海量数据。键仍然存在，但是它们的特点是指向了多个列。如HBase，Riak
- 文档型数据库：该类型的数据模型是版本化的文档，半结构化的文档以特定的格式存储，比如JSON。文档数据库可以看作是键值数据库的升级版，允许之间嵌套键值。而且，文档数据库比键值数据库的查询效率更高。如CouchDB，MongoDB
- 图形(Graph)数据库：该数据库同其它数据不同，它是使用灵活的图形模型，并且能够扩展到多个服务器上。

NoSQL数据库没有标准的查询语句(SQL)，因此，进行数据库查询需要定制数据模型。许多NoSQL数据库都有REST式的数据接口或者查询API。如Neo4J，InfoGrid，Infinite Graph

## 1.2 非关系型数据库特点
1. 数据模型比较简单
1. 需要灵活性更强的IT系统
1. 对数据库性能要求较高
1. 不需要高度的数据一致性
1. 对于给定key，比较容易映射复杂值的环境

## 1.3 Redis简介
优点：
- 对数据高并发读写
- 对海量数据的高效率存储和访问
- 对数据的可扩展性和高可用性

缺点：
- Redis（ACID处理非常简单）
- 无法做到太复杂的关系数据库模型

Redis是以key-value store存储，data structure service数据结构服务器。数据都是缓存在内存中，也可以周期性地把更新的数据写入硬盘或者把修改操作写入追加到文件。3种
1. 主从
1. 哨兵
1. 集群

- Redis：多实例，串行
- Memcached：单实例，并行

## 1.4 Redis的安装
[Redis下载](https://redis.io/download)
1. 安装gcc，把redis-3.0.0-rc2.tar.gz放到/usr/local文件夹下
1. 解压`tar -zxvf redis-3.0.0-rc2.tar.gz`
1. 进入redis-3.0.0目录下，进行编译`make`
1. 键入src安装`make install`，验证（`ll`查看src目录有redis-server、redis-cli即可）
1. 建立2个文件夹存放Redis命令和配置文件
    - `mkdir -p /usr/local/redis/etc`
    - `mkdir -p /usr/local/redis/bin`
1. 把redis-3.0.0下的redis.conf移动到/usr/local/redis/etc下，
    - `cp redis.conf /usr/local/redis/etc/`
1. 把redis-3.0.0/src里的mkreleasehdr.sh、redis-benchmark、redis-check-aof、redis-check-dump、redis-cli、redis-server文件移动到bin下，命令：
    - `mv mkreleasehdr.sh redis-benchmark redis-check-aof redis-check-dump redis-cli redis-server /usr/local/redis/bin`
1. 启动时指定配置文件：`./redis-server /usr/local/redis/etc/redis.conf`（注意要使用后台启动，修改redis.conf里的daemonize改为yes）
1. 验证启动是否成功：
    - `ps -ef | grep redis`查看是否有Redis服务，或查看端口：`netstat -tunpl | grep 6379`
    - 进入Redis客户端`./redis-cli`，退出客户端`quit`
    - 退出Redis服务：
        1. `pkill redis-server`
        1. kill 进程号
        1. `/usr/local/redis/bin/redis-cli shutdown`

## 2.1 String类型
Redis一共分为五种基本数据类型：`String`、`Hash`、`List`、`Set`、`ZSet`

`String`类型是包含很多种类型的特殊类型，并且是二进制安全的。

- `set`和`get`方法：
    - 设置值：`set name bhz`
    - 取值：`get name`
- 删除值：`del name`
- 使用`setnx`（not exist）
    - name如果不存在进行设置，存在就不需要进行设置了，返回0
- 使用`setex`（expired）
    - `setex color 10 red`设置color的有效期为10秒，10秒后返回`nil`（在Redis里`nil`表示空）
- 使用`setrange`替换字符串：
    - `set email 123@gmail.com`
    - `setrange email 10 ww`（10表示从第几位开始替换，后面跟上替换的字符串）
- 一次性设置多个和获取多个值的`mset`、`mget`方法：
    - `mset key1 hbz key2 bai key3 28`
    - `mget key1 key2 key3`方法
    - 也有`msetnx`和`msetex`方法
- 一次性设置和取值的`getset`方法：
    - `set key4 cc`
    - `getset key4 changchun`：返回旧值并设置新值的方法。
- `incr`和`decr`方法：对某一个值进行递增和递减
- `incrby`和`decrby`方法：对某个值行进指定长度的递增和递减（语法：`incrby key [步长]`）
- `append[name]`方法：字符串追加方法
- `strlen[name]`方法：获取字符串的长度

## 2.2 Hash类型
Hash类型是String类型和field和value的映射表，或者说一个String集合。特别适合存储对象，相比较而言，将一个对象类型存储在Hash类型里，要比存储在String类型里占用更少的内存空间，并方便存取整个对象。
- `hset myhash field1 hello`：含义是hset是hash集合，myhash是集合名字，field1是字段名，hello为其值
- `hget myhash field1`：获取内容
- `hmset myhash sex man addr beijing`：进行批量存储多个键值对
- `hmget`：进行批量获取多个键值对
- `hsetnx`和`setnx`大同小异
- `hincrby`和`hdecrby`集合递增和递减
- `hexists`：是否存在key，如果存在返回，不存在返回0
- `hlen`：返回hash集合里的所有键值对
- `hdel`：删除指定hash的field
- `hkeys`：返回hash里所有的字段
- `hvals`：返回hash的所有value
- `hgetall`：返回hash里所有的key和value

## 2.3 List类型
List类型是一个链表结构的集合，其主要功能`push`、`pop`、获取元素等。更详细地说，List类型是一个双端链表的结构，我们可以通过相关操作，进行集合的头部或者尾部添加删除元素，List的设计非常精巧，既可以作为栈，又可以作为队列。满足绝大多数需求。
- `lpush`：从头部加入元素（栈），先进后出
    - `lpush list1 "hello" lpush list1 "world"`
    - `lrange list1 0 -1`
- `rpush`：从尾部加入元素（队列），先进先出
    - `rpush list2 "beijing" rpush list2 "sxt"`
    - `lrange list2 0 -1`
- `linsert`：插入元素
    - `linsert list3 before [集合的元素] [插入的元素]`
- `lset`：将指定下标的元素替换
- `lrem`：删除元素，返回删除的个数
- `ltrim`：保留指定key的值范围内的数据
- `lpop`：从list的头部删除元素，并返回删除元素
- `rpop`：从list的尾部删除元素，并返回删除元素
- `rpoplpush`：第一步从尾部删除元素，然后第二步从头部加入元素
- `lindex`：返回名称为key的list中index位置的元素
- `llen`：返回元素的个数

## 2.4 set类型和zset类型
set集合是String类型的无序集合，set是通过Hashtable实现的，对集合我们可以取交集、并集、差集。
- `sadd`：向名称为key的set中添加元素，set集合不允许重复元素
- `smembers`查看set集合的元素
- `srem`：删除set集合元素
- `spop`：随机返回删除的key
- `sdiff`：返回两个集合的不同元素（哪个集合在前面就以哪个集合为标准）
- `sdiffstore`：将返回的不同元素存储到另一个集合里
- `sinter`：返回集合的交集
- `sinterstore`：返回交集结果，存入set3中
- `sunion`：取并集
- `sunionstore`：取得并集，存入set3中
- `smove`：从一个set集合移动到另一个set集合里（相当于剪切复制）
- `scard`：查看集合里元素个数
- `sismember`：判断某元素是否为集合中的元素
    - 返回1代表是集合中的元素，0代表不是
- `srandmember`：随机返回一个预算预算元素

zset类型
- `zadd`：向有序集合中添加一个元素，该元素如果存在，则更新顺序
    - 在重复插入的时候，会根据顺序属性更新
- `zrem`：删除名称为key的zset中的元素member
- `zincrby`：已指定值去自动递增或者减少，用法和`incrby`类似
- `zrangebyscore`：找到指定区间范围的数据进行返回
- `zremrangebyrank`：删除1到1（只删除索引1）
- `zremrangebyscore`：删除指定序号
- `zrank`：返回排序索引，从小到大排序（升序排序之后再找索引）
    - 注意：一个是顺序号，一个是索引号，`zrank`返回的是索引
- `zrevrank`：返回排序索引，从大到小排序（降序排序之后再找索引）
- `zrangebyscore zset1 2 3 withscores`：找到指定区间范围的数据进行返回
- `zcard`：返回集合里所有元素的个数
- `zcount`：返回集合中score在给定区间中的数量
- `zremrangebyrank zset [from] [to]`：删除索引
- `zremrangebyscore zset [from] [to]`：删除指定序号

## 3.1 Redis高级命令及特性
- `keys *`：返回满足的所有键（可以模糊匹配）
- `exists`：是否存在指定的key
- `expire`：设置某个key的过期时间，使用`ttl`查看剩余时间
- `persist`：取消过期时间
- `select`：选择数据库，数据库从0到15（一共16个数据库）默认进入的是0数据库
- `move [key] [数据库下标]`：将当前数据库中的key，转移到其它数据库中
- `randomkey`：随机返回数据库里的一个key
- `rename`：重命名key
- `echo`：打印命令
- `dbsize`：查看数据库的key数量
- `info`：获取数据库信息
- `config get`：实时传存收到的请求（返回相关的配置信息）
    - `config get *`：返回所有配置
- `flushdb`：清空当前数据库
- `flushdball`：清空所有数据库

## 3.2 Redis的安全性
- `redis.conf`文件，设置密码：
```
#requirepass foobared
requirepass ***
```
- 重启服务器：`pkill redis-server`
- 直接登录授权：`/usr/local/redis/bin/redis-cli -a bhz`

## 3.3 主从复制
主从复制：
1. master可以拥有多个slave
1. 多个slave可以连接同一个master外，还可以连接到其它的slave
1. 主从复制不会阻塞master。在同步数据时，master可以继续处理client请求
1. 提供系统的伸缩性

主从复制过程：
1. slave和master建立连接，发送sync同步命令
1. master会开启一个后台进程，将数据库快照保存到文件中，同时master主进程会开始收集新的写命令并缓存
1. 后台完成保存后，就将文件发送给slave
1. slave将此文件保存到硬盘上

主从复制配置：
- clone服务器之后修改slave的IP地址
- 修改配置文件：`/usr/local/redis/etc/redis.conf`
    1. `slaveof <masterip> <masterport>`
    1. `masterauth <master-password>`
- 使用`info`查看role角色，即可知道是主服务或从服务。
>远程文件传输：```scp -r redis-3.0.0/ 192.168.1.122:/usr/local/```

## 3.4 哨兵
哨兵就是监控Redis系统的运行状况。其主要功能有两点：
1. 监控主数据库和从数据库是否正常运行。
1. 主数据库出现故障时，可以自动将从数据库转换为主数据库，实现自动切换。

实现步骤：从其中一台从服务器配置`sentinel.conf`（比如175）
1. copy文件到`/usr/local/redis/bin/redis-server /usr/local/redis/etc/`中
1. 修改`sentinel.conf`文件：
	```
	sentinel monitor mymaster 192.168.174 6379 1 #名称、ip、端口、投票选举次数
	sentinel down-after-milliseconds mymaster 5000 #默认1s检测一次，这里配置超时5000毫秒宕机
	sentinel failover-timeout mymaster 900000
	sentinel parallel-syncs mymaster 2
	sentinel can-failover mymaster yes
	```
1. 启动sentinel哨兵
	```
	/usr/local/redis/bin/redis-server /usr/local/redis/etc/sentinel.conf --sentinel &
	```
1. 查看哨兵相关信息命令
	```
	/usr/local/redis/bin/redis-cli -h 192.168.1.175 -p 26379 info Sentinel
	```
1. 关闭主服务器查看集群信息：
	```
	/usr/local/redis/bin/redis-cli -h 192.168.1.174 -p 6379 shutdown
	```

## 3.5 Redis简单事务
- `multi`：打开事务，设置的数据都会放入队列里进行保存
- `exec`：执行事务，把数据一次存储到Redis中
- `discard`：取消事务

Redis的事务不能保证同时成功或失败进行提交会回滚，所以Redis的事务目前还是比较简单的。

## 3.6 持久化机制
Redis是一个支持持久化的内存数据库，也就是说需要经常将内存中的数据同步到硬盘，来保证持久化。Redis持久化的两种方式：
1. snapshotting（快照）默认方式，将内存中以快照的方式写入到二进制文件中，默认为`dump.rdb`。可以通过配置设置自动做快照持久化的方式。可以配置Redis在n秒内，如果超过m个key则修改就自动做快照。snapshotting设置：
	```
	save 900 1 #900秒内如果超过1个key被修改，则发起快照保存
	save 300 10 #300秒内如果超过10个key被修改，则发起快照保存
	save 60 10000
	```
1. append-only file（缩写aof）的方式（有点类似于Oracle日志）由于快照方式是一定时间间隔做一次，所以，可能发生Redis意外down的情况，就会丢失最后一次快照后的所有修改的数据，aof比快照方式有更好的持久化性，是由于使用aof时，Redis会将每一个收到的写命令都通过`write`函数追加到命令中，当Redis重新启动时会执行文件中保存的写命令在内存中重建这个数据库的内容，这个文件在bin目录下：`appendonly.aof`。aof不是立即写到硬盘上，可以通过配置文件修改强制写到硬盘中。aof设置：
	```
	appendonly yes #启动aof持久化方式，有三种修改方式：
	appendfsync always #收到写命令就立即写入到磁盘，效率最慢，但是保证完全的持久化
	# appendfsync everysec #每秒钟写入磁盘一次，在性能和持久化方面做了很好的折中
	# appendfsync no #完全依赖os，性能最好，持久化没保证
	```

## 3.7 发布与订阅消息
- 使用`subscribe [频道]`进行订阅监听。
- 使用`publish [频道] [发布内容]`进行发布消息广播。

## 3.8 虚拟内存的使用
Redis会暂时把不经常访问的数据，从内存交换到磁盘中，腾出宝贵的空间，用于其它需要访问的数据，这需要对vm相关进行配置。（**3.0版本是不带vm特性的，配置无效！**）

修改配置文件：`redis.conf`
```
vm-enabled yes #开启vm功能
vm-swap-file /tmp/redis.swap #交换处理的value保存的文件路径
vm-max-memory 1000000 #Redis使用的最大内存上限
vm-page-size 32 #每个页面的大小32个字节
vm-pages 134217728 #最多使用多少页面
vm-max-threads 4 #用于执行value对象换入缓存的工作线程数量
```
重新启动服务，会弹出是否启用虚拟内存

把提示`really-use-vm yes`粘贴到`redis.conf`中，然后重启服务

## 4.x Java&Redis
Jedis就是Redis支持Java的第三方类库，可以使用Jedis类库操作Redis数据库。

## 5.1 Redis集群的搭建
Redis 3.0已经支持集群的容错功能。集群搭建：至少3个master
1. 创建一个文件夹`redis-cluster`，然后在其下面分别创建6个文件如下：
    1. `mkdir -p /usr/local/redis-cluster`
    1. `mkdir 7001`、`mkdir 7002`、`mkdir 7003`、`mkdir 7004`、`mkdir 7005`、`mkdir 7006`
1. 把`redis.conf`配置文件分别copy到`700*`下，进行修改各个文件内容，也就是对`700*`下的每一个copy的`redis.conf`文件进行修改。如下：
    1. `daemonize yes`
    1. `port 700*`（分别对每个及其的端口号进行设置）
    1. `bind 192.168.1.171`（必须要绑定当前及其的ip，不然会无限悲剧下去…深坑勿入！！！）
    1. `dir /usr/local/redis-cluster/700*/`（指定数据文件存放位置，必须要指定不同的目录位置，不然会丢失数据，深坑勿入！！！）
    1. `cluster-enabled yes`（启动集群模式，开始玩耍）
    1. `cluster-config-file nodes-700*.conf`（这里`700*`最好和`port`对应上）
    1. `cluster-node-timeout 15000`
    1. `appendonly yes`
1. 把修改后的配置文件，分别copy到各个文件夹下，注意每个文件要修改端口号，并且nodes文件也要不相同！
1. 由于Redis集群需要使用ruby命令，所以我们需要安装ruby
    1. `yum install ruby`
    1. `yum install rubygems`
    1. `gem install redis`（安装Redis和ruby的接口）
1. 分别启动6个Redis实例，然后检查是否启动成功
    1. `/usr/local/redis/bin/redis-server /usr/local/redis-cluster/700*/redis.conf`
    1. `ps -ef | grep redis`查看是否启动成功
1. 首先到redis-3.0.0的安装目录下，然后执行`redis-trib.rb`命令。
    1. `cd /usr/local/redis3.0/src`
    1. `./redis-trib.rb create --replicas 1 192.168.1.121:7001 192.168.1.121:7002 192.168.1.121:7003 192.168.1.121:7004 192.168.1.121:7005 192.168.1.121:7006`
1. 到此为止我们集群搭建成功！进行验证：
    1. 连接任意一个客户端即可：`./redis-cli -c -h -p`（-c表示集群模式，指定ip地址和端口号）如：`/usr/local/redis/bin/redis-cli -c -h 192.168.1.121 -p 700*`
    1. 进行验证：`cluster info`（查看集群信息）、`cluster nodes`（查看节点列表）
    1. 进行数据操作验证
    1. 关闭集群则需要逐个进行关闭，使用命令：`/usr/local/redis/bin/redis-cli -c -h 192.168.1.121 -p 700* shutdown`
1. （补充）
    - 友情提示：当出现集群无法启动时，删除临时的数据文件，再次重新启动每一个Redis服务，然后重新构建集群环境。 
    - `redis-trib.rb`官方群操作指令：https://redis.io/topics/cluster-tutorial

## 5.2 cluster命令
```
// 集群（cluster）
CLUSTER INFO 打印集群的信息
CLUSTER NODES 列出集群当前已知的所有节点（node），以及这些节点的相关信息。

// 节点（node）
CLUSTER MEET <ip> <port> 将 ip 和 port 所指定的节点添加到集群当中，让它成为集群的一份子。
CLUSTER FORGET <node_id> 从集群中移除 node_id 指定的节点。
CLUSTER REPLICATE <node_id> 将当前节点设置为 node_id 指定的节点的从节点。
CLUSTER SAVECONFIG 将节点的配置文件保存到硬盘里面。

// 槽（slot）
CLUSTER ADDSLOTS <slot> [slot ...] 将一个或多个槽（slot）指派（assign）给当前节点。
CLUSTER DELSLOTS <slot> [slot ...] 移除一个或多个槽对当前节点的指派。
CLUSTER FLUSHSLOTS 移除指派给当前节点的所有槽，让当前节点变成一个没有指派任何槽的节点。
CLUSTER SETSLOT <slot> NODE <node_id> 将槽 slot 指派给 node_id 指定的节点，如果槽已经指派给另一个节点，那么先让另一个节点删除该槽，然后再进行指派。
CLUSTER SETSLOT <slot> MIGRATING <node_id> 将本节点的槽 slot 迁移到 node_id 指定的节点中。
CLUSTER SETSLOT <slot> IMPORTING <node_id> 从 node_id 指定的节点中导入槽 slot 到本节点。
CLUSTER SETSLOT <slot> STABLE 取消对槽 slot 的导入（import）或者迁移（migrate）。

// 键（key）
CLUSTER KEYSLOT <key> 计算键 key 应该被放置在哪个槽上。
CLUSTER COUNTKEYSINSLOT <slot> 返回槽 slot 目前包含的键值对数量。
CLUSTER GETKEYSINSLOT <slot> <count> 返回 count 个 slot 槽中的键。

// 新增
CLUSTER SLAVES node-id 返回一个master节点的slaves 列表
```
https://redis.io/commands/cluster-addslots

## 5.3 Redis 3.0 操作集群
1. Redis集群操作主要命令：
    - `create`：构建集群
    - `fix`：单点修复
    - `check`：集群验证
    - `add-node`：添加节点
    - `del-node`：删除节点
    - `reshard`：重新分片
1. 操作文档手册：见Redis集群操作文档

## 5.4 Java操作Redis集群
Java使用jedis2.7以上版本对Redis 3.0集群进行操作。使用clusterAPI进行操作，但是目前支持的方式不够完善，比如有些操作还没有在集群API中体现，如一些二进制的key、value存储方法等，不过在jedis3.0以上已经给予完整的实现啦（jedis-master）。

## References
- Redis缓存技术