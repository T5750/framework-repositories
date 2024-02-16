# TDengine Docker

TDengine 是一款专为物联网、工业互联网等场景设计并优化的大数据平台，它能安全高效地将大量设备、数据采集器每天产生的高达 TB 甚至 PB 级的数据进行汇聚、存储、分析和分发，对业务运行状态进行实时监测、预警，提供实时的商业洞察。其核心模块是高性能、集群开源、云原生、极简的时序数据库 TDengine OSS。

## Docker
```sh
docker run -d --name tdengine -p 6030:6030 -p 6041:6041 -p 6043-6049:6043-6049 -p 6043-6049:6043-6049/udp tdengine/tdengine
docker run -d -v ~/data/taos/dnode/data:/var/lib/taos \
  -v ~/data/taos/dnode/log:/var/log/taos \
  -p 6030:6030 -p 6041:6041 -p 6043-6049:6043-6049 -p 6043-6049:6043-6049/udp tdengine/tdengine
```
注意：TDengine 3.0 服务端仅使用 6030 TCP 端口。6041 为 taosAdapter 所使用提供 REST 服务端口。6043-6049 为 taosAdapter 提供第三方应用接入所使用端口，可根据需要选择是否打开。

## 体验 TDengine
```sh
CREATE DATABASE demo;
USE demo;
CREATE TABLE t (ts TIMESTAMP, speed INT);
INSERT INTO t VALUES('2019-07-15 00:00:00', 10);
INSERT INTO t VALUES('2019-07-15 01:00:00', 20);
SELECT * FROM t;
          ts          |   speed   |
===================================
 19-07-15 00:00:00.000|         10|
 19-07-15 01:00:00.000|         20|
Query OK, 2 row(s) in set (0.001700s)
```

## Runtime Environment
- C, C++

## Architecture
![](https://docs.taosdata.com/assets/images/structure-05743da7f120b67765155692c21aee08.webp)

## 应用实践
### TDengine + Telegraf + Grafana
![](https://docs.taosdata.com/assets/images/IT-DevOps-Solutions-Telegraf-889eef1b485cfab0d6f6092b64b5e4a8.webp)

![](https://docs.taosdata.com/assets/images/IT-DevOps-Solutions-telegraf-dashboard-85178ef9c0c054b77307ceda2a33d47a.webp)

## References
- [TDengine](https://www.taosdata.com/)
- [TDengine GitHub](https://github.com/taosdata/TDengine)
- [TDengine Docker](https://docs.taosdata.com/get-started/docker/)
- [TDengine 整体架构](https://docs.taosdata.com/tdinternal/arch/)
- [TDengine + Telegraf + Grafana](https://docs.taosdata.com/application/telegraf/)