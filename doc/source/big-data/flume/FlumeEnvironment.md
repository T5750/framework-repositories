# Flume Environment

## Installing Flume
```
wget http://mirrors.tuna.tsinghua.edu.cn/apache/flume/1.9.0/apache-flume-1.9.0-bin.tar.gz
tar -zxvf apache-flume-1.9.0-bin.tar.gz -C /usr/local
mv apache-flume-1.9.0-bin/ apache-flume-1.9.0
chown -R hadoop /usr/local/apache-flume-1.9.0/
```

## Configuring Flume
```
vi ~/.bashrc
export FLUME_HOME=/usr/local/apache-flume-1.9.0
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin:$SCALA_HOME/bin:$SPARK_HOME/bin:$FLUME_HOME/bin
source ~/.bashrc
cp flume-conf.properties.template flume-conf.properties
cp flume-env.sh.template flume-env.sh
vi flume-env.sh
export JAVA_HOME=/usr/local/jdk1.8.0_181
```

## Verifying the Installation
```
$ ./flume-ng
```

## References
- [Flume - Environment](https://www.tutorialspoint.com/apache_flume/apache_flume_environment.htm)