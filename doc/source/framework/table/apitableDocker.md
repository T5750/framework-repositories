# APITable Docker

APITable是面向API的，可支持嵌入的可视化数据库，可替代Airtable

## Docker
使用 docker compose 安装 apitable，请打开您的终端并运行：
```sh
curl https://apitable.github.io/install.sh | bash
```
[http://localhost:80/](http://localhost:80/)
```sh
# 基于 pm2 的完整镜像，用于演示或测试目的(不推荐用于企业或生产用途)：
sudo docker run -d -v ${PWD}/.data:/apitable -p 80:80 --name apitable apitable/all-in-one:latest
```

## Tips
注：社区版在功能上有较多限制

## Runtime Environment
- [Java 17](https://github.com/openjdk/jdk)
- [Spring Boot 3.x](https://spring.io/projects/spring-boot)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://github.com/apitable/apitable/raw/develop/docs/readme/zh-CN/docs/static/screenshot-realtime.png)

![](https://github.com/apitable/apitable/raw/develop/docs/readme/zh-CN/docs/static/screenshot-automation.png)

## References
- [APITable](https://aitable.ai/)
- [APITable GitHub](https://github.com/apitable/apitable)
- [开源社区版本，单表限制的数据行数问题](https://github.com/apitable/apitable/issues/1122)