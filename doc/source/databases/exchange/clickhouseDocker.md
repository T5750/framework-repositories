# ClickHouse Docker

ClickHouse is an open-source column-oriented database management system.

## Demo
[ClickHouse Playground](https://play.clickhouse.com/play?user=play)

## Docker
```sh
docker run -d --name clickhouse -p 8123:8123 -p 9000:9000 -e TZ=Asia/Shanghai -e CLICKHOUSE_PASSWORD=password --ulimit nofile=262144:262144 clickhouse/clickhouse-server
```
- [http://localhost:8123/](http://localhost:8123/)
- [http://localhost:8123/play](http://localhost:8123/play)

## Screenshots
![](https://clickhouse.com/docs/assets/images/play-e6b0e90bcd6089c309d037a10e824075.png)

## References
- [ClickHouse](https://clickhouse.com/)
- [ClickHouse GitHub](https://github.com/ClickHouse/ClickHouse)
- [ClickHouse Docker](https://hub.docker.com/r/clickhouse/clickhouse-server/)