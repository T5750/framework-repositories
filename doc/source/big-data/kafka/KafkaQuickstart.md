# Kafka Quickstart
## 1.Download the code
```
tar -xzf kafka_2.11-0.9.0.0.tgz
```

## 2.Start the server
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
```
bin/kafka-server-start.sh config/server.properties &
```

## 3.Create a topic
创建名为test的topic，1个分区分别存放数据，数据备份总共1份：
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```
查看topic列表：
```
bin/kafka-topics.sh --list --zookeeper localhost:2181
```

## 4.Send some messages
kafka命令发送数据：
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
```

## 5.Start a consumer
kafka命令接收数据：
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic test --from-beginning
```

## 6.Setting up a multi-broker cluster
```
cp config/server.properties config/server-1.properties
cp config/server.properties config/server-2.properties
```
`server-1.properties`
```
    broker.id=1
    listeners=PLAINTEXT://:9093
    log.dirs=/tmp/kafka-logs-1
```
`server-2.properties`
```
    broker.id=2
    listeners=PLAINTEXT://:9094
    log.dirs=/tmp/kafka-logs-2
```
```
bin/kafka-server-start.sh config/server-1.properties &
bin/kafka-server-start.sh config/server-2.properties &
```
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic
```
```
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic test
```
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic my-replicated-topic
```
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic my-replicated-topic
```
```
ps aux | grep server-1.properties
kill -9 7564
```
```
bin/kafka-topics.sh --describe --zookeeper localhost:2181 --topic my-replicated-topic
```
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --from-beginning --topic my-replicated-topic
```

## 7.Use Kafka Connect to import/export data
```
echo -e "foo\nbar" > test.txt
```
```
bin/connect-standalone.sh config/connect-standalone.properties config/connect-file-source.properties config/connect-file-sink.properties
```
```
cat test.sink.txt
```
```
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic connect-test --from-beginning
```
```
echo "Another line" >> test.txt
```

## Windows
`server.properties`
```
log.dirs=D:\\kafka_2.10-0.9.0.1\\kafka-logs
```
`log4j.properties`
```
kafka.logs.dir=D:\\kafka_2.10-0.9.0.1\\logs
```
cmd
```
bin\windows\kafka-server-start.bat config\server.properties
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic testCmd
bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic testCmd
bin\windows\kafka-console-consumer.bat --zookeeper localhost:2181 --topic testCmd
```

### Setting up a multi-broker cluster
`server-1.properties`
```
    broker.id=1
    listeners=PLAINTEXT://:9093
    log.dirs=D:\\kafka_2.10-0.9.0.1\\kafka-logs-1
```
`server-2.properties`
```
    broker.id=2
    listeners=PLAINTEXT://:9094
    log.dirs=D:\\kafka_2.10-0.9.0.1\\kafka-logs-2
```
```
bin\windows\kafka-server-start.bat config\server-1.properties
bin\windows\kafka-server-start.bat config\server-2.properties
bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 3 --partitions 1 --topic my-replicated-topic
bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181 --topic my-replicated-topic
bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181 --topic test
bin\windows\kafka-console-producer.bat --broker-list localhost:9092 --topic my-replicated-topic
bin\windows\kafka-console-consumer.bat --zookeeper localhost:2181 --topic my-replicated-topic
wmic process where "caption = 'java.exe' and commandline like '%server-1.properties%'" get processid
taskkill /pid 6016 /f
bin\windows\kafka-topics.bat --describe --zookeeper localhost:2181 --topic my-replicated-topic
bin\windows\kafka-console-consumer.bat --zookeeper localhost:2181 --topic my-replicated-topic
```

## References
- [Kafka 0.9.0 Quickstart](http://kafka.apache.org/090/documentation.html#quickstart)