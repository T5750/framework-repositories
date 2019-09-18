## nginx Lua Redis/SSDB+Twemproxy

### Redis
```
cd /usr/servers/
$ wget http://download.redis.io/releases/redis-5.0.5.tar.gz
$ tar xzf redis-5.0.5.tar.gz
$ cd redis-5.0.5
$ make
nohup /usr/servers/redis-5.0.5/src/redis-server /usr/servers/redis-5.0.5/redis.conf &
ps -aux | grep redis
/usr/servers/redis-5.0.5/src/redis-cli -p 6379
```

#### Redis Settings
```
#设置Redis占用100mb的大小
maxmemory 100mb
#如果内存满了就需要按照如相应算法进行删除过期的/最老的
#volatile-lru 根据LRU算法移除设置了过期的key
#allkeys-lru 根据LRU算法移除任何key(包含那些未设置过期时间的key)
#volatile-random/allkeys->random 使用随机算法而不是LRU进行删除
#volatile-ttl 根据Time-To-Live移除即将过期的key
#noeviction 永不过期，而是报错
maxmemory-policy volatile-lru
#Redis并不是真正的LRU/TTL，而是基于采样进行移除的，即如采样10个数据移除其中最老的/即将过期的
maxmemory-samples 10
```

### SSDB
```
yum install gcc-c++
yum install autoconf automake
cd /usr/servers
wget https://github.com/ideawu/ssdb/archive/1.9.4.tar.gz
tar -xvf 1.9.4.tar.gz
cd ssdb-1.9.4
make
nohup /usr/servers/ssdb-1.9.4/ssdb-server -d /usr/servers/ssdb-1.9.4/ssdb.conf &
ps -aux | grep ssdb
/usr/servers/ssdb-1.9.4/tools/ssdb-cli -p 8888
/usr/servers/redis-5.0.5/src/redis-cli -p 8888
```

### Twemproxy
```
yum install libtool
cd /usr/servers
wget https://github.com/twitter/twemproxy/archive/v0.4.1.tar.gz
tar -xvf v0.4.1.tar.gz
cd twemproxy-0.4.1/
autoreconf -fvi
./configure && make
vim /usr/servers/twemproxy-0.4.1/conf/nutcracker.yml
```
```
server1:
  listen: 127.0.0.1:1111
  hash: fnv1a_64
  distribution: ketama
  timeout: 1000
  redis: true
  servers:
   - 127.0.0.1:6379:1
```
```
/usr/servers/twemproxy-0.4.1/src/nutcracker -d -c /usr/servers/twemproxy-0.4.1/conf/nutcracker.yml -o /usr/servers/twemproxy-0.4.1/redisproxy.log
ps aux | grep nutcracker
/usr/servers/redis-5.0.5/src/redis-cli -p 1111
```

#### Twemproxy Settings
- `server1`：是给当前分片配置起的名字，一个配置文件可以有多个分片配置
- `listen`：监听的ip和端口
- `hash`：散列算法
- `distribution`：分片算法，比如一致性Hash/取模
- `timeout`：连接后端Redis或接收响应的超时时间
- `redis`：是否是redis代理，如果是false则是memcached代理
- `servers`：代理的服务器列表，该列表会使用distribution配置的分片算法进行分片

#### hash算法
- one_at_a_time
- md5
- crc16
- crc32 (crc32 implementation compatible with libmemcached)
- crc32a (correct crc32 implementation as per the spec)
- fnv1_64
- fnv1a_64
- fnv1_32
- fnv1a_32
- hsieh
- murmur
- jenkins

#### 分片算法
- ketama(一致性Hash算法)
- modula(取模)
- random(随机算法)

#### HashTag
比如一个商品有：商品基本信息(`p:id:`)、商品介绍(`d:id:`)、颜色尺码(`c:id:`)等，假设存储时不采用HashTag将会导致这些数据不会存储到一个分片，而是分散到多个分片，这样获取时将需要从多个分片获取数据进行合并，无法进行mget；那么如果有了HashTag，那么可以使用“::”中间的数据做分片逻辑，这样id一样的将会分到一个分片

#### 一致性Hash与服务器宕机
如果把Redis服务器作为缓存服务器并使用一致性Hash进行分片，当有服务器宕机时需要自动从一致性Hash环上摘掉，或者其上线后自动加上，此时就需要如下配置：
```
#是否在节点故障无法响应时自动摘除该节点，如果作为存储需要设置为为false
auto_eject_hosts: true
#重试时间（毫秒），重新连接一个临时摘掉的故障节点的间隔，如果判断节点正常会自动加到一致性Hash环上
server_retry_timeout: 30000
#节点故障无法响应多少次从一致性Hash环临时摘掉它，默认是2
server_failure_limit: 2
```

#### 支持的Redis命令
不是所有Redis命令都支持，请参考https://github.com/twitter/twemproxy/blob/master/notes/redis.md

```
vi nutcracker.sh
chmod u+x /usr/servers/twemproxy-0.4.1/scripts/nutcracker.sh
/usr/servers/twemproxy-0.4.1/scripts/nutcracker.sh
```

扩容最简单的办法是：
1. 创建新的集群
2. 双写两个集群
3. 把数据从老集群迁移到新集群（不存在才设置值，防止覆盖新的值）
4. 复制速度要根据实际情况调整，不能影响老集群的性能
5. 切换到新集群即可，如果使用Twemproxy代理层的话，可以做到迁移对读的应用透明

### Tips
Autoconf version 2.64 or higher is required
```
/usr/bin/autoconf -V
rpm -e --nodeps autoconf-2.63
wget ftp://ftp.gnu.org/gnu/autoconf/autoconf-2.69.tar.gz
tar zxvf autoconf-2.69.tar.gz
cd autoconf-2.69
./configure --prefix=/usr/
make && make install
```

#### SSDB Install
```
# 启动主库, 此命令会阻塞住命令行
./ssdb-server ssdb.conf
# 或者启动为后台进程(不阻塞命令行)
./ssdb-server -d ssdb.conf
# 停止 ssdb-server
./ssdb-server ssdb.conf -s stop
# 对于旧版本
kill `cat ./var/ssdb.pid`
# 重启
./ssdb-server ssdb.conf -s restart
```

### References
- [第三章 Redis/SSDB+Twemproxy安装与使用](https://www.iteye.com/blog/jinnianshilongnian-2186787)
- [SSDB Documentation](http://ssdb.io/zh_cn/)