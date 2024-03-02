# Apache Kylin Docker

Analytical Data Warehouse for Big Data

## Docker
```sh
docker run -d \
    -m 8G \
    -p 7070:7070 \
    -p 8088:8088 \
    -p 50070:50070 \
    -p 8032:8032 \
    -p 8042:8042 \
    -p 2181:2181 \
    apachekylin/apache-kylin-standalone:4.0.0
```
- Kylin 页面：http://127.0.0.1:7070/kylin/login
    + User: ADMIN / KYLIN
- HDFS NameNode 页面：http://127.0.0.1:50070
- YARN ResourceManager 页面：http://127.0.0.1:8088

### MDX for Kylin
```sh
docker run -d \
    -m 8G \
    -p 7070:7070 \
    -p 7080:7080 \
    -p 8088:8088 \
    -p 50070:50070 \
    -p 8032:8032 \
    -p 8042:8042 \
    -p 2181:2181 \
    apachekylin/apache-kylin-standalone:kylin-4.0.1-mondrian
```
- Kylin 页面：http://127.0.0.1:7070/kylin/login
- MDX for Kylin 页面：http://127.0.0.1:7080
- HDFS NameNode 页面：http://127.0.0.1:50070
- YARN ResourceManager 页面：http://127.0.0.1:8088

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Spring Boot 2.x](https://spring.io/projects/spring-boot)

## Architecture
![](https://kylin.apache.org/assets/images/kylin_diagram.png)

## Screenshots
![](https://kylin.apache.org/images/tutorial/1.5/Kylin-Web-Tutorial/2%20tables.png)

![](https://kylin.apache.org/images/tutorial/1.5/Kylin-Web-Tutorial/3%20cubes.png)

![](https://kylin.apache.org/images/tutorial/1.5/Kylin-Web-Tutorial/4%20grid-view.PNG)

![](https://kylin.apache.org/images/tutorial/1.5/Kylin-Web-Tutorial/15%20bar-chart.png)

## References
- [Apache Kylin](https://kylin.apache.org/cn/)
- [Apache Kylin GitHub](https://github.com/apache/kylin)
- [Apache Kylin Docker](https://kylin.apache.org/cn/docs/install/kylin_docker.html)
- [Apache Kylin Web 界面](https://kylin.apache.org/cn/docs/tutorial/web.html)