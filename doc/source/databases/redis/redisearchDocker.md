# RediSearch Docker

A query and indexing engine for Redis, providing secondary indexing, full-text search, vector similarity search and aggregations.

- `redis/redis-stack` contains both Redis Stack server and RedisInsight. This container is best for local development because you can use the embedded RedisInsight to visualize your data.
- `redis/redis-stack-server` provides Redis Stack server only. This container is best for production deployment.

## Docker
```sh
docker run -d --name redis-stack-server -p 6379:6379 redis/redis-stack-server
docker run -d --name redis-stack -p 6379:6379 -p 8001:8001 redis/redis-stack
```
[http://localhost:8001/](http://localhost:8001/)

### Connect with redis-cli
```sh
docker exec -it redis-stack redis-cli
```

### Persistence in Docker
```sh
docker run -v /local-data/:/data redis/redis-stack
```

### Environment variables
```sh
docker run -e REDIS_ARGS="--requirepass redis-stack" redis/redis-stack
docker run -e REDIS_ARGS="--save 60 1000 --appendonly yes" redis/redis-stack
docker run -e REDISTIMESERIES_ARGS="RETENTION_POLICY=20" redis/redis-stack
```

## Runtime Environment
- C, C++
- [Python 3.9.x](https://www.python.org/downloads/)

## References
- [RediSearch](https://redis.io/docs/stack/search/)
- [RediSearch GitHub](https://github.com/RediSearch/RediSearch)
- [RediSearch Docker](https://redis.io/docs/install/install-stack/docker/)