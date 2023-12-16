# Huntly Docker

Huntly, information management tool, rss reader, automatic saving browsed contents include tweets, github stars management tool. 信息管理工具、RSS 阅读器、GitHub stars 管理、推文管理、自动记录浏览过的文章

## Docker
```sh
mkdir huntly && cd huntly
docker run -itd --name huntly --restart=always -p 8123:80 -v `pwd`/data:/data lcomplete/huntly
```
[http://localhost:8123/](http://localhost:8123/)

## Java
```sh
java -Xms128m -Xmx1024m -jar huntly-server.jar --server.port=8123
```

## Runtime Environment
- [Java 11](https://openjdk.java.net/projects/jdk/11/)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Screenshots
![](https://github.com/lcomplete/huntly/raw/main/static/images/intro1.png)

![](https://github.com/lcomplete/huntly/raw/main/static/images/intro2.png)

## References
- [Huntly GitHub](https://github.com/lcomplete/huntly)