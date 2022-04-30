# Cloudreve Docker

Cloudreve能助您以最低的成本快速搭建公私兼备的网盘系统

## DEMO
[VIEW DEMO](https://demo.cloudreve.org/)

## Docker
```
docker run -d \
  --name cloudreve \
  -e PUID=1000 \ # optional
  -e PGID=1000 \ # optional
  -e TZ="Asia/Shanghai" \ # optional
  -p 5212:5212 \
  --restart=unless-stopped \
  -v <PATH TO uploads>:/cloudreve/uploads \
  -v <PATH TO config>:/cloudreve/config \
  -v <PATH TO db>:/cloudreve/db \
  -v <PATH TO avatar>:/cloudreve/avatar \
  xavierniu/cloudreve

docker run -d \
  --name cloudreve \
  -e TZ="Asia/Shanghai" \
  -p 5212:5212 \
  --restart=unless-stopped \
  xavierniu/cloudreve:3.5.0
```
- [http://localhost:5212/](http://localhost:5212/)
- User: admin@cloudreve.org / 密码在首次启动时随机生成，在控制台日志中

## Upgrading
```
docker stop cloudreve \
  && docker rm cloudreve \
  && docker pull xavierniu/cloudreve
```

## Runtime Environment
- [Go](https://golang.org/)
- [Gin](https://github.com/gin-gonic/gin)

## Screenshots
![](https://raw.githubusercontent.com/cloudreve/docs/master/images/homepage.png)

## References
- [Cloudreve Docker](https://hub.docker.com/r/xavierniu/cloudreve)
- [Cloudreve GitHub](https://github.com/cloudreve/Cloudreve)
- [Cloudreve](https://cloudreve.org/)