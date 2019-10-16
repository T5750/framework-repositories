# ZooKeeper Management

## zkui
A UI dashboard that allows CRUD operations on Zookeeper.

### Setup
1. `mvn clean install`
1. Copy the `config.cfg` to the folder with the jar file. Modify it to point to the zookeeper instance. Multiple zk instances are coma separated. eg: `server1:2181,server2:2181`. First server should always be the leader.
1. Run the jar. ( `nohup java -jar zkui-2.0-SNAPSHOT-jar-with-dependencies.jar &` )
1. http://localhost:9090

### Login Info
- username: admin, pwd: manager (Admin privileges, CRUD operations supported)
- username: appconfig, pwd: appconfig (Readonly privileges, Read operations supported)

### REST call
1. Add Node: `/appconfig/hosts`
1. Add Property: `myhost.com`, Value: `/appconfig/hosts`

Eg:
- http://localhost:9090/acd/appconfig?propNames=foo&host=myhost.com
- http://localhost:9090/acd/appconfig?propNames=foo&cluster=cluster1&host=myhost.com
- http://localhost:9090/acd/appconfig?propNames=foo&app=myapp&host=myhost.com
- A shell script will call this via MY_PROPERTY="$(curl -f -s -S -k "http://localhost:9090/acd/appconfig?propNames=foo&host=`hostname -f`" | cut -d '=' -f 2)" echo $MY_PROPERTY

### Tips
KeeperErrorCode = ConnectionLoss for /
- `zkSessionTimeout=20`

## References
- [zkui](https://github.com/DeemOpen/zkui)