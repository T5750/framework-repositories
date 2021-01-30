# Docker

## Contents
- [Docker Installation](../../doc/source/docker/dockerInstallation.md)
- [Docker Compose Installation](../../doc/source/docker/dockerComposeInstallation.md)
- [Docker Quickstart](../../doc/source/docker/dockerQuickstart.md)
- [Docker One Hour](../../doc/source/docker/dockerOneHour.md)
- [Docker Desktop for Windows](../../doc/source/docker/dockerDesktopWindows.md)
- [Docker ELK](../../doc/source/docker/dockerELK.md)
- [Docker Compose ELK](../../doc/source/docker/dockerComposeELK.md)
- [Docker Networks](../../doc/source/docker/dockerNetworks.md)

### DevOps
- [Jenkins Docker](../../doc/source/framework/devops/jenkinsDocker.md)
- [Jenkins Pipeline](../../doc/source/framework/devops/jenkinsPipeline.md)
- [Nexus Docker](../../doc/source/framework/devops/nexusDocker.md)
- [GitLab Docker](../../doc/source/framework/devops/gitlabDocker.md)

## Networks

image | networks | container_name | ports
---|---|---|---
postgres | 172.60.0.100 | pgmaster | 5432:5432
postgres | 172.60.0.101 | pgslave | 5431:5432
pgadmin4 | 172.60.0.102 | pgadmin4 | 5433:80
redis | 172.60.0.110 | redis | 6379:6379
redisinsight | 172.60.0.111 | redisinsight | 8001:8001
cassandra | 172.60.0.121 | cassandra1 | 9041:9042
cassandra | 172.60.0.122 | cassandra2 | 9042:9042
cassandra | 172.60.0.123 | cassandra3 | 9043:9042
zookeeper | 172.60.0.201 | zoo1 | 2181:2181
zookeeper | 172.60.0.202 | zoo2 | 2182:2181
zookeeper | 172.60.0.203 | zoo3 | 2183:2181
kafka | 172.60.0.204 | kafka1 | 9091:9092
kafka | 172.60.0.205 | kafka2 | 9092:9092
kafka | 172.60.0.206 | kafka3 | 9093:9092
kafka-manager | 172.60.0.207 | kafka-manager | 9002:9000

## Runtime Environment
- [Docker 19.x](https://www.docker.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)