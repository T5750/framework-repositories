# RedisInsight

## Installing RedisInsight on Docker
```
docker run -v redisinsight:/db -p 8001:8001 redislabs/redisinsight:latest
```
[http://localhost:8001/](http://localhost:8001/)

[http://localhost:8001/healthcheck/](http://localhost:8001/healthcheck/)

## Docker Compose
`redis-standalone.yml`

## References
- [Installing RedisInsight on Docker](https://docs.redislabs.com/latest/ri/installing/install-docker/)
- [Redis向け GUI ツール RedisInsight を使う](https://tech.guitarrapc.com/entry/2019/12/13/043349)