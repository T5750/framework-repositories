# Hive Database

## Create Database
### Create Database Statement
```
Syntax: CREATE DATABASE|SCHEMA [IF NOT EXISTS] <database name>
hive> CREATE DATABASE [IF NOT EXISTS] userdb;
# or
hive> CREATE SCHEMA userdb;
```
```
hive> SHOW DATABASES;
default
userdb
```

### JDBC Program
- `HiveCreateDb`

### Tips
```
hiveserver2 &
netstat -anp |grep 10000
tail -f -n 99 /tmp/hadoop/hive.log
```
[HiveServer2](http://tc210:10002/)

`vi /usr/local/hadoop/etc/hadoop/core-site.xml`
```
<property>
	<name>hadoop.proxyuser.hadoop.groups</name>
	<value>hadoop</value>
	<description>Allow the superuser oozie to impersonate any members of the group group1 and group2</description>
</property>
<property>
	<name>hadoop.proxyuser.hadoop.hosts</name>
	<value>tc210,127.0.0.1,localhost</value>
	<description>The superuser can connect only from host1 and host2 to impersonate a user</description>
</property>
```

## Drop Database
### Drop Database Statement
```
DROP DATABASE StatementDROP (DATABASE|SCHEMA) [IF EXISTS] database_name [RESTRICT|CASCADE];
hive> DROP DATABASE IF EXISTS userdb;
hive> DROP DATABASE IF EXISTS userdb CASCADE;
hive> DROP SCHEMA userdb;
```

### JDBC Program
- `HiveDropDb`

## References
- [Hive - Create Database](https://www.tutorialspoint.com/hive/hive_create_database.htm)
- [Hive - Drop Database](https://www.tutorialspoint.com/hive/hive_drop_database.htm)