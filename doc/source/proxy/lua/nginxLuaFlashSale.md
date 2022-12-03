## nginx Lua Flash Sale

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
1. 解析请求参数，根据促销Id按照Jedis中MurmurHash算法获取分片，然后按照分片包装`Pipeline`批量发送请求参数`argList`
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