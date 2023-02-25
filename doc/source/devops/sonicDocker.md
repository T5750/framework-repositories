# Sonic Docker

免费开源的云真机测试平台，用心打造更好的使用体验。

## 前后端部署
1. 需提前建数据库库！字符集为 `utf8`，排序规则为 `utf8_general_ci`
2. [点击这里](https://ghproxy.com/https://github.com/SonicCloudOrg/sonic-server/releases/download/v2.3.0/sonic-server-v2.3.0.zip) 下载最新版本的 zip 文件到任意目录（如加速链接失效，请自行前往 [这里](https://github.com/SonicCloudOrg/sonic-server/releases) 下载）
3. 解压 zip，更改`.env`中的信息
```sh
vi .env
# SONIC_SERVER_HOST
# MYSQL_*
docker-compose up -d
docker-compose -f docker-compose-zh.yml up -d
```
- [http://localhost:3000/](http://localhost:3000/)
- [http://localhost:9090/](http://localhost:9090/)

### 使用自己的 Eureka
1. 将 `docker-compose.yml` 文件去掉 Eureka 服务。
2. 将 `docker-compose.yml` 的 `SONIC_EUREKA_HOST` 填写为自己 Eureka 服务的 host。
3. 将`.env` 中的配置修改为自己 Eureka 服务的信息。

## Agent 端部署
### jar 方式部署
```sh
java -Dfile.encoding=utf-8 -jar sonic-agent-xxxx.jar
```

### Docker 部署
Docker部署仅 Ubuntu 可用！

## Screenshots
![](https://sonic-cloud.cn/assets/use-p.c3c13f6a.png)

![](https://sonic-cloud.cn/assets/use-10.ec2a48f6.png)

## References
- [Sonic](https://sonic-cloud.cn/)
- [Sonic GitHub](https://github.com/SonicCloudOrg)
- [sonic-server GitHub](https://github.com/SonicCloudOrg/sonic-server)
- [Sonic 前后端部署](https://sonic-cloud.cn/deploy/back-end-deploy.html)
- [Sonic Agent 端部署](https://sonic-cloud.cn/deploy/agent-deploy.html)