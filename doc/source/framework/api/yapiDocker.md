# YApi Docker

## Docker Compose
`yapi.yml`

### Installation
```
# 第一次启动使用
command: "yapi server"
```
- [http://localhost:9090/](http://localhost:9090/)
- 管理员邮箱: admin@admin.com

### Deploy
```
# 之后使用下面的命令
command: "node /my-yapi/vendors/server/app.js"
```
- [http://localhost:3000/](http://localhost:3000/)
- User: admin@admin.com / admin

## References
- [YApi](https://github.com/YMFE/yapi)
- [docker-yapi: 基于官方yapi-cli的docker-compose方案](https://github.com/Ryan-Miao/docker-yapi)