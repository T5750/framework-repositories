# nginx笔记

## 1.1 nginx概述
- nginx是一款轻量级的Web服务器/反向代理服务器及电子邮件（IMAP/POP3）代理服务器，并在一个BSD-like协议下发行。
- 由俄罗斯的程序设计师Igor Sysoev所开发，供俄国大型的入口网站及搜索引擎Rambler（俄文：Pam6nep）使用。
- 其特点是占有内存少，并发能力强，事实上nginx的并发能力确实在同类型的网页服务器中表现较好，中国大陆使用nginx网站用户有：百度、新浪、网易、腾讯等。

## 1.2 负载均衡策略
1. 使用硬件负载均衡策略实现，如使用F5、Array等负载划衡器
1. 使用软件进行负载均衡：
    - 使用阿里云服务器均衡负载SLB
    - 使用nginx+Keepalived
    - 其他软件负载均衡，如：LVS（Linux Virtual Server）、haproxy等

## 1.3 nginx优点
- nginx可以在大多数Unix like OS上编译运行，并有Windows移植版。一般情况下，对于新建站点，建议使用最新稳定版作为生产版本，已有站点的升级急迫性不高。nginx的源代码使用2-clause BSD-like license。
- nginx是一个很强大的高性能Web和反向代理服务器，它具有很多非常优越的特性。
- 在高连接并发的情况下，nginx是Apache服务器不错的替代品。nginx在美国是做虚拟主机生意的老板们经常选择的软件平台之一。能够支持高达50,000个并发连接数的响应，感谢nginx为我们选择了epoll andkqueue作为开发模型。

## 2.1 nginx环境搭建
1. wget下载：[http://nginx.org/download/nginx-1.6.2.tar.gz](http://nginx.org/download/nginx-1.6.2.tar.gz)
1. 进行安装：`tar -zxvf nginx-1.6.2.tar.gz`
1. 下载锁需要的依赖库文件：
    - `yum install pcre`
	- `yum install pcre-devel`
	- `yum install zlib`
	- `yum install zlib-devel`
1. 进行configure配置：`cd nginx-1.6.2 && ./configure --prefixs=/usr/local/nginx`查看是否报错
1. 编译安装`make && make install`
1. 启动nginx：
    - `cd /usr/local/nginx`目录下：conf配置文件、html网页文件、logs日志文件、sbin主要二进制程序
    - 启动命令：`/usr/local/nginx/sbin/nginx`关闭（`-s stop`）重启（`-s reload`）
1. 成功：查看是否启动（`netstat -ano|grep 80`）失败：可能为80端口被占用等，浏览器访问地址：[http://127.0.0.1:80](http://127.0.0.1:80)（看到欢迎页面即可）

## 2.2 nginx配置文件说明

### 2.2.1 nginx虚拟主机配置
```
#虚拟主机配置：
server {
    listen 1234;
    server_name bm.com;
    location / {
        root bm.com;
        index index.html;
}
```
### 2.2.2 nginx日志管理
nginx访问日志放在`logs/host.access.log`下，并且使用main格式（还可以自定义格式）

对于main格式如下定义：
```
#日志文件输出格式这个位置相于全局设置
#log_format main '$remote_addr - $remote_user [$time_local] "$request"'
#    '$status $body_bytes_sent "$http_referer"'
#    "$http_user_agent" "$http_x_forwarded_for'";
```
查看日志内容命令：`tail -n 100 -f nginx/logs/access.log`

我们在日常生活中，对nginx日志的分析非常的重要，通常需要运维去对nginx的日志进行切割和分析处理。比如实现一个定时任务，去处理nginx日志等。

第一步：分析如何去实现日志切分，编写shell脚本。
第二步：定时任务对脚本进行调度：`crontab -e`
```
*/1****sh /usr/local/nginx/sbinlog.sh
```

### 2.2.3 location语法
location语法：表示uri方式定位。基础语法有三种：
- `location = pattern {}`精准匹配
- `location pattern {}`一般匹配
- `location ~ pattern {}`正则匹配

### 2.2.4 nginx语法
- `if(条件为：=~~*)`、`return`、`break`、`rewrite`
- `-f`是否为文件、`-d`是否为目录、`-e`是否存在

nginx可以对数据进行压缩，对一些图片、html、cass、js等文件进行缓存，从而实现动静分离等优化功能，在网站做优化时非常有用

### 2.2.5 nginx反向代理proxy与负载均衡upstream
- 配置反向代理proxy：proxy_pass url地址
- 配置负载均衡upstream：upstream
- 官方配置：[http://nginx.org/en/docs](http://nginx.org/en/docs)

注意：反向代理之后获取客户端IP地址为nginx服务器地址，这里需要nginx进行forward，设置真实的ip地址：
```
#设置客户端真实ip地址
proxy_set_header X-real-ip $remote_addr;
```


## References
- nginx