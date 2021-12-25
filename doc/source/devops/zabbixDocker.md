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
      -e TZ=Asia/Shanghai \
      -v /etc/localtime:/etc/localtime:ro \
      -d zabbix/zabbix-java-gateway:alpine-5.4.8

docker run --name zabbix-server-mysql -t \
             -e DB_SERVER_HOST=mysql_master \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             -e ZBX_JAVAGATEWAY=zabbix-java-gateway \
             -e TZ=Asia/Shanghai \
             -v /etc/localtime:/etc/localtime:ro \
             --link zabbix-java-gateway:zabbix-java-gateway \
             -p 10051:10051 \
             -d zabbix/zabbix-server-mysql:alpine-5.4.8

docker run --name zabbix-web-nginx-mysql -t \
             -e DB_SERVER_HOST=mysql_master \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             -e TZ=Asia/Shanghai \
             -v /etc/localtime:/etc/localtime:ro \
             --link zabbix-server-mysql:zabbix-server \
             -p 8080:8080 \
             -d zabbix/zabbix-web-nginx-mysql:alpine-5.4.8

docker run --name zabbix-agent -t \
             -e ZBX_SERVER_PORT=10051 \
             -e TZ=Asia/Shanghai \
             -v /etc/localtime:/etc/localtime:ro \
             --link zabbix-server-mysql:zabbix-server \
             -p 10050:10050 \
             -d zabbix/zabbix-agent:alpine-5.4.8
```
- [http://localhost:8080/](http://localhost:8080/)
- User: Admin / zabbix

## Screenshots
![](https://www.zabbix.com/documentation/current/assets/en/manual/introduction/vertical_menu.png)

![](https://www.zabbix.com/documentation/current/assets/en/manual/web_interface/dashboard1.png)

![](https://www.zabbix.com/documentation/current/assets/en/manual/web_monitoring/scenario_details1.png)

![](https://www.zabbix.com/documentation/current/assets/en/manual/web_interface/frontend_sections/monitoring/event_details_new1.png)

![](https://www.zabbix.com/documentation/current/assets/en/manual/web_interface/maps.png)

## Tips
Zabbix agent is not available (for 3m)
- 配置 -> 主机 -> Interfaces -> 客户端，需要修改为zabbix-agent容器的IP: `127.0.0.1` -> `172.17.0.7`
- `docker inspect zabbix-agent | grep IPAddress`

## References
- [从容器中安装Zabbix](https://www.zabbix.com/documentation/current/zh/manual/installation/containers)
- [Docker安装Zabbix5.2](https://www.cnblogs.com/st2021/p/14457212.html)