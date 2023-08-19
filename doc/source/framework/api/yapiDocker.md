# YApi Docker

旨在为开发、产品、测试人员提供更优雅的接口管理服务。可以帮助开发者轻松创建、发布、维护 API

## Linux
### 方式一. 可视化部署[推荐]
```sh
npm install -g yapi-cli --registry https://registry.npm.taobao.org
yapi server
```

### 方式二. 命令行部署
```sh
mkdir yapi
cd yapi
git clone https://github.com/YMFE/yapi.git vendors //或者下载 zip 包解压到 vendors 目录（clone 整个仓库大概 140+ M，可以通过 `git clone --depth=1 https://github.com/YMFE/yapi.git vendors` 命令减少，大概 10+ M）
cp vendors/config_example.json ./config.json //复制完成后请修改相关配置
cd vendors
npm install --production --registry https://registry.npm.taobao.org
npm run install-server //安装程序会初始化数据库索引和管理员账号，管理员账号名可在 config.json 配置
node server/app.js //启动服务器后，请访问 127.0.0.1:{config.json配置的端口}，初次运行会有个编译的过程，请耐心等候
```

## Upgrade
```sh
yapi ls //查看版本号列表
yapi update //更新到最新版本
yapi update -v {Version} //更新到指定版本
```

## Ryan-Miao/docker-yapi
`yapi.yml`

### Installation
```
# 第一次启动使用
command: "yapi server"
```
- [http://localhost:9090/](http://localhost:9090/)
- 管理员邮箱: admin@admin.com
- 默认密码: ymfe.org

### Deploy
```
# 之后使用下面的命令
command: "node /my-yapi/vendors/server/app.js"
```

### Docker Structure
```
├── mongo
│   ├── data
│   │   └── db
│   └── etc
├── mongo-conf
│   └── init-mongo.js
└── mongo-yapi.yml

# yapi
├── Dockerfile
├── yapi.yml
└── repositories
```

## fjc0k/docker-YApi
`yapi-web.yml`

### yapi-plugin-dding
```sh
docker exec -it yapi-web /bin/sh
npm config set registry https://registry.npm.taobao.org
npm install && npm install -g ykit && npm install -g yapi-cli
yapi plugin --name yapi-plugin-dingding
```
```sh
docker cp yapi-web:/yapi ./
```

### Docker Structure
```
# yapi
├── yapi-web.yml
└── yapi
```

## AD Integration
`vi my-yapi/config.json`
```
  "ldapLogin": {
      "enable": true,
      "server": "ldap://192.168.1.10", //AD域控服务器
      "baseDn": "yapi@test.com", //AD域控用对应给与一个认证用户
      "bindPassword": "Test1234", //对应认证用户密码
      "searchDn": "OU=技术中心,DC=test,DC=com", //可读取的OU路径
      "searchStandard": "&(objectCategory=Person)(sAMAccountName=%s)", //MS AD的用户属性
      "emailPostfix": "", //非必须可为空
      "emailKey": "", //非必须可为空
      "usernameKey": "sAMAccountName" 用户登录密码取值为对应用户属性，原因为AD密码不可逆，只取此值
  }
```

## Runtime Environment
- [Node.js](https://nodejs.org/en/download)
- [MongoDB 4.x](https://www.mongodb.com/download-center/community/releases)
- [Koa](https://koajs.com/)
- [Mongoose](https://mongoosejs.com/)

## Screenshots
![](https://hellosean1025.github.io/yapi/documents/images/usage/index.png)

![](https://hellosean1025.github.io/yapi/documents/images/usage/project.png)

![](https://hellosean1025.github.io/yapi/documents/images/usage/chrome-5.jpg)

![](https://hellosean1025.github.io/yapi/documents/images/usage/json-schema-demo.jpg)

## References
- [YApi](https://hellosean1025.github.io/yapi/)
- [YApi GitHub](https://github.com/YMFE/yapi)
- [YApi 内网部署](https://hellosean1025.github.io/yapi/devops/index.html)
- [docker-yapi: 基于官方yapi-cli的docker-compose方案](https://github.com/Ryan-Miao/docker-yapi)
- [fjc0k/docker-YApi: 更易用的 YApi 镜像](https://github.com/fjc0k/docker-YApi)
- [能集成AD登录方式吗](https://github.com/YMFE/yapi/issues/730)
- [dingding 钉钉机器人推送插件](https://github.com/zgs225/yapi-plugin-dding)