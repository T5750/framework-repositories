# Hive Installation

## Pre-Installation Setup
- [Enviornment Setup](../hadoop/HadoopEnviornmentSetup.md)

## Installing Hive
```
tar zxvf apache-hive-2.3.5-bin.tar.gz
mv apache-hive-2.3.5-bin /usr/local/
mv apache-hive-2.3.5-bin hive-2.3.5
chown -R hadoop /usr/local/hive-2.3.5/
chgrp -R dialout /usr/local/hive-2.3.5/
vi ~/.bashrc
export HIVE_HOME=/usr/local/hive-2.3.5
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:${HADOOP_HOME}/lib:${HBASE_HOME}/lib:${HIVE_HOME}/lib
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin
source ~/.bashrc
```

## Configuring Hive
```
cd $HIVE_HOME/conf
cp hive-env.sh.template hive-env.sh
vi hive-env.sh
HADOOP_HOME=/usr/local/hadoop
```

## Downloading and Installing Apache Derby
```
wget http://mirrors.tuna.tsinghua.edu.cn/apache//db/derby/db-derby-10.14.2.0/db-derby-10.14.2.0-bin.tar.gz
tar zxvf db-derby-10.14.2.0-bin.tar.gz
mv db-derby-10.14.2.0-bin /usr/local/
mv db-derby-10.14.2.0-bin/ derby-10.14.2.0
chown -R hadoop /usr/local/derby-10.14.2.0/
chgrp -R dialout /usr/local/derby-10.14.2.0/
vi ~/.bashrc
export DERBY_HOME=/usr/local/derby-10.14.2.0
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib:${HADOOP_HOME}/lib:${HBASE_HOME}/lib:${HIVE_HOME}/lib:$DERBY_HOME/lib/derby.jar:$DERBY_HOME/lib/derbytools.jar
export PATH=$PATH:${JAVA_HOME}/bin:$HADOOP_HOME/sbin:$HADOOP_HOME/bin:$HBASE_HOME/bin:$HIVE_HOME/bin:$DERBY_HOME/bin
source ~/.bashrc
mkdir $DERBY_HOME/data
```
```
sysinfo
cd $DERBY_HOME/data
startNetworkServer &
mv metastore_db metastore_db.tmp
schematool -initSchema -dbType derby
```

### Results
```
Metastore connection URL:        jdbc:derby://localhost:1527/metastore_db;create=true
Metastore Connection Driver :    org.apache.derby.jdbc.ClientDriver
Metastore connection User:       APP
Starting metastore schema initialization to 2.3.0
Initialization script hive-schema-2.3.0.derby.sql
Initialization script completed
schemaTool completed
```

### ij
```
$ ij
ij> connect 'jdbc:derby://localhost:1527/metastore_db;create=true;';
ij> show tables;
```

## Configuring Metastore of Hive
```
cd $HIVE_HOME/conf
cp hive-default.xml.template hive-site.xml
```

`vi hive-site.xml`
```
<property>
	<name>javax.jdo.option.ConnectionURL</name>
	<value>jdbc:derby://localhost:1527/metastore_db;create=true</value>
	<description>JDBC connect string for a JDBC metastore </description>
</property>
<property>
	<name>hive.exec.local.scratchdir</name>
	<!--<value>${system:java.io.tmpdir}/${system:user.name}</value>-->
	<value>/tmp/hive_hadoop</value>
	<description>Local scratch space for Hive jobs</description>
</property>
<property>
	<name>hive.downloaded.resources.dir</name>
	<!--<value>${system:java.io.tmpdir}/${hive.session.id}_resources</value>-->
	<value>/tmp/hive_resources</value>
	<description>Temporary local directory for added resources in the remote file system.</description>
</property>
<property>
	<name>hive.druid.metadata.db.type</name>
	<!--<value>mysql</value>-->
	<value>derby</value>
</property>
<property>
	<name>javax.jdo.option.ConnectionDriverName</name>
	<!--<value>org.apache.derby.jdbc.EmbeddedDriver</value>-->
	<value>org.apache.derby.jdbc.ClientDriver</value>
	<description>Driver class name for a JDBC metastore</description>
</property>
<property>
	<name>hive.server2.thrift.bind.host</name>
	<value>tc210</value>
</property>
```

`vi jpox.properties`
```
javax.jdo.PersistenceManagerFactoryClass =

org.jpox.PersistenceManagerFactoryImpl
org.jpox.autoCreateSchema = false
org.jpox.validateTables = false
org.jpox.validateColumns = false
org.jpox.validateConstraints = false
org.jpox.storeManagerType = rdbms
org.jpox.autoCreateSchema = true
org.jpox.autoStartMechanismMode = checked
org.jpox.transactionIsolation = read_committed
javax.jdo.option.DetachAllOnCommit = true
javax.jdo.option.NontransactionalRead = true
javax.jdo.option.ConnectionDriverName = org.apache.derby.jdbc.ClientDriver
javax.jdo.option.ConnectionURL = jdbc:derby://localhost:1527/metastore_db;create=true
javax.jdo.option.ConnectionUserName = APP
javax.jdo.option.ConnectionPassword = mine
```

## Verifying Hive Installation
```
$HADOOP_HOME/bin/hadoop fs -mkdir /tmp
$HADOOP_HOME/bin/hadoop fs -mkdir -p /user/hive/warehouse
$HADOOP_HOME/bin/hadoop fs -chmod g+w /tmp
$HADOOP_HOME/bin/hadoop fs -chmod g+w /user/hive/warehouse
$HIVE_HOME/bin/hive
show tables;
```

## Tips
```
tail -f -n 99 /tmp/hadoop/hive.log
```

## References
- [Hive - Installation](https://www.tutorialspoint.com/hive/hive_installation.htm)
- [HiveDerbyServerMode](https://cwiki.apache.org/confluence/display/Hive/HiveDerbyServerMode)
- [Hive installation issues: Hive metastore database is not initialized](https://stackoverflow.com/questions/35655306/hive-installation-issues-hive-metastore-database-is-not-initialized)
- [Step 2: ij Basics](https://db.apache.org/derby/papers/DerbyTut/ij_intro.html)