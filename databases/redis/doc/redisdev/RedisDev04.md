# Redis开发与运维笔记

## 第4章　客户端
### 4.1　客户端通信协议
几乎所有的主流编程语言都有Redis的客户端（http://redis.io/clients），站在技术的角度看原因还有两个：
1. 客户端与服务端之间的通信协议是在TCP协议之上构建的。
1. Redis制定了RESP（REdis Serialization Protocol，Redis序列化协议）实现客户端与服务端的正常交互，这种协议简单高效，既能够被机器解析，又容易被人类识别。

### 4.2　Java客户端Jedis
Java有很多优秀的Redis客户端（详见：http://redis.io/clients#java）

#### 4.2.1　获取Jedis
```
<dependency>
	<groupId>redis.clients</groupId>
	<artifactId>jedis</artifactId>
	<version>2.8.2</version>
</dependency>
```

#### 4.2.2　Jedis的基本使用方法
Jedis四个参数的构造函数是比较常用的：
```
Jedis(final String host, final int port, final int connectionTimeout, final int soTimeout)
```
- `host`：Redis实例的所在机器的IP。
- `port`：Redis实例的端口。
- `connectionTimeout`：客户端连接超时。
- `soTimeout`：客户端读写超时。

在实际项目中比较推荐使用`try catch finally`的形式来进行代码的书写：
```
Jedis jedis = null;
try {
	jedis = new Jedis("127.0.0.1", 6379);
	jedis.get("hello");
} catch (Exception e) {
	logger.error(e.getMessage(),e);
} finally {
	if (jedis != null) {
		jedis.close();
	}
}
```

参数除了可以是字符串，Jedis还提供了字节数组的参数，例如：
```
public String set(final String key, String value)
public String set(final byte[] key, final byte[] value)
public byte[] get(final byte[] key)
public String get(final String key)
```
示例：`JedisSimpleTest`

#### 4.2.3　Jedis连接池的使用方法
生产环境中一般使用连接池的方式对Jedis连接进行管理，所有Jedis对象预先放在池子中（`JedisPool`），每次要连接Redis，只需要在池子中借，用完了在归还给池子。

客户端连接Redis使用的是TCP协议，直连的方式每次需要建立TCP连接，而连接池的方式是可以预先初始化好Jedis连接，所以每次只需要从Jedis连接池借用即可，而借用和归还操作是在本地进行的，只有少量的并发同步开销，远远小于新建TCP连接的开销。另外直连的方式无法限制Jedis对象的个数，在极端情况下可能会造成连接泄露，而连接池的形式可以有效的保护和控制资源的使用。

Jedis直连方式和连接池方式对比

连接方式 | 优点 | 缺点
----|------|------
直连 | 简单方便，适用于少量长期连接的场景 | 1）存在每次新建/关闭TCP连接开销。2）资源无法控制，极端情况会出现连接泄露。3）Jedis对象线程不安全
连接池 | 1）无需每次连接都生成Jedis对象，降低开销。2）使用连接池的形式保护和控制资源的使用 | 相对于直连，使用相对麻烦，尤其在资源的管理上需要很多参数来保证，一旦规划不合理也会出现问题

示例：`JedisPoolTest`

`GenericObjectPoolConfig`的重要属性

#### 4.2.4　Redis中Pipeline的使用方法
Redis提供了`mget`、`mset`方法，但是并没有提供mdel方法，如果想实现这个功能，可以借助Pipeline来模拟批量删除，虽然不会像`mget`和`mset`那样是一个原子命令，但是在绝大数场景下可以使用。
```
public void mdel(List<String> keys) {
	Jedis jedis = new Jedis("127.0.0.1");
	// 1)生成pipeline对象
	Pipeline pipeline = jedis.pipelined();
	// 2)pipeline执行命令，注意此时命令并未真正执行
	for (String key : keys) {
		pipeline.del(key);
	}
	// 3)执行命令
	pipeline.sync();
}
```
- 利用jedis对象生成一个pipeline对象，直接可以调用`jedis.pipelined()`。
- 将`del`命令封装到pipeline中，可以调用`pipeline.del(String key)`，这个方法和`jedis.del(String key)`的写法是完全一致的，只不过此时不会真正的执行命令。
- 使用`pipeline.sync()`完成此次pipeline对象的调用。
- `pipeline.syncAndReturnAll()`将pipeline的命令进行返回。

示例：`JedisPipelineTest`

#### 4.2.5　Jedis的Lua脚本
Jedis中执行Lua脚本和redis-cli十分类似，Jedis提供了三个重要的函数实现Lua脚本的执行：
```
Object eval(String script, int keyCount, String... params)
Object evalsha(String sha1, int keyCount, String... params)
String scriptLoad(String script)
```
eval函数有三个参数，分别是：
- `script`：Lua脚本内容。
- `keyCount`：键的个数。
- `params`：相关参数KEYS和ARGV。

`scriptLoad`和`evalsha`函数要一起使用，首先使用`scriptLoad`将脚本加载到Redis中，代码如下：
```
String scriptSha = jedis.scriptLoad(script);
```
`evalsha`函数用来执行脚本的SHA1校验和，它需要三个参数：
- `scriptSha`：脚本的SHA1。
- `keyCount`：键的个数。
- `params`：相关参数KEYS和ARGV。

示例：`JedisLuaTest`

总体来说，重点注意以下几点即可：
1. Jedis操作放在`try catch finally`里更加合理。
1. 区分直连和连接池两种实现方式优缺点。
1. `jedis.close()`方法的两种实现方式。
1. Jedis依赖了common-pool，有关common-pool的参数需要根据不同的使用场景，各不相同，需要具体问题具体分析。
1. 如果key和value涉及了字节数组，需要自己选择适合的序列化方法。

### 4.3　Python客户端redis-py


### 4.4　客户端管理
#### 4.4.1　客户端API
1. `client list`：列出与Redis服务端相连的所有客户端连接信息
1. `client setName`和`client getName`：`client setName`用于给客户端设置名字，`client getName`查看当前客户端的name
1. `client kill`：用于杀掉指定IP地址和端口的客户端
1. `client pause`：用于阻塞客户端timeout毫秒数，在此期间客户端连接将被阻塞
1. `monitor`：用于监控Redis正在执行的命令

#### 4.4.2　客户端相关配置
- `timeout`：检测客户端空闲连接的超时时间，一旦idle时间达到了timeout，客户端将会被关闭，如果设置为0就不进行检测。
- `maxclients`：客户端最大连接数，这个参数会受到操作系统设置的限制。
- `tcp-keepalive`：检测TCP连接活性的周期，默认值为0，也就是不进行检测，如果需要设置，建议为60，那么Redis会每隔60秒对它创建的TCP连接进行活性检测，防止大量死连接占用系统资源。
- `tcp-backlog`：TCP三次握手后，会将接受的连接放入队列中，`tcp-backlog`就是队列的大小，它在Redis中的默认值是511。通常来讲这个参数不需要调整，但是这个参数会受到操作系统的影响，例如在Linux操作系统中，如果`/proc/sys/net/core/somaxconn`小于`tcp-backlog`，那么在Redis启动时会看到如下日志，并建议将`/proc/sys/net/core/somaxconn`设置更大。

#### 4.4.3　客户端统计片段



### 4.5　客户端常见异常

### 4.6　客户端案例分析

#### 4.6.1　Redis内存陡增


#### 4.6.2　客户端周期性的超时



## References
- 《Redis开发与运维》