# Storm网摘笔记

## Storm集群安装配置
安装Storm的步骤：
1. 安装ZooKeeper集群；
1. 在各个机器上安装运行集群所需要的依赖组件；
1. 下载Storm安装程序并解压缩到集群的各个机器上；
1. 在`storm.yaml`中添加集群配置信息；
1. 使用“storm”脚本启动各机器后台进程。

### 安装ZooKeeper集群
关于ZooKeeper部署的几点说明：
1. ZooKeeper必须在监控模式下运行。因为ZooKeeper是个快速失败系统，如果遇到了故障，ZooKeeper服务会主动关闭。
1. 需要设置一个cron服务来定时压缩ZooKeeper的数据与事务日志。因为ZooKeeper的后台进程不会处理这个问题，如果不配置cron，ZooKeeper的日志会很快填满磁盘空间。

### 安装必要的依赖组件
1. Java 6（推荐使用JDK 7以上版本）
1. Python 2.6.6（推荐使用Python 2.7.x版本）

### 下载Storm安装程序并解压
- 安装[apache-storm-1.2.2](http://storm.apache.org/downloads.html)
- 解压三台机器：`tar -zxvf apache-storm-1.2.2.tar.gz -C /usr/local/`

### 配置 storm.yaml
`cd /usr/local/apache-storm-1.2.2/conf`修改Storm配置文件：`vim storm.yaml`（三台ZooKeeper的地址）
1. **storm.zookeeper.servers**：这是Storm关联的ZooKeeper集群的地址列表
	```
	storm.zookeeper.servers:
	- "192.168.100.163"
	- "192.168.100.164"
	- "192.168.100.165"
	```
1. **storm.local.dir**：Nimbus 和 Supervisor 后台进程都需要一个用于存放一些状态数据（比如 jar 包、配置文件等等）的目录
    - 创建data目录：`mkdir /usr/local/apache-storm-1.2.2/data`
    - 添加到配置文件中
	```
	storm.local.dir: "/usr/local/apache-storm-1.2.2/data"
	```
1. **nimbus.host**：集群的工作节点需要知道集群中的哪台机器是主机，以便从主机上下载拓扑以及配置文件
	```
	nimbus.host: "192.168.100.163"
	```
1. **supervisor.slots.ports**：配置每个 Supervisor 机器能够运行的工作进程（worker）数
	```
	supervisor.slots.ports:
	- 6700
	- 6701
	- 6702
	- 6703
	```
1. 配置`ui.port`：`ui.port: 18080`

```
storm.zookeeper.servers:
- "192.168.100.163"
- "192.168.100.164"
- "192.168.100.165"
nimbus.seeds: ["bm-163"]
storm.local.dir: "/usr/local/apache-storm-1.2.2/data"
#nimbus.host: "192.168.100.163"
ui.port: 18080
supervisor.slots.ports:
- 6700
- 6701
- 6702
- 6703
```

### 使用“storm”脚本启动后台进程
1. 把apache-storm-1.2.2使用scp到各个集群的服务器中去。	
1. 修改配置文件`/etc/profile`把Storm配置到其中去，并使用scp到各个集群的服务器中去。
    - 命令：`vim /etc/profile`
    - 修改内容：
    ```
    export STORM_HOME=/usr/local/apache-storm-1.2.2
    export PATH=.:$JAVA_HOME/bin:$ZOOKEEPER_HOME/bin:$STORM_HOME/bin:$PATH
    ```
    - 最后分别进行`source /etc/profile`
1. 首先启动ZooKeeper集群，然后分别启动运行Storm：
    - 192.168.100.163：主机器（nimbus运行）`storm nimbus &`
    - 192.168.100.164：从机器（supervisor运行）`storm supervisor &`
    - 192.168.100.165：从机器（supervisor运行）`storm supervisor &`
    - 192.168.100.163：主机器（ui运行）`storm ui &`（查看ui）
    - 192.168.100.163：主机器（logviewer运行）`storm logviewer &`（查看工作日志）
1. 然后在浏览器中输入主机器的ip，端口默认8080（[http://192.168.100.163:18080/](http://192.168.100.163:18080/)）这样可以看到Storm的一些集群配置

## Tips
### /etc/profile
```
export JAVA_HOME=/usr/local/jdk1.8.0_191/
export ZOOKEEPER_HOME=/usr/local/zookeeper-3.4.10
export STORM_HOME=/usr/local/apache-storm-1.2.2
export PATH=.:$JAVA_HOME/bin:$ZOOKEEPER_HOME/bin:$STORM_HOME/bin:$PATH
```

### /etc/hosts
```
192.168.100.163 bm-163
192.168.100.164 bm-164
192.168.100.165 bm-165
```

## References
- [Apache Storm 官方文档 —— Storm 集群安装配置](http://ifeve.com/storm-setting-up-a-storm-cluster/)