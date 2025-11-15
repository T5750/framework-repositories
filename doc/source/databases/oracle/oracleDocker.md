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

### Tests
```sh
docker exec -it oracle bash
sqlplus system/oracle
select * from v$version;
# 查看当前所有的数据库
select name from v$database;
```

### Tips
- ORACLE_HOME: /u01/app/oracle/product/11.2.0/xe
- Oracle ORADATA: /u01/app/oracle/oradata

## helowin/oracle_11g
```sh
docker run -d -p 1521:1521 \
--restart=always \
--name oracle_11g \
-v /opt/oracle_data:/home/oracle/app/oracle/oradata/mydata \
registry.cn-hangzhou.aliyuncs.com/helowin/oracle_11g
```

### 修改配置
```sh
#1.进入容器
docker exec -it oracle_11g bash

#2.切换root用户
su - root
#密码：helowin

#3.修改环境变量
vi /etc/profile

export ORACLE_HOME=/home/oracle/app/oracle/product/11.2.0/dbhome_2
export ORACLE_SID=helowin
export PATH=$ORACLE_HOME/bin:$PATH

#4.使生效
source /etc/profile

#5.建立软连接
ln -s $ORACLE_HOME/bin/sqlplus /usr/bin

#6.退出root用户
exit
```

### 修改密码
```sh
#1.再次执行，使环境生效(因为oracle用户还没有生效)
source /etc/profile

#2.以dba身份连接
sqlplus / as sysdba

#3.修改密码
alter user system identified by 123456;
alter user sys identified by 123456;
ALTER PROFILE DEFAULT LIMIT PASSWORD_LIFE_TIME UNLIMITED;
```

## References
- [wnameless/docker-oracle-xe-11g GitHub](https://github.com/wnameless/docker-oracle-xe-11g)
- [Docker安装Oracle11g](https://juejin.cn/post/7286088186848739388)