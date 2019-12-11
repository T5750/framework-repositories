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
    - 其它软件负载均衡，如：LVS（Linux Virtual Server）、haproxy等

## 1.3 nginx优点
- nginx可以在大多数Unix like OS上编译运行，并有Windows移植版。一般情况下，对于新建站点，建议使用最新稳定版作为生产版本，已有站点的升级急迫性不高。nginx的源代码使用2-clause BSD-like license。
- nginx是一个很强大的高性能Web和反向代理服务器，它具有很多非常优越的特性。
- 在高连接并发的情况下，nginx是Apache服务器不错的替代品。nginx在美国是做虚拟主机生意的老板们经常选择的软件平台之一。能够支持高达50,000个并发连接数的响应，感谢nginx为我们选择了epoll and kqueue作为开发模型。

## 2.1 nginx环境搭建
1. wget下载：[http://nginx.org/download/nginx-1.6.2.tar.gz](http://nginx.org/download/nginx-1.6.2.tar.gz)
1. 进行安装：`tar -zxvf nginx-1.6.2.tar.gz`
1. 下载锁需要的依赖库文件：
    - `yum install pcre`
	- `yum install pcre-devel`
	- `yum install zlib`
	- `yum install zlib-devel`
1. 进行configure配置：`cd nginx-1.6.2 && ./configure --prefix=/usr/local/nginx`查看是否报错
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
}
```

### 2.2.2 nginx日志管理
nginx访问日志放在`logs/host.access.log`下，并使用main格式（可自定义格式）。对于main格式如下定义：
```
    #日志文件输出格式这个位置相于全局设置
    log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
                      '$status $body_bytes_sent "$http_referer" '
                      '"$http_user_agent" "$http_x_forwarded_for"';
```
查看日志内容命令：`tail -n 100 -f nginx/logs/access.log`

现实中对nginx日志的分析非常的重要，通常需要运维去对nginx的日志进行切割和分析处理。比如实现一个定时任务，去处理nginx日志等。
1. 分析如何去实现日志切分，编写shell脚本。
1. 定时任务对脚本进行调度：`crontab -e`
    ```
    */1 * * * * sh /usr/local/nginx/sbin/log.sh
    ```

### 2.2.3 location语法
location语法：表示uri方式定位。基础语法有三种：
- `location = pattern {}`精准匹配
- `location pattern {}`一般匹配
- `location ~ pattern {}`正则匹配

### 2.2.4 nginx语法
- `if(条件为：=~~*)`、`return`、`break`、`rewrite`
- `-f`是否为文件、`-d`是否为目录、`-e`是否存在

nginx可以对数据进行压缩，对一些图片、html、css、js等文件进行缓存，从而实现动静分离等优化功能，在网站做优化时非常有用

### 2.2.5 nginx反向代理proxy与负载均衡upstream
- 配置反向代理proxy：`proxy_pass url地址`
- 配置负载均衡upstream：`upstream`
- 官方配置：[http://nginx.org/en/docs](http://nginx.org/en/docs)

注意：反向代理后，获取客户端ip地址为nginx服务器地址，这里需要nginx进行forward，设置真实的ip地址：
```
#设置客户端真实ip地址
proxy_set_header X-real-ip $remote_addr;
```

## 3.1 nginx+Keepalived实现高可用
集群规划清单

虚拟机 | IP | 说明
-----|---|-----
Keepalived+nginx[Master] | 192.168.100.165 | nginx Server 01
Keepalived+nginx[Backup]+Tomcat164 | 192.168.100.164 | nginx Server 02 + Tomcat Web Server164
Tomcat163 | 192.168.100.163 | Tomcat Web Server163
VIP | 192.168.100.166 | 虚拟漂移IP

### 3.1.1
下载Keepalived地址：[http://www.keepalived.org/download.html](http://www.keepalived.org/download.html)。解压安装：
```
tar -zxvf keepalived-1.2.24.tar.gz -C /usr/local/
yum install -y openssl openssl-devel（需要安装一个软件包）
cd keepalived-1.2.24/ && ./configure --prefix=/usr/local/keepalived
make && make install
```

### 3.1.2
将Keepalived安装成Linux系统服务，因为没有使用Keepalived的默认安装路径（默认路径：`/usr/local`），安装完成后，需要做一些修改工作：
- 首先，创建文件夹，将Keepalived配置文件进行复制：
	```
	mkdir /etc/keepalived
	cp /usr/local/keepalived/etc/keepalived/keepalived.conf /etc/keepalived/
	```
- 然后，复制Keepalived脚本文件：
	```
	cp /usr/local/keepalived/etc/rc.d/init.d/keepalived /etc/init.d/
	cp /usr/local/keepalived/etc/sysconfig/keepalived /etc/sysconfig/
	ln -s /usr/local/sbin/keepalived /usr/sbin/
	ln -s /usr/local/keepalived/sbin/keepalived /sbin/
	```
- 可以设置开机启动：`chkconfig keepalived on`

### 3.1.3
对配置文件进行修改：`vim /etc/keepalived/keepalived.conf`
1. master
	```
	! Configuration File for keepalived
	global_defs {
	   router_id LVS_DEVEL ##标识节点的字符串，通常为hostname
	}
	## keepalived会定时执行脚本并且对脚本的执行结果进行分析，动态调整vrrp_instance的优先级。这里的权重weight是与下面的优先级priority有关，如果执行了一次检查脚本成功，则权重会-20，也就是由100-20变成了80，Master的优先级为80就低于了Backup的优先级90，那么会进行自动的主备切换。
	如果脚本执行结果为0并且weight配置的值大于0，则优先级会相应增加。
	如果脚本执行结果不为0并且weight配置的值小于0，则优先级会相应减少。
	vrrp_script chk_nginx {
		script "/etc/keepalived/nginx_check.sh" ##执行脚本位置
		interval 2 ##检测时间间隔
		weight -20 ## 如果条件成立则权重减20（-20）
	}
	## 定义虚拟路由 VI_1为自定义标识。
	vrrp_instance VI_1 {
	state MASTER   ## 主节点为MASTER，备份节点为BACKUP
		## 绑定虚拟IP的网络接口（网卡），与本机IP地址所在的网络接口相同（我这里是enp0s3）
		interface enp0s3  #ifconfig确定
		virtual_router_id 51  ## 虚拟路由ID号
		mcast_src_ip 192.168.100.165  ## 本机ip地址
		priority 100  ##优先级配置（0-254的值）
		nopreempt  ## 
		advert_int 1 ## 组播信息发送间隔，两个节点必须配置一致，默认1s
		authentication {  
			auth_type PASS
			auth_pass 1111 ## 真实生产环境下对密码进行匹配
		}
		track_script {
			chk_nginx
		}
		virtual_ipaddress {
			192.168.100.166 ## 虚拟ip(VIP)，可以指定多个
		}
	}
	```
1. backup
	```
	! Configuration File for keepalived
	global_defs {
	   router_id LVS_DEVEL
	}
	vrrp_script chk_nginx {
		script "/etc/keepalived/nginx_check.sh"
		interval 2
		weight -20
	}
	vrrp_instance VI_1 {
		state BACKUP
		interface enp0s3
		virtual_router_id 51
		mcast_src_ip 192.168.100.164
		priority 90 ##优先级配置
		advert_int 1
		authentication {
			auth_type PASS
			auth_pass 1111
		}
		track_script {
			chk_nginx
		}
		virtual_ipaddress {
			192.168.100.166
		}
	}
	```
1. `nginx_check.sh`脚本：
	```
	#!/bin/bash
	A=`ps -C nginx --no-header |wc -l`
	if [ $A -eq 0 ];then
		/usr/local/nginx/sbin/nginx
		sleep 2
		if [ `ps -C nginx --no-header |wc -l` -eq 0 ];then
			killall keepalived
		fi
	fi
	```
1. 把master的Keepalived配置文件copy到master机器（165）的`/etc/keepalived/`文件夹下，在把backup的Keepalived配置文件copy到backup机器（164）的`/etc/keepalived/`文件夹下。最后，把`nginx_check.sh`脚本分别copy到两台机器的`/etc/keepalived/`文件夹下。
1. `nginx_check.sh`脚本授权。赋予可执行权限：`chmod +x /etc/keepalived/nginx_check.sh`
1. 启动2台机器的nginx后，启动两台机器的Keepalived
	```
	/usr/local/nginx/sbin/nginx
	service keepalived start
	service keepalived status
	ps -ef | grep nginx 
	ps -ef | grep keepalived
	```
    - 可以进行测试，首先看一下两台机器的`ip a`命令下都会出现一个虚拟ip，我们可以停掉一个机器的Keepalived，然后测试，命令：`service keepalived stop`。结果发现当前停掉的机器已经不可用，Keepalived会自动切换到另一台机器上。
1. 可以测试在nginx出现问题的情况下，实现切换，这时只需要把nginx的配置文件进行修改，让其变得不可用，然后强杀掉nginx进程即可，发现也会实现自动切换服务器节点。

## Results
设置host：`127.0.0.1 bm.com`
- [http://bm.com:1234/](http://bm.com:1234/)
- [http://bm.com:1234/test.html](http://bm.com:1234/test.html)
- [http://bm.com:1234/firefox.html](http://bm.com:1234/firefox.html)
- [http://bm.com:1234/goods-123.html](http://bm.com:1234/goods-123.html)
- [http://bm.com/test.jsp](http://bm.com/test.jsp)
- [http://192.168.100.166/test.jsp](http://192.168.100.166/test.jsp)

## Tips
1. 在Firefox的地址栏上输入`about:config`回车
1. 找到`browser.cache.check_doc_frequency`选项，双击将`3`改成`1`保存即可。选项含义：
    - 0：Once per session，每个进程一次，每次启动Firefox时检查
    - 1：Each time，开发人员强烈建议开这个，每次访问此页时检查
    - 2：Never
    - 3：When appropriate/automatically

telnet测试端口号：`telnet bm.com 1234`

## References
- 尚学堂互联网架构师课程