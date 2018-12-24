# Kafka Quickstart
## 1.Download the code
```
tar -xzf kafka_2.11-2.1.0.tgz
```

## 2.Start the server
```
bin/zookeeper-server-start.sh config/zookeeper.properties
```
```
bin/kafka-server-start.sh config/server.properties
```

## 3.Create a topic
```
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
```
```
bin/kafka-topics.sh --list --zookeeper localhost:2181
```

## 4.Send some messages
```
bin/kafka-console-producer.sh --broker-list localhost:9092 --topic test
```

## 5.Start a consumer
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning
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

## References
- [Quickstart](http://kafka.apache.org/quickstart)