# Kafka Zookeeper Docker

## Kafka Zookeeper Standalone in Docker Compose
`zookeeper-kafka-standalone.yml`

```
kafka-topics.sh --create --zookeeper 172.18.0.201:2181 --replication-factor 1 --partitions 1 --topic kafkazookeeper
kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic kafkazookeeper
kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic kafkazookeeper --from-beginning

kafka-topics.sh --zookeeper 172.18.0.201:2181 --list
kafka-topics.sh --zookeeper 172.18.0.201:2181 --topic kafkazookeeper --describe
kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --list
#kafka-consumer-groups.sh --zookeeper 172.18.0.201:2181 --list
kafka-consumer-groups.sh --bootstrap-server 127.0.0.1:9092 --group console-consumer-99577 --describe
kafka-run-class.sh kafka.tools.GetOffsetShell --broker-list 127.0.0.1:9092 --topic kafkazookeeper
kafka-topics.sh --zookeeper 172.18.0.201:2181 --delete --topic kafkazookeeper
```

### CMAK
- [CMAK](https://github.com/yahoo/CMAK)
- [http://localhost:9000/](http://localhost:9000/)

## Kafka Zookeeper Cluster in Docker Compose
`zookeeper-kafka.yml`

```
kafka-topics.sh --create --zookeeper 172.18.0.201:2181,172.18.0.202:2181,172.18.0.203:2181 --replication-factor 1 --partitions 1 --topic kafkazookeeper
kafka-topics.sh --zookeeper 172.18.0.201:2181,172.18.0.202:2181,172.18.0.203:2181 --list
```

## References
- [kafka 命令行工具常用命令行操作](https://blog.csdn.net/asd136912/article/details/103735037)