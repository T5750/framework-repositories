# HiveMQ Docker

## HiveMQ Community Edition
HiveMQ CE is a Java-based open source MQTT broker that fully supports MQTT 3.x and MQTT 5.

```
docker run --name hivemq-ce -d -p 1883:1883 hivemq/hivemq-ce
```

### Defining the Log Level
```
docker run --name hivemq-ce -e HIVEMQ_LOG_LEVEL=INFO -d -p 1883:1883 hivemq/hivemq-ce
```

## HiveMQ 4
HiveMQ is a fast, efficient, secure and reliable MQTT based messaging platform for enterprises.

```
docker run --name hivemq4 -d -p 8080:8080 -p 1883:1883 hivemq/hivemq4
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / hivemq

>License Warning: No valid license file found. Using evaluation license, restricted to 25 connections. 

## Architecture
![](https://www.hivemq.com/img/hivemq-platform.png)

## Screenshots
![](https://www.hivemq.com/img/downloads/hivemq-control-center.png)

![](https://www.hivemq.com/docs/hivemq/4.7/user-guide/_images/getting-started/dashboard.png)

![](https://www.hivemq.com/docs/hivemq/4.7/user-guide/_images/getting-started/clients-overview.png)

## References
- [hivemq4 Docker](https://hub.docker.com/r/hivemq/hivemq4)
- [hivemq-ce Docker](https://hub.docker.com/r/hivemq/hivemq-ce)
- [Getting Started with HiveMQ](https://www.hivemq.com/docs/hivemq/4.7/user-guide/getting-started.html)