# ZooKeeper Logs

## Unix
```
java -classpath .:slf4j-api-1.7.25.jar:zookeeper-3.4.10.jar org.apache.zookeeper.server.LogFormatter log.4b8f
```

## Windows
```
java -classpath ".;*" org.apache.zookeeper.server.LogFormatter log.4b8f
```

## Results
- `ZkLog`

## References
- [ZooKeeper Administrator's Guide](https://zookeeper.apache.org/doc/r3.4.10/zookeeperAdmin.html)