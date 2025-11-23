# Presto Docker

快速可靠的 SQL 引擎，适用于数据分析和开放湖仓

## Docker
`vi config.properties`
```
coordinator=true
node-scheduler.include-coordinator=true
http-server.http.port=8080
discovery-server.enabled=true
discovery.uri=http://localhost:8080
```

`vi jvm.config`
```
-server
-Xmx2G
-XX:+UseG1GC
-XX:G1HeapRegionSize=32M
-XX:+UseGCOverheadLimit
-XX:+ExplicitGCInvokesConcurrent
-XX:+HeapDumpOnOutOfMemoryError
-XX:+ExitOnOutOfMemoryError
-Djdk.attach.allowAttachSelf=true
```

```sh
docker run -p 8080:8080 -it -v ./config.properties:/opt/presto-server/etc/config.properties -v ./jvm.config:/opt/presto-server/etc/jvm.config --name presto prestodb/presto:latest
docker run -d -p 8080:8080 --name presto prestodb/presto
```
- 控制台: [http://localhost:8080/](http://localhost:8080/)
- 查询查看器: [http://localhost:8080/ui/dev/index.html](http://localhost:8080/ui/dev/index.html)

### Executing queries
```sh
docker exec -it presto presto-cli
presto> select count(*) from tpch.sf1.nation;
```

## Connectors
### MySQL
#### Configuration
`vi /opt/presto-server/etc/catalog/example.properties`
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
- [Java 17](https://github.com/openjdk/jdk)
- [Python 3.9.x](https://www.python.org/downloads/)
- [Presto 0.295](https://presto.java.net.cn/getting-started/)

## Architecture
![](https://presto.java.net.cn/wp-content/uploads/presto-ecosystem-diagram.png)

## Screenshots
![](https://presto.java.net.cn/docs/current/_images/presto_console.png)

## References
- [Presto](https://prestodb.io/)
- [Presto GitHub](https://github.com/prestodb/presto)
- [Presto Docker](https://github.com/prestodb/presto/blob/master/presto-docs/src/main/sphinx/installation/deploy-docker.rst)
- [Presto 中文](https://presto.java.net.cn/)
- [Presto 控制台](https://presto.java.net.cn/docs/current/clients/presto-console.html)
- [Presto MySQL 连接器](https://presto.java.net.cn/docs/current/connector/mysql.html)