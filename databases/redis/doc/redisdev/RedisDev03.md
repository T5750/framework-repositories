# Redis开发与运维笔记

## 第3章　小功能大用处
### 3.1　慢查询分析
慢查询日志就是系统在命令执行前后计算每条命令的执行时间，当超过预设阀值，就将这条命令的相关信息（例如：发生时间，耗时，命令的详细信息）记录下来。

>注意，慢查询只统计执行命令的时间，所以没有慢查询并不代表客户端没有超时问题。

#### 3.1.1　慢查询的两个配置参数
- `slowlog-log-slower-than`：预设阀值，单位是微秒（1秒=1000毫秒=1000000微秒），默认值是10000
- `slowlog-max-len`：列表的最大长度。一个新的命令满足慢查询条件时被插入到这个列表中，当慢查询日志列表已处于其最大长度时，最早插入的一个命令将从列表中移出

>如果`slowlog-log-slower-than=0`会记录所有的命令，`slowlog-log-slowerthan<0`对于任何命令都不会进行记录。

Redis中有两种修改配置的方法，一种是修改配置文件，另一种是使用`config set`命令动态修改。例如下面使用`config set`命令将`slowlog-log-slowerthan`设置为20000微秒，`slowlog-max-len`设置为1000：
```
config set slowlog-log-slower-than 20000
config set slowlog-max-len 1000
config rewrite
```

如果要Redis将配置持久化到本地配置文件，需要执行`config rewrite`命令
1. 获取慢查询日志：`slowlog get [n]`
1. 获取慢查询日志列表当前的长度：`slowlog len`
1. 慢查询日志重置：`slowlog reset`

#### 3.1.2　最佳实践
在实际使用过程中要注意以下几点：
- `slowlog-max-len`配置建议：线上建议调大慢查询列表，记录慢查询时Redis会对长命令做截断操作，并不会占用大量内存。增大慢查询列表可以减缓慢查询被剔除的可能，例如线上可设置为1000以上。
- `slowlog-log-slower-than`配置建议：默认值超过10毫秒判定为慢查询，需要根据Redis并发量调整该值。由于Redis采用单线程响应命令，对于高流量的场景，如果命令执行时间在1毫秒以上，那么Redis最多可支撑OPS不到1000。因此对于高OPS场景的Redis建议设置为1毫秒。
- 慢查询只记录命令执行时间，并不包括命令排队和网络传输时间。因此客户端执行命令的时间会大于命令实际执行时间。因为命令执行排队机制，慢查询会导致其他命令级联阻塞，因此当客户端出现请求超时，需要检查该时间点是否有对应的慢查询，从而分析出是否为慢查询导致的命令级联阻塞。
- 由于慢查询日志是一个先进先出的队列，也就是说如果慢查询比较多的情况下，可能会丢失部分慢查询命令，为了防止这种情况发生，可以定期执行`slow get`命令将慢查询日志持久化到其他存储中（例如MySQL），然后可以制作可视化界面进行查询。

### 3.2　Redis Shell
#### 3.2.1　redis-cli详解
1. `-r`：将命令执行多次
1. `-i`：每隔几秒执行一次命令，但是`-i`选项必须和`-r`选项一起使用。注意`-i`的单位是秒，不支持毫秒为单位，但是如果想以每隔10毫秒执行一次，可以用`-i 0.01`
1. `-x`：从标准输入（stdin）读取数据作为`redis-cli`的最后一个参数
1. `-c`：连接Redis Cluster节点时需要使用的，`-c`选项可以防止moved和ask异常
1. `-a`：有了这个选项就不需要手动输入`auth`命令
1. `--scan和--pattern`：用于扫描指定模式的键，相当于使用`scan`命令
1. `--slave`：把当前客户端模拟成当前Redis节点的从节点，可以用来获取当前Redis节点的更新操作
1. `--rdb`：请求Redis实例生成并发送RDB持久化文件，保存在本地。可使用它做持久化文件的定期备份
1. `--pipe`：用于将命令封装成Redis通信协议定义的数据格式，批量发送给Redis执行
1. `--bigkeys`：使用scan命令对Redis的键进行采样，从中找到内存占用比较大的键值，这些键可能是系统的瓶颈。
1. `--eval`：用于执行指定Lua脚本
1. `--latency`：有三个选项，都可以检测网络延迟
    1. `--latency`：测试客户端到目标Redis的网络延迟
    1. `--latency-history`：以分时段的形式了解延迟信息
    1. `--latency-dist`：会使用统计图表的形式从控制台输出延迟统计信息
1. `--stat`：可以实时获取Redis的重要统计信息
1. `--raw`和`--no-raw`：`--no-raw`选项是要求命令的返回结果必须是原始的格式，`--raw`恰恰相反，返回格式化后的结果

#### 3.2.2　redis-server详解
`redis-server --test-memory`可以用来检测当前操作系统能否稳定地分配指定容量的内存给Redis，通过这种检测可以有效避免因为内存问题造成Redis崩溃，例如下面操作检测当前操作系统能否提供1G的内存给Redis：
```
redis-server --test-memory 1024
```
当输出passed this test时说明内存检测完毕，最后会提示--test-memory只是简单检测

#### 3.2.3　redis-benchmark详解
redis-benchmark可以为Redis做基准性能测试
1. `-c`：代表客户端的并发数量（默认是50）
1. `-n<requests>`：代表客户端请求总量（默认是100000）
1. `-q`：仅仅显示redis-benchmark的requests per second信息
1. `-r`：可以向Redis插入更多随机的键
1. `-P`：代表每个请求pipeline的数据量（默认为1）
1. `-k<boolean>`：代表客户端是否使用keepalive，1为使用，0为不使用，默认值为1
1. `-t`：可以对指定命令进行基准测试
1. `--csv`：会将结果按照csv格式输出，便于后续处理，如导出到Excel等

### 3.3　Pipeline
#### 3.3.1　Pipeline概念
Redis客户端执行一条命令分为如下四个过程：
1. 发送命令
1. 命令排队
1. 命令执行
1. 返回结果

其中1+4称为Round Trip Time（RTT，往返时间）。

Pipeline（流水线）机制能能将一组Redis命令进行组装，通过一次RTT传输给Redis，再将这组Redis命令的执行结果按顺序返回给客户端

redis-cli的`--pipe`选项实际上就是使用Pipeline机制，例如下面操作将`set hello world`和`incr counter`两条命令组装：
```
echo -en '*3\r\n$3\r\nSET\r\n$5\r\nhello\r\n$5\r\nworld\r\n*2\r\n$4\r\nincr\r\
n$7\r\ncounter\r\n' | redis-cli --pipe
```

#### 3.3.2　性能测试
在不同网络下，10000条`set`非Pipeline和Pipeline的执行时间对比

网络 | 延迟 | 非Pipeline | Pipeline
----|----|----|----
本机 | 0.17ms | 573ms | 134ms
内网服务器 | 0.41ms | 1610ms | 240ms
异地机房 | 7ms | 78499ms | 1104ms

两个结论：
- Pipeline执行速度一般比逐条执行要快。
- 客户端和服务端的网络延时越大，Pipeline的效果越明显。

#### 3.3.3　原生批量命令与Pipeline对比
- 原生批量命令是原子的，Pipeline是非原子的。
- 原生批量命令是一个命令对应多个key，Pipeline支持多个命令。
- 原生批量命令是Redis服务端支持实现的，而Pipeline需要服务端和客户端的共同实现。

#### 3.3.4　最佳实践
Pipeline虽然好用，但是每次Pipeline组装的命令个数不能没有节制，否则一次组装Pipeline数据量过大，一方面会增加客户端的等待时间，另一方面会造成一定的网络阻塞，可以将一次包含大量命令的Pipeline拆分成多次较小的Pipeline来完成。

Pipeline只能操作一个Redis实例，但是即使在分布式Redis场景中，也可以作为批量操作的重要优化手段，具体细节见第11章。

### 3.4　事务与Lua
#### 3.4.1　事务
Redis事务不支持回滚功能
- `multi`：事务开始
- `exec`：事务结束
- `discard`：停止事务的执行
- `watch`：在事务之前，确保事务中的key没有被其他客户端修改过，才执行事务，否则不执行（类似乐观锁）

事务中`watch`命令演示时序

时间点 | 客户端-1 | 客户端-2
----|----|----
T1 | set key java | 
T2 | watch key | 
T3 | multi | 
T4 |  | append key python
T5 | append key jedis | 
T6 | exec | 
T7 | get key | 

可以看到“客户端-1”在执行`multi`之前执行了`watch`命令，“客户端-2”在“客户端-1”执行`exec`之前修改了`key`值，造成事务没有执行（`exec`结果为`nil`），整个代码如下所示：
```
#T1：客户端1
127.0.0.1:6379> set key "java"
OK
#T2：客户端1
127.0.0.1:6379> watch key
OK
#T3：客户端1
127.0.0.1:6379> multi
OK
#T4：客户端2
127.0.0.1:6379> append key python
(integer) 11
#T5：客户端1
127.0.0.1:6379> append key jedis
QUEUED
#T6：客户端1
127.0.0.1:6379> exec
(nil)
#T7：客户端1
127.0.0.1:6379> get key
"javapython"
```

#### 3.4.2　Lua用法简述
**1.数据类型及其逻辑处理**

Lua语言提供了如下几种数据类型：`booleans`（布尔）、`numbers`（数值）、`strings`（字符串）、`tables`（表格），和许多高级语言相比，相对简单。

**2.函数定义**

在Lua中，函数以`function`开头，以`end`结尾，`funcName`是函数名，中间部分是函数体：
```
function funcName()
...
end
contact函数将两个字符串拼接：
function contact(str1, str2)
return str1 .. str2
end
--"hello world"
print(contact("hello ", "world"))
```

#### 3.4.3　Redis与Lua
**1.在Redis中使用Lua**

在Redis中执行Lua脚本有两种方法：`eval`和`evalsha`。

```
eval 脚本内容 key个数 key列表 参数列表
```
```
127.0.0.1:6379> eval 'return "hello " .. KEYS[1] .. ARGV[1]' 1 redis world
"hello redisworld"
```
如果Lua脚本较长，还可以使用`redis-cli--eval`直接执行文件。`eval`命令和`--eval`参数本质是一样的。

![redis-lua-eval-min](http://www.wailian.work/images/2018/10/30/redis-lua-eval-min.png)

`eval`命令执行Lua脚本过程

![redis-lua-evalsha-min](http://www.wailian.work/images/2018/10/30/redis-lua-evalsha-min.png)

`evalsha`执行Lua脚本过程

首先要将Lua脚本加载到Redis服务端，得到该脚本的SHA1校验和，`evalsha`命令使用SHA1作为参数可以直接执行对应Lua脚本，避免每次发送Lua脚本的开销。这样客户端就不需要每次执行脚本内容，而脚本也会常驻在服务端，脚本功能得到了复用。

加载脚本：`script load`命令可以将脚本内容加载到Redis内存中，例如下面将`lua_get.lua`加载到Redis中，得到SHA1为："7413dc2440db1fea7c0a0bde841fa68eefaf149c"
```
# redis-cli script load "$(cat lua_get.lua)"
"7413dc2440db1fea7c0a0bde841fa68eefaf149c"
```
执行脚本：`evalsha`的使用方法如下，参数使用SHA1值，执行逻辑和`eval`一致。
```
evalsha 脚本SHA1值 key个数 key列表 参数列表
```
所以只需要执行如下操作，就可以调用`lua_get.lua`脚本：
```
127.0.0.1:6379> evalsha 7413dc2440db1fea7c0a0bde841fa68eefaf149c 1 redis world
"hello redisworld"
```

**2.Lua的Redis API**

Lua可以使用`redis.call`函数实现对Redis的访问，例如下面代码是Lua使用`redis.call`调用了Redis的`set`和`get`操作：
```
redis.call("set", "hello", "world")
redis.call("get", "hello")
```
放在Redis的执行效果如下：
```
127.0.0.1:6379> eval 'return redis.call("get", KEYS[1])' 1 hello
"world"
```
除此之外Lua还可以使用`redis.pcall`函数实现对Redis的调用，`redis.call`和`redis.pcall`的不同在于，如果`redis.call`执行失败，那么脚本执行结束会直接返回错误，而`redis.pcall`会忽略错误继续执行脚本，所以在实际开发中要根据具体的应用场景进行函数的选择。

#### 3.4.4　案例
Lua脚本功能为Redis开发和运维人员带来如下三个好处：
- Lua脚本在Redis中是原子执行的，执行过程中间不会插入其他命令。
- Lua脚本可以帮助开发和运维人员创造出自己定制的命令，并可以将这些命令常驻在Redis内存中，实现复用的效果。
- Lua脚本可以将多条命令一次性打包，有效地减少网络开销。

#### 3.4.5　Redis如何管理Lua脚本
Redis提供了4个命令实现对Lua脚本的管理。
1. `script load`：用于将Lua脚本加载到Redis内存中
1. `script exists`：用于判断sha1是否已经加载到Redis内存中
1. `script flush`：用于清除Redis内存已经加载的所有Lua脚本
1. `script kill`：用于杀掉正在执行的Lua脚本

### 3.5　Bitmaps
#### 3.5.1　数据结构模型
Redis提供了Bitmaps这个“数据结构”可以实现对位的操作。把数据结构加上引号主要因为：
- Bitmaps本身不是一种数据结构，实际上它就是字符串，但是它可以对字符串的位进行操作。
- Bitmaps单独提供了一套命令，所以在Redis中使用Bitmaps和使用字符串的方法不太相同。可以把Bitmaps想象成一个以位为单位的数组，数组的每个单元只能存储0和1，数组的下标在Bitmaps中叫做偏移量。

#### 3.5.2　命令
将每个独立用户是否访问过网站存放在Bitmaps中，将访问的用户记做1，没有访问的用户记做0，用偏移量作为用户的id。
1. 设置值：`setbit key offset value`
    - 设置键的第`offset`个位的值（从0算起），假设现在有20个用户，userid=0，5，11，15，19的用户对网站进行了访问，那么当前Bitmaps初始化结果如下所示。
    - ![redis-setbit-min](http://www.wailian.work/images/2018/10/30/redis-setbit-min.png)
    - 如果此时有一个userid=50的用户访问了网站，那么Bitmaps的结构变成了下图，第20位~49位都是0。
    - ![redis-setbit-50-min](http://www.wailian.work/images/2018/10/30/redis-setbit-50-min.png)
    - 很多应用的用户id以一个指定数字（例如10000）开头，直接将用户id和Bitmaps的偏移量对应势必会造成一定的浪费，通常的做法是每次做`setbit`操作时将用户id减去这个指定数字。在第一次初始化Bitmaps时，假如偏移量非常大，那么整个初始化过程执行会比较慢，可能会造成Redis的阻塞。
1. 获取值：`getbit key offset`
1. 获取Bitmaps指定范围值为1的个数：`bitcount [start][end]`
1. Bitmaps间的运算：`bitop op destkey key[key …]`
    - `bitop`是一个复合操作，它可以做多个Bitmaps的`and`（交集）、`or`（并集）、`not`（非）、`xor`（异或）操作并将结果保存在`destkey`中。
    - 利用`bitop and`命令计算两天都访问网站的用户。
    - ![redis-bitop-min](http://www.wailian.work/images/2018/10/30/redis-bitop-min.png)
1. 计算Bitmaps中第一个值为`targetBit`的偏移量：`bitpos key targetBit [start] [end]`

#### 3.5.3　Bitmaps分析
假设网站有1亿用户，每天独立访问的用户有5千万，set和Bitmaps存储一天活跃用户的对比

数据类型 | 每个用户id占用空间 | 需要存储的用户量 | 全部内存量
----|----|----|----
集合类型 | 64位 | 50000000 | 64位*50000000=400MB
Bitmaps | 1位 | 100000000 | 1位*100000000=12.5MB

set和Bitmaps存储独立用户空间对比

数据类型 | 一天 | 一个月 | 一年
----|----|----|----
set | 400M | 12G | 144G
Bitmaps | 12.5M | 375M | 4.5G

假如该网站每天的独立访问用户很少，例如只有10万（大量的僵尸用户）。set和Bitmaps存储一天活跃用户的对比（独立用户比较少）

数据类型 | 每个用户id占用空间 | 需要存储的用户量 | 全部内存量
----|----|----|----
集合类型 | 64位 | 100000 | 64位*100000=800KB
Bitmaps | 1位 | 100000000 | 1位*100000000=12.5MB

### 3.6　HyperLogLog
HyperLogLog并不是一种新的数据结构（实际类型为字符串类型），而是一种基数算法，通过HyperLogLog可以利用极小的内存空间完成独立总数的统计，数据集可以是IP、Email、ID等。HyperLogLog提供了3个命令：
1. `pfadd`：添加。`pfadd key element [element …]`
1. `pfcount`：计算独立用户数。`pfcount key [key …]`
1. `pfmerge`：合并。`pfmerge destkey sourcekey [sourcekey …]`

HyperLogLog内存占用量非常小，但是存在错误率，开发者在进行数据结构选型时只需要确认如下两条即可：
- 只为了计算独立总数，不需要获取单条数据。
- 可以容忍一定误差率，毕竟HyperLogLog在内存的占用量上有很大的优势。

### 3.7　发布订阅
#### 3.7.1　命令
1. 发布消息：`publish channel message`
1. 订阅消息：`subscribe channel [channel ...]`，订阅者可以订阅一个或多个频道，订阅命令有两点需要注意：
    - 客户端在执行订阅命令之后进入了订阅状态，只能接收`subscribe`、`psubscribe`、`unsubscribe`、`punsubscribe`的四个命令。
    - 新开启的订阅客户端，无法收到该频道之前的消息，因为Redis不会对发布的消息进行持久化。
1. 取消订阅：`unsubscribe [channel [channel ...]]`
1. 按照模式订阅和取消订阅：
	```
	psubscribe pattern [pattern...]
	punsubscribe [pattern [pattern ...]]
	```
1. 查询订阅
    1. 查看活跃的频道：`pubsub channels [pattern]`
    1. 查看频道订阅数：`pubsub numsub [channel ...]`
    1. 查看模式订阅数：`pubsub numpat`

#### 3.7.2　使用场景
聊天室、公告牌、服务之间利用消息解耦都可以使用发布订阅模式

![redis-publish-min](http://www.wailian.work/images/2018/10/30/redis-publish-min.png)

- 视频服务订阅video：changes频道如下：
	```
	subscribe video:changes
	```
- 视频管理系统发布消息到video：changes频道如下：
	```
	publish video:changes "video1,video3,video5"
	```
- 当视频服务收到消息，对视频信息进行更新，如下所示：
	```
	for video in video1,video3,video5
	update {video}
	```

### 3.8　GEO
Redis3.2版本提供了GEO（地理信息定位）功能，支持存储地理位置信息用来实现诸如附近位置、摇一摇这类依赖于地理位置信息的功能。
1. 增加地理位置信息
	```
	geoadd key longitude latitude member [longitude latitude member ...]
	```
1. 获取地理位置信息
	```
	geopos key member [member ...]
	```
1. 获取两个地理位置的距离
	```
	geodist key member1 member2 [unit]
	```
1. 获取指定位置范围内的地理信息位置集合
	```
	georadius key longitude latitude radiusm|km|ft|mi [withcoord] [withdist]
    [withhash] [COUNT count] [asc|desc] [store key] [storedist key]
    georadiusbymember key member radiusm|km|ft|mi [withcoord] [withdist]
    [withhash] [COUNT count] [asc|desc] [store key] [storedist key]
	```
1. 获取`geohash`
	```
	geohash key member [member ...]
	```
1. 删除地理位置信息
	```
	zrem key member
	```

## References
- 《Redis开发与运维》