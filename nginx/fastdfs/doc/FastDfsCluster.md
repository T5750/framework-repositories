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


