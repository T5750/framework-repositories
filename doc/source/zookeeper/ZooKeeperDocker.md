# ZooKeeper Docker

Apache ZooKeeper is an open-source server which enables highly reliable distributed coordination.

## Standalone in Docker
```sh
docker run -d --name zoo1 --restart=always -v /etc/localtime:/etc/localtime:ro -p 2181:2181 zookeeper:3.5
```

### Connect to Zookeeper from the Zookeeper command line client
```sh
docker run -it --rm --link zoo1:zookeeper zookeeper:3.5 zkCli.sh -server zookeeper
```

## Standalone in Docker Compose
`zookeeper-standalone.yml`

## Cluster in Docker Compose
`zookeeper.yml`

```sh
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
```sh
zkCli.sh -server 172.18.0.201:2181,172.18.0.201:2182,172.18.0.201:2183
```

## References
- [ZooKeeper Docker](https://hub.docker.com/_/zookeeper)
- [Docker下安装zookeeper（单机 & 集群）](https://www.cnblogs.com/LUA123/p/11428113.html)
- [docker-compose搭建zookeeper集群](https://www.jianshu.com/p/98bb69256cc3)