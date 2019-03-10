# FastDFS集群安装手册
- 192.168.1.110 tracker-group1
   - 192.168.1.112 storage-group1-1
   - 192.168.1.113 storage-group1-2
- 192.168.1.111 tracker-group2
   - 192.168.1.114 storage-group2-1
   - 192.168.1.115 storage-group2-2

## 安装相关软件，6台机器都需要安装

### 安装gcc
`yum install make cmake gcc gcc-c++`

### 安装libfastcommon
1. 上传`libfastcommon-master.zip`到`/usr/local/software`下，解压`libfastcommon-master.zip`：`unzip libfastcommon-master.zip -d /usr/local/fast/`
1. 进入目录：`cd /usr/local/fast/libfastcommon-master/`，进行编译和安装：
    - `./make.sh`
    - `./make.sh install`
1. 创建软连接。FastDFS主程序设置的目录为`/usr/local/lib/`，所以需要创建`/usr/lib64/`下的一些核心执行程序的软连接文件。
```
mkdir /usr/local/lib/
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so
```

### 安装FastDFS
1. 进入到`cd /usr/local/software`下，解压`FastDFS_v5.05.tar.gz`文件
    - `tar -zxvf FastDFS_v5.05.tar.gz -C /usr/local/fast/`
2. 安装编译
    - 命令：`cd /usr/local/fast/FastDFS/`
    - 编译命令：`./make.sh`
    - 安装命令：`./make.sh install`
3. 因为FastDFS服务脚本设置的bin目录为`/usr/local/bin/`下，但是实际安装在了`/usr/bin/`下面。所以需要修改FastDFS配置文件中的路径，也就是需要修改2个配置文件：
    - 命令：`vim /etc/init.d/fdfs_storaged`
    - 进行全局替换命令：`%s+/usr/local/bin+/usr/bin`
    - 命令：`vim /etc/init.d/fdfs_trackerd`
    - 进行全局替换命令：`%s+/usr/local/bin+/usr/bin`

## 两台节点配置跟踪器（192.168.1.110，192.168.1.111）
1. 进入`cd /etc/fdfs/`目录配置跟踪器文件（注意是110和111节点），把`tracker.conf.sample`文件进行cope一份：去修改`tracker.conf`文件
2. 编辑跟踪器文件：`vim /etc/fdfs/tracker.conf`
    - 修改配置文件里的`base_path`即可：`base_path=/fastdfs/tracker`
3. 创建`/fastdfs/tracker`文件夹：`mkdir -p /fastdfs/tracker`
4. 启动跟踪器
    - 目录命令：`cd /fastdfs/tracker/ && ll`
    - 启动tracker命令：`/etc/init.d/fdfs_trackerd start`
    - 查看进程命令：`ps -el | grep fdfs`或`netstat -tunpl | grep fdfs`
    - 查看启动日志：`tail -f -n 100 /fastdfs/tracker/logs/trackerd.log`
    - 停止tracker命令：`/etc/init.d/fdfs_trackerd stop`

## 四台机器配置存储节点（192.168.1.112，192.168.1.113，192.168.1.114，192.168.1.115）
1. 进入文件目录：`cd /etc/fdfs/`，进行copy storage文件一份
    - 命令：`cp storage.conf.sample storage.conf`
2. 修改storage.conf文件：`vim /etc/fdfs/storage.conf`
    - 注意：112和113为一组（group1），114和115为一组（group2），修改内容：
	```
	disabled=false #启用配置文件
	group_name=group1 #组名
	port=23000 #storage端口号，同一个组的storage端口号必须相同
	base_path=/fastdfs/storage #设置storage的日志目录
	store_path_count=1 #存储路径个数，需要和store_path个数匹配
	store_path0=/fastdfs/storage #设置存储路径
	tracker_server=192.168.1.110:22122 #tracker服务器的IP和端口
	tracker_server=192.168.1.111:22122 #多个tracker直接添加多条配置
	http.server_port=8888 #设置http端口号
	```
3. 创建存储目录：`mkdir -p /fastdfs/storage`
4. 启动storage
    - 启动：`/etc/init.d/fdfs_storaged start`
    - 日志：`tail -f -n 100 /fastdfs/storage/logs/storaged.log`
5. 首先，一定要启动110和111跟踪器节点。然后，分别启动第一组存储节点，112和113节点是相互知道对方存在的，也知道哪个节点是leader
	- 112日志：
	```
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.111:22122, as a tracker client, my ip is 192.168.1.112
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.110:22122, as a tracker client, my ip is 192.168.1.112
	INFO - file: tracker_client_thread.c, line: 1235, tracker server 192.168.1.111:22122, set tracker leader: 192.168.1.111:22122
	INFO - file: storage_sync.c, line: 2698, successfully connect to storage server 192.168.1.113:23000
	```
	- 113日志：
	```
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.111:22122, as a tracker client, my ip is 192.168.1.113
    INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.110:22122, as a tracker client, my ip is 192.168.1.113
    INFO - file: tracker_client_thread.c, line: 1235, tracker server 192.168.1.111:22122, set tracker leader: 192.168.1.111:22122
    INFO - file: storage_sync.c, line: 2698, successfully connect to storage server 192.168.1.112:23000
    ```
    - 114日志：
	```
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.111:22122, as a tracker client, my ip is 192.168.1.114
    INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.110:22122, as a tracker client, my ip is 192.168.1.114
    INFO - file: tracker_client_thread.c, line: 1235, tracker server 192.168.1.111:22122, set tracker leader: 192.168.1.111:22122
    INFO - file: storage_sync.c, line: 2698, successfully connect to storage server 192.168.1.115:23000
    ```
    - 115日志：
	```
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.110:22122, as a tracker client, my ip is 192.168.1.115
    INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.111:22122, as a tracker client, my ip is 192.168.1.115
    INFO - file: tracker_client_thread.c, line: 1235, tracker server 192.168.1.111:22122, set tracker leader: 192.168.1.111:22122
    INFO - file: storage_sync.c, line: 2698, successfully connect to storage server 192.168.1.114:23000
    ```
6. 跟踪器节点有主从选举算法，假如跟踪器的leader节点挂了，那么，跟踪器从节点会切换为主节点
    - 把111节点跟踪器关闭：`/etc/init.d/fdfs_trackerd stop`
    - 其它存储节点日志发生变化：
	```
	INFO - file: tracker_client_thread.c, line: 1235, tracker server 192.168.1.110:22122, set tracker leader: 192.168.1.110:22122
	```
	- 说明已经实现了跟踪器自动切换功能。最后，再次开启111跟踪器节点，发现其它存储节点又会识别111跟踪器节点：
	```
	INFO - file: tracker_client_thread.c, line: 310, successfully connect to tracker server 192.168.1.111:22122, continuous fail count: 14, as a tracker client, my ip is 192.168.1.112
	```
7. 当所有的tracker和storage节点都启动成功后，可以在任意一个存储节点上查看存储集群的信息：`/usr/bin/fdfs_monitor /etc/fdfs/storage.conf`
	- 显示tracker主节点，显示group1和group2的存储详细信息
	```
	tracker server is 192.168.1.110:22122
    group count: 2
    Group 1:
    group name = group1
		Storage 1:
				id = 192.168.1.112
		Storage 2:
				id = 192.168.1.113
	Group 2:
    group name = group2
		Storage 1:
				id = 192.168.1.114
		Storage 2:
				id = 192.168.1.115
	......
	```

## 测试文件上传
`cd /usr/bin/ && ll | grep fdfs`
1. 任意一个跟踪器节点，进入`cd /etc/fdfs/`，copy一份`client.conf`文件：`cp client.conf.sample client.conf`
2. 编辑`client.conf`文件：`vim /etc/fdfs/client.conf`
    - 修改内容：
	```
	base_path=/fastdfs/tracker
	tracker_server=192.168.1.110:22122
	tracker_server=192.168.1.111:22122
	```
3. 进入存储文件夹`cd /fastdfs/storage/data/00/00`，没有任何数据文件
4. 使用`fdfs_upload_file`进行上传操作：
    - `/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/software/FastDFS_v5.05.tar.gz`
    - 返回一个`group1/M00/00/00/...`的ID，到group1下的目录可以看到上传的文件，而group2没有数据

## 4个存储节点配置nginx
1. 在存储节点上，安装`fastdfs-nginx-module_v1.16.tar.gz`包进行整合
    - 解压命令：`tar -zxvf /usr/local/software/fastdfs-nginx-module_v1.16.tar.gz -C /usr/local/fast/`
2. 在安装fastdfs-nginx-module之前，对其路径进行修改
    - 编辑配置文件：`vim /usr/local/fast/fastdfs-nginx-module/src/config`
    - 去掉local文件层次：`CORE_INCS="$CORE_INCS /usr/include/fastdfs /usr/include/fastcommon/"`
3. 4个存储节点安装nginx依赖库文件：
	```
	yum install pcre
	yum install pcre-devel
	yum install zlib
	yum install zlib-devel
	```
4. 解压并安装nginx，加入fastdfs-nginx-module
    - `tar -zxvf /usr/local/software/nginx-1.6.2.tar.gz -C /usr/local/`
    - 进入到nginx目录命令：`cd /usr/local/nginx-1.6.2/`
    - 加入模块命令：`./configure --add-module=/usr/local/fast/fastdfs-nginx-module/src/`
    - 编译命令：`make && make install`
5. 复制fastdfs-nginx-module中的配置文件，到`/etc/fdfs`目录
    - 复制命令：`cp /usr/local/fast/fastdfs-nginx-module/src/mod_fastdfs.conf /etc/fdfs/`
    - 修改命令：`vim /etc/fdfs/mod_fastdfs.conf`
    - 修改内容：比如连接超时时间、跟踪器路径配置、url的group配置
    - 注意：第一组和第二组节点修改内容，不同内容只有一个组名！
	```
	connect_timeout=10
	tracker_server=192.168.1.110:22122
	tracker_server=192.168.1.111:22122
	storage_server_port=23000
	group_name=group1	#第二组为group_name=group2
	url_have_group_name = true
	store_path0=/fastdfs/storage
	group_count = 2

	[group1]
	group_name=group1
	storage_server_port=23000
	store_path_count=1
	store_path0=/fastdfs/storage

	[group2]
	group_name=group2
	storage_server_port=23000
	store_path_count=1
	store_path0=/fastdfs/storage
	```
6. 复制FastDFS里的2个文件，到`/etc/fdfs`目录中
    - 目录命令：`cd /usr/local/fast/FastDFS/conf/`
    - Copy命令：`cp http.conf mime.types /etc/fdfs/`
7. 创建一个软连接，在`/fastdfs/storage`文件存储目录下创建软连接，将其链接到实际存放数据的目录
    - 命令：`ln -s /fastdfs/storage/data/ /fastdfs/storage/data/M00`
8. 修改nginx配置文件（所有节点都一致）`vim /usr/local/nginx/conf/nginx.conf`，修改配置内容：
	```
	listen 8888;
	server_name localhost;
	location ~/group([0-9])/M00 {
	    #alias /fastdfs/storage/data;
	    ngx_fastdfs_module;
	}
	```
    - 注意：nginx的端口要和`storage.conf`配置一致，也就是`http.server_port=8888`。另外storage有多个group，与nginx整合需要通配！
9. 最后检查防火墙，启动nginx服务
    - `/usr/local/nginx/sbin/nginx`（加`-s stop`为停止，加`-s reload`为重启）
10. nginx与FastDFS集成测试，通过跟踪器的Client上传文件，然后打开浏览器，可通过nginx访问FastDFS的文件
    - 上传文件：`/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/software/avatar.jpg`
    - 浏览器地址：http://192.168.1.114:8888/group2/M00/00/00/wKgBclxn9QiAL3Q_AAAZtkdii-k180.jpg
    - 浏览器地址：http://192.168.1.115:8888/group2/M00/00/00/wKgBclxn9QiAL3Q_AAAZtkdii-k180.jpg

## 2个跟踪器安装nginx
110和111节点提供反向代理服务，目的是使用统一的一个IP地址对外提供服务
1. 上传nginx缓存模块ngx_cache_purge-2.3.tar.gz，并进行解压：
    - `tar -zxvf /usr/local/software/ngx_cache_purge-2.3.tar.gz -C /usr/local/fast/`
2. 安装nginx依赖库文件：
	```
	yum install pcre
	yum install pcre-devel
	yum install zlib
	yum install zlib-devel
	```
3. 解压并安装nginx，加入缓存模块ngx_cache_purge
    - `tar -zxvf /usr/local/software/nginx-1.6.2.tar.gz -C /usr/local/`
    - 进入到nginx目录命令：`cd /usr/local/nginx-1.6.2/`
    - 加入模块命令：`./configure --add-module=/usr/local/fast/ngx_cache_purge-2.3`
    - 编译命令：`make && make install`
4. 配置nginx负载均衡和缓存（110和111节点配置一致）
    - `mkdir -p /fastdfs/cache/nginx/proxy_cache`
    - `vim /usr/local/nginx/conf/nginx.conf`，配置如下：
	```
	#user  root;
	worker_processes  1;

	#error_log  logs/error.log;
	#error_log  logs/error.log  notice;
	#error_log  logs/error.log  info;

	#pid        logs/nginx.pid;


	events {
		worker_connections  1024;
		use epoll;
	}


	http {
		include       mime.types;
		default_type  application/octet-stream;

		#log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
		#                  '$status $body_bytes_sent "$http_referer" '
		#                  '"$http_user_agent" "$http_x_forwarded_for"';

		#access_log  logs/access.log  main;

		sendfile        on;
		tcp_nopush     on;

		#keepalive_timeout  0;
		keepalive_timeout  65;

		#gzip  on;
		#设置缓存参数
		server_names_hash_bucket_size 128;
		client_header_buffer_size 32k;
		large_client_header_buffers 4 32k;
		client_max_body_size 300m;

		proxy_redirect off;
		proxy_set_header Host $http_host;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;

		proxy_connect_timeout 90;
		proxy_send_timeout 90;
		proxy_read_timeout 90;
		proxy_buffer_size 16k;
		proxy_buffers 4 64k;
		proxy_busy_buffers_size 128k;
		proxy_temp_file_write_size 128k;
		#设置缓存存储路径、存储方式、分配内存大小、磁盘最大空间、缓存期限
		proxy_cache_path /fastdfs/cache/nginx/proxy_cache levels=1:2
		keys_zone=http-cache:200m max_size=1g inactive=30d;
		proxy_temp_path /fastdfs/cache/nginx/proxy_cache/tmp;
		#设置group1的服务器
		upstream fdfs_group1 {
			 server 192.168.1.112:8888 weight=1 max_fails=2 fail_timeout=30s;
			 server 192.168.1.113:8888 weight=1 max_fails=2 fail_timeout=30s;
		}
		#设置group2的服务器
		upstream fdfs_group2 {
			 server 192.168.1.114:8888 weight=1 max_fails=2 fail_timeout=30s;
			 server 192.168.1.115:8888 weight=1 max_fails=2 fail_timeout=30s;
		}

		server {
			listen       8000;
			server_name  localhost;

			#charset koi8-r;

			#access_log  logs/host.access.log  main;
			#设置group1的负载均衡参数
			location /group1/M00 {
				proxy_next_upstream http_502 http_504 error timeout invalid_header;
				proxy_cache http-cache;
				proxy_cache_valid  200 304 12h;
				proxy_cache_key $uri$is_args$args;
				#对应group1的服务设置
				proxy_pass http://fdfs_group1;
				expires 30d;
			}
			#设置group2的负载均衡参数
			location /group2/M00 {
				proxy_next_upstream http_502 http_504 error timeout invalid_header;
				proxy_cache http-cache;
				proxy_cache_valid  200 304 12h;
				proxy_cache_key $uri$is_args$args;
				#对应group2的服务设置
				proxy_pass http://fdfs_group2;
				expires 30d;
			}
			#设置清除缓存的访问权限
			location ~/purge(/.*) {
				allow 127.0.0.1;
				allow 192.168.1.103;
				deny all;
				proxy_cache_purge http-cache  $1$is_args$args;
			}

			#error_page  404              /404.html;

			# redirect server error pages to the static page /50x.html
			#
			error_page   500 502 503 504  /50x.html;
			location = /50x.html {
				root   html;
			}

			# proxy the PHP scripts to Apache listening on 127.0.0.1:80
			#
			#location ~ \.php$ {
			#    proxy_pass   http://127.0.0.1;
			#}

			# pass the PHP scripts to FastCGI server listening on 127.0.0.1:9000
			#
			#location ~ \.php$ {
			#    root           html;
			#    fastcgi_pass   127.0.0.1:9000;
			#    fastcgi_index  index.php;
			#    fastcgi_param  SCRIPT_FILENAME  /scripts$fastcgi_script_name;
			#    include        fastcgi_params;
			#}

			# deny access to .htaccess files, if Apache's document root
			# concurs with nginx's one
			#
			#location ~ /\.ht {
			#    deny  all;
			#}
		}

		# another virtual host using mix of IP-, name-, and port-based configuration
		#
		#server {
		#    listen       8000;
		#    listen       somename:8888;
		#    server_name  somename  alias  another.alias;

		#    location / {
		#        root   html;
		#        index  index.html index.htm;
		#    }
		#}


		# HTTPS server
		#
		#server {
		#    listen       443 ssl;
		#    server_name  localhost;

		#    ssl_certificate      cert.pem;
		#    ssl_certificate_key  cert.key;

		#    ssl_session_cache    shared:SSL:1m;
		#    ssl_session_timeout  5m;

		#    ssl_ciphers  HIGH:!aNULL:!MD5;
		#    ssl_prefer_server_ciphers  on;

		#    location / {
		#        root   html;
		#        index  index.html index.htm;
		#    }
		#}

	}
	```
5. 创建缓存的使用目录：
    - `mkdir -p /fastdfs/cache/nginx/proxy_cache`
    - `mkdir -p /fastdfs/cache/nginx/proxy_cache/tmp`
6. 检查防火墙，启动nginx：`/usr/local/nginx/sbin/nginx`
    - http://192.168.1.110:8000/group1/M00/00/00/wKgBcFxpMZOAKz7OAAAZtkdii-k290.jpg

## 2台安装Keepalived
虚拟出一个VIP，对2台跟踪器做高可用配置
1. 修改nginx配置文件`vim /usr/local/nginx/conf/nginx.conf`
	```
	# FastDFS Tracker Proxy 2台跟踪器的nginx代理服务
	upstream fastdfs_tracker {
		server 192.168.1.110:8000 weight=1 max_fails=2 fail_timeout=30s;
		server 192.168.1.111:8000 weight=1 max_fails=2 fail_timeout=30s;
	}
	# FastDFS Proxy 代理路径为/fastdfs
	location /fastdfs {
		root   html;
		index  index.html index.htm;
		proxy_pass  http://fastdfs_tracker/;
		proxy_set_header Host  $http_host;
		proxy_set_header Cookie $http_cookie;
		proxy_set_header X-Real-IP $remote_addr;
		proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
		proxy_set_header X-Forwarded-Proto $scheme;
		client_max_body_size  300m;
	}
	```
    - `nginx.conf`分别上传到2个带Keepalived的节点（116和117）
2. 启动2台机器的nginx和Keepalived，即可进行最终的测试
    - `/usr/local/nginx/sbin/nginx`
    - `service keepalived start`
    - 查看虚拟IP：`ip a`，虚拟IP为：192.168.1.166
    - 在任意一个跟踪器上传文件：`/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/software/avatar.jpg`
    - http://192.168.1.166/fastdfs/group1/M00/00/00/wKgBcFx7wWeAVqGPAAAZtkdii-k652.jpg
    - 注意：116和117的nginx配置文件里，指定了location的前缀为`/fastdfs`

![FastDfs-min-min](https://www.wailian.work/images/2019/03/10/FastDfs-min-min.png)

## 关闭集群
1. 116 117（2台一级负载节点）
    - 关闭Keepalived：`service keepalived stop`
    - 关闭nginx：`/usr/local/nginx/sbin/nginx -s stop`
2. 110 111（2台二级负载节点，跟踪器节点）
    - 关闭nginx：`/usr/local/nginx/sbin/nginx -s stop`
    - 关闭跟踪器：`/etc/init.d/fdfs_trackerd stop`
3. 112 113 114 115（4台三级负载节点，存储器节点）
    - 关闭nginx：`/usr/local/nginx/sbin/nginx -s stop`
    - 关闭存储器：`/etc/init.d/fdfs_storaged stop`
