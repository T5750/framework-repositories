# RabbitMQ Docker

RabbitMQ is an open source multi-protocol messaging broker.

## Docker
```
docker run -d --hostname rabbitmq --name rabbitmq rabbitmq:3
```

### Management Plugin
```
docker run -d --hostname rabbitmq --name rabbitmq -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin -p 15672:15672 rabbitmq:3-management
```
[http://localhost:15672/](http://localhost:15672/)

## Federated Queues
![](https://www.rabbitmq.com/img/federation/federated_queues00.png)

![](https://www.rabbitmq.com/img/federation/federated_queues01.png)

## References
- [RabbitMQ Docker](https://hub.docker.com/_/rabbitmq)
- [Federated Queues](https://www.rabbitmq.com/federated-queues.html)