# Password Pusher Docker

An application to securely communicate passwords over the web. Passwords automatically expire after a certain number of views and/or time has passed. Track who, what and when.

## Docker
```sh
# ephemeral Temporary database that is wiped on container restart.
docker run -d -p 5100:5100 pglombardo/pwpush:release
# using an External Postgres Database Postgres database backed instance.
docker run -d -p 5100:5100 pglombardo/pwpush:release -e DATABASE_URL=postgres://pwpush_user:pwpush_passwd@postgres:5432/pwpush_db
# using an External MariaDB (MySQL) Database Mariadb database backed instance.
docker run -d -p 5100:5100 pglombardo/pwpush:release -e DATABASE_URL=mysql2://pwpush_user:pwpush_passwd@mysql:3306/pwpush_db
docker run -d --name pwpush -p 5100:5100 --privileged=true pglombardo/pwpush:release
```
[http://localhost:5100/](http://localhost:5100/)

## Docker Compose
```sh
# One-liner Password Pusher with a Postgres Database
curl -s -o docker-compose.yml https://raw.githubusercontent.com/pglombardo/PasswordPusher/master/containers/docker/docker-compose-postgres.yml && docker compose up -d
# One-liner Password Pusher with a MariaDB (MySQL) Database
curl -s -o docker-compose.yml https://raw.githubusercontent.com/pglombardo/PasswordPusher/master/containers/docker/docker-compose-mariadb.yml && docker compose up -d
```

## Runtime Environment
- [Ruby](https://rubyinstaller.org/downloads/)

## Screenshots
![](https://camo.githubusercontent.com/eb2dbe2005687c883de5395f4855b17fd5b46e68058da9ff5b5e34fb16c4855d/68747470733a2f2f7077707573682e667261312e63646e2e6469676974616c6f6365616e7370616365732e636f6d2f6272616e64696e672532467077707573682d6272616e642d6578616d706c652e706e67)

![](https://camo.githubusercontent.com/6d24c97a4e468f93f3cd6da5d440e71d8569256e7630de7a26bca93110e844bb/68747470733a2f2f7077707573682e667261312e63646e2e6469676974616c6f6365616e7370616365732e636f6d2f7468656d657325324671756172747a2d7468656d652d7077707573682e636f6d2e706e67)

## References
- [Password Pusher](https://pwpush.com/)
- [Password Pusher GitHub](https://github.com/pglombardo/PasswordPusher)
- [Password Pusher Docker](https://hub.docker.com/u/pglombardo)
- [Password Pusher Configuration](https://github.com/pglombardo/PasswordPusher/blob/master/Configuration.md)