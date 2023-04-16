# Teleport Docker

Teleport是一款简单易用的堡垒机系统。

## Docker
```sh
mkdir ~/tpdata
docker run --name=teleport -d \
  -v ~/tpdata:/usr/local/teleport/data \
  -p 7190:7190 -p 52089:52089 -p 52189:52189 -p 52389:52389 \
  tp4a/teleport:3.5.6
```
[http://localhost:7190/](http://localhost:7190/)

```
~/tpdata
    |- db        如果使用sqlite数据库，则数据库文件存放在此目录下
    |- etc       teleport核心服务和web服务的配置文件，以及SSH服务端的密钥文件
    |- log       核心服务和web服务的日志文件
    \- replay    录像文件
```

## Screenshots
![](https://docs.tp4a.com/img/web-login.png)

![](https://docs.tp4a.com/img/web-dashboard.png)

![](https://docs.tp4a.com/img/web-bind-oath-1.png)

![](https://docs.tp4a.com/img/web-host-acc.png)

![](https://docs.tp4a.com/img/web-assist-setting.png)

## References
- [Teleport](https://tp4a.com/)
- [Teleport GitHub](https://github.com/tp4a/teleport)
- [Teleport Docker](https://hub.docker.com/r/tp4a/teleport)
- [Teleport 客户端助手](https://docs.tp4a.com/assist/)