# ZFile Docker

最方便快捷的在线目录展示程序，支持将本地文件、FTP、SFTP、S3、OneDrive 等存储在网站上展示并浏览.

## Demo
[查看演示](https://demo.zfile.vip/)

## Docker
```
docker run -d --name=zfile --restart=always \
    -p 8080:8080 \
    -v /root/zfile/db:/root/.zfile-v4/db \
    -v /root/zfile/logs:/root/.zfile-v4/logs \
    -v /root/zfile/file:/data/file \
    zhaojun1998/zfile
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.3'
services:
    zfile:
        container_name: zfile
        restart: always
        ports:
            - '8080:8080'
        volumes:
            - '/root/zfile/db:/root/.zfile-v4/db'
            - '/root/zfile/logs:/root/.zfile-v4/logs'
            - '/root/zfile/file:/data/file'
        image: zhaojun1998/zfile
```

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
- [ZFile](https://www.zfile.vip/)
- [ZFile Docker](https://hub.docker.com/r/zhaojun1998/zfile)
- [ZFile GitHub](https://github.com/zfile-dev/zfile)
- [ZFile 文档](https://docs.zfile.vip/)