# Axelor Docker

Axelor Open Suite reduces the complexity and improve responsiveness of business processes. Thanks to its modularity, you can start with few features and then activate other modules when needed.

## Demo
[Axelor Demo](https://demo.axelor.com/open-suite-en/login.jsp)

## Docker
```
docker run -d --name axelor -p 8080:80 axelor/aio-erp
```
- [http://localhost:8080/](http://localhost:8080/)
- User: admin / admin

## Other Options
The docker image exposes following ports:
- `80` - nginx http port
- `443` - nginx https port
- `8080` - tomcat http port
- `5432` - postgresql port

The docker image exposes following volumes:
- `/var/lib/tomcat` - tomcat base directory
- `/var/lib/postgresql` - postgresql data directory
- `/var/log/tomcat` - tomcat log files
- `/var/log/postgresql` - postgresql log files

Following environment variables can be used to change container settings:
- `NGINX_HOST` - the public host name (default: localhost)
- `NGINX_PORT` - the public port (default: 443)
- `POSTGRES_USER` - the postgresql user name (default: axelor)
- `POSTGRES_PASSWORD` - the postgresql user password (default: axelor)
- `POSTGRES_DB` - the postgresql database name (default: axelor)

## Runtime Environment
- [PostgreSQL 9.x](https://www.postgresql.org/download/)
- [Java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [nginx 1.15.x](http://nginx.org/en/download.html)

## Screenshots
![](https://docs.axelor.com/abs/5.0/functional/_images/en/CRM/creationpisteen.png)

![](https://docs.axelor.com/abs/5.0/functional/_images/en/Calendar/eventsen.png)

![](https://docs.axelor.com/abs/5.0/functional/_images/en/Sales/devisen.png)

![](https://docs.axelor.com/abs/5.0/functional/_images/en/Fleet/vehicleen.png)

![](https://docs.axelor.com/abs/5.0/functional/_images/en/Project/projecten.png)

## References
- [axelor/aio-erp Docker](https://hub.docker.com/r/axelor/aio-erp)
- [Axelor Open Suite](https://github.com/axelor/axelor-open-suite)
- [Axelor CRM](https://docs.axelor.com/abs/5.0/functional/crm.html)