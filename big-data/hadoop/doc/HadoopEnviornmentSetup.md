# Enviornment Setup

## Creating a User
```
useradd hadoop
passwd hadoop
```

## SSH Setup and Key Generation
```
$ ssh-keygen -t rsa
$ cat ~/.ssh/id_rsa.pub >> ~/.ssh/authorized_keys
$ chmod 0600 ~/.ssh/authorized_keys
```

## Downloading Hadoop
```
wget http://mirrors.shu.edu.cn/apache/hadoop/common/hadoop-2.9.2/hadoop-2.9.2.tar.gz
tar -zxvf hadoop-2.9.2.tar.gz -C /usr/local
mv hadoop-2.9.2/ hadoop
```

## Installing Hadoop in Standalone Mode
### Setting Up Hadoop
`vim ~/.bashrc`
```
export JAVA_HOME=/usr/local/jdk1.8.0_181
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export HADOOP_HOME=/usr/local/hadoop
export PATH=${JAVA_HOME}/bin:${HADOOP_HOME}/bin:$PATH
```
`hadoop version`

### Example
1. Create temporary content files in the input directory
	```
	$ mkdir input 
	$ cp $HADOOP_HOME/*.txt input
	```
1. Let's start the Hadoop process to count the total number of words: `hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.9.2.jar wordcount input output`
1. It will list down all the words along with their total counts available: `cat output/*`

## Installing Hadoop in Pseudo Distributed Mode
1. Setting Up Hadoop
	```
	export JAVA_HOME=/usr/local/jdk1.8.0_181
	export JRE_HOME=${JAVA_HOME}/jre
	export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
	export HADOOP_HOME=/usr/local/hadoop
	export HADOOP_MAPRED_HOME=$HADOOP_HOME
	export HADOOP_COMMON_HOME=$HADOOP_HOME
	export HADOOP_HDFS_HOME=$HADOOP_HOME
	export YARN_HOME=$HADOOP_HOME
	export HADOOP_COMMON_LIB_NATIVE_DIR=$HADOOP_HOME/lib/native
	export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin
	export HADOOP_INSTALL=$HADOOP_HOME
	```
    - `source ~/.bashrc`

1. Hadoop Configuration
    - `cd $HADOOP_HOME/etc/hadoop`
    - `vim core-site.xml`
	```
	<configuration>
	   <property>
		  <name>fs.default.name</name>
		  <value>hdfs://localhost:9000</value>
	   </property>
	</configuration>
	```
	- Let us assume the following data
	```
	dfs.replication (data replication value) = 1
	(In the below given path /hadoop/ is the user name.
	hadoopinfra/hdfs/namenode is the directory created by hdfs file system.)
	namenode path = //home/hadoop/hadoopinfra/hdfs/namenode 
	(hadoopinfra/hdfs/datanode is the directory created by hdfs file system.)
	datanode path = //home/hadoop/hadoopinfra/hdfs/datanode
	```
    - `vim hdfs-site.xml`
	```
	<configuration>
	   <property>
		  <name>dfs.replication</name>
		  <value>1</value>
	   </property>
	   <property>
		  <name>dfs.name.dir</name>
		  <value>file:///home/hadoop/hadoopinfra/hdfs/namenode</value>
	   </property>
	   <property>
		  <name>dfs.data.dir</name>
		  <value>file:///home/hadoop/hadoopinfra/hdfs/datanode</value>
	   </property>
	   <property>
          <name>hadoop.tmp.dir</name>
          <value>file:///home/hadoop/hadoopinfra/hdfs/tmp</value>
       </property>
	</configuration>
	```
    - `vim yarn-site.xml`
	```
	<configuration>
	   <property>
		  <name>yarn.nodemanager.aux-services</name>
		  <value>mapreduce_shuffle</value>
	   </property>
	</configuration>
	```
    - `cp mapred-site.xml.template mapred-site.xml`
    - `vim mapred-site.xml`
	```
	<configuration>
	   <property>
		  <name>mapreduce.framework.name</name>
		  <value>yarn</value>
	   </property>
	</configuration>
	```

## Verifying Hadoop Installation
1. Name Node Setup
	```
	$ cd ~ 
	$ hdfs namenode -format
	```
1. Verifying Hadoop dfs: `start-dfs.sh`
1. Verifying Yarn Script: `start-yarn.sh`
1. Accessing Hadoop on Browser: [http://192.168.100.211:50070](http://192.168.100.211:50070)
1. Verify All Applications for Cluster: [http://192.168.100.211:8088](http://192.168.100.211:8088)

## References
- [Hadoop - Enviornment Setup](https://www.tutorialspoint.com/hadoop/hadoop_enviornment_setup.htm)
- [Setting up a Single Node Cluster](http://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html)