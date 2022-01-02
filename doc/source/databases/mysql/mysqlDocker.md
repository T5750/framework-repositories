# MySQL Docker

## MySQL 5.x in Docker
```
docker run --name mysql_master -v $PWD/mysql_master/data:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:5.7
docker run --name mysql_master -v $PWD/mysql_master/data:/var/lib/mysql -v $PWD/mysql_master/mysql_master.cnf:/etc/mysql/my.cnf -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -d mysql:5.7
```

## MySQL 5.x in Docker Compose
- `mysql_master.cnf`
- `mysql_slave.cnf`
- `mysql.yml`

```
mkdir -p mysql_master/data mysql_slave/data
docker exec -it mysql_slave bash
export MYSQL_PWD=123456; mysql -u root -e "CHANGE MASTER TO MASTER_HOST='172.18.0.105',MASTER_USER='root',MASTER_PASSWORD='123456',MASTER_LOG_FILE='replicas-mysql-bin.000003',MASTER_LOG_POS=154;"
```

### Tests
```
docker exec mysql_master sh -c 'mysql -u root -p123456 -e "SHOW MASTER STATUS \G"'
docker exec mysql_slave sh -c 'mysql -u root -p123456 -e "SHOW SLAVE STATUS \G"'

docker exec mysql_master sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'create table code(code int); insert into code values (100), (200)'"
docker exec mysql_slave sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'select * from code \G'"
```

## MySQL 8.x in Docker Compose
- `mysql8master.cnf`
- `mysql8slave.cnf`
- `mysql8.yml`

```
mkdir -p mysql8master/data mysql8slave/data
docker exec -it mysql8slave bash
export MYSQL_PWD=123456; mysql -u root -e "CHANGE MASTER TO MASTER_HOST='172.18.0.107',MASTER_USER='root',MASTER_PASSWORD='123456',MASTER_LOG_FILE='replicas-mysql-bin.000003',MASTER_LOG_POS=156;"
```

### Tests
```
docker exec mysql8master sh -c 'mysql -u root -p123456 -e "SHOW MASTER STATUS \G"'
docker exec mysql8slave sh -c 'mysql -u root -p123456 -e "SHOW SLAVE STATUS \G"'

docker exec mysql8master sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'create table code(code int); insert into code values (100), (200)'"
docker exec mysql8slave sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'select * from code \G'"
```

## References
- [Docker Compose搭建MySQL主从复制集群](https://zhuanlan.zhihu.com/p/45193580)
- [docker-box/mysql-cluster](https://github.com/docker-box/mysql-cluster)
- [MySQL 8.0 - Docker 搭建主从复制](https://github.com/solidSpoon/solidSpoon.github.io/issues/26)
- [MySQL Docker](https://hub.docker.com/_/mysql)