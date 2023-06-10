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

## Docker Compose
### Application Mode
```
version: "2.2"
services:
  jobmanager:
    image: flink:1.17.1-scala_2.12
    ports:
      - "8081:8081"
    command: standalone-job --job-classname com.job.ClassName [--job-id <job id>] [--fromSavepoint /path/to/savepoint [--allowNonRestoredState]] [job arguments]
    volumes:
      - /host/path/to/job/artifacts:/opt/flink/usrlib
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager
        parallelism.default: 2

  taskmanager:
    image: flink:1.17.1-scala_2.12
    depends_on:
      - jobmanager
    command: taskmanager
    scale: 1
    volumes:
      - /host/path/to/job/artifacts:/opt/flink/usrlib
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager
        taskmanager.numberOfTaskSlots: 2
        parallelism.default: 2
```

### Session Mode
```
version: "2.2"
services:
  jobmanager:
    image: flink:1.17.1-scala_2.12
    ports:
      - "8081:8081"
    command: jobmanager
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager

  taskmanager:
    image: flink:1.17.1-scala_2.12
    depends_on:
      - jobmanager
    command: taskmanager
    scale: 1
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager
        taskmanager.numberOfTaskSlots: 2
```

### Flink SQL Client with Session Cluster
```
version: "2.2"
services:
  jobmanager:
    image: flink:1.17.1-scala_2.12
    ports:
      - "8081:8081"
    command: jobmanager
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager

  taskmanager:
    image: flink:1.17.1-scala_2.12
    depends_on:
      - jobmanager
    command: taskmanager
    scale: 1
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager
        taskmanager.numberOfTaskSlots: 2
  sql-client:
    image: flink:1.17.1-scala_2.12
    command: bin/sql-client.sh
    depends_on:
      - jobmanager
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager
        rest.address: jobmanager
```
```sh
docker-compose run sql-client
```

## Screenshots
![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/bounded-unbounded.png)

![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/program_dataflow.svg)

## References
- [Flink](https://flink.apache.org/)
- [Flink GitHub](https://github.com/apache/flink)
- [Flink Docker](https://hub.docker.com/_/flink)
- [Flink Docker Setup](https://nightlies.apache.org/flink/flink-docs-release-1.17/docs/deployment/resource-providers/standalone/docker/)