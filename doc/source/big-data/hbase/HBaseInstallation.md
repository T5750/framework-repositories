# HBase Installation

The three modes: Standalone mode, Pseudo Distributed mode, and Fully Distributed mode.

## Pre-Installation Setup
- [Enviornment Setup](../hadoop/HadoopEnviornmentSetup.md)

## Installing HBase
### Installing HBase in Standalone Mode
```
wget http://mirrors.tuna.tsinghua.edu.cn/apache/hbase/1.4.9/hbase-1.4.9-bin.tar.gz
tar -zxvf hbase-1.4.9-bin.tar.gz
chown -R hadoop /usr/local/hbase-1.4.9/
chgrp -R dialout /usr/local/hbase-1.4.9/
```

### Configuring HBase in Standalone Mode
`hbase-env.sh`
```
cd /usr/local/hbase-1.4.9/conf
vi hbase-env.sh
export JAVA_HOME=/usr/local/jdk1.8.0_181
```

`hbase-site.xml`
```
<configuration>
	<property>
		<name>hbase.rootdir</name>
		<value>file:/home/hadoop/hbase/hfiles</value>
	</property>
	<property>
		<name>hbase.zookeeper.property.dataDir</name>
		<value>/home/hadoop/zookeeper</value>
	</property>
	<property>
		<name>hbase.master.info.port</name>
		<value>60010</value>
	</property>
</configuration>
```
```
$ /usr/local/hbase-1.4.9/bin/start-hbase.sh
tail -f -n 99 /usr/local/hbase-1.4.9/logs/hbase-hadoop-master-tc210.log
```

### Installing HBase in Pseudo-Distributed Mode
`hbase-site.xml`
We are running HDFS on the localhost at port 9000.
```
<property>
	<name>hbase.cluster.distributed</name>
	<value>true</value>
</property>
<property>
	<name>hbase.rootdir</name>
	<value>hdfs://localhost:9000/hbase</value>
</property>
```

### Starting HBase
`$ /usr/local/hbase-1.4.9/bin/start-hbase.sh`

### Checking the HBase Directory in HDFS
`hadoop fs -ls /hbase`

### Starting and Stopping a Master
```
$ ./bin/local-master-backup.sh 2 4
$ cat /tmp/hbase-hadoop-master.pid |xargs kill -9
```

### Starting and Stopping RegionServers
```
$ .bin/local-regionservers.sh start 2 3
$ .bin/local-regionservers.sh stop 3
```

### Starting HBaseShell
1. Start Hadoop File System: `$ start-all.sh`
1. Start HBase: `$ /usr/local/hbase-1.4.9/bin/start-hbase.sh`
1. Start HBase Master Server: `$ ./bin/local-master-backup.sh start 2 (number signifies specific server.)`
1. Start Region: `$ ./bin/local-regionservers.sh start 3`
1. Start HBase Shell: `$ bin/hbase shell`

### HBase Web Interface
- [http://192.168.100.210:60010](http://192.168.100.210:60010)

### Setting the Classpath
`vi ~/.bashrc`
```
export HBASE_HOME=/usr/local/hbase-1.4.9
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:${HBASE_HOME}/lib
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin
```

## References
- [HBase - Installation](https://www.tutorialspoint.com/hbase/hbase_installation.htm)