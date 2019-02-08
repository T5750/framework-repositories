# FastDFS单节点安装手册

## 准备工作（2台机器同时进行）
1. 下载软件：[http://sourceforge.net/projects/fastdfs/files/](http://sourceforge.net/projects/fastdfs/files/)
2. 安装gcc：`yum install make cmake gcc gcc-c++`

## 安装libfastcommon（2台机器同时进行）
1. 上传`libfastcommon-master.zip`到`/usr/local/software`下
2. 解压`libfastcommon-master.zip`：`unzip libfastcommon-master.zip -d /usr/local/fast/`
3. 进入目录：`cd /usr/local/fast/libfastcommon-master/`
4. 进行编译和安装：
    - `./make.sh`
    - `./make.sh install`

注意安装的路径：libfastcommon默认安装到了`/usr/lib64/`这个位置进行软件创建。FastDFS主程序设置的目录为`/usr/local/lib/`，所以需要创建`/usr/lib64/`下的一些核心执行程序的软连接文件。
```
mkdir /usr/local/lib/
ln -s /usr/lib64/libfastcommon.so /usr/local/lib/libfastcommon.so
ln -s /usr/lib64/libfastcommon.so /usr/lib/libfastcommon.so
ln -s /usr/lib64/libfdfsclient.so /usr/local/lib/libfdfsclient.so
ln -s /usr/lib64/libfdfsclient.so /usr/lib/libfdfsclient.so
```

## 安装FastDFS
1. 进入到`cd /usr/local/software`下，解压`FastDFS_v5.05.tar.gz`文件
    - `tar -zxvf FastDFS_v5.05.tar.gz -C /usr/local/fast/`
2. 安装编译
    - 命令：`cd /usr/local/fast/FastDFS/`
    - 编译命令：`./make.sh`
    - 安装命令：`./make.sh install`
3. 采用默认安装方式脚本文件说明：
    - 服务脚本在：
	```
	/etc/init.d/fdfs_storaged
	/etc/init.d/fdfs_trackerd
	```
    - 配置文件在：
	```
	/etc/fdfs/client.conf.sample
	/etc/fdfs/storage.conf.sample
	/etc/fdfs/tracker.conf.sample
	```
    - 命令行工具在`/usr/bin/`目录下，`Fdfs_*`的一些列执行脚本
4. 因为FastDFS服务脚本设置的bin目录为`/usr/local/bin/`下,但是实际安装在了`/usr/bin/`下面。所以需要修改FastDFS配置文件中的路径，也就是需要修改2个配置文件：
    - 命令：`vim /etc/init.d/fdfs_storaged`
    - 进行全局替换命令：`%s+/usr/local/bin+/usr/bin`
    - 命令：`vim /etc/init.d/fdfs_trackerd`
    - 进行全局替换命令：`%s+/usr/local/bin+/usr/bin`

## 配置跟踪器（192.168.1.110节点）
1. 进入`cd /etc/fdfs/`目录配置跟踪器文件（注意是192.168.1.110节点），把`tracker.conf.sample`文件进行cope一份：去修改`tracker.conf`文件
2. 修改`tracker.conf`文件：`vim /etc/fdfs/tracker.conf`
    - 修改配置文件里的`base_path`即可：`base_path=/fastdfs/tracker`
    - 注意：对于`tracker.conf`配置文件参数解释可以找官方文档，地址为：[http://bbs.chinaunix.net/thread-1941456-1-1.html](http://bbs.chinaunix.net/thread-1941456-1-1.html)
3. 最后一定要创建之前定义好的目录（也就是`/fastdfs/tracker`）：`mkdir -p /fastdfs/tracker`
4. 关闭防火墙：`vim /etc/sysconfig/iptables`
    - 添加：`-A INPUT -m state --state NEW -m tcp -p tcp --dport 22122 -j ACCEPT`
    - 重启：`service iptables restart`
5. 启动跟踪器
    - 目录命令：`cd /fastdfs/tracker/ && ll`
    - 启动tracker命令：`/etc/init.d/fdfs_trackerd start`
    - 查看进程命令：`ps -el | grep fdfs`
    - 停止tracker命令：`/etc/init.d/fdfs_trackerd stop`
6. 可以设置开机启动跟踪器：（一般生产环境需要开机启动一些服务，如keepalived、linux、tomcat等等）
    - 命令：`vim /etc/rc.d/rc.local`
    - 加入配置：`/etc/init.d/fdfs_trackerd start`

## 配置FastDFS存储（192.168.1.112）
1. 进入文件目录：`cd /etc/fdfs/`，进行copy storage文件一份
    - 命令：`cp storage.conf.sample storage.conf`
2. 修改storage.conf文件：`vim /etc/fdfs/storage.conf`
    - 修改内容：
	```
	base_path=/fastdfs/storage
	store_path0=/fastdfs/storage
	tracker_server=192.168.1.110:22122
	http.server_port=8888
	```
3. 创建存储目录：`mkdir -p /fastdfs/storage`
4. 打开防火墙：
    - 命令：`vim /etc/sysconfig/iptables`
    - 添加：`-A INPUT -m state --state NEW -m tcp -p tcp --dport 23000 -j ACCEPT`
    - 重启：`service iptables restart`
5. 启动存储（storage）
    - 命令：`/etc/init.d/fdfs_storaged start`
    - 关闭：`/etc/init.d/fdfs_storaged stop`（初次启动成功后会在`/fastdbf/storage/`目录下创建data、logs目录）
6. 查看FastDFS storage 是否启动成功：`ps -ef | grep fdfs`
    - 并且进入到`/fastdfs/storage/data/`文件夹下会看到一些目录文件（256*256），如下：
    - 命令：`cd /fastdfs/storage/data/ && ls`
7. 同理，也可以设置开机启动存储器：（一般生产环境需要开机启动一些服务，如keepalived、linux、tomcat等等）
    - 命令：`vim /etc/rc.d/rc.local`
    - 加入配置：`/etc/init.d/fdfs_storaged start`

## 测试环境
1. 先使用命令上传一个文件。注意：是在tracker（跟踪器）中上传。首先在跟踪器（192.168.1.110）里copy一份`client.conf`文件。
    - 命令：`cd /etc/fdfs/`
    - 命令：`cp client.conf.sample client.conf`
2. 编辑`client.conf`文件：`vim /etc/fdfs/client.conf`
    - 修改内容：
	```
	base_path=/fastdfs/tracker
	tracker_server=192.168.1.110:22122
	```
3. 找到命令的脚本位置，并且使用命令，进行文件的上传：
    - 命令：`cd /usr/bin/`
    - 命令：`ls | grep fdfs`
4. 使用命令`fdfs_upload_file`进行上传操作：
    - 首先，先看一下存储器（192.168.1.112），进入到data下，在进入00文件夹下，发现00文件夹下还有一堆文件夹，然后继续进入00文件夹下，最终所进入的文件夹为：`/fastdfs/storage/data/00/00`里面什么文件都没有。
    - 然后，进行上传操作，比如把之前的`/usr/local/software/`文件夹下的某一个
    - 文件上传到FastDFS系统中去，在跟踪器（192.168.1.110）中上传文件：`/usr/bin/fdfs_upload_file /etc/fdfs/client.conf /usr/local/software/FastDFS_v5.05.tar.gz`
    - 最后发现，命令执行完毕后，返回一个`group1/M00/00/00/...`的ID，其实就是返回当前所上传的文件在存储器（192.168.1.112）中的哪一个组、哪一个目录位置，所以查看存储器中的`/fastdfs/storage/data/00/00`文件夹位置，发现已经存在了刚才上传的文件，测试上传文件已经OK。

