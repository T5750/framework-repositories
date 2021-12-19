# Zabbix Docker

Zabbix is an enterprise-class open source distributed monitoring solution.

## Docker
1. Start empty MySQL server instance
2. Start Zabbix Java gateway instance
3. Start Zabbix server instance and link the instance with created MySQL server instance
4. Start Zabbix web interface and link the instance with created MySQL server and Zabbix server instances

```
docker run --name mysql_master -t \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             -e TZ=Asia/Shanghai \
             -v $PWD/mysql_master/data:/var/lib/mysql \
             -v /etc/localtime:/etc/localtime:ro \
             -p 3306:3306 \
             -d mysql:5.7 \
             --character-set-server=utf8 --collation-server=utf8_bin

docker run --name zabbix-java-gateway -t \
      -d zabbix/zabbix-java-gateway:alpine-5.4.8

docker run --name zabbix-server-mysql -t \
             -e DB_SERVER_HOST=mysql_master \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             -e ZBX_JAVAGATEWAY=zabbix-java-gateway \
             --link zabbix-java-gateway:zabbix-java-gateway \
             -p 10051:10051 \
             -d zabbix/zabbix-server-mysql:alpine-5.4.8

docker run --name zabbix-web-nginx-mysql -t \
             -e DB_SERVER_HOST=mysql_master \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             --link zabbix-server-mysql:zabbix-server \
             -p 8080:8080 \
             -d zabbix/zabbix-web-nginx-mysql:alpine-5.4.8
```
- [http://localhost:8080/](http://localhost:8080/)
- User: Admin / zabbix

## References
- [从容器中安装Zabbix](https://www.zabbix.com/documentation/current/zh/manual/installation/containers)
- [Docker安装Zabbix5.2](https://www.cnblogs.com/st2021/p/14457212.html)