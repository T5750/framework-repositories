# RedisInsight

## Installing RedisInsight on Docker
```
docker run -v redisinsight:/db -p 8001:8001 redislabs/redisinsight:latest
docker run -d --name redisinsight --restart=always -v ~/redisinsight:/db -v /etc/localtime:/etc/localtime:ro -p 8001:8001 redislabs/redisinsight:latest
```
[http://localhost:8001/](http://localhost:8001/)

[http://localhost:8001/healthcheck/](http://localhost:8001/healthcheck/)

## Docker Compose
`redis-standalone.yml`

## Screenshots
![](https://redis.com/wp-content/uploads/2019/10/redisinsights-overview.png)

![](https://redis.com/wp-content/uploads/2019/10/redisinsights-redisgraph.png)

![](https://redis.com/wp-content/uploads/2019/10/redisinsights-analyze-overview.png)

![](https://redis.com/wp-content/uploads/2019/10/keyspace_summary.png)

![](https://redis.com/wp-content/uploads/2019/10/redisinsights-CLI.png)

## References
- [Installing RedisInsight on Docker](https://docs.redislabs.com/latest/ri/installing/install-docker/)
- [Redis向け GUI ツール RedisInsight を使う](https://tech.guitarrapc.com/entry/2019/12/13/043349)