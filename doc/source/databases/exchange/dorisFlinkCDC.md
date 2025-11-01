# Doris Flink CDC

基于 Flink CDC 快速构建 MySQL 到 Doris 的 Streaming ELT 作业，包含整库同步、表结构变更同步和分库分表同步的功能。

Flink Doris Connector是通过 Flink 来读取和写入数据到 Doris 集群，同时集成了FlinkCDC，可以更便捷的对上游 MySQL 等数据库进行整库同步。

## Docker
- `doris.yml`
- Doris: [http://localhost:8030/](http://localhost:8030/)
- User: root | admin / 密码为空
- `flink.yml`
- Flink: [http://localhost:8081/](http://localhost:8081/)

## Flink Doris Connector
- [Flink Doris Connector 24.0.1](https://doris.apache.org/download#doris-ecosystem)
- [flink-sql-connector-mysql-cdc-3.5.0](https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-connector-mysql-cdc/3.5.0/flink-sql-connector-mysql-cdc-3.5.0.jar)
- [MySQL Connector Java](https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.27/mysql-connector-java-8.0.27.jar)

```sh
#wget https://repository.apache.org/content/repositories/releases/org/apache/doris/flink-doris-connector-1.20/24.0.1/flink-doris-connector-1.20-24.0.1.jar
#wget https://repo.maven.apache.org/maven2/org/apache/flink/flink-sql-connector-mysql-cdc/3.5.0/flink-sql-connector-mysql-cdc-3.5.0.jar
#wget https://repo1.maven.org/maven2/mysql/mysql-connector-java/8.0.27/mysql-connector-java-8.0.27.jar
docker cp flink-doris-connector-1.20-24.0.1.jar jobmanager:/opt/flink/lib
docker cp flink-sql-connector-mysql-cdc-3.5.0.jar jobmanager:/opt/flink/lib
docker cp mysql-connector-java-8.0.27.jar jobmanager:/opt/flink/lib
docker cp flink-doris-connector-1.20-24.0.1.jar taskmanager:/opt/flink/lib
docker cp flink-sql-connector-mysql-cdc-3.5.0.jar taskmanager:/opt/flink/lib
docker cp mysql-connector-java-8.0.27.jar taskmanager:/opt/flink/lib
```

### MySQL 整库同步
```sh
docker exec -it jobmanager bash
bin/flink run \
    -Dexecution.checkpointing.interval=10s \
    -Dparallelism.default=1 \
    -c org.apache.doris.flink.tools.cdc.CdcTools \
    lib/flink-doris-connector-1.20-24.0.1.jar \
    mysql-sync-database \
    --database test_db \
    --mysql-conf hostname=127.0.0.1 \
    --mysql-conf port=3306 \
    --mysql-conf username=root \
    --mysql-conf password=123456 \
    --mysql-conf database-name=mysql_db \
    --including-tables "tbl1|test.*" \
    --sink-conf fenodes=127.0.0.1:8030 \
    --sink-conf username=root \
    --sink-conf password=123456 \
    --sink-conf jdbc-url=jdbc:mysql://127.0.0.1:9030 \
    --sink-conf sink.label-prefix=label \
    --table-conf replication_num=1
```

## Runtime Environment
- [Doris 2.1](https://doris.apache.org/zh-CN/download)
- [Flink Doris Connector 24.0.1](https://doris.apache.org/zh-CN/download)
- [Flink 1.20](https://flink.apache.org/downloads/)
- [Flink CDC 3.5.0](https://github.com/apache/flink-cdc/releases)

## 使用原理
![](https://doris.apache.org/zh-CN/assets/images/FlinkConnectorPrinciples-JDBC-Doris-7726ceb2bfe36b6d1b4e0446381d0e83.png)

## Screenshots
![](https://nightlies.apache.org/flink/flink-cdc-docs-release-3.5/fig/mysql-doris-tutorial/mysql-to-doris.png)

![](https://nightlies.apache.org/flink/flink-cdc-docs-release-3.5/fig/mysql-doris-tutorial/doris-display-data.png)

## References
- [Flink Doris Connector](https://doris.apache.org/zh-CN/docs/2.1/ecosystem/flink-doris-connector)
- [Flink Doris Connector GitHub](https://github.com/apache/doris-flink-connector)
- [Streaming ELT 同步 MySQL 到 Doris](https://nightlies.apache.org/flink/flink-cdc-docs-release-3.5/zh/docs/get-started/quickstart/mysql-to-doris/)
- [Flink CDC](https://nightlies.apache.org/flink/flink-cdc-docs-stable)
- [Flink CDC GitHub](https://github.com/apache/flink-cdc)
- [Flink Sources 连接器](https://nightlies.apache.org/flink/flink-cdc-docs-release-3.5/zh/docs/connectors/flink-sources/overview/)