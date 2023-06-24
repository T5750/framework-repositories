# Superset Docker

Apache Superset is a modern data exploration and visualization platform

## Docker
```sh
docker run -d -p 8088:8088 -e "SUPERSET_SECRET_KEY=your_secret_key_here" --name superset apache/superset
```

### Initialize a local Superset Instance
1.Setup your local admin account
```sh
docker exec -it superset superset fab create-admin \
              --username admin \
              --firstname Superset \
              --lastname Admin \
              --email admin@superset.com \
              --password admin
```
2.Migrate local DB to latest
```sh
docker exec -it superset superset db upgrade
```
3.Load Examples
```sh
docker exec -it superset superset load_examples
```
4.Setup roles
```sh
docker exec -it superset superset init
```
5.Login and take a look
- [http://localhost:8088/](http://localhost:8088/)
- User: admin / admin

## Docker Compose
- [docker-compose-non-dev.yml](https://github.com/apache/superset/blob/master/docker-compose-non-dev.yml)

## Screenshots
![](https://superset.apache.org/img/explore.jpg)

![](https://superset.apache.org/img/dashboard.jpg)

![](https://superset.apache.org/img/sql_lab.jpg)

## References
- [Superset](https://superset.apache.org/)
- [Superset GitHub](https://github.com/apache/superset)
- [Superset Docker](https://hub.docker.com/r/apache/superset)
- [Superset API](https://superset.apache.org/docs/api)