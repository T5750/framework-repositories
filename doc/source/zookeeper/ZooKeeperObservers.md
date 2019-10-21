# ZooKeeper Observers

## Observers: Scaling ZooKeeper Without Hurting Write Performance
- Observers are non-voting members of an ensemble which only hear the results of votes, not the agreement protocol that leads up to them.
- Therefore they can fail, or be disconnected from the cluster, without harming the availability of the ZooKeeper service.

## How to use Observers
Firstly, in the config file of every node that is to be an Observer, you must place this line:
```
peerType=observer
```
Secondly, in every server config file, you must add `:observer` to the server definition line of each Observer. For example:
```
server.1:localhost:2181:3181:observer
```
This tells every other server that server.1 is an Observer, and that they should not expect it to vote.
```
$ bin/zkCli.sh -server localhost:2181
```

## Example use cases
- As a datacenter bridge
- As a link to a message bus

## Tips
```
zookeeper-3.4.10-observer/
├── conf/
│    ├── java.env
│    ├── zoo-2181.cfg
│    ├── zoo-2182.cfg
│    ├── zoo-2183.cfg
│    └── zoo-2188.cfg
├── data-2181/
│    └── myid
├── data-2182/
│    └── myid
├── data-2183/
│    └── myid
├── data-2188/
│    └── myid
├── logs-2181
├── logs-2182
├── logs-2183
└── logs-2188
```
```
cd /usr/local/zookeeper-3.4.10-observer
bin/zkServer.sh start conf/zoo-2181.cfg
bin/zkServer.sh start conf/zoo-2182.cfg
bin/zkServer.sh start conf/zoo-2183.cfg
bin/zkServer.sh start conf/zoo-2188.cfg
bin/zkServer.sh status conf/zoo-2181.cfg
bin/zkServer.sh status conf/zoo-2182.cfg
bin/zkServer.sh status conf/zoo-2183.cfg
bin/zkServer.sh status conf/zoo-2188.cfg
bin/zkCli.sh -server localhost:2188
```

## References
- [ZooKeeper Observers](http://zookeeper.apache.org/doc/r3.4.10/zookeeperObservers.html)