# Apache ShenYu Docker

Java 原生API网关,用于服务代理、协议转换和API治理

## Docker
### Run Apache ShenYu Admin
```sh
docker network create shenyu
docker run -d --name shenyu-admin -p 9095:9095 --net shenyu apache/shenyu-admin
```
- [http://localhost:9095/](http://localhost:9095/)
- User: admin / 123456

### Run Apache ShenYu Bootstrap
```sh
docker run -d -p 9195:9195 -e "shenyu.local.enabled=true" --net shenyu apache/shenyu-bootstrap
docker run -d --name shenyu-bootstrap -p 9195:9195 -e "shenyu.local.enabled=true" -e SHENYU_SYNC_WEBSOCKET_URLS=ws://shenyu-admin:9095/websocket --net shenyu apache/shenyu-bootstrap
```

### Set router
```sh
curl --location --request POST 'http://localhost:9195/shenyu/plugin/selectorAndRules' \
--header 'Content-Type: application/json' \
--header 'localKey: 123456' \
--data-raw '{
    "pluginName": "divide",
    "selectorHandler": "[{\"upstreamUrl\":\"127.0.0.1:8080\"}]",
    "conditionDataList": [{
        "paramType": "uri",
        "operator": "match",
        "paramValue": "/**"
    }],
    "ruleDataList": [{
        "ruleHandler": "{\"loadBalance\":\"random\"}",
        "conditionDataList": [{
            "paramType": "uri",
            "operator": "match",
            "paramValue": "/**"
        }]
    }]
}'
```

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://shenyu.apache.org/assets/images/shenyu-architecture-3d-2b673fe8dfd0ef6a14223ffd00bfe824.png)

## Screenshots
![](https://shenyu.apache.org/zh/img/home/2_2.jpg)

![](https://shenyu.apache.org/zh/img/home/2_3.jpg)

![](https://shenyu.apache.org/zh/img/home/2_4.jpg)

![](https://shenyu.apache.org/zh/img/home/2_5.jpg)

![](https://shenyu.apache.org/zh/img/home/2_6.jpg)

![](https://shenyu.apache.org/zh/img/home/2_7.jpg)

![](https://shenyu.apache.org/zh/img/home/2_8.jpg)

## References
- [Apache ShenYu](https://shenyu.apache.org/zh/)
- [Apache ShenYu GitHub](https://github.com/apache/shenyu)
- [Apache ShenYu Overview](https://shenyu.apache.org/docs/index/)
- [Apache ShenYu Docker](https://shenyu.apache.org/zh/docs/deployment/deployment-docker)