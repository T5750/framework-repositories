# MySQL Docker

## Docker Compose
- `mysql_master.cnf`
- `mysql_slave.cnf`
- `mysql.yml`

```
mkdir -p mysql_master/data mysql_slave/data
docker exec -it mysql_slave bash
export MYSQL_PWD=123456; mysql -u root -e "CHANGE MASTER TO MASTER_HOST='172.60.0.105',MASTER_USER='root',MASTER_PASSWORD='123456',MASTER_LOG_FILE='replicas-mysql-bin.000003',MASTER_LOG_POS=154;"
```

### Tests
```
docker exec mysql_master sh -c 'mysql -u root -p123456 -e "SHOW MASTER STATUS \G"'
docker exec mysql_slave sh -c 'mysql -u root -p123456 -e "SHOW SLAVE STATUS \G"'

docker exec mysql_master sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'create table code(code int); insert into code values (100), (200)'"
docker exec mysql_slave sh -c "export MYSQL_PWD=123456; mysql -u root replicas_db -e 'select * from code \G'"
```

## References
- [Docker Compose搭建MySQL主从复制集群](https://zhuanlan.zhihu.com/p/45193580)
- [docker-box/mysql-cluster](https://github.com/docker-box/mysql-cluster)