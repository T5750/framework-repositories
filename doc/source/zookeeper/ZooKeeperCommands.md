# ZooKeeper Commands

## Commands
```
yum install nc -y
echo conf | nc localhost 2181
echo ruok | nc localhost 2181
```

Command | Description
---|-----
conf | Print details about serving configuration.
cons | List full connection/session details for all clients connected to this server. Includes information on numbers of packets received/sent, session id, operation latencies, last operation performed, etc...
crst | Reset connection/session statistics for all connections.
dump | Lists the outstanding sessions and ephemeral nodes. This only works on the leader.
envi | Print details about serving environment
ruok | Tests if server is running in a non-error state. The server will respond with imok if it is running. Otherwise it will not respond at all. A response of "imok" does not necessarily indicate that the server has joined the quorum, just that the server process is active and bound to the specified client port. Use "stat" for details on state wrt quorum and client connection information.
srst | Reset server statistics.
srvr | Lists full details for the server.
stat | Lists brief details for the server and connected clients.
wchs | Lists brief information on watches for the server.
wchc | Lists detailed information on watches for the server, by session. This outputs a list of sessions(connections) with associated watches (paths). Note, depending on the number of watches this operation may be expensive (ie impact server performance), use it carefully.
wchp | Lists detailed information on watches for the server, by path. This outputs a list of paths (znodes) with associated sessions. Note, depending on the number of watches this operation may be expensive (ie impact server performance), use it carefully.
mntr | Outputs a list of variables that could be used for monitoring the health of the cluster.

## Tips
### Config
```
vi ~/.bashrc
export ZOOKEEPER_HOME=/usr/local/zookeeper-3.4.10
export PATH=${JAVA_HOME}/bin:${NGINX_HOME}/sbin:${REDIS_HOME}/src:$ZOOKEEPER_HOME/bin:$PATH
```

### JVM
`$ZOOKEEPER_HOME/conf/java.env`

## References
- [ZooKeeper Administrator's Guide](https://zookeeper.apache.org/doc/r3.4.10/zookeeperAdmin.html)