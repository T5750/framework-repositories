# Redis Docker

## Redis Standalone in Docker Compose
`redis-standalone.yml`

## Redis Sentinel in Docker Compose
- `redis.conf`
- `sentinel.conf`
- `redis-sentinel.yml`

```
mkdir redis{1,2,3}
cp -avx redis.conf sentinel.conf redis1
```

### Test
```
docker exec -it redis1 bash
redis-cli -h redis1 -p 6379
info replication

docker exec -it redis3 bash
redis-cli -h redis3 -p 6379
info replication

redis-cli -h sentinel2 -p 26379
sentinel master mymaster
sentinel slaves mymaster
```

## References
- [docker-compose 搭建 Redis Sentinel 测试环境](https://www.cnblogs.com/leffss/p/12082361.html)