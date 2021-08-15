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
- [Docker Compose WordPress](../../doc/source/docker/dockerComposeWordPress.md)

### DevOps
- [Jenkins Docker](../../doc/source/framework/devops/jenkinsDocker.md)
- [Jenkins Pipeline](../../doc/source/framework/devops/jenkinsPipeline.md)
- [Nexus Docker](../../doc/source/framework/devops/nexusDocker.md)
- [GitLab Docker](../../doc/source/framework/devops/gitlabDocker.md)
- [Zentao Docker](../../doc/source/framework/devops/zentaoDocker.md)

## Networks

### Databases

image | networks | container_name | ports
---|---|---|---
postgres | 172.18.0.100 | pgmaster | 5432:5432
postgres | 172.18.0.101 | pgslave | 5431:5432
pgadmin4 | 172.18.0.102 | pgadmin4 | 5433:80
mysql:5.7 | 172.18.0.105 | mysql_master | 3306:3306
mysql:5.7 | 172.18.0.106 | mysql_slave | 3307:3306
mysql | 172.18.0.107 | mysql8master | 3306:3306
mysql | 172.18.0.108 | mysql8slave | 3307:3306
redis | 172.18.0.111 | redis1 | 6379:6379
redis | 172.18.0.112 | redis2 | 6380:6379
redis | 172.18.0.113 | redis3 | 6381:6379
redis | 172.18.0.114 | redis4 | 6382:6379
redis | 172.18.0.115 | redis5 | 6383:6379
redis | 172.18.0.116 | redis6 | 6384:6379
redisinsight | 172.18.0.117 | redisinsight | 8001:8001
cassandra | 172.18.0.121 | cassandra1 | 9041:9042
cassandra | 172.18.0.122 | cassandra2 | 9042:9042
cassandra | 172.18.0.123 | cassandra3 | 9043:9042
mongo | 172.18.0.131 | mongo | 27017:27017
mongo-express | 172.18.0.134 | mongo-express | 8081:8081

### Middleware

image | networks | container_name | ports
---|---|---|---
dubbo-admin | 172.18.0.140 | admin | 8080:8080
elasticsearch | 172.18.0.191 | es01 | 9200:9200
elasticsearch | 172.18.0.192 | es02 | 9200:9200
elasticsearch | 172.18.0.193 | es03 | 9200:9200
zookeeper | 172.18.0.201 | zoo1 | 2181:2181
zookeeper | 172.18.0.202 | zoo2 | 2182:2181
zookeeper | 172.18.0.203 | zoo3 | 2183:2181
kafka | 172.18.0.204 | kafka1 | 9091:9092
kafka | 172.18.0.205 | kafka2 | 9092:9092
kafka | 172.18.0.206 | kafka3 | 9093:9092
kafka-manager | 172.18.0.207 | kafka-manager | 9000:9000
nacos-server | 172.18.0.208 | nacos1 | 8848:8848
nacos-server | 172.18.0.209 | nacos2 | 8849:8848
nacos-server | 172.18.0.210 | nacos3 | 8850:8848
minio | 172.18.0.211 | minio1 | 9000
minio | 172.18.0.212 | minio2 | 9000
minio | 172.18.0.213 | minio3 | 9000
minio | 172.18.0.214 | minio4 | 9000
kong | 172.18.0.215 | kong-migrations | 
kong | 172.18.0.216 | kong-migrations-up | 
kong | 172.18.0.217 | kong | 8005:8001
konga | 172.18.0.218 |  | 
konga | 172.18.0.219 |  | 1337:1337
tb-postgres | 172.18.0.220 | tb | 9090:9090, 1883:1883
xxl-job-admin | 172.18.0.230 | xxl-job | 8080:8080
xxl-job-admin | 172.18.0.231 | xxl-job | 8080:8080
zentao | localhost | zentao | 8080:80

## Runtime Environment
- [Docker 19.x](https://www.docker.com/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)