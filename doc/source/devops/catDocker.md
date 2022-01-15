# CAT Docker

CAT 是基于 Java 开发的实时应用监控平台，为美团点评提供了全面的实时监控告警服务。
- 减少故障发现时间
- 降低故障定位成本
- 辅助应用程序优化

## Docker Compose
- [CatApplication.sql](https://github.com/dianping/cat/blob/master/script/CatApplication.sql)
- `cat.yml`
- [http://localhost:8080/cat](http://localhost:8080/cat)
- User: admin / admin

## Runtime Environment
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [MySQL 5.x](http://www.mysql.com/)

## 整体设计
![](https://github.com/dianping/cat/wiki/resources/ch4-design/overall.png)

## Screenshots
![](https://raw.githubusercontent.com/wiki/dianping/cat/resources/ch1-report/transaction_view.png)

![](https://raw.githubusercontent.com/wiki/dianping/cat/resources/ch1-report/transaction_chart1.png)

![](https://raw.githubusercontent.com/wiki/dianping/cat/resources/ch1-report/heartbeat_view.png)

## References
- [dianping/cat](https://github.com/dianping/cat)