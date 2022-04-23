# ZFile Docker

此项目是一个在线文件目录的程序, 支持各种对象存储和本地存储, 使用定位是个人放常用工具下载, 或做公共的文件库. 不会向多账户方向开发.

## Docker
```
docker run -d --name=zfile --restart=always \
    -p 8080:8080 \
    -v /root/zfile/db:/root/.zfile/db \
    -v /root/zfile/logs:/root/.zfile/logs \
    zhaojun1998/zfile
```
[http://localhost:8080/](http://localhost:8080/)

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.5.4.RELEASE](https://spring.io/projects/spring-boot)

## Screenshots
![](https://cdn.jun6.net/2021/03/23/c1f4631ee2de4.png)

![](https://cdn.jun6.net/2021/03/23/713741d43b939.png)

![](https://cdn.jun6.net/2021/03/23/9c724383bb506.png)

![](https://cdn.jun6.net/2021/03/23/b00efdfb4892e.png)

![](https://cdn.jun6.net/2021/03/23/e70e04f8cc5b6.png)

## References
- [ZFile Docker](https://hub.docker.com/r/zhaojun1998/zfile)
- [ZFile GitHub](https://github.com/zhaojun1998/zfile)
- [ZFile](https://docs.zfile.vip/)