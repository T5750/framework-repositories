# Impala Environment

```
wget http://mirror.bit.edu.cn/apache/impala/3.2.0/apache-impala-3.2.0.tar.gz
tar -zxvf apache-impala-3.2.0.tar.gz -C /usr/local
chown -R hadoop /usr/local/apache-impala-3.2.0/
vi ~/.bashrc
export IMPALA_HOME=/usr/local/apache-impala-3.2.0
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin:$SCALA_HOME/bin:$SPARK_HOME/bin:$FLUME_HOME/bin:$PIG_HOME/bin:$IMPALA_HOME/bin
```
```
git clone https://gitbox.apache.org/repos/asf/impala.git ~/impala
cd ~/impala
//TODO
```

## Tips
//TODO
```
chmod u+w /etc/sudoers
vi /etc/sudoers
hadoop    ALL=(ALL)       ALL
chmod u-w /etc/sudoers
```

```
wget http://mirrors.tuna.tsinghua.edu.cn/apache/thrift/0.12.0/thrift-0.12.0.tar.gz
tar -zxvf thrift-0.12.0.tar.gz -C /usr/local
chown -R hadoop /usr/local/thrift-0.12.0/
export THRIFT_HOME=/usr/local/thrift-0.12.0
./configure && make
thrift -version

df -h
du --max-depth=1 -h  /
```

## References
- [Impala - Environment](https://www.tutorialspoint.com/impala/impala_environment.htm)