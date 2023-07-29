# YApi Docker

旨在为开发、产品、测试人员提供更优雅的接口管理服务。可以帮助开发者轻松创建、发布、维护 API

## Linux
```sh
npm install -g yapi-cli --registry https://registry.npm.taobao.org
yapi server
```

## Upgrade
```sh
yapi ls //查看版本号列表
yapi update //更新到最新版本
yapi update -v {Version} //更新到指定版本
```

## Docker Compose
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

## Structure
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

## References
- [YApi](https://hellosean1025.github.io/yapi/)
- [YApi GitHub](https://github.com/YMFE/yapi)
- [docker-yapi: 基于官方yapi-cli的docker-compose方案](https://github.com/Ryan-Miao/docker-yapi)
- [能集成AD登录方式吗](https://github.com/YMFE/yapi/issues/730)