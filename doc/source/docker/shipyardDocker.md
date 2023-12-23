# Shipyard Docker

Composable Docker Management

**WARNING**: as the project retired I no longer maintained the domain. Someone else has setup a duplicate site that is not official and is carrying an invalid SSL certificate. PLEASE DO NOT GO TO THE DOMAIN shipyard-project.com. I cannot endorse it nor vouch for the security or content of the site.

## Docker
### 自动安装
```sh
curl -sSL https://shipyard-project.com/deploy | bash -s
```

For full usage details:
```sh
curl -sSL https://shipyard-project.com/deploy | bash -s -- -h
```

一键部署
```sh
chmod 755 shipyard-deploy
sh shipyard-deploy
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / shipyard

修改web访问端口
```sh
cat shipyard-deploy |grep 8080
# 比如将脚本中默认的8080端口改为80端口
sed -i 's/8080/80/g' shipyard-deploy
```

删除Shipyard环境操作
```sh
cat shipyard-deploy |ACTION=remove bash
```

Shipyard添加其他节点主机
```sh
cat shipyard-deploy| ACTION=node DISCOVERY=etcd://192.168.3.141:4001 bash
```

删除节点机
```sh
cat shipyard-deploy |ACTION=remove bash -s
```

### 手动安装
```sh
# 1. 数据存储
docker run -ti -d --restart=always --name shipyard-rethinkdb rethinkdb
# 2. 服务发现
docker run -ti -d -p 4001:4001 -p 7001:7001 --restart=always --name shipyard-discovery microbox/etcd -name discovery
# 3. Docker代理服务
docker run -ti -d -p 2375:2375 --hostname=$HOSTNAME --restart=always --name shipyard-proxy -v /var/run/docker.sock:/var/run/docker.sock -e PORT=2375 shipyard/docker-proxy
# 4. Swarm管理节点
docker run -ti -d --restart=always --name shipyard-swarm-manager swarm manage --host tcp://0.0.0.0:3375 etcd://<IP-OF-HOST>:4001
# 5. Swarm Agent节点
docker run -ti -d --restart=always --name shipyard-swarm-agent swarm join --addr <ip-of-host>:2375 etcd://<ip-of-host>:4001
# 6. Shipyard管理工具
docker run -ti -d --restart=always --name shipyard-controller --link shipyard-rethinkdb:rethinkdb --link shipyard-swarm-manager:swarm -p 8080:8080 shipyard/shipyard server -d tcp://swarm:3375
```

## Screenshots
![](https://shipyard-project.com/wp-content/uploads/2019/02/containers-main.png)

![](https://shipyard-project.com/wp-content/uploads/2019/02/containers-detail.png)

![](https://shipyard-project.com/wp-content/uploads/2019/02/containers-stats.png)

![](https://shipyard-project.com/wp-content/uploads/2019/02/containers-console.png)

## References
- [Shipyard](https://shipyard-project.com/)
- [Shipyard GitHub](https://github.com/shipyard/shipyard)
- [Shipyard Containers](https://shipyard-project.com/containers/)
- [Shipyard Automated Deployment](https://shipyard-project.com/automated-deployment/)
- [Shipyard Manual Deployment](https://shipyard-project.com/manual-deployment/)
- [docker可视化集中管理工具shipyard安装部署](https://www.cnblogs.com/heyongboke/p/10837503.html)
- [Docker可视化管理工具Shipyard安装与配置](https://www.jianshu.com/p/497615077c71)