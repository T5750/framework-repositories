# ZooKeeper Web UI Docker

This images contains the latest release of [zk-web](https://github.com/qiuxiafei/zk-web) on a minimal Alpine Linux base image with Java 8.

## Configuration options
- `HTTP_PORT`: The container porton which the `zk-web` application should run. Default 8080.
- `USER`: The user name for accessing the frontend
- `PASSWORD`: The password for accessing the frontend
- `ZK_DEFAULT_NODE`: The default node address, i.e. `192.168.0.100:2181/`

## Docker Compose
`zookeeper-webui.yml`

- [http://localhost:8080](http://localhost:8080)
- User: admin / admin

## References
- [ZooKeeper Web UI (zk-web) as Docker image](https://hub.docker.com/r/tobilg/zookeeper-webui)