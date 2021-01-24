# Cassandra Docker

## Cassandra Standalone in Docker Compose
`cassandra-standalone.yml`

## Cassandra Cluster in Docker Compose
```
docker run --name cassandra -d cassandra
docker cp cassandra:/opt/cassandra/conf/cassandra.yaml .
mkdir cassandra{1,2,3}
vi cassandra.yaml
AllowAllAuthenticator -> PasswordAuthenticator
```

## Docker Compose
`cassandra.yml`

```
docker exec -it cassandra1 bash
cqlsh -u cassandra -pcassandra
describe keyspaces;
CREATE KEYSPACE IF NOT EXISTS mycasdb WITH REPLICATION = {'class': 'SimpleStrategy','replication_factor':3};
describe keyspaces;
```

### CRUD
```
use mycasdb;
CREATE TABLE user (id int,user_name varchar,PRIMARY KEY (id));
describe tables;
INSERT INTO user (id,user_name) VALUES (1,'user_1');
INSERT INTO user (id,user_name) VALUES (2,'user_2');
INSERT INTO user (id,user_name) VALUES (3,'user_3');
select * from user;
delete from user where id=2;
select * from user;
```

### Test
`nodetool -h host -u username -pw password [option]`
```
nodetool status
nodetool info
nodetool -h 172.18.0.152 -p 7001 status
```

## References
- [基于docker创建Cassandra集群](https://www.cnblogs.com/xiao987334176/p/13219163.html)