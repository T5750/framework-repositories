# Jpom Docker

一款原生 ops 软件

🚀简而轻的低侵入式在线构建、自动部署、日常运维、项目运维监控软件

## Demo
[演示系统](https://demo.jpom.top)

## Docker
### 一键安装（不推荐）
```sh
docker run -d -p 2122:2122 --name jpom-server --restart=always jpomdocker/jpom
```
[http://localhost:2122/](http://localhost:2122/)

### 挂载安装
```sh
docker run -d -p 2122:2122 \
    --name jpom-server \
    --restart=always \
    -v /etc/localtime:/etc/localtime:ro \
    -v /home/jpom-server/logs:/usr/local/jpom-server/logs \
    -v /home/jpom-server/data:/usr/local/jpom-server/data \
    -v /home/jpom-server/conf:/usr/local/jpom-server/conf \
    jpomdocker/jpom
```

### 卷安装
```sh
docker volume create jpom-server-data
docker volume create jpom-server-logs
docker volume create jpom-server-conf
docker run -d -p 2122:2122 \
    --name jpom-server \
    --restart=always \
    -v /etc/localtime:/etc/localtime:ro \
    -v jpom-server-data:/usr/local/jpom-server/data \
    -v jpom-server-logs:/usr/local/jpom-server/logs \
    -v jpom-server-conf:/usr/local/jpom-server/conf \
    jpomdocker/jpom
```

## Docker Compose
> 需要注意修改 `.env` 文件中的 token 值
```sh
yum install -y git
git clone https://gitee.com/dromara/Jpom.git
cd Jpom
docker-compose -f docker-compose.yml up
# docker-compose -f docker-compose.yml up --build
# docker-compose -f docker-compose.yml build --no-cache
# docker-compose -f docker-compose-local.yml up
# docker-compose -f docker-compose-local.yml build --build-arg TEMP_VERSION=.0
# docker-compose -f docker-compose-cluster.yml up --build
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://jpom.top/images/jpom-func-arch.png)

## Screenshots
![](https://jpom.top/images/tutorial/install/use-docker/img_4.png)

![](https://jpom.top/images/tutorial/install/use-docker/img_5.png)

## References
- [Jpom](https://jpom.top/)
- [Jpom GitHub](https://github.com/dromara/Jpom)
- [Jpom Docker](https://jpom.top/pages/820286/)