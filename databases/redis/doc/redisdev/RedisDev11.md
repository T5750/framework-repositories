# Redis开发与运维笔记

## 第11章　缓存设计
### 11.1　缓存的收益和成本
收益如下：
- 加速读写
- 降低后端负载

成本如下：
- 数据不一致性
- 代码维护成本
- 运维成本

缓存的使用场景基本包含如下两种：
- 开销大的复杂计算
- 加速请求响应

### 11.2　缓存更新策略
三种常见更新策略的对比

策略 | 一致性 | 维护成本
-----|----|----
LRU/LFU/FIFO算法剔除 | 最差 | 低
超时剔除 | 较差 | 较低
主动更新 | 强 | 高

1. LRU/LFU/FIFO算法剔除
    - 使用场景。剔除算法通常用于缓存使用量超过了预设的最大值时候，如何对现有的数据进行剔除。例如Redis使用`maxmemory-policy`这个配置作为内存最大值后对于数据的剔除策略。
    - 一致性。要清理哪些数据是由具体算法决定，所以数据的一致性是最差的。
    - 维护成本。算法不需要开发人员自己来实现，通常只需要配置最大`maxmemory`和对应的策略即可。（LRU的淘汰规则是基于访问时间，而LFU是基于访问次数的。）
1. 超时剔除
    - 使用场景。超时剔除通过给缓存数据设置过期时间，让其在过期时间后自动删除，例如Redis提供的`expire`命令。
    - 一致性。一段时间窗口内（取决于过期时间长短）存在一致性问题，即缓存数据和真实数据源的数据不一致。
    - 维护成本。维护成本不是很高，只需设置`expire`过期时间即可。
1. 主动更新
    - 使用场景。应用方对于数据的一致性要求高，需要在真实数据更新后，立即更新缓存数据。例如可以利用消息系统或者其他方式通知缓存更新。
    - 一致性。一致性最高，但如果主动更新发生了问题，那么这条数据很可能很长时间不会更新，所以建议结合超时剔除一起使用效果会更好。
    - 维护成本。维护成本会比较高，开发者需要自己来完成更新，并保证更新操作的正确性。
1. 最佳实践
    - 低一致性业务建议配置最大内存和淘汰策略的方式使用。
    - 高一致性业务可以结合使用超时剔除和主动更新，这样即使主动更新出了问题，也能保证数据过期时间后删除脏数据。

### 11.3　缓存粒度控制
缓存全部数据和部分数据对比

数据类型 | 通用性 | 空间占用比（内存空间+网路带宽） | 代码维护
----|----|-----|----
全部数据 | 高 | 大 | 简单
部分数据 | 低 | 小 | 较为复杂

- 通用性。缓存全部数据比部分数据更加通用，但从实际经验看，很长时间内应用只需要几个重要的属性。
- 空间占用。缓存全部数据要比部分数据占用更多的空间，可能存在以下问题：
    - 全部数据会造成内存的浪费。
    - 全部数据可能每次传输产生的网络流量会比较大，耗时相对较大，在极端情况下会阻塞网络。
    - 全部数据的序列化和反序列化的CPU开销更大。
- 代码维护。全部数据的优势更加明显，而部分数据一旦要加新字段需要修改业务代码，而且修改后通常还需要刷新缓存数据。

### 11.4　穿透优化
![redis-miss-min](http://www.wailian.work/images/2018/10/29/redis-miss-min.png)

缓存穿透是指查询一个根本不存在的数据，缓存层和存储层都不会命中，通常出于容错的考虑，如果从存储层查不到数据则不写入缓存层。

1. 缓存层不命中。
1. 存储层不命中，不将空结果写回缓存。
1. 返回空结果。

造成缓存穿透的基本原因有两个。
1. 自身业务代码或者数据出现问题。
1. 一些恶意攻击、爬虫等造成大量空命中。

#### 1.缓存空对象

![redis-miss-null-min](http://www.wailian.work/images/2018/10/29/redis-miss-null-min.png)

缓存空对象会有两个问题：
1. 空值做了缓存，意味着缓存层中存了更多的键，需要更多的内存空间（如果是攻击，问题更严重），比较有效的方法是针对这类数据设置一个较短的过期时间，让其自动剔除。
1. 缓存层和存储层的数据会有一段时间窗口的不一致，可能会对业务有一定影响。例如过期时间设置为5分钟，如果此时存储层添加了这个数据，那此段时间就会出现缓存层和存储层数据的不一致，此时可以利用消息系统或者其他方式清除掉缓存层中的空对象。

缓存空对象的实现代码：
```
String get(String key) {
	// 从缓存中获取数据
	String cacheValue = cache.get(key);
	// 缓存为空
	if (StringUtils.isBlank(cacheValue)) {
		// 从存储中获取
		String storageValue = storage.get(key);
		cache.set(key, storageValue);
		// 如果存储数据为空，需要设置一个过期时间(300秒)
		if (storageValue == null) {
			cache.expire(key, 60 * 5);
		}
		return storageValue;
	} else {
		// 缓存非空
		return cacheValue;
	}
}
```

#### 2.布隆过滤器拦截

![redis-miss-bloomFilter-min](http://www.wailian.work/images/2018/10/29/redis-miss-bloomFilter-min.png)

在访问缓存层和存储层之前，将存在的key用布隆过滤器提前保存起来，做第一层拦截。例如：一个推荐系统有4亿个用户id，每个小时算法工程师会根据每个用户之前历史行为计算出推荐数据放到存储层中，但是最新的用户由于没有历史行为，就会发生缓存穿透的行为，为此可以将所有推荐数据的用户做成布隆过滤器。如果布隆过滤器认为该用户id不存在，那么就不会访问存储层，在一定程度保护了存储层。

#### 3.两种方案对比

缓存空对象和布隆过滤器方案对比

解决缓存穿透 | 适用场景 | 维护成本
----|-----|-----
缓存空对象 | 数据命中不高，数据频繁变化实时性高 | 代码维护简单，需要过多的缓存空间，数据不一致
布隆过滤器 | 数据命中不高，数据相对固定实时性低 | 代码维护复杂，缓存空间占用少

### 11.5　无底洞优化
2010年，Facebook的Memcache节点已经达到了3000个，承载着TB级别的缓存数据。但开发和运维人员发现了一个问题，为了满足业务要求添加了大量新Memcache节点，但是发现性能不但没有好转反而下降了，当时将这种现象称为缓存的“无底洞”现象。

无底洞问题分析：
- 客户端一次批量操作会涉及多次网络操作，也就意味着批量操作会随着节点的增多，耗时会不断增大。
- 网络连接数变多，对节点的性能也有一定影响。

用一句通俗的话总结就是，更多的节点不代表更高的性能，所谓“无底洞”就是说投入越多不一定产出越多。

常见的IO优化思路：
- 命令本身的优化，例如优化SQL语句等。
- 减少网络通信次数。
- 降低接入成本，例如客户端使用长连/连接池、NIO等。

以Redis批量获取n个字符串为例，有三种实现方法：
- 客户端n次get：n次网络+n次get命令本身。
- 客户端1次pipeline get：1次网络+n次get命令本身。
- 客户端1次mget：1次网络+1次mget命令本身。

#### 1.串行命令
![redis-serialMget-min](http://www.wailian.work/images/2018/10/29/redis-serialMget-min.png)

由于n个key是比较均匀地分布在Redis Cluster的各个节点上，因此无法使用mget命令一次性获取，所以通常来讲要获取n个key的值，最简单的方法就是逐次执行n个get命令，这种操作时间复杂度较高，`操作时间=n次网络时间+n次命令时间`，网络次数是n。很显然这种方案不是最优的，但是实现起来比较简单。

Jedis客户端示例代码如下：
```
List<String> serialMGet(List<String> keys) {
	// 结果集
	List<String> values = new ArrayList<String>();
	// n次串行get
	for (String key : keys) {
		String value = jedisCluster.get(key);
		values.add(value);
	}
	return values;
}
```

#### 2.串行IO
![redis-serialIOMget-min](http://www.wailian.work/images/2018/10/29/redis-serialIOMget-min.png)

Redis Cluster使用CRC16算法计算出散列值，再取对16383的余数就可以算出slot值，同时Smart客户端会保存slot和节点的对应关系，有了这两个数据就可以将属于同一个节点的key进行归档，得到每个节点的key子列表，之后对每个节点执行mget或者Pipeline操作，`操作时间=node次网络时间+n次命令时间`，网络次数是node的个数，很明显这种方案比第一种要好很多，但是如果节点数太多，还是有一定的性能问题。

Jedis客户端示例代码如下：
```
Map<String, String> serialIOMget(List<String> keys) {
	// 结果集
	Map<String, String> keyValueMap = new HashMap<String, String>();
	// 属于各个节点的key列表,JedisPool要提供基于ip和port的hashcode方法
	Map<JedisPool, List<String>> nodeKeyListMap = new HashMap<JedisPool, List<String>>();
	// 遍历所有的key
	for (String key : keys) {
		// 使用CRC16本地计算每个key的slot
		int slot = JedisClusterCRC16.getSlot(key);
		// 通过jedisCluster本地slot->node映射获取slot对应的node
		JedisPool jedisPool = jedisCluster.getConnectionHandler().getJedisPoolFromSlot(slot);
		// 归档
		if (nodeKeyListMap.containsKey(jedisPool)) {
			nodeKeyListMap.get(jedisPool).add(key);
		} else {
			List<String> list = new ArrayList<String>();
			list.add(key);
			nodeKeyListMap.put(jedisPool, list);
		}
	}
	// 从每个节点上批量获取，这里使用mget也可以使用pipeline
	for (Entry<JedisPool, List<String>> entry : nodeKeyListMap.entrySet()) {
		JedisPool jedisPool = entry.getKey();
		List<String> nodeKeyList = entry.getValue();
		// 列表变为数组
		String[] nodeKeyArray = nodeKeyList.toArray(new String[nodeKeyList.size()]);
		// 批量获取，可以使用mget或者Pipeline
		List<String> nodeValueList = jedisPool.getResource().mget(nodeKeyArray);
		// 归档
		for (int i = 0; i < nodeKeyList.size(); i++) {
			keyValueMap.put(nodeKeyList.get(i), nodeValueList.get(i));
		}
	}
	return keyValueMap;
}
```

#### 3.并行IO
![redis-parallelIOMget-min](http://www.wailian.work/images/2018/10/29/redis-parallelIOMget-min.png)

此方案是将方案2中的最后一步改为多线程执行，网络次数虽然还是节点个数，但由于使用多线程网络时间变为`O（1）`，这种方案会增加编程的复杂度。`操作时间=max_slow(node网络时间)+n次命令时间`。

Jedis客户端示例代码如下，只需要将串行IO变为多线程：
```
Map<String, String> parallelIOMget(List<String> keys) {
	// 结果集
	Map<String, String> keyValueMap = new HashMap<String, String>();
	// 属于各个节点的key列表
	Map<JedisPool, List<String>> nodeKeyListMap = new HashMap<JedisPool, List<String>>();
	...和前面一样
	// 多线程mget，最终汇总结果
	for (Entry<JedisPool, List<String>> entry : nodeKeyListMap.entrySet()) {
		// 多线程实现
	}
	return keyValueMap;
}
```

#### 4.hash_tag实现
![redis-hash-tag-node-min](http://www.wailian.work/images/2018/10/29/redis-hash-tag-node-min.png)

Redis Cluster的hash_tag功能可以将多个key强制分配到一个节点上，`操作时间=1次网络时间+n次命令时间`。所有key属于node2节点。

![redis-hash-tag-min](http://www.wailian.work/images/2018/10/29/redis-hash-tag-min.png)

Jedis客户端示例代码如下：
```
List<String> hashTagMget(String[] hashTagKeys) {
	return jedisCluster.mget(hashTagKeys);
}
```

四种批量操作解决方案对比，没有最好的方案只有最合适的方案。

方案 | 优点 | 缺点 | 网络IO
----|-----|-----|----
串行命令 | 编程简单，如果少量keys，性能可以满足要求 | 大量keys请求延迟严重 | O(keys)
串行IO | 编程简单，少量节点，性能满足要求 | 大量nodes延迟严重 | O(nodes)
并行IO | 利用并行特性，延迟取决于最慢的节点 | 编程复杂，由于多线程，问题定位可能较难 | O(max_slow(nodes))
hash_tag | 性能最高 | 业务维护成本较高，容易出现数据倾斜 | O(1)

### 11.6　雪崩优化
![redis-stampeding-herd-min](http://www.wailian.work/images/2018/10/29/redis-stampeding-herd-min.png)

缓存雪崩：由于缓存层承载着大量请求，有效地保护了存储层，但是如果缓存层由于某些原因不能提供服务，于是所有的请求都会达到存储层，存储层的调用量会暴增，造成存储层也会级联宕机的情况。缓存雪崩的英文原意是stampeding herd（奔逃的野牛），指的是缓存层宕掉后，流量会像奔逃的野牛一样，打向后端存储。

预防和解决缓存雪崩问题，可以从以下三个方面进行着手。
1. 保证缓存层服务高可用性。例如Redis Sentinel和Redis Cluster都实现了高可用。
1. 依赖隔离组件为后端限流并降级。无论是缓存层还是存储层都会有出错的概率，可以将它们视同为资源。作为并发量较大的系统，假如有一个资源不可用，可能会造成线程全部阻塞（hang）在这个资源上，造成整个系统不可用。降级机制在高并发系统中是非常普遍的：比如推荐服务中，如果个性化推荐服务不可用，可以降级补充热点数据，不至于造成前端页面是开天窗。在实际项目中，我们需要对重要的资源（例如Redis、MySQL、HBase、外部接口）都进行隔离，让每种资源都单独运行在自己的线程池中，即使个别资源出现了问题，对其他服务没有影响。但是线程池如何管理，比如如何关闭资源池、开启资源池、资源池阀值管理，这些做起来还是相当复杂的。这里推荐一个Java依赖隔离工具[Hystrix](https://github.com/netflix/hystrix)。
1. 提前演练。

### 11.7　热点key重建优化
有两个问题如果同时出现，可能就会对应用造成致命的危害：
- 当前key是一个热点key（例如一个热门的娱乐新闻），并发量非常大。
- 重建缓存不能在短时间完成，可能是一个复杂计算，例如复杂的SQL、多次IO、多个依赖等。

要解决这个问题需要制定如下目标：
- 减少重建缓存的次数。
- 数据尽可能一致。
- 较少的潜在危险。

#### 1.互斥锁（mutex key）
![redis-setnx-min](http://www.wailian.work/images/2018/10/29/redis-setnx-min.png)

此方法只允许一个线程重建缓存，其他线程等待重建缓存的线程执行完，重新从缓存获取数据即可。

下面代码使用Redis的`setnx`命令实现上述功能：
```
String get(String key) {
	// 从Redis中获取数据
	String value = redis.get(key);
	// 如果value为空，则开始重构缓存
	if (value == null) {
		// 只允许一个线程重构缓存，使用nx，并设置过期时间ex
		String mutexKey = "mutext:key:" + key;
		if (redis.set(mutexKey, "1", "ex 180", "nx")) {
			// 从数据源获取数据
			value = db.get(key);
			// 回写Redis，并设置过期时间
			redis.setex(key, timeout, value);
			// 删除key_mutex
			redis.delete(mutexKey);
		}
		// 其他线程休息50毫秒后重试
		else {
			Thread.sleep(50);
			get(key);
		}
	}
	return value;
}
```

#### 2.永远不过期
![redis-logicTimeout-min](http://www.wailian.work/images/2018/10/29/redis-logicTimeout-min.png)

“永远不过期”包含两层意思：
- 从缓存层面来看，确实没有设置过期时间，所以不会出现热点key过期后产生的问题，也就是“物理”不过期。
- 从功能层面来看，为每个value设置一个逻辑过期时间，当发现超过逻辑过期时间后，会使用单独的线程去构建缓存。

从实战看，此方法有效杜绝了热点key产生的问题，但唯一不足的就是重构缓存期间，会出现数据不一致的情况，这取决于应用方是否容忍这种不一致。下面代码使用Redis进行模拟：
```
String get(final String key) {
	V v = redis.get(key);
	String value = v.getValue();
	// 逻辑过期时间
	long logicTimeout = v.getLogicTimeout();
	// 如果逻辑过期时间小于当前时间，开始后台构建
	if (v.logicTimeout <= System.currentTimeMillis()) {
		String mutexKey = "mutex:key:" + key;
		if (redis.set(mutexKey, "1", "ex 180", "nx")) {
			// 重构缓存
			threadPool.execute(new Runnable() {
				public void run() {
				String dbValue = db.get(key);
				redis.set(key, (dbvalue,newLogicTimeout));
				redis.delete(mutexKey);
			}
			});
		}
	}
	return value;
}
```

两种热点key的解决方法

解决方案 | 优点 | 缺点
----|-----|-----
简单分布式锁 | 思路简单，保证一致性 | 代码复杂度增大，存在死锁的风险，存在线程池阻塞的风险
“永远不过期” | 基本杜绝热点key问题 | 不保证一致性，逻辑过期时间增加代码维护成本和内存成本

## References
- 《Redis开发与运维》