# Hadoop - Multi-Node Cluster

- Hadoop Master: 192.168.100.213 (tc213)
- Hadoop Slave: 192.168.100.211 (tc211)
- Hadoop Slave: 192.168.100.212 (tc212)

## Installing Java
```
export JAVA_HOME=/usr/local/jdk1.8.0_181
export PATH=PATH:$JAVA_HOME/bin
```

## Creating User Account
```
# useradd hadoop
# passwd hadoop
```

## Mapping the nodes
- `vi /etc/hosts`
```
192.168.100.213 tc213
192.168.100.211 tc211
192.168.100.212 tc212
```

## Configuring Key Based Login
```
# su hadoop 
$ ssh-keygen -t rsa
$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@tc213
$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@tc211
$ ssh-copy-id -i ~/.ssh/id_rsa.pub hadoop@tc212
$ chmod 0600 ~/.ssh/authorized_keys
$ exit
```

## Installing Hadoop
```
# mkdir /usr/local/hadoop
# cd /usr/local/hadoop/
# wget http://mirror.bit.edu.cn/apache/hadoop/common/hadoop-2.9.2/hadoop-2.9.2.tar.gz
# tar -xzf hadoop-2.9.2.tar.gz
# mv hadoop-2.9.2 hadoop
# chown -R hadoop /usr/local/hadoop
# cd /usr/local/hadoop/
```

## Configuring Hadoop
### core-site.xml
```
<configuration>
	<property>
		<name>fs.defaultFS</name>
		<value>hdfs://tc213:9000/</value>
	</property>
	<property>
		<name>hadoop.tmp.dir</name>
		<value>file:/home/hadoop/cluster</value>
	</property>
</configuration>
```

### hdfs-site.xml
```
<configuration>
	<property>
		<name>dfs.replication</name>
		<value>3</value>
	</property>
	<property>
		<name>dfs.data.dir</name>
		<value>/home/hadoop/cluster/dfs/data</value>
		<final>true</final>
	</property>
	<property>
		<name>dfs.name.dir</name>
		<value>/home/hadoop/cluster/dfs/name</value>
		<final>true</final>
	</property>
</configuration>
```

### mapred-site.xml
```
<configuration>
	<property>
		<name>mapreduce.framework.name</name>
		<value>yarn</value>
	</property>
</configuration>
```

### yarn-site.xml
```
<configuration>
	<property>
		<name>yarn.nodemanager.aux-services</name>
		<value>mapreduce_shuffle</value>
	</property>
	<property>
		<name>yarn.resourcemanager.hostname</name>
		<value>tc213</value>
	</property>
</configuration>
```

### hadoop-env.sh
```
export JAVA_HOME=/usr/local/jdk1.8.0_181
export HADOOP_OPTS="-Djava.net.preferIPv4Stack=true"
export HADOOP_CONF_DIR=${HADOOP_CONF_DIR:-"/etc/hadoop"}
```

## Installing Hadoop on Slave Servers
```
$ scp -r hadoop tc211:/usr/local/hadoop
$ scp -r hadoop tc212:/usr/local/hadoop
```

## Configuring Hadoop on Master Server
### Configuring Master Node
```
vi /usr/local/hadoop/etc/hadoop/masters
tc213
```

### Configuring Slave Node
```
vi /usr/local/hadoop/etc/hadoop/slaves
tc211
tc212
```

### Format Name Node on Hadoop Master
`hadoop namenode –format`

## Starting Hadoop Services
```
mkdir -p /home/hadoop/cluster/dfs/name
mkdir -p /home/hadoop/cluster/dfs/data

cd $HADOOP_HOME/sbin
start-all.sh
```

## Results
cluster | hostname | IP | jps
---|---|----|-----
Master | tc213 | 192.168.100.213 | NameNode, SecondaryNameNode, ResourceManager
Slave | tc211 | 192.168.100.211 | DataNode, NodeManager
Slave | tc212 | 192.168.100.212 | DataNode, NodeManager

### Master
```
tail -f -n 99 /usr/local/hadoop/logs/hadoop-hadoop-namenode-tc213.log
tail -f -n 99 /usr/local/hadoop/logs/hadoop-hadoop-secondarynamenode-tc213.log
tail -f -n 99 /usr/local/hadoop/logs/yarn-hadoop-resourcemanager-tc213.log
```

### Slave
```
tail -f -n 99 /usr/local/hadoop/logs/hadoop-hadoop-datanode-tc211.log
tail -f -n 99 /usr/local/hadoop/logs/yarn-hadoop-nodemanager-tc211.log
```

## References
- [Hadoop - Multi-Node Cluster](https://www.tutorialspoint.com/hadoop/hadoop_multi_node_cluster.htm)
- [Hadoop 2.7分布式集群环境搭建](http://dblab.xmu.edu.cn/blog/1177-2/)
- [Hadoop - namenode is not starting up](https://stackoverflow.com/questions/16713011/hadoop-namenode-is-not-starting-up)