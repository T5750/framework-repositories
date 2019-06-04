# Pig Installation

## Prerequisites
- [Enviornment Setup](../../hadoop/doc/HadoopEnviornmentSetup.md)

## Install Pig
```
wget https://mirrors.tuna.tsinghua.edu.cn/apache/pig/pig-0.17.0/pig-0.17.0.tar.gz
tar -zxvf pig-0.17.0.tar.gz -C /usr/local
chown -R hadoop /usr/local/pig-0.17.0/
```

## Configure Pig
```
export PIG_HOME=/usr/local/pig-0.17.0
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin:$SCALA_HOME/bin:$SPARK_HOME/bin:$FLUME_HOME/bin:$PIG_HOME/bin
```

## Verifying the Installation
```
$ pig –version
```

## References
- [Pig - Installation](https://www.tutorialspoint.com/apache_pig/apache_pig_installation.htm)