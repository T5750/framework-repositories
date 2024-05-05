# Kafka Docker

Apache Kafka is an open-source distributed event streaming platform used by thousands of companies for high-performance data pipelines, streaming analytics, data integration, and mission-critical applications.

## Docker
```sh
docker run -d --name kafka -p 9092:9092 apache/kafka:3.7.0
docker run -d --name kafka -p 9092:9092 --privileged=true apache/kafka:3.7.0
```

## Tests
```sh
docker exec -it kafka bash
cd /opt/kafka
# STEP 3: CREATE A TOPIC TO STORE YOUR EVENTS
bin/kafka-topics.sh --create --topic quickstart-events --bootstrap-server localhost:9092
bin/kafka-topics.sh --describe --topic quickstart-events --bootstrap-server localhost:9092
# STEP 4: WRITE SOME EVENTS INTO THE TOPIC
bin/kafka-console-producer.sh --topic quickstart-events --bootstrap-server localhost:9092
# STEP 5: READ THE EVENTS
bin/kafka-console-consumer.sh --topic quickstart-events --from-beginning --bootstrap-server localhost:9092
```

## References
- [Kafka](https://kafka.apache.org/)
- [Kafka GitHub](https://github.com/apache/kafka)
- [Kafka Docker](https://kafka.apache.org/documentation/#docker)
- [Kafka Quickstart](https://kafka.apache.org/quickstart)
- [Kafka Usage guide](https://github.com/apache/kafka/blob/trunk/docker/examples/README.md)