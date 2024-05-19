# DolphinScheduler Docker

Apache DolphinScheduler是一个分布式和可扩展的开源工作流协调平台，具有强大的DAG可视化界面

DAG： 全称 Directed Acyclic Graph，简称 DAG。工作流中的 Task 任务以有向无环图的形式组装起来，从入度为零的节点进行拓扑遍历，直到无后继节点为止。举例如下图：

![](https://dolphinscheduler.apache.org/img/new_ui/dev/about/glossary.png)

模块介绍
- dolphinscheduler-master master模块，提供工作流管理和编排服务。
- dolphinscheduler-worker worker模块，提供任务执行管理服务。
- dolphinscheduler-alert 告警模块，提供 AlertServer 服务。
- dolphinscheduler-api web应用模块，提供 ApiServer 服务。
- dolphinscheduler-common 通用的常量枚举、工具类、数据结构或者基类
- dolphinscheduler-dao 提供数据库访问等操作。
- dolphinscheduler-extract extract模块，包含master/worker/alert的sdk
- dolphinscheduler-service service模块，包含Quartz、Zookeeper、日志客户端访问服务，便于server模块和api模块调用
- dolphinscheduler-ui 前端模块

## Docker
- 如果你想要快速体验，推荐使用 standalone-server 镜像，
- 如果你想要体验比较完成的服务，推荐使用 docker-compose 启动服务.
- 如果你已经有自己的数据库或者 Zookeeper 服务你想要沿用这些基础服务，你可以参考沿用已有的 PostgreSQL 和 ZooKeeper 服务完成部署。

```sh
docker run --name dolphinscheduler-standalone-server -p 12345:12345 -p 25333:25333 -d apache/dolphinscheduler-standalone-server:3.2.1
```
- [http://localhost:12345/dolphinscheduler/ui](http://localhost:12345/dolphinscheduler/ui)
- User: admin / dolphinscheduler123

## Docker Compose
[下载页面](https://dolphinscheduler.apache.org/en-us/download/3.2.1)
```sh
$ DOLPHINSCHEDULER_VERSION=3.2.1
$ tar -zxf apache-dolphinscheduler-"${DOLPHINSCHEDULER_VERSION}"-src.tar.gz
# Mac Linux 用户
$ cd apache-dolphinscheduler-"${DOLPHINSCHEDULER_VERSION}"-src/deploy/docker
# Windows 用户, `cd apache-dolphinscheduler-"${DOLPHINSCHEDULER_VERSION}"-src\deploy\docker`

# 如果需要初始化或者升级数据库结构，需要指定profile为schema
$ docker-compose --profile schema up -d

# 启动dolphinscheduler所有服务，指定profile为all
$ docker-compose --profile all up -d
```

## 沿用已有的 PostgreSQL 和 ZooKeeper 服务
```sh
$ DOLPHINSCHEDULER_VERSION=3.2.1
# 初始化数据库，其确保数据库 <DATABASE> 已经存在
$ docker run -d --name dolphinscheduler-tools \
    -e DATABASE="postgresql" \
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/<DATABASE>" \
    -e SPRING_DATASOURCE_USERNAME="<USER>" \
    -e SPRING_DATASOURCE_PASSWORD="<PASSWORD>" \
    -e SPRING_JACKSON_TIME_ZONE="UTC" \
    --net host \
    apache/dolphinscheduler-tools:"${DOLPHINSCHEDULER_VERSION}" tools/bin/upgrade-schema.sh
# 启动 DolphinScheduler 对应的服务
$ docker run -d --name dolphinscheduler-master \
    -e DATABASE="postgresql" \
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/<DATABASE>" \
    -e SPRING_DATASOURCE_USERNAME="<USER>" \
    -e SPRING_DATASOURCE_PASSWORD="<PASSWORD>" \
    -e SPRING_JACKSON_TIME_ZONE="UTC" \
    -e REGISTRY_ZOOKEEPER_CONNECT_STRING="localhost:2181" \
    --net host \
    -d apache/dolphinscheduler-master:"${DOLPHINSCHEDULER_VERSION}"
$ docker run -d --name dolphinscheduler-worker \
    -e DATABASE="postgresql" \
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/<DATABASE>" \
    -e SPRING_DATASOURCE_USERNAME="<USER>" \
    -e SPRING_DATASOURCE_PASSWORD="<PASSWORD>" \
    -e SPRING_JACKSON_TIME_ZONE="UTC" \
    -e REGISTRY_ZOOKEEPER_CONNECT_STRING="localhost:2181" \
    --net host \
    -d apache/dolphinscheduler-worker:"${DOLPHINSCHEDULER_VERSION}"
$ docker run -d --name dolphinscheduler-api \
    -e DATABASE="postgresql" \
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/<DATABASE>" \
    -e SPRING_DATASOURCE_USERNAME="<USER>" \
    -e SPRING_DATASOURCE_PASSWORD="<PASSWORD>" \
    -e SPRING_JACKSON_TIME_ZONE="UTC" \
    -e REGISTRY_ZOOKEEPER_CONNECT_STRING="localhost:2181" \
    --net host \
    -d apache/dolphinscheduler-api:"${DOLPHINSCHEDULER_VERSION}"
$ docker run -d --name dolphinscheduler-alert-server \
    -e DATABASE="postgresql" \
    -e SPRING_DATASOURCE_URL="jdbc:postgresql://localhost:5432/<DATABASE>" \
    -e SPRING_DATASOURCE_USERNAME="<USER>" \
    -e SPRING_DATASOURCE_PASSWORD="<PASSWORD>" \
    -e SPRING_JACKSON_TIME_ZONE="UTC" \
    -e REGISTRY_ZOOKEEPER_CONNECT_STRING="localhost:2181" \
    --net host \
    -d apache/dolphinscheduler-alert-server:"${DOLPHINSCHEDULER_VERSION}"
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://dolphinscheduler.apache.org/img/architecture-1.3.0.jpg)

![](https://dolphinscheduler.apache.org/img/process-start-flow-1.3.0.png)

## Screenshots
![](https://dolphinscheduler.apache.org/img/introduction_ui.png)

![](https://dolphinscheduler.apache.org/img/new_ui/dev/project/workflow-dependent.png)

## References
- [DolphinScheduler](https://dolphinscheduler.apache.org/zh-cn)
- [DolphinScheduler GitHub](https://github.com/apache/dolphinscheduler)
- [DolphinScheduler Docker](https://dolphinscheduler.apache.org/zh-cn/docs/3.2.1/guide/start/docker)
- [DolphinScheduler 系统架构设计](https://dolphinscheduler.apache.org/zh-cn/docs/3.2.1/architecture/design)
- [DolphinScheduler 名词解释](https://dolphinscheduler.apache.org/zh-cn/docs/3.2.1/about/glossary)