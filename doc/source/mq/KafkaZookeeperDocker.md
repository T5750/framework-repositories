# Kafka Zookeeper Docker

## Kafka Zookeeper Standalone in Docker Compose
`zookeeper-kafka-standalone.yml`

```
kafka-topics.sh --create --zookeeper 172.60.0.211:2181 --replication-factor 1 --partitions 1 --topic kafkazookeeper
kafka-topics.sh --zookeeper 172.60.0.211:2181 --list
kafka-console-producer.sh --broker-list 172.60.0.211:9092 --topic TOPIC
```

## Kafka Zookeeper Cluster in Docker Compose
