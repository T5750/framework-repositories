# Flink Docker

Stateful Computations over Data Streams

![](https://flink.apache.org/img/flink-home-graphic.png)

## Docker
### Starting a Session Cluster on Docker
```sh
FLINK_PROPERTIES="jobmanager.rpc.address: jobmanager"
docker network create flink-network
```
Then we launch the JobManager:
```sh
docker run \
    --rm \
    --name=jobmanager \
    --network flink-network \
    --publish 8081:8081 \
    --env FLINK_PROPERTIES="${FLINK_PROPERTIES}" \
    flink:1.17.0-scala_2.12 jobmanager
```
and one or more TaskManager containers:
```sh
docker run \
    --rm \
    --name=taskmanager \
    --network flink-network \
    --env FLINK_PROPERTIES="${FLINK_PROPERTIES}" \
    flink:1.17.0-scala_2.12 taskmanager
```
[http://localhost:8081/](http://localhost:8081/)

Submission of a job is now possible like this (assuming you have a local distribution of Flink available):
```sh
./bin/flink run ./examples/streaming/TopSpeedWindowing.jar
```

## Screenshots
![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/bounded-unbounded.png)

![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/program_dataflow.svg)

## References
- [Flink](https://flink.apache.org/)
- [Flink GitHub](https://github.com/apache/flink)
- [Flink Docker](https://hub.docker.com/_/flink)
- [Flink Docker Setup](https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/deployment/resource-providers/standalone/docker/)