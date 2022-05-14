# MCMS Docker

## 源码部署
```
git clone https://gitee.com/mingSoft/MCMS.git
CREATE DATABASE IF NOT EXISTS mcms DEFAULT CHARSET utf8 COLLATE utf8_general_ci;
#source mcms/doc/mcms-5.2.8.sql
/src/main/resources/application-dev.yml
```
- [http://localhost:8080/](http://localhost:8080/)
- User: msopen / msopen

## Docker
```
docker run -d --name mcms -v $PWD/ms-mcms.jar:/maven.jar \
-e TZ=Asia/Shanghai -p 8080:8080 openjdk:8 java -jar maven.jar
```
### nginx
```
docker run --name nginx -d \
-p 80:80 \
-v /data:/data \
-v /data/nginx/logs:/etc/nginx/logs \
-v /data/nginx/nginx.conf:/etc/nginx/nginx.conf \
--restart=always nginx:stable
```
[nginx.conf配置](http://doc.mingsoft.net/server/huan-jing-pei-zhi/nnginxdai-li-pei-zhi.html)

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.3.12.RELEASE](https://spring.io/projects/spring-boot)
- [Hutool 5.x](https://hutool.cn/)

## Screenshots
![](https://www.mingsoft.net/upload/1/cms/content/1630419069663.gif)

![](http://doc.mingsoft.net/mcms/assets/yysz.png)

![](http://doc.mingsoft.net/mcms/assets/jth.png)

## References
- [MCMS](https://www.mingsoft.net/)
- [MCMS Gitee](https://gitee.com/mingSoft/MCMS)
- [铭飞 提供的服务](https://www.mingsoft.net/html/default/cms/banben/index.html)
- [MCMS 源码部署](http://doc.mingsoft.net/mcms/kai-yuan-ban-ben.html)
- [MCMS docker安装](http://doc.mingsoft.net/server/huan-jing-pei-zhi/docker.html)