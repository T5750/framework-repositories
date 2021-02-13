# ZooKeeper Docker

## ZooKeeper Standalone in Docker Compose
`zookeeper-standalone.yml`

## ZooKeeper Cluster in Docker Compose
`zookeeper.yml`

```
docker exec -it zoo1 bash
zkServer.sh status
```
```
ZooKeeper JMX enabled by default
Using config: /conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: follower
```
```
ZooKeeper JMX enabled by default
Using config: /conf/zoo.cfg
Client port found: 2181. Client address: localhost.
Mode: leader
```

### Tests
```
zkCli.sh -server 172.60.0.201:2181,172.60.0.201:2182,172.60.0.201:2183
```

## References
- [Docker下安装zookeeper（单机 & 集群）](https://www.cnblogs.com/LUA123/p/11428113.html)
- [docker-compose搭建zookeeper集群](https://www.jianshu.com/p/98bb69256cc3)