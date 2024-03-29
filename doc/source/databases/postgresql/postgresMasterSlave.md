# PostgreSQL Master-Slave

## Requirements
- dockerNetworks.md

## pgmaster
`sudo vi pgmaster/pg_hba.conf`
```
host    replication     replica             172.18.0.101/32            trust
```
`sudo vi pgmaster/postgresql.conf`
```
listen_addresses = '*'        # 监听所有IP
archive_mode = on             # 允许归档
wal_level = replica           # 开启热备
archive_command = '/bin/date' # 用该命令来归档logfile segment,这里取消归档。
max_wal_senders = 32          # 这个设置了可以最多有几个流复制连接，差不多有几个从，就设置几个
wal_keep_segments = 64        # 设置流复制保留的最多的xlog数目，一份是 16M，注意机器磁盘 16M*64 = 1G
wal_sender_timeout = 60s      # 设置流复制主机发送数据的超时时间
max_connections = 100         # 这个设置要注意下，从库的max_connections必须要大于主库的
```
```
docker exec -it pgmaster bash
psql -U postgres
show archive_mode;
CREATE ROLE replica login replication encrypted password 'replica';
\du
\q
```

## pgslave
```
docker exec -it pgslave bash
su - postgres
rm -rf /var/lib/postgresql/data/*
pg_basebackup -h pgmaster -p 5432 -U replica -Fp -Xs -Pv -R -D /var/lib/postgresql/data &
# 混合云，先存datatmp，再替换data
# pg_basebackup -h pgmaster -p 5432 -U replica -Fp -Xs -Pv -R -D /var/lib/postgresql/datatmp &
```
```
pg_basebackup: initiating base backup, waiting for checkpoint to complete
pg_basebackup: checkpoint completed
pg_basebackup: write-ahead log start point: 0/2000028 on timeline 1
pg_basebackup: starting background WAL receiver
pg_basebackup: created temporary replication slot "pg_basebackup_43"
24636/24636 kB (100%), 1/1 tablespace
pg_basebackup: write-ahead log end point: 0/2000138
pg_basebackup: waiting for background process to finish streaming ...
pg_basebackup: syncing data to disk ...
pg_basebackup: base backup completed
```

`sudo vi pgslave/pg_hba.conf`
```
host    replication     replica             172.18.0.100/32            trust
```
`sudo vi pgslave/postgresql.conf`
```
hot_standby = on
hot_standby_feedback = on
```
`sudo vi pgslave/recovery.conf`
```
standby_mode = on
primary_conninfo = 'host=pgmaster port=5432 user=replica password=replica'
recovery_target_timeline = 'latest'
```
```
docker exec -it pgmaster bash
psql -U postgres -c "select client_addr,sync_state from pg_stat_replication;"
psql -U postgres
select client_addr,sync_state from pg_stat_replication;
```

## Tests
```
docker stop pgmaster
docker exec -it pgslave bash
su postgres
/usr/lib/postgresql/11/bin/pg_ctl promote
```

`psql -U postgres -c "select pg_is_in_recovery();"`
- master: f
- slave: t

`vi pgmaster/recovery.conf`
```
standby_mode = on
primary_conninfo = 'host=pgslave port=5432 user=replica password=replica'
recovery_target_timeline = 'latest'
```

## References
- [利用Docker部署 PostgreSQL 12.4主从](https://blog.csdn.net/qianglei6077/article/details/109581525)