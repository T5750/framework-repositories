# IoTDB Docker

Apache IoTDB（物联网数据库）是一体化收集、存储、管理与分析物联网时序数据的软件系统。 Apache IoTDB 采用轻量式架构，具有高性能和丰富的功能，并与Apache Hadoop、Spark和Flink等进行了深度集成，可以满足工业物联网领域的海量数据存储、高速数据读取和复杂数据分析需求。

## Docker
```
docker run -d -p 6667:6667 -p 31999:31999 -p 8181:8181 --name iotdb apache/iotdb
docker exec -it iotdb /bin/bash
cd /iotdb/
```
By default, the ports that IoTDB uses are:
- 6667: RPC port
- 31999: JMX port
- 8181: Monitor port
- 5555: Data sync port
- 9003: internal metadata rpc port (for cluster)
- 40010: internal data rpc port (for cluster)

### Use Cli
```
sbin/start-cli.sh -h 127.0.0.1 -p 6667 -u root -pw root
```

## 应用场景
![](https://iotdb.apache.org/img/home-Slide1.png)

![](https://iotdb.apache.org/img/home-Slide2.png)

![](https://iotdb.apache.org/img/home-Slide3.png)

## References
- [IoTDB 下载与安装](https://iotdb.apache.org/zh/UserGuide/Master/QuickStart/WayToGetIoTDB.html)
- [IoTDB Docker](https://hub.docker.com/r/apache/iotdb)
- [IoTDB GitHub](https://github.com/apache/iotdb)