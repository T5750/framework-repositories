# PostgreSQL Tutorial

The PostgreSQL object-relational database system provides reliability and data integrity.

## Docker
```sh
docker run -d --name pg01 -p 5432:5432 --restart=always -e POSTGRES_DB=pg -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -v /usr/local/postgres/pg01:/var/lib/postgresql/data -v /etc/localtime:/etc/localtime:ro postgres:11.9
```

## via `psql`
```sh
docker run -it --rm postgres:11.9 psql -h pg01_IP -U postgres
```

## CentOS 7
```sh
sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
sudo yum install -y postgresql11-server
sudo /usr/pgsql-11/bin/postgresql-11-setup initdb
sudo systemctl enable postgresql-11
sudo systemctl start postgresql-11
```
开启远程连接
```sh
vi /var/lib/pgsql/11/data/postgresql.conf
listen_addresses = '*'
vi /var/lib/pgsql/11/data/pg_hba.conf
host    all             all             0.0.0.0/0               trust
systemctl restart postgresql-11
```

## Tests
```sql
createdb mydb
# dropdb mydb
```

```
psql mydb
SELECT version();
SELECT current_date;
SELECT 2 + 2;
\h
\q
```

## References
- [PostgreSQL 11.2 手册](http://www.postgres.cn/docs/11/index.html)
- [PostgreSQL Docker](https://hub.docker.com/_/postgres/)
- [Linux downloads (Red Hat family)](https://www.postgresql.org/download/linux/redhat/)
- [PostgreSQL开启远程连接](https://blog.csdn.net/a654540233/article/details/115458860)