# Spark Installation

## Verifying Java Installation
`java -version`

## Downloading Scala
```
wget https://downloads.lightbend.com/scala/2.11.12/scala-2.11.12.tgz
```

## Installing Scala
```
tar -zxvf scala-2.11.12.tgz -C /usr/local
vi ~/.bashrc
export SCALA_HOME=/usr/local/scala-2.11.12
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin:$SCALA_HOME/bin
source ~/.bashrc
scala -version
```

## Downloading Apache Spark
```
wget http://mirror.bit.edu.cn/apache/spark/spark-2.4.3/spark-2.4.3-bin-hadoop2.7.tgz
```

## Installing Spark
```
tar -zxvf spark-2.4.3-bin-hadoop2.7.tgz -C /usr/local
mv spark-2.4.3-bin-hadoop2.7/ spark-2.4.3
vi ~/.bashrc
export SPARK_HOME=/usr/local/spark-2.4.3
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin:$SCALA_HOME/bin:$SPARK_HOME/bin
source ~/.bashrc
```

## Verifying the Spark Installation
```
spark-shell
```

## References
- [Spark - Installation](https://www.tutorialspoint.com/spark_sql/spark_installation.htm)