# JumpServer Docker

JumpServer 是广受欢迎的开源堡垒机，是符合 4A 规范的专业运维安全审计系统。

## Demo
[Demo](https://demo.jumpserver.org/)

## Linux
```sh
wget https://github.com/jumpserver/installer/releases/download/v3.1.2/jumpserver-installer-v3.1.2.tar.gz
tar -xf jumpserver-installer-v3.1.2.tar.gz
sudo mv jumpserver-installer-v3.1.2 /opt
mkdir -p jumpserver/config
sudo mv jumpserver /opt
curl -sSL https://resource.fit2cloud.com/jumpserver/jumpserver/releases/latest/download/quick_start.sh | bash
```
- [http://localhost:80/](http://localhost:80/)
- User: admin / admin

安装完成后 JumpServer 配置文件路径为： `/opt/jumpserver/config/config.txt`
```sh
cd /opt/jumpserver-installer-v3.1.2
# 启动
./jmsctl.sh start
# 停止
./jmsctl.sh down
# 卸载
./jmsctl.sh uninstall
# 帮助
./jmsctl.sh -h
```

## Docker
### 快速部署
```sh
# 测试环境可以使用，生产环境推荐外置数据
git clone --depth=1 https://github.com/jumpserver/Dockerfile.git
cd Dockerfile
cp config_example.conf .env
docker-compose -f docker-compose-network.yml -f docker-compose-redis.yml -f docker-compose-mariadb.yml -f docker-compose-init-db.yml up -d
docker exec -i jms_core bash -c './jms upgrade_db'
docker-compose -f docker-compose-network.yml -f docker-compose-redis.yml -f docker-compose-mariadb.yml -f docker-compose.yml up -d
```

### 标准部署
```sh
docker-compose -f docker-compose-network.yml -f docker-compose-init-db.yml up -d
docker exec -i jms_core bash -c './jms upgrade_db'
docker-compose -f docker-compose-network.yml -f docker-compose.yml up -d
```

## Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)
- [MariaDB 10.x](https://mariadb.org/download/)
- [Redis 6.x](https://redis.io/download)

## 组件项目

| 项目 | 描述 |
|---|---|
| [Lina](https://github.com/jumpserver/lina) | JumpServer Web UI 项目|
| [Luna](https://github.com/jumpserver/luna) | JumpServer Web Terminal 项目|
| [KoKo](https://github.com/jumpserver/koko) | JumpServer 字符协议 Connector 项目，替代原来 Python 版本的 [Coco](https://github.com/jumpserver/coco)|
| [Lion](https://github.com/jumpserver/lion-release) | JumpServer 图形协议 Connector 项目，依赖 [Apache Guacamole](https://guacamole.apache.org/)|
| [Magnus](https://github.com/jumpserver/magnus-release) | JumpServer 数据库代理 Connector 项目|
| [Clients](https://github.com/jumpserver/clients) | JumpServer 客户端 项目|
| [Installer](https://github.com/jumpserver/installer) | JumpServer 安装包 项目|

## Architecture
![](https://docs.jumpserver.org/zh/v3/img/architecture_01.png)

- Core 组件是 JumpServer 的核心组件，其他组件依赖此组件启动。
- Koko 是服务于类 Unix 资产平台的组件，通过 SSH、Telnet 协议提供字符型连接。
- Lion 是服务于 Windows 资产平台的组件，用于 Web 端访问 Windows 资产。
- Omnidb 是服务于数据库的组件，用于可视化界面纳管数据库。
- Razor 是服务于 RDP 协议组件，该组件主要功能是通过 JumpServer Client 方式访问 Windows 资产。
- Magnus 是服务于数据库的组件，用于通过客户端代理访问数据库。
- Celery 是处理异步任务的组件，用于执行 JumpServer 相关的自动化任务。

## Screenshots
![](https://docs.jumpserver.org/zh/v3/img/dashboard.png)

## References
- [JumpServer](http://www.jumpserver.org/)
- [JumpServer GitHub](https://github.com/jumpserver/jumpserver)
- [JumpServer Docker](https://github.com/jumpserver/Dockerfile)
- [JumpServer 系统架构](https://docs.jumpserver.org/zh/v3/architecture/)
- [JumpServer 在线安装](https://docs.jumpserver.org/zh/v3/installation/setup_linux_standalone/online_install/)
- [JumpServer API 文档](https://docs.jumpserver.org/zh/v3/dev/rest_api/)