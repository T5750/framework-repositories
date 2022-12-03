## nginx Lua Web HTTP service

### Architecture
![nginxLuaWeb-Architecture-min](https://s0.wailian.download/2019/09/19/nginxLuaWeb-Architecture-min.png)

### Redis+Twemproxy
#### Redis Settings
`redis_6660.conf/redis_6661.conf`
```
#分别为6660 6661
port 6660
#进程ID 分别改为redis_6660.pid redis_6661.pid
pidfile "/var/run/redis_6660.pid"
#设置内存大小，根据实际情况设置，此处测试仅设置20mb
maxmemory 20mb
#内存不足时，按照过期时间进行LRU删除
maxmemory-policy volatile-lru
#Redis的过期算法不是精确的而是通过采样来算的，默认采样为3个，此处我们改成10
maxmemory-samples 10
#不进行RDB持久化
save ""
daemonize yes
bind 192.168.1.110
appendonly yes
appendfilename "appendonly_6661.aof"
```

#### Twemproxy Settings
`vim /usr/servers/twemproxy-0.4.1/conf/nutcracker.yml`
```
server1:
  listen: 192.168.1.110:1111
  hash: fnv1a_64
  distribution: ketama
  redis: true
  timeout: 1000
  servers:
   - 192.168.1.110:6660:1 server1
   - 192.168.1.110:6661:1 server2
```
```
nohup /usr/servers/redis-5.0.5/src/redis-server /usr/servers/redis-5.0.5/redis_6660.conf &
nohup /usr/servers/redis-5.0.5/src/redis-server /usr/servers/redis-5.0.5/redis_6661.conf &
/usr/servers/twemproxy-0.4.1/scripts/nutcracker.sh start
ps aux | grep -e redis -e nutcracker
/usr/servers/redis-5.0.5/src/redis-cli -h 192.168.1.110 -p 6660
/usr/servers/redis-5.0.5/src/redis-cli -h 192.168.1.110 -p 1111
```

### MySQL+Atlas
#### MySQL
`nginx-lua-web.sql`

#### Atlas Installation
[Atlas文档](https://github.com/Qihoo360/Atlas/blob/master/README_ZH.md)
```
cd /usr/servers/software
wget https://github.com/Qihoo360/Atlas/releases/download/2.2.1/Atlas-2.2.1.el6.x86_64.rpm
rpm -ivh Atlas-2.2.1.el6.x86_64.rpm
```

#### Atlas Configuration
`vi /usr/local/mysql-proxy/conf/test.cnf`
```
[mysql-proxy]
#Atlas代理的主库，多个之间逗号分隔
proxy-backend-addresses = 192.168.1.100:3306
#Atlas代理的从库，多个之间逗号分隔，格式ip:port@weight，权重默认1
#proxy-read-only-backend-addresses = 192.168.1.100:3306,192.168.1.100:3306
#用户名/密码，密码使用/usr/local/mysql-proxy/bin/encrypt 123456加密
pwds = root:/iZxz+0GRoA=
#后端进程运行
daemon = true
#开启monitor进程，当worker进程挂了自动重启
keepalive = true
#工作线程数，对Atlas的性能有很大影响，可根据情况适当设置
event-threads = 64
#日志级别
log-level = message
#日志存放的路径
log-path = /usr/local/mysql-proxy/log
#实例名称，用于同一台机器上多个Atlas实例间的区分
instance = test
#监听的ip和port
proxy-address = 0.0.0.0:1234
#监听的管理接口的ip和port
admin-address = 0.0.0.0:2345
#管理接口的用户名
admin-username = admin
#管理接口的密码
admin-password = 123456
#分表逻辑
tables = nginx-lua-web.ad.sku_id.2
#默认字符集
charset = utf8
```

#### Running Atlas
```
/usr/local/mysql-proxy/bin/mysql-proxyd test start
/usr/local/mysql-proxy/bin/mysql-proxyd test restart
/usr/local/mysql-proxy/bin/mysql-proxyd test stop
```

#### Atlas Admin
```
yum install mysql
mysql -h192.168.1.110 -P2345 -uadmin -p123456
select * from help;
```

#### Atlas Proxy
```
mysql -h192.168.1.110 -P1234 -uroot -p123456
use nginx-lua-web;
insert into ad values(1, '测试1');
insert into ad values(2, '测试2');
insert into ad values(3, '测试3');
select * from ad where sku_id=1;
select * from ad where sku_id=2;
#通过如下sql可以看到实际的分表结果
select * from ad_0;
select * from ad_1;
```

### Java+Tomcat
#### Java
```
vim ~/.bashrc
export JAVA_HOME=/usr/local/jdk1.8.0_201
export PATH=$JAVA_HOME/bin:$JAVA_HOME/jre/bin:$PATH
export CLASSPATH=$CLASSPATH:.:$JAVA_HOME/lib:$JAVA_HOME/jre/lib
source ~/.bashrc
```

#### Tomcat
```
cd /usr/local/apache-tomcat-8.5.43/
rm -rf /usr/local/apache-tomcat-8.5.43/webapps/*
cd /usr/local/apache-tomcat-8.5.43/conf/Catalina/localhost/
vim ROOT.xml
<Context path="" docBase="/usr/servers/templates/default"></Context>
```
```
/usr/local/apache-tomcat-8.5.43/bin/startup.sh
/usr/local/apache-tomcat-8.5.43/bin/shutdown.sh
```
http://192.168.1.110:8080/index.html
```
#变更目录结构
cd /usr/servers/
cp -avx /usr/local/apache-tomcat-8.5.43/ tomcat-8-foo
#此处创建两个tomcat实例
cp -avx /usr/local/apache-tomcat-8.5.43/ tomcat-8-bar
vim /usr/servers/tomcat-8-bar/conf/server.xml
#如下端口进行变更
8080--->8090
8005--->8006
```
```
/usr/servers/tomcat-8-foo/bin/startup.sh
/usr/servers/tomcat-8-bar/bin/startup.sh
```
- http://192.168.1.110:8080/index.html
- http://192.168.1.110:8090/index.html

```
cd /usr/servers/templates/default/
unzip nginx-1.0.war
```
- http://192.168.1.110:8080/ad?id=1
- http://192.168.1.110:8090/ad?id=1

#### nginx Settings
`vi /usr/servers/nginx/conf/nginx_lua_web.conf`: `/backend/(.*)`

[upstream配置](http://nginx.org/cn/docs/http/ngx_http_upstream_module.html)
- `server`：指定上游到的服务器
- `weight`：权重，权重可以认为负载均衡的比例
- `fail_timeout+max_fails`：在指定时间内失败多少次认为服务器不可用，通过`proxy_next_upstream`来判断是否失败
- `check`：ngx_http_upstream_check_module模块，上游服务器的健康检查
- `interval`：发送心跳包的时间间隔
- `rise`：连续成功rise次数则认为服务器up
- `fall`：连续失败fall次则认为服务器down
- `timeout`：上游服务器请求超时时间
- `type`：心跳检测类型（比如此处使用tcp）
- `keepalive`：用来支持upstream server http keepalive特性(需要上游服务器支持，比如tomcat)。默认的负载均衡算法是round-robin，还可以根据ip、url等做hash来做负载均衡

[Tomcat keepalive配置](http://tomcat.apache.org/tomcat-7.0-doc/config/http.html)
- `maxKeepAliveRequests`：默认100
- `keepAliveTimeout`：默认等于`connectionTimeout`，默认60秒

[location proxy配置](http://nginx.org/cn/docs/http/ngx_http_proxy_module.html)
- `rewrite`：将当前请求的url重写，如我们请求时是/backend/ad，则重写后是/ad
- `proxy_pass`：将整个请求转发到上游服务器
- `proxy_next_upstream`：什么情况认为当前upstream server失败，需要next upstream，默认是连接失败/超时，负载均衡参数
- `proxy_pass_request_headers`：之前已经介绍过了，两个原因：
    1. 假设上游服务器不需要请求头则没必要传输请求头
    1. ngx.location.capture时防止gzip乱码（也可以使用more_clear_input_headers配置）
- `keepalive_timeout`：keepalive超时设置
- `keepalive_requests`：长连接数量。此处的keepalive（别人访问该location时的长连接）和upstream keepalive（nginx与上游服务器的长连接）是不一样的；注意，如果服务是面向客户的，而且是单个动态内容就没必要使用长连接了

```
vi /usr/servers/nginx/conf/nginx.conf
include nginx_lua_web.conf;
```
http://192.168.1.110:8070/backend/ad?id=1

### nginx+Lua
- `nginx_lua_web.conf`: `^/ad/(\d+)$`
- `ad.lua`
- http://192.168.1.110:8070/ad/1
```
nginx -s reload
tail -fn 99 /usr/servers/nginx/logs/error.log
redis not found content, back to http, id : 2
```

### Tips
```
vi ~/.bashrc
export REDIS_HOME=/usr/servers/redis-5.0.5
export PATH=${JAVA_HOME}/bin:${NGINX_HOME}/sbin:${REDIS_HOME}/src:$PATH
```

Module | HTTP Port | Command or Url | Start
---|---|---|---
Redis | 6660/6661 | redis-cli -h 192.168.1.110 -p 6660 | nohup redis-server /usr/servers/redis-5.0.5/redis_6660.conf &
Twemproxy | 1111 | redis-cli -h 192.168.1.110 -p 1111 | /usr/servers/twemproxy-0.4.1/scripts/nutcracker.sh start
MySQL | 3306 |  | 
Atlas Proxy | 1234 | mysql -h192.168.1.110 -P1234 -uroot -p123456 | /usr/local/mysql-proxy/bin/mysql-proxyd test start
Atlas Admin | 2345 | mysql -h192.168.1.110 -P2345 -uadmin -p123456 | 
tomcat-8-foo | 8080 | http://192.168.1.110:8080/ad?id=1 | /usr/servers/tomcat-8-foo/bin/startup.sh
tomcat-8-bar | 8090 | http://192.168.1.110:8090/ad?id=1 | /usr/servers/tomcat-8-bar/bin/startup.sh
nginx | 8070 | http://192.168.1.110:8070/ad/1 | nginx

### References
- [第六章 Web开发实战1——HTTP服务](https://www.iteye.com/blog/jinnianshilongnian-2188113)