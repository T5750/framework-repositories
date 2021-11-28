# Nacos Docker

```
git clone https://github.com/nacos-group/nacos-docker.git
cd nacos-docker
```

## Nacos Standalone in Docker Compose
`nacos-standalone-mysql.yaml`

## Nacos Cluster in Docker Compose
`nacos-mysql.yaml`

- [http://localhost:8848/nacos](http://localhost:8848/nacos)
- Nacos User: nacos / nacos
- [http://prometheus:9090/](http://prometheus:9090/)
- [http://grafana:3000/](http://grafana:3000/)
- Grafana User: admin / admin

服务注册
```
curl -X POST 'http://127.0.0.1:8848/nacos/v1/ns/instance?serviceName=nacos.naming.serviceName&ip=20.18.7.10&port=8080'
```
服务发现
```
curl -X GET 'http://127.0.0.1:8848/nacos/v1/ns/instance/list?serviceName=nacos.naming.serviceName'
```
发布配置
```
curl -X POST "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test&content=helloWorld"
```
获取配置
```
curl -X GET "http://127.0.0.1:8848/nacos/v1/cs/configs?dataId=nacos.cfg.dataId&group=test"
```

## Monitor
[Prometheus query nacos_monitor](http://prometheus:9090/graph?g0.expr=nacos_monitor&g0.tab=1&g0.stacked=0&g0.range_input=1h)

Grafana
- Configuration -> Data Sources
    * Name: `prometheus`
    * URL: `http://prometheus:9090/`
- Create -> Import
    * Nacos grafana监控[模版](https://github.com/nacos-group/nacos-template/blob/master/nacos-grafana.json)
    * Nacos-Sync监控[模版](https://github.com/nacos-group/nacos-template/blob/master/nacos-sync-grafana)

## References
- [Nacos Docker 快速开始](https://nacos.io/zh-cn/docs/quick-start-docker.html)
- [Nacos 监控手册](https://nacos.io/zh-cn/docs/monitor-guide.html)