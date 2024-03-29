# EMQX Docker

大规模分布式 MQTT 消息服务器

## Docker
```sh
docker run -d --name emqx -p 1883:1883 -p 8083:8083 -p 8084:8084 -p 8883:8883 -p 18083:18083 emqx/emqx:5.4.0
# 启动容器并挂载目录
docker run -d --name emqx \
  -p 1883:1883 -p 8083:8083 \
  -p 8084:8084 -p 8883:8883 \
  -p 18083:18083 \
  -v $PWD/data:/opt/emqx/data \
  -v $PWD/log:/opt/emqx/log \
  emqx/emqx:5.4.0
```
- [http://localhost:18083/](http://localhost:18083/)
- User: admin / public

### 忘记密码
```sh
./bin/emqx ctl admins passwd <Username> <Password>
```

## Docker Compose
```
version: '3'

services:
  emqx1:
    image: emqx:5.4.0
    container_name: emqx1
    environment:
    - "EMQX_NODE_NAME=emqx@node1.emqx.io"
    - "EMQX_CLUSTER__DISCOVERY_STRATEGY=static"
    - "EMQX_CLUSTER__STATIC__SEEDS=[emqx@node1.emqx.io,emqx@node2.emqx.io]"
    healthcheck:
      test: ["CMD", "/opt/emqx/bin/emqx ctl", "status"]
      interval: 5s
      timeout: 25s
      retries: 5
    networks:
      emqx-bridge:
        aliases:
        - node1.emqx.io
    ports:
      - 1883:1883
      - 8083:8083
      - 8084:8084
      - 8883:8883
      - 18083:18083 
    # volumes:
    #   - $PWD/emqx1_data:/opt/emqx/data

  emqx2:
    image: emqx:5.4.0
    container_name: emqx2
    environment:
    - "EMQX_NODE_NAME=emqx@node2.emqx.io"
    - "EMQX_CLUSTER__DISCOVERY_STRATEGY=static"
    - "EMQX_CLUSTER__STATIC__SEEDS=[emqx@node1.emqx.io,emqx@node2.emqx.io]"
    healthcheck:
      test: ["CMD", "/opt/emqx/bin/emqx ctl", "status"]
      interval: 5s
      timeout: 25s
      retries: 5
    networks:
      emqx-bridge:
        aliases:
        - node2.emqx.io
    # volumes:
    #   - $PWD/emqx2_data:/opt/emqx/data

networks:
  emqx-bridge:
    driver: bridge
```

查看集群状态
```
docker exec -it emqx1 sh -c "emqx ctl cluster status"
Cluster status: #{running_nodes => ['emqx@node1.emqx.com','emqx@node2.emqx.com'],
                  stopped_nodes => []}
```

### Runtime Environment
- [Erlang](https://www.erlang.org/downloads)

### Screenshots
![](https://www.emqx.io/docs/assets/architecture_image.f5sZc1A2.png)

![](https://www.emqx.io/docs/assets/dashboard-preview.pLCSCcZ3.png)

![](https://www.emqx.io/docs/assets/emqx-dashboard.c8MXyVxN.png)

## MQTTX Web
MQTTX Web 是一款开源的 MQTT 5.0 浏览器客户端，也是一个在线 MQTT WebSocket 客户端工具。使用 WebSocket 在浏览器中连接到 MQTT，帮助开发者更快地开发和调试 MQTT 服务和应用程序，而不必在本地下载和安装 MQTTX。

### Demo
[MQTTX Web](http://www.emqx.io/online-mqtt-client#/recent_connections)

### CLI
```sh
docker run -it --rm emqx/mqttx-cli
```

### Web
```sh
docker run -d --name mqttx-web -p 80:80 emqx/mqttx-web
```

### Runtime Environment
- [Vue.js](https://github.com/vuejs/vue)
- [Node.js](https://nodejs.org/en/download)

### Screenshots
![](https://mqttx.app/images/mqttx-web-preview.png)

## References
- [EMQX](https://www.emqx.io/)
- [EMQX GitHub](https://github.com/emqx/emqx)
- [EMQX Docker](https://www.emqx.io/docs/zh/v5.4/deploy/install-docker.html)
- [EMQX 快速开始](https://www.emqx.io/docs/zh/v5.4/getting-started/getting-started.html)
- [MQTTX Download](https://mqttx.app/downloads?os=docker)