# Debezium Docker

Stream changes from your database.

Debezium is an open source distributed platform for change data capture. Start it up, point it at your databases, and your apps can start responding to all of the inserts, updates, and deletes that other apps commit to your databases. Debezium is durable and fast, so your apps can respond quickly and never miss an event, even when things go wrong.

## Starting the services
### Starting Zookeeper
```
docker run -d -it --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 debezium/zookeeper:1.8.0.Final
```

### Starting Kafka
```
docker run -d -it --name kafka -p 9092:9092 --link zookeeper:zookeeper debezium/kafka:1.8.0.Final
```

### Starting a MySQL database
```
docker run -it --rm --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=debezium -e MYSQL_USER=mysqluser -e MYSQL_PASSWORD=mysqlpw debezium/example-mysql:1.8
```
Or [inventory.sql](https://github.com/debezium/docker-images/blob/main/examples/mysql/1.8/inventory.sql)

### Starting Kafka Connect
```
docker run -d -it --name connect -p 8083:8083 -e GROUP_ID=1 -e CONFIG_STORAGE_TOPIC=my-connect-configs -e OFFSET_STORAGE_TOPIC=my-connect-offsets -e ADVERTISED_HOST_NAME=$(echo $DOCKER_HOST | cut -f3 -d'/' | cut -f1 -d':') --link zookeeper:zookeeper --link kafka:kafka debezium/connect:1.8.0.Final
```

### Starting Debezium UI
```
docker run -d -it --rm --name debezium-ui -p 8086:8080 -e KAFKA_CONNECT_URIS=http://connectIP:8083 debezium/debezium-ui:1.8.0.Final
```
[http://localhost:8086/](http://localhost:8086/)

## Deploying the MySQL connector
### Registering a connector to monitor the inventory database
```json
{
  "name": "inventory-connector",
  "config": {
    "connector.class": "io.debezium.connector.mysql.MySqlConnector",
    "tasks.max": "1",
    "database.hostname": "mysql",
    "database.port": "3306",
    "database.user": "debezium",
    "database.password": "dbz",
    "database.server.id": "184054",
    "database.server.name": "dbserver1",
    "database.include.list": "inventory",
    "database.history.kafka.bootstrap.servers": "kafka:9092",
    "database.history.kafka.topic": "schema-changes.inventory"
  }
}
```
```
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" localhost:8083/connectors/ -d '{ "name": "inventory-connector", "config": { "connector.class": "io.debezium.connector.mysql.MySqlConnector", "tasks.max": "1", "database.hostname": "mysql", "database.port": "3306", "database.user": "debezium", "database.password": "dbz", "database.server.id": "184054", "database.server.name": "dbserver1", "database.include.list": "inventory", "database.history.kafka.bootstrap.servers": "kafkaIP:9092", "database.history.kafka.topic": "dbhistory.inventory" } }'
curl -H "Accept:application/json" localhost:8083/connectors/
curl -i -X GET -H "Accept:application/json" localhost:8083/connectors/inventory-connector
```

## Viewing change events
```
docker run -it --rm --name watcher --link zookeeper:zookeeper --link kafka:kafka debezium/kafka:1.8.0.Final watch-topic -a -k dbserver1.inventory.customers
```

## Cleaning up
```
$ docker stop mysqlterm watcher connect mysql kafka zookeeper
```

## Architecture
![](https://debezium.io/documentation/reference/1.8/_images/debezium-architecture.png)

### Debezium Server
Another way to deploy Debezium is using the [Debezium server](https://debezium.io/documentation/reference/1.8/operations/debezium-server.html). The Debezium server is a configurable, ready-to-use application that streams change events from a source database to a variety of messaging infrastructures.

![](https://debezium.io/documentation/reference/1.8/_images/debezium-server-architecture.png)

## Screenshots
![](https://debezium.io/documentation/reference/1.8/_images/debezium-ui-connectors-list.png)

![](https://debezium.io/documentation/reference/1.8/_images/debezium-ui-create-connector-step1.png)

![](https://debezium.io/documentation/reference/1.8/_images/debezium-ui-create-connector-step2.png)

![](https://debezium.io/documentation/reference/1.8/_images/debezium-ui-create-connector-review.png)

## References
- [Debezium Tutorial](https://debezium.io/documentation/reference/1.8/tutorial.html)
- [Debezium UI](https://debezium.io/documentation/reference/1.8/operations/debezium-ui.html)
- [Debezium Github](http://www.github.com/debezium/)
- [Debezium UI Github](https://github.com/debezium/debezium-ui)
- [debezium/connect](https://hub.docker.com/r/debezium/connect)