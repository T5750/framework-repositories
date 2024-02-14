# HertzBeat Docker

易用友好的开源实时监控系统

## Demo
[云服务](https://console.tancloud.cn/)

## Docker
```sh
docker run -d -p 1157:1157 -p 1158:1158 \
    -e LANG=zh_CN.UTF-8 \
    -e TZ=Asia/Shanghai \
    -v $(pwd)/data:/opt/hertzbeat/data \
    -v $(pwd)/logs:/opt/hertzbeat/logs \
    -v $(pwd)/application.yml:/opt/hertzbeat/config/application.yml \
    -v $(pwd)/sureness.yml:/opt/hertzbeat/config/sureness.yml \
    --restart=always \
    --name hertzbeat tancloud/hertzbeat
docker run -d -p 1157:1157 -p 1158:1158 --name hertzbeat tancloud/hertzbeat
```
- [http://localhost:1157/](http://localhost:1157/)
- User: admin / hertzbeat

部署采集器集群(可选)
```sh
docker run -d \
    -e IDENTITY=custom-collector-name \
    -e MODE=public \
    -e MANAGER_HOST=127.0.0.1 \
    -e MANAGER_PORT=1158 \
    --name hertzbeat-collector tancloud/hertzbeat-collector
```
- `-e MANAGER_HOST=127.0.0.1` : 重要⚠️ 设置连接的主HertzBeat服务地址IP

## Docker Compose
- [docker-compose部署脚本](https://github.com/dromara/hertzbeat/tree/master/script/docker-compose) 统一安装 hertzbeat+mysql+iotdb/tdengine
- [docker-compose部署方案](https://github.com/dromara/hertzbeat/tree/master/script/docker-compose/README.md)

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://hertzbeat.com/zh-cn/img/docs/hertzbeat-arch.png)

## Screenshots
![](https://hertzbeat.com/zh-cn/img/home/0.png)

![](https://hertzbeat.com/zh-cn/img/home/1.png)

![](https://hertzbeat.com/zh-cn/img/home/2.png)

## References
- [HertzBeat](https://hertzbeat.com/zh-cn/)
- [HertzBeat GitHub](https://github.com/dromara/hertzbeat)
- [HertzBeat Docker](https://hertzbeat.com/zh-cn/docs/start/docker-deploy/)