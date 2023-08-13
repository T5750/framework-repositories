## nginx Lua Flash Sale

### 服务介绍
限时抢购又称闪购，英文Flash sale，起源于法国网站Vente Privée。闪购模式即是以互联网为媒介的B2C电子零售交易活动，以限时特卖的形式，定期定时推出国际知名品牌的商品，一般以原价1-5折的价格供专属会员限时抢购，每次特卖时间持续5-10天不等，先到先买，限时限量，售完即止。顾客在指定时间内（一般为20分钟）必须付款，否则商品会重新放到待销售商品的行列里。

#### 模式特征
- 品牌丰富
- 时间短暂
- 折扣超低

#### 服务主要功能
- 创建促销服务：采销创建促销后，促销管理系统审核通过后，会调用抢购系统创建促销
- 抢服务：为符合条件的订单操作剩余数，主要是扣减剩余数

### 系统设计要点

#### 如何实现实时库存？
这里说的库存不是真正意义上的库存，其实是该促销可以抢购的数量，真正的库存在基础库存服务。用户点击『提交订单』按钮后，在抢购系统中获取了资格后才去基础库存服务中扣减真正的库存；而抢购系统控制的就是资格/剩余数。传统方案利用数据库行锁，但是在促销高峰数据库压力过大导致服务不可用，目前采用redis集群（16分片）缓存促销信息，例如促销id、促销剩余数、抢次数等，抢的过程中按照促销id散列到对应分片，实时扣减剩余数。当剩余数为0或促销删除，价格恢复原价。

#### 如何设计抢购redis数据结构？
采销人员发布促销后，在抢购redis中生成一笔记录，给抢服务提供基本信息。每一个促销对应一个促销id，促销信息是Hashes结构。

例如促销A，对应的类型为单品促销，我们暂且认为类型值为1，对应redis中的key为 `C_A_1`，数据结构内容类似于如下：
```
o:  100 // 原始数量
b:  99  // 可抢购数量，假如抢购了一个剩下了99
c:  1   // 抢购次数记录，用来限流，后面会介绍到
```

#### 如何保证不超卖？
因为扣减资格是一组操作，我们利用`EVAL`操作redis剩余数实现原子化操作，伪代码如下：
```
local key = KEYS[1]
local tag = "b"
local num = tonumber(ARGV[1]);
local lastNum = redis.call('HINCRBY',key,tag,-num);
if业务性判断ortonumber(lastNum) == 0then
    return lastNum
end
```
如上代码会返回剩余数，如果小于等于0了，则没有库存了

#### 如何提高吞吐量？
减少网络交互（一次抢数据通过 `EVALSHA` 一次性提交给redis集群）；数据库操作异步化（使用JMQ异步记录日志）

#### 如何保证可用性？
抢购系统主要依赖于redis集群，redis采用一主三从集群方案，部署在两个机房，每个集群16个分片，每两分片共用一台物理机，可通过配置中心切换主从；

如果Redis挂掉了，如何恢复呢？通过汇总MySQL中的抢购和取消流水日志，并恢复Redis的抢购数量。

### 系统架构
![nginxLuaFlashSale-framework](https://s1.wailian.download/2020/02/17/nginxLuaFlashSale-framework-min.png)

注：此处的库存是可抢购数量设置，或者叫做资格/剩余数，并非真正的实际库存

#### 抢服务流程
Redis使用单个Lua解释器去运行所有脚本，并且Redis 也保证脚本会以原子性(atomic)的方式执行：当某个脚本正在运行的时候，不会有其他脚本或Redis命令被执行

![nginxLuaFlashSale-process](https://s1.wailian.download/2020/02/17/nginxLuaFlashSale-process-min.jpg)

#### REDIS+LUA抢购子流程
通过lua Script脚本实现，暂时命名为`q.lua`（主要功能限流和扣减促销活动剩余数）
```
--[[
--!@brief 促销Id下限流：可以防止某个促销过热导致服务不可以用
--]]
local function limited()
    -- todo: 实现
end
--[[
--!@brief 限制逻辑(ip和pin)：比如有的促销是限制ip,这里校验ip是否存在,如果为限ip类型抢购活动,存在抛出异常告知ip已经存在不能抢购
--]]
local function check_ip_pin()
    -- todo: 实现
end
--[[
--!@brief 记录订单号：主要目的实现抢方法幂等性,调用方网络超时可以重复调用,存在订单号直接返回抢购成功,不至于超卖
--]]
local function record_order_id()
    -- todo: 实现
end
--[[
--!@brief 扣减剩余数
--]]
local function scalebuy()
    --
    local lastNum = redis.call('HINCRBY',key,tag,-num);
    --
end
-- 调用顺序不可调整
-- 1 限流
local status,msg = limited()
if status == 0then
    return msg
end
-- 2 校验
status,msg = check_ip_pin()
if status == 0 then
    return msg
end
-- 3 记录订单
status,msg = record_order_id()
if status == 0 then
    return msg
end
-- 4 扣减剩余数
status,msg = scalebuy()
if status == 0 then
    return msg
end
-- 5 返回成功标示
return 1
```

子流程具体如下：
1. 解析请求参数，根据促销Id按照Jedis中MurmurHash算法获取分片，然后按照分片包装Pipeline批量发送请求参数`argList`
2. 获取系统初始化时SCRIPT LOAD加载`q.lua`返回的串`shaValue`
3. 执行`EVALSHA`，伪代码如下：
	```
	// 其他操作
	Pipeline p;
	// 初始化p
	p.evalsha(shaValue,keyList, argList);
	// 其他操作
	```
4. 处理返回结果，只要有一个分片失败，本次抢购就失败

#### 限流处理
方法级限流，限流阈值通过配置中心配置，一分钟生效，伪代码如下：
```
private static AtomicInteger atomic = new AtomicInteger(0);
public void test() {
    try {
        // 限流
        int limitNum = XXX.getLimitNum();
        int nowConcurrent = atomic.incrementAndGet();
        if(nowConcurrent > limitNum) {
            // 异常处理
        }  
        // 正常业务逻辑
    } catch(Exception e) {
        // 异常处理
    } finally {
        atomic.decrementAndGet();
    }
}
```

### References
- [京东抢购服务高并发实践](https://mp.weixin.qq.com/s?__biz=MzIwODA4NjMwNA==&mid=2652897841&idx=1&sn=6328c5011e6c66d9a10d714a9bda52d1&scene=21)