# Kafka Zookeeper Docker

## Standalone in Docker
```sh
docker run -d --name zoo1 --restart=always -v /etc/localtime:/etc/localtime:ro -p 2181:2181 zookeeper:3.5
docker run -d --name kafka1 --restart=always -v /etc/localtime:/etc/localtime:ro -p 9092:9092 -e KAFKA_BROKER_ID=0 -e KAFKA_ZOOKEEPER_CONNECT=zoo1:2181 --link zoo1 -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://kafka_ip:9092 -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 -e JMX_PORT=1099 -t wurstmeister/kafka
```

## Standalone in Docker Compose
`zookeeper-kafka-standalone.yml`

```sh
kafka-topics.sh --create --bootstrap-server 127.0.0.1:9092 --replication-factor 1 --partitions 1 --topic kafkazookeeper
kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic kafkazookeeper
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic kafkazookeeper --from-beginning
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic kafkazookeeper --group kafkazookeepergroup --from-beginning

kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --list
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic kafkazookeeper --describe
kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --list
kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group kafkazookeepergroup --describe
kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group kafkazookeepergroup --describe --members
kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list 127.0.0.1:9092 --topic kafkazookeeper
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --delete --topic kafkazookeeper
# bin/kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --delete --topic kafkazookeeper.*

kafka-topics.sh --version
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic __consumer_offsets --describe
kafka-topics.sh --bootstrap-server 127.0.0.1:9092 --topic kafkazookeeper --partitions 2 --alter
kafka-reassign-partitions.sh --bootstrap-server 127.0.0.1:9092 --reassignment-json-file replica.json --execute
```
`vi replica.json`
```json
{
    "version":1,
    "partitions":[
        {"topic":"kafkazookeeper","partition":0,"replicas":[1,2,3]},
        {"topic":"kafkazookeeper","partition":1,"replicas":[1,2,3]},
        {"topic":"kafkazookeeper","partition":2,"replicas":[1,2,3]}
    ]
}
```

## Cluster in Docker Compose
`zookeeper-kafka.yml`

```sh
kafka-topics.sh --create --zookeeper 172.18.0.201:2181,172.18.0.202:2181,172.18.0.203:2181 --replication-factor 1 --partitions 1 --topic kafkazookeeper
kafka-topics.sh --zookeeper 172.18.0.201:2181,172.18.0.202:2181,172.18.0.203:2181 --list
```

## Runtime Environment
- [ZooKeeper 3.x](https://zookeeper.apache.org/releases.html)
- [Kafka 2.x](https://kafka.apache.org/downloads)

## Tips
### java.rmi.server.ExportException: Port already in use: 1099
```sh
unset JMX_PORT;
```

### UnrecognizedOptionException: bootstrap-server is not a recognized option
The `bin/kafka-topics.sh` command line tool is now able to connect directly to brokers with `--bootstrap-server` instead of zookeeper. The old `--zookeeper` option is still available for now.
```sh
# Kafka 2.2.X
bin/kafka-topics.sh --list --bootstrap-server localhost:9092
# Kafka 2.1.X
bin/kafka-topics.sh --list --zookeeper localhost:2181
```

### Kafka 2.3.0
There have been several improvements to the Kafka Connect REST API

### Kafka 3.0.0
The deprecation of support for Java 8 and Scala 2.12

### Kafka 3.1.0
Apache Kafka supports Java 17

## Introduction
![](https://kafka.apache.org/images/streams-and-tables-p1_p4.png)

## References
- [ZooKeeper Docker](https://hub.docker.com/_/zookeeper)
- [wurstmeister/kafka Docker](https://hub.docker.com/r/wurstmeister/kafka)
- [kafka 命令行工具常用命令行操作](https://blog.csdn.net/asd136912/article/details/103735037)
- [Kafka 2.2 Documentation](https://kafka.apache.org/22/documentation.html#api)
- [Kafka 2.1 Documentation](https://kafka.apache.org/21/documentation.html#api)