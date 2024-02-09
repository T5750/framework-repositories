# Pulsar Docker

Apache Pulsar is an open-source, distributed messaging and streaming platform built for the cloud.

## Docker
```sh
docker run -it -p 6650:6650 -p 8080:8080 --mount source=pulsardata,target=/pulsar/data --mount source=pulsarconf,target=/pulsar/conf apachepulsar/pulsar:3.1.2 bin/pulsar standalone
docker run -d --name pulsar -p 6650:6650 -p 8080:8080 apachepulsar/pulsar:3.1.2 bin/pulsar standalone
```
[http://localhost:8080/](http://localhost:8080/)

## Get the topic statistics
```sh
curl http://localhost:8080/admin/v2/persistent/public/default/my-topic/stats | python -m json.tool
```

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)

## Architecture
![](https://pulsar.apache.org/assets/images/pulsar-system-architecture-6890df6b0c59a065a56492659ba87933.png)

![](https://pulsar.apache.org/assets/images/pulsar-service-discovery-82df27ebfa89540d04bf34dfa4fa1b8d.png)

## References
- [Pulsar](https://pulsar.apache.org/)
- [Pulsar GitHub](https://github.com/apache/pulsar/)
- [Pulsar Docker](https://pulsar.apache.org/docs/3.1.x/getting-started-docker/)
- [Pulsar Architecture Overview](https://pulsar.apache.org/docs/3.1.x/concepts-architecture-overview/)
- [Deploy a cluster on Docker](https://pulsar.apache.org/docs/3.1.x/deploy-docker/)