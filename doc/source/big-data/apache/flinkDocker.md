# Flink Docker

Stateful Computations over Data Streams

Apache Flink 是一个在有界数据流和无界数据流上进行有状态计算分布式处理引擎和框架。

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
    flink:1.20.2-scala_2.12 jobmanager
```
and one or more TaskManager containers:
```sh
docker run \
    --rm \
    --name=taskmanager \
    --network flink-network \
    --env FLINK_PROPERTIES="${FLINK_PROPERTIES}" \
    flink:1.20.2-scala_2.12 taskmanager
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
    image: flink:1.20.2-scala_2.12
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
    image: flink:1.20.2-scala_2.12
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
    image: flink:1.20.2-scala_2.12
    ports:
      - "8081:8081"
    command: jobmanager
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager

  taskmanager:
    image: flink:1.20.2-scala_2.12
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
    image: flink:1.20.2-scala_2.12
    ports:
      - "8081:8081"
    command: jobmanager
    environment:
      - |
        FLINK_PROPERTIES=
        jobmanager.rpc.address: jobmanager

  taskmanager:
    image: flink:1.20.2-scala_2.12
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
    image: flink:1.20.2-scala_2.12
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

## SQL 客户端
SQL 客户端 的目的是提供一种简单的方式来编写、调试和提交表程序到 Flink 集群上，而无需写一行 Java 或 Scala 代码
```sh
./bin/sql-client.sh
```

## Examples
```sh
docker cp jobmanager:/opt/flink/examples/streaming/WordCount.jar .
```

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)

## Architecture
Flink 运行时由两种类型的进程组成：一个 JobManager 和一个或者多个 TaskManager。
- JobManager 具有许多与协调 Flink 应用程序的分布式执行有关的职责：它决定何时调度下一个 task（或一组 task）、对完成的 task 或执行失败做出反应、协调 checkpoint、并且协调从失败中恢复等等
- TaskManager（也称为 worker）执行作业流的 task，并且缓存和交换数据流

![](https://nightlies.apache.org/flink/flink-docs-release-1.20/fig/processes.svg)

## Screenshots
![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/bounded-unbounded.png)

![](https://nightlies.apache.org/flink/flink-docs-release-1.17/fig/program_dataflow.svg)

## References
- [Flink](https://flink.apache.org/)
- [Flink GitHub](https://github.com/apache/flink)
- [Flink Docker](https://hub.docker.com/_/flink)
- [Flink Docker Setup](https://nightlies.apache.org/flink/flink-docs-release-1.20/docs/deployment/resource-providers/standalone/docker/)
- [Flink 架构](https://nightlies.apache.org/flink/flink-docs-release-1.20/zh/docs/concepts/flink-architecture/)
- [Flink SQL 客户端](https://nightlies.apache.org/flink/flink-docs-release-1.20/zh/docs/dev/table/sqlclient/)