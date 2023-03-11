# Polaris Mesh Docker

支持多语言、多框架的云原生服务治理平台

服务管理、流量管理、故障容错、配置管理、可观测性

## Demo
[体验版](http://14.116.241.63:8080/#/login)

## Docker
```sh
docker run -d --name polaris -p 15010:15010 -p 8101:8101 -p 8100:8100 -p 8080:8080 -p 8090:8090 -p 8091:8091 -p 8093:8093 -p 8761:8761 -p 9000:9000 -p 9090:9090 polarismesh/polaris-server-standalone
```
[http://localhost:8080/](http://localhost:8080/)

## 单机版
北极星支持单机版的安装架构，适用于用户在开发测试阶段，通过本机快速拉起北极星服务进行验证。

![](https://polarismesh.cn/docs/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%AE%89%E8%A3%85/%e5%9b%be%e7%89%87/%e5%ae%89%e8%a3%85%e5%8d%95%e6%9c%ba%e7%89%88/%e5%8d%95%e6%9c%ba%e6%9e%b6%e6%9e%84.png)

单机版包含以下4个组件：
- polaris-console：可视化控制台，提供服务治理管控页面
- polaris-server：控制面，提供数据面组件及控制台所需的后台接口
- polaris-limiter: 分布式限流服务端，提供全局配额统计的功能
- prometheus：服务治理监控所需的指标汇聚统计组件

单机版默认占用以下端口：
- polaris-console：8080(http/tcp)
- polaris-server：8090(http/tcp，注册中心端口)、8091(grpc/tcp，注册中心端口)、8093(grpc/tcp，配置中心端口)
- polaris-limiter：8101(grpc/tcp)、8100(http/tcp)
- prometheus：9090(http/tcp)

## 集群版
![](https://polarismesh.cn/docs/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%AE%89%E8%A3%85/%e5%9b%be%e7%89%87/%e5%ae%89%e8%a3%85%e9%9b%86%e7%be%a4%e7%89%88/%e5%9f%ba%e7%a1%80%e6%9e%b6%e6%9e%84.png)

## Architecture
![](https://polarismesh.cn/img/structure-diagram.jpg)

## Screenshots
![](https://polarismesh.cn/img/registry-center.png)

![](https://polarismesh.cn/img/observability.png)

## References
- [Polaris Mesh](https://polarismesh.cn/)
- [Polaris Mesh GitHub](https://github.com/PolarisMesh)
- [Polaris Mesh 单机版安装](https://polarismesh.cn/docs/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%AE%89%E8%A3%85/%E5%8D%95%E6%9C%BA%E7%89%88%E5%AE%89%E8%A3%85/)
- [Polaris Mesh 集群版安装](https://polarismesh.cn/docs/%E4%BD%BF%E7%94%A8%E6%8C%87%E5%8D%97/%E6%9C%8D%E5%8A%A1%E7%AB%AF%E5%AE%89%E8%A3%85/%E9%9B%86%E7%BE%A4%E7%89%88%E5%AE%89%E8%A3%85/)