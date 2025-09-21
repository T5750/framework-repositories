# Oracle Docker

## wnameless/docker-oracle-xe-11g
Oracle Express Edition 11g Release 2 on Ubuntu 18.04 LTS

Run with 1521 port opened:
```sh
docker run -d -p 49161:1521 wnameless/oracle-xe-11g-r2
docker run -d --name oracle -p 1521:1521 -e ORACLE_ALLOW_REMOTE=true --restart=always -e TZ=Asia/Shanghai wnameless/oracle-xe-11g-r2
```

Run this, if you want the database to be connected remotely:
```sh
docker run -d -p 49161:1521 -e ORACLE_ALLOW_REMOTE=true wnameless/oracle-xe-11g-r2
```

Enable XDB user with default password: xdb, run this:
```sh
docker run -d -p 49161:1521 -e ORACLE_ENABLE_XDB=true wnameless/oracle-xe-11g-r2
```

For APEX user:
```sh
docker run -d -p 49161:1521 -p 8080:8080 wnameless/oracle-xe-11g-r2
```
- Oracle APEX: [http://localhost:8080/apex/apex_admin](http://localhost:8080/apex/apex_admin)
- User: ADMIN / admin

By default, the password verification is disable(password never expired)<br/>
Connect database with following setting:
```
hostname: localhost
port: 49161
sid: xe
username: system
password: oracle
```

Password for SYS & SYSTEM
```
oracle
```

## Tests
```sh
docker exec -it oracle bash
sqlplus system/oracle
select * from v$version;
# 查看当前所有的数据库
select name from v$database;
```

## Tips
- ORACLE_HOME: /u01/app/oracle/product/11.2.0/xe
- Oracle ORADATA: /u01/app/oracle/oradata

## References
- [wnameless/docker-oracle-xe-11g GitHub](https://github.com/wnameless/docker-oracle-xe-11g)