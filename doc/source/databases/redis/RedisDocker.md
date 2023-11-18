# Redis Docker

Redis is an open source key-value store that functions as a data structure server.

## Redis Standalone in Docker
```sh
docker run --name redis -p 6379:6379 -d redis
docker run --name redis -p 6379:6379 -d --restart=always redis:5 --requirepass 123456
```

## Redis Standalone in Docker Compose
`redis-standalone.yml`

## Redis Sentinel in Docker Compose
- `redis.conf`
- `sentinel.conf`
- `redis-sentinel.yml`

```sh
mkdir redis{1,2,3}
cp -avx redis.conf sentinel.conf redis1
```

### Tests
```sh
docker exec -it redis1 bash
redis-cli -h redis1 -p 6379
info replication

docker exec -it redis3 bash
redis-cli -h redis3 -p 6379
info replication

redis-cli -h sentinel2 -p 26379
sentinel master mymaster
sentinel slaves mymaster
```

## Redis Cluster in Docker Compose
- `redis.conf`
- `redis-cluster.yml`

`vi redis.conf`
```
bind 0.0.0.0
masterauth 123456
requirepass 123456
appendonly yes
cluster-enabled yes
cluster-config-file nodes-6379.conf
cluster-node-timeout 15000
```

```sh
mkdir r{1,2,3,4,5,6}
cp -avx redis.conf r1

docker exec -it redis1 bash
redis-cli -a 123456 --cluster create 172.18.0.111:6379 172.18.0.112:6379 172.18.0.113:6379 172.18.0.114:6379 172.18.0.115:6379 172.18.0.116:6379 --cluster-replicas 1

redis-cli -h redis1 -a 123456
cluster nodes
cluster info
redis-cli -a 123456 --cluster check 172.18.0.111:6379
```

### Tests
```sh
docker exec -it redis2 /usr/local/bin/redis-cli -c -a 123456 -h 172.18.0.112
set name t5750
set aaa 111
set bbb 222
```

### Commands
```
CLUSTER INFO 打印集群的信息
CLUSTER NODES 列出集群当前已知的所有节点（node），以及这些节点的相关信息

//节点(node)
CLUSTER MEET <ip> <port> 将 ip 和 port 所指定的节点添加到集群当中，让它成为集群的一份子
CLUSTER FORGET <node_id> 从集群中移除 node_id 指定的节点
CLUSTER REPLICATE <node_id> 将当前节点设置为 node_id 指定的节点的从节点
CLUSTER SAVECONFIG 将节点的配置文件保存到硬盘里面

//槽(slot)
CLUSTER ADDSLOTS <slot> [slot ...] 将一个或多个槽（slot）指派（assign）给当前节点
CLUSTER DELSLOTS <slot> [slot ...] 移除一个或多个槽对当前节点的指派
CLUSTER FLUSHSLOTS 移除指派给当前节点的所有槽，让当前节点变成一个没有指派任何槽的节点
CLUSTER SETSLOT <slot> NODE <node_id> 将槽 slot 指派给 node_id 指定的节点，如果槽已经指派给另一个节点，那么先让另一个节点删除该槽>，然后再进行指派
CLUSTER SETSLOT <slot> MIGRATING <node_id> 将本节点的槽 slot 迁移到 node_id 指定的节点中
CLUSTER SETSLOT <slot> IMPORTING <node_id> 从 node_id 指定的节点中导入槽 slot 到本节点
CLUSTER SETSLOT <slot> STABLE 取消对槽 slot 的导入（import）或者迁移（migrate）

//键 (key)
CLUSTER KEYSLOT <key> 计算键 key 应该被放置在哪个槽上
CLUSTER COUNTKEYSINSLOT <slot> 返回槽 slot 目前包含的键值对数量
CLUSTER GETKEYSINSLOT <slot> <count> 返回 count 个 slot 槽中的键
```

## Tests
```sh
SET spring:string value
# Hash
HMSET spring:hash field1 "Hello" field2 "World"
HGET spring:hash field1
# List
lpush spring:list redis mongodb
lrange spring:list 0 10
# Set
sadd spring:set redis mongodb
smembers spring:set
# Zset
zadd spring:zset 0 redis
zadd spring:zset 10 mongodb
ZRANGEBYSCORE spring:zset 0 1000
# SCAN
scan 0 MATCH spring:* COUNT 100
hscan spring:hash 0 MATCH field* COUNT 100
sscan spring:set 0 MATCH re* COUNT 100
type spring:hash
```

## References
- [docker-compose 搭建 Redis Sentinel 测试环境](https://www.cnblogs.com/leffss/p/12082361.html)
- [docker-compose 搭建 redis集群](https://www.jianshu.com/p/ce14357cf0b4)
- [Docker Compose 搭建 Redis Cluster 集群环境](https://www.cnblogs.com/mrhelloworld/p/docker14.html)
- [Redis Docker](https://hub.docker.com/_/redis)
- [Redis SCAN 命令](https://redis.com.cn/commands/scan.html)