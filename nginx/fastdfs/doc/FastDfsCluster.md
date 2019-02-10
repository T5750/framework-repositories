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
2. 修改`tracker.conf`文件：`vim /etc/fdfs/tracker.conf`
    - 修改配置文件里的`base_path`即可：`base_path=/fastdfs/tracker`
3. 最后一定要创建之前定义好的目录（也就是`/fastdfs/tracker`）：`mkdir -p /fastdfs/tracker`
4. 启动跟踪器
    - 目录命令：`cd /fastdfs/tracker/ && ll`
    - 启动tracker命令：`/etc/init.d/fdfs_trackerd start`
    - 查看进程命令：`ps -el | grep fdfs`或`netstat -tunpl | grep fdfs`
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
	base_path=/fastdfs/storage #
	store_path_count=1 #
	store_path0=/fastdfs/storage #
	tracker_server=192.168.1.110:22122 #tracker服务器的IP和端口
	tracker_server=192.168.1.111:22122 #多个tracker直接添加多条配置
	http.server_port=8888 #设置http端口号
	```
3. 创建存储目录：`mkdir -p /fastdfs/storage`
4. 启动storage
    - 启动：`/etc/init.d/fdfs_storaged start`
    - 日志：`tail -f /fastdfs/storage/logs/storaged.log`
5. 查看FastDFS storage 是否启动成功：`ps -ef | grep fdfs`
    - 并且进入到`/fastdfs/storage/data/`文件夹下会看到一些目录文件（256*256），如下：
    - 命令：`cd /fastdfs/storage/data/ && ls`

## 测试文件上传
1. 先使用命令上传一个文件。注意：是在tracker（跟踪器）中上传。首先在跟踪器（192.168.1.110）里copy一份`client.conf`文件。
    - 命令：`cd /etc/fdfs/`
    - 命令：`cp client.conf.sample client.conf`
2. 编辑`client.conf`文件：`vim /etc/fdfs/client.conf`
    - 修改内容：
	```
	base_path=/fastdfs/tracker
	tracker_server=192.168.1.110:22122
	tracker_server=192.168.1.111:22122
	```
3. 找到命令的脚本位置，并且使用命令，进行文件的上传：
    - 命令：`cd /usr/bin/`
    - 命令：`ls | grep fdfs`
4. 使用命令`fdfs_upload_file`进行上传操作：
    - 首先，先看一下存储器（192.168.1.112），进入到data下，在进入00文件夹下，发现00文件夹下还有一堆文件夹，然后继续进入00文件夹下，最终所进入的文件夹为：`/fastdfs/storage/data/00/00`里面什么文件都没有。
    - 然后，进行上传操作，比如把之前的`/usr/local/software/`文件夹下的某一个
    - 文件上传到FastDFS系统中去，在跟踪器（192.168.1.110）中上传文件：`/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/software/FastDFS_v5.05.tar.gz`
    - 最后发现，命令执行完毕后，返回一个`group1/M00/00/00/...`的ID，其实就是返回当前所上传的文件在存储器（192.168.1.112）中的哪一个组、哪一个目录位置，所以查看存储器中的`/fastdfs/storage/data/00/00`文件夹位置，发现已经存在了刚才上传的文件，测试上传文件已经OK。


