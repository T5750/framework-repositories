# Trino Docker

Trino is a high performance, distributed SQL query engine for big data.

## Docker
```sh
docker run --name trino -d -p 8080:8080 trinodb/trino
```
[http://localhost:8080/](http://localhost:8080/)

### Executing queries
```sh
docker exec -it trino trino
trino> select count(*) from tpch.sf1.nation;
```
Once you are done with your exploration, enter the `quit` command.

### Configuring Trino
```sh
docker run --name trino -d -p 8080:8080 --volume $PWD/etc:/etc/trino trinodb/trino
```
To avoid having to create catalog files and mount them in the container, you can enable dynamic catalog management by setting the `CATALOG_MANAGEMENT` environmental variable to `dynamic`.
```sh
docker run --name trino -d -p 8080:8080 -e CATALOG_MANAGEMENT=dynamic trinodb/trino
```

## Connectors
### MySQL
#### Configuration
`vi /etc/trino/catalog/example.properties`
```
connector.name=mysql
connection-url=jdbc:mysql://example.net:3306
connection-user=root
connection-password=secret
```

#### Querying MySQL
```sql
SHOW SCHEMAS FROM example;
SHOW TABLES FROM example.web;
DESCRIBE example.web.clicks;
SHOW COLUMNS FROM example.web.clicks;
SELECT * FROM example.web.clicks;
```

## Runtime Environment
- [Java 25](https://github.com/openjdk/jdk)
- [Trino 478](https://trino.io/download)

## References
- [Trino](https://trino.io/)
- [Trino GitHub](https://github.com/trinodb/trino)
- [Trino Docker](https://trino.io/docs/current/installation/containers.html)
- [Trino MySQL connector](https://trino.io/docs/current/connector/mysql.html)