# Yearning Docker

出色 方便 快捷的 Mysql SQL审核平台

## Docker
```
docker run -d -it --name yearning -p8000:8000 -e MYSQL_USER=root -e MYSQL_ADDR=10.0.0.3:3306 -e MYSQL_PASSWORD=123456 -e MYSQL_DB=Yearning chaiyd/yearning
```
- [http://localhost:8000/](http://localhost:8000/)
- User: admin / Yearning_admin

## Docker Compose
`yearning.yml`

- 第一次安装，取消下列compose 中的注释进行初始化
  * `command: /bin/bash -c "./Yearning install"`
- 升级使用
  * `command: /bin/bash -c "./Yearning migrate"`
- 重置admin密码
  * `command: /bin/bash -c "./Yearning reset_super"`

## Screenshots
![](https://wuchen-1252812685.cos.ap-shanghai.myqcloud.com/img/yearning/15640342046708.jpg)

![](https://wuchen-1252812685.cos.ap-shanghai.myqcloud.com/img/yearning/15640385444117.jpg)

## References
- [cookieY/Yearning GitHub](https://github.com/cookieY/Yearning)
- [zhangsean/yearning Docker](https://hub.docker.com/r/chaiyd/yearning)
- [Yearning](https://yearning.io/)
- [Yearning 容器化](https://guide.yearning.io/container.html)