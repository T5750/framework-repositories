# ZooKeeper异常笔记

## java.net.ConnectException:拒绝连接
```
java.net.ConnectException: 拒绝连接
	at sun.nio.ch.SocketChannelImpl.checkConnect(Native Method)
	at sun.nio.ch.SocketChannelImpl.finishConnect(SocketChannelImpl.java:717)
	at org.apache.zookeeper.ClientCnxnSocketNIO.doTransport(ClientCnxnSocketNIO.java:361)
	at org.apache.zookeeper.ClientCnxn$SendThread.run(ClientCnxn.java:1141)
```
`./zkCli.sh`默认是`2181`连接，加上端口号连接就没问题了：
```
bin/zkCli.sh -timeout 5000 -server 192.168.100.165:2183
```

## References
- [ZooKeeper zkCli时报 java.net.ConnectException: 拒绝连接](https://blog.csdn.net/qq_41875147/article/details/81216599)