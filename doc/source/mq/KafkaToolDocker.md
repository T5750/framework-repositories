# Kafka Tool Docker

## CMAK
CMAK is a tool for managing Apache Kafka clusters

### hlebalbau/kafka-manager
#### Docker
```sh
docker run -d --name kafka-manager \
     -p 9000:9000  \
     -e ZK_HOSTS="localhost:2181" \
     hlebalbau/kafka-manager:stable
```
[http://localhost:9000/](http://localhost:9000/)

#### Docker Compose
`kafka-manager.yml`

### Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

### Screenshots
![](https://github.com/yahoo/CMAK/raw/master/img/cluster.png)

![](https://github.com/yahoo/CMAK/raw/master/img/topic-list.png)

![](https://github.com/yahoo/CMAK/raw/master/img/topic.png)

## Kafdrop
Kafka Web UI

### Docker
```sh
docker run -d --name kafdrop --rm -p 9000:9000 \
    -e KAFKA_BROKERCONNECT=172.18.0.204:9092 \
    -e JVM_OPTS="-Xms32M -Xmx64M" \
    -e SERVER_SERVLET_CONTEXTPATH="/" \
    obsidiandynamics/kafdrop
```
- `kafdrop.yml`
- [http://localhost:9000/](http://localhost:9000/)

### Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

### Screenshots
![](https://github.com/obsidiandynamics/kafdrop/raw/master/docs/images/overview.png?raw=true)

## UI for Apache Kafka
Open-Source Web UI for Apache Kafka Management

### Docker
```sh
docker run -it -p 8080:8080 -e DYNAMIC_CONFIG_ENABLED=true provectuslabs/kafka-ui
docker run -d --name kafka-ui -p 8080:8080 -e DYNAMIC_CONFIG_ENABLED=true provectuslabs/kafka-ui
```
- [http://localhost:8080/](http://localhost:8080/)

### Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- [TypeScript](https://www.typescriptlang.org/)

### Screenshots
![](https://github.com/provectus/kafka-ui/raw/master/documentation/images/Interface.gif)

## AKHQ
Kafka GUI for Apache Kafka ® to manage topics, topics data, consumers group, schema registry, connect and more...

### Docker Compose
- Download [docker-compose.yml](https://raw.githubusercontent.com/tchiotludo/akhq/master/docker-compose.yml) file or `akhq.yml`
- [http://localhost:8080/](http://localhost:8080/)
- Api: [http://localhost:8080/api](http://localhost:8080/api)
- Configuration: [application.example.yml](https://github.com/tchiotludo/akhq/blob/dev/application.example.yml)

### Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

### Screenshots
![](https://akhq.io/assets/images/preview.jpg)

## References
- [CMAK GitHub](https://github.com/yahoo/CMAK)
- [hlebalbau/kafka-manager Docker](https://hub.docker.com/r/hlebalbau/kafka-manager)
- [Kafdrop GitHub](https://github.com/obsidiandynamics/kafdrop)
- [provectus/kafka-ui GitHub](https://github.com/provectus/kafka-ui)
- [AKHQ](https://akhq.io/)
- [AKHQ GitHub](https://github.com/tchiotludo/akhq)