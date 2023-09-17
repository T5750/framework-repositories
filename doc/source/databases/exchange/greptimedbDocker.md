# GreptimeDB Docker

云原生时序数据库

## Linux & Mac
```sh
curl -L https://raw.githubusercontent.com/GreptimeTeam/greptimedb/develop/scripts/install.sh | sh
./greptime standalone start
```

## Docker
```sh
docker run -p 4000-4003:4000-4003 \
-p 4242:4242 -v "$(pwd)/greptimedb:/tmp/greptimedb" \
--name greptime --rm \
greptime/greptimedb standalone start \
--http-addr 0.0.0.0:4000 \
--rpc-addr 0.0.0.0:4001 \
--mysql-addr 0.0.0.0:4002 \
--postgres-addr 0.0.0.0:4003 \
--opentsdb-addr 0.0.0.0:4242
```
[http://localhost:4000/dashboard](http://localhost:4000/dashboard)

## 建表
```
CREATE TABLE IF NOT EXISTS system_metrics (
    host STRING,
    idc STRING,
    cpu_util DOUBLE,
    memory_util DOUBLE,
    disk_util DOUBLE,
    ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(host, idc),
    TIME INDEX(ts)
);
```

## 数据写入
```
INSERT INTO system_metrics
VALUES
    ("host1", "idc_a", 11.8, 10.3, 10.3, 1667446797450),
    ("host1", "idc_a", 80.1, 70.3, 90.0, 1667446797550),
    ("host1", "idc_b", 50.0, 66.7, 40.6, 1667446797650),
    ("host1", "idc_b", 51.0, 66.5, 39.6, 1667446797750),
    ("host1", "idc_b", 52.0, 66.9, 70.6, 1667446797850),
    ("host1", "idc_b", 53.0, 63.0, 50.6, 1667446797950),
    ("host1", "idc_b", 78.0, 66.7, 20.6, 1667446798050),
    ("host1", "idc_b", 68.0, 63.9, 50.6, 1667446798150),
    ("host1", "idc_b", 90.0, 39.9, 60.6, 1667446798250);
```

## 数据查询
```
SELECT * FROM system_metrics;
SELECT count(*) FROM system_metrics;
SELECT avg(cpu_util) FROM system_metrics;
SELECT idc, avg(memory_util) FROM system_metrics GROUP BY idc;
```

## Runtime Environment
- [Rust](https://www.rust-lang.org)

## Architecture
![](https://docs.greptime.cn/architecture-2.png)

- [Meta](https://docs.greptime.cn/developer-guide/metasrv/overview) 控制着 GreptimeDB 集群的核心命令
- [Frontend](https://docs.greptime.cn/developer-guide/frontend/overview) 作为无状态的组件，可以根据需求进行伸缩扩容
- [Datanode](https://docs.greptime.cn/developer-guide/datanode/overview) 负责 GreptimeDB 集群中表和数据的存储，接收并执行从 Frontend 发来的读写请求

## Screenshots
![](https://docs.greptime.cn/assets/dashboard-select.1aeca426.png)

![](https://docs.greptime.cn/assets/dashboard-line.fbf7ed30.png)

![](https://docs.greptime.cn/assets/dashboard-scatter.81e9ccf6.png)

## References
- [GreptimeDB](https://greptime.cn/)
- [GreptimeDB GitHub](https://github.com/greptimeteam)
- [GreptimeDB Docker](https://docs.greptime.cn/getting-started/overview)
- [GreptimeDB 基础架构](https://docs.greptime.cn/user-guide/concepts/architecture)
- [GreptimeDB Java SDK 库](https://docs.greptime.cn/user-guide/clients/sdk-libraries/java)