# Arthas Docker

## Docker
```
docker run -d --rm --name arthas-math-game -it hengyunabc/arthas:latest /bin/sh -c "java -jar /opt/arthas/math-game.jar"
docker exec -it arthas-math-game /bin/sh -c "java -jar /opt/arthas/arthas-boot.jar"
```

### 查看dashboard
```
dashboard
```

### 通过thread命令来获取到math-game进程的Main Class
```
thread 1 | grep 'main('
```

### 通过jad来反编译Main Class
```
jad demo.MathGame
```

### watch
```
watch demo.MathGame primeFactors returnObj
```
## 诊断Docker里的Java进程
```
docker exec -it ${containerId} /bin/bash -c "wget https://arthas.aliyun.com/arthas-boot.jar && java -jar arthas-boot.jar"
```

## 把Arthas安装到基础镜像里
```
FROM openjdk:8-jdk-alpine
# copy arthas
COPY --from=hengyunabc/arthas:latest /opt/arthas /opt/arthas
```

## Arthas Tunnel
[Arthas Tunnel Server](https://github.com/alibaba/arthas/releases)
```
java -jar arthas-tunnel-server-3.5.5-fatjar.jar
```
- http://127.0.0.1:8080/
- http://127.0.0.1:8080/actuator/arthas
- User: arthas, 密码在arthas tunnel server的日志里

### 启动arthas时连接到tunnel server
```
docker exec -it arthas-math-game /bin/sh -c "java -jar /opt/arthas/arthas-boot.jar --tunnel-server 'ws://127.0.0.1:7777/ws' --app-name demoapp"
```
Or `session`
```
  ,---.  ,------. ,--------.,--.  ,--.  ,---.   ,---.
 /  O  \ |  .--. ''--.  .--'|  '--'  | /  O  \ '   .-'
|  .-.  ||  '--'.'   |  |   |  .--.  ||  .-.  |`.  `-.
|  | |  ||  |\  \    |  |   |  |  |  ||  | |  |.-'    |
`--' `--'`--' '--'   `--'   `--'  `--'`--' `--'`-----'
 
wiki      https://arthas.aliyun.com/doc
tutorials https://arthas.aliyun.com/doc/arthas-tutorials.html
version   3.1.2
pid       86183
time      2019-08-30 15:40:53
id        URJZ5L48RPBR2ALI5K4V
```

### Tunnel Server的管理页面
http://localhost:8080/apps.html

![](https://arthas.aliyun.com/doc/_images/arthas-tunnel-server.png)

## Web Console
![](https://arthas.aliyun.com/doc/_images/web-console-local.png)

## References
- [Arthas GitHub](https://github.com/alibaba/arthas)
- [Arthas Docker](https://arthas.aliyun.com/doc/docker.html)
- [Arthas 快速入门](https://arthas.aliyun.com/doc/quick-start.html)
- [Arthas Tunnel](https://arthas.aliyun.com/doc/tunnel.html)