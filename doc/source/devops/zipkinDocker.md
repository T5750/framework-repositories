# Zipkin Docker

[Zipkin](https://zipkin.io/) is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in service architectures. Features include both the collection and lookup of this data.

## Docker
```sh
# Note: this is mirrored as ghcr.io/openzipkin/zipkin
docker run -d --name zipkin -p 9411:9411 openzipkin/zipkin
```
[http://localhost:9411/](http://localhost:9411/)

## Docker Compose
`zipkin.yml`

## Zipkin Slim
The slim build of Zipkin is smaller and starts faster. It supports in-memory and Elasticsearch storage, but doesn't support messaging transports like Kafka or RabbitMQ.

```sh
# Note: this is mirrored as ghcr.io/openzipkin/zipkin-slim
docker run -d -p 9411:9411 openzipkin/zipkin-slim
```

## Zipkin Docker Examples
```sh
docker-compose -f docker-compose-cassandra.yml up
docker-compose -f docker-compose-elasticsearch.yml up
docker-compose -f docker-compose-kafka.yml up
docker-compose -f docker-compose-mysql.yml up
docker-compose -f docker-compose-prometheus.yml up
```

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://zipkin.io/public/img/architecture-1.png)

## Screenshots
![](https://zipkin.io/public/img/web-screenshot.png)

![](https://zipkin.io/public/img/dependency-graph.png)

## References
- [Zipkin Docker](https://hub.docker.com/r/openzipkin/zipkin)
- [Zipkin GitHub](https://github.com/openzipkin/zipkin)
- [Zipkin Architecture](https://zipkin.io/pages/architecture.html)
- [Zipkin Docker Examples](https://github.com/openzipkin/zipkin/tree/master/docker/examples)