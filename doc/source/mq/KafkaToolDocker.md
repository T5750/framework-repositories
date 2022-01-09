# Kafka Tool Docker

## CMAK

### hlebalbau/kafka-manager
#### Docker
```
docker run -d \
     -p 9000:9000  \
     -e ZK_HOSTS="localhost:2181" \
     hlebalbau/kafka-manager:stable
```
[http://localhost:9000/](http://localhost:9000/)

#### Docker Compose
`kafka-manager.yml`

### Screenshots
![](https://github.com/yahoo/CMAK/raw/master/img/cluster.png)

![](https://github.com/yahoo/CMAK/raw/master/img/topic-list.png)

![](https://github.com/yahoo/CMAK/raw/master/img/topic.png)

## Kafdrop
```
docker run -d --name kafdrop --rm -p 9000:9000 \
    -e KAFKA_BROKERCONNECT=172.18.0.204:9092 \
    -e JVM_OPTS="-Xms32M -Xmx64M" \
    -e SERVER_SERVLET_CONTEXTPATH="/" \
    obsidiandynamics/kafdrop
```
- `kafdrop.yml`
- [http://localhost:9000/](http://localhost:9000/)

### Screenshots
![](https://github.com/obsidiandynamics/kafdrop/raw/master/docs/images/overview.png?raw=true)

## References
- [CMAK Github](https://github.com/yahoo/CMAK)
- [hlebalbau/kafka-manager](https://hub.docker.com/r/hlebalbau/kafka-manager)
- [Kafdrop Github](https://github.com/obsidiandynamics/kafdrop)