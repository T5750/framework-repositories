# RAP2-DELOS Docker

- [rap2-delos](http://github.com/thx/rap2-delos): 后端数据 API 服务器，基于 Koa + MySQL
- [rap2-dolores](http://github.com/thx/rap2-dolores): 前端静态资源，基于 React

## Docker Compose
`rap2-delos.yml`

```
# 启动后，第一次运行需要手动初始化mysql数据库
# ⚠️注意: 只有第一次该这样做
docker-compose exec delos node scripts/init
docker-compose -f rap2-delos.yml exec delos node scripts/init

# 部署成功后 访问
http://localhost:3000 # 前端（可自定义端口号）
http://localhost:38080 # 后端

# 如果 Sequelize 报错可能是数据库表发生了变化，运行下面命令同步
docker-compose exec delos node scripts/updateSchema
```

## Upgrade
```
# 拉取一下最新的镜像
docker-compose pull
# 暂停当前应用
docker-compose down
# 重新构建并启动
docker-compose up -d --build
# 有时表结构会发生变化，执行下面命令同步
docker-compose exec delos node scripts/updateSchema
# 清空不被使用的虚悬镜像
docker image prune -f
```

## References
- [rap2-delos](https://github.com/thx/rap2-delos)