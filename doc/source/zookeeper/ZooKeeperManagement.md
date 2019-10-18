# ZooKeeper Management

## zkui
A UI dashboard that allows CRUD operations on Zookeeper.

### Requirements
Requires Java 7 to run.

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

### Screenshots
![](https://www.wailian.work/images/2019/10/18/zkuiDashboardConsole-min.png)

### Tips
KeeperErrorCode = ConnectionLoss for /
- `zkSessionTimeout=20`

## Shepher
Shepher is a management tool of ZooKeeper. In Xiaomi, we use it as the configuration management center.

### Function comparison of similar products

Product | Introduction | Visualized operation of nodes | Snapshot management | Node modified Diff and Review function | Node operated mail notification | CAS and LDAP log | Authority management | Cascade delete | System status monitor
---|---|---|---|---|---|---|---|---|---
Shepher | ZooKeeper management | √ | √ | √ | √ | √ | √ |   |  
TaoKeeper | ZooKeeper cluster monitor and statement |   |   |   |   |   |   |   | √
Zkdash | ZooKeeper management | √ | √ |   |   |   |   | √ |  
Disconf | ZooKeeper management | √ | √ |   | √ |   | √ | √ | √
XDiamond | Configuration center | √ |   |   |   | √ | √ |   | √

### Requirements
- JDK 1.8
- Maven 3.2 +
- MySQL 5.6

### Setup
1. `db/init.sql`: `youradmin` -> `root`
1. `vi conf/application-online.properties`
1. `$ mvn clean package`
1. `$ cd shepher-packaging/target/shepher-packaging-{version}-bin`
1. `$ sh bin/run.sh -c conf/application-online.properties start`
1. http://192.168.1.110:8089

### Screenshots
![](https://www.wailian.work/images/2019/10/18/ShepherHome-min.png)

## References
- [zkui](https://github.com/DeemOpen/zkui)
- [shepher](https://github.com/XiaoMi/shepher)