# Zabbix JVM Docker

## What is Zabbix server?
Zabbix server is the central process of Zabbix software.

The server performs the polling and trapping of data, it calculates triggers, sends notifications to users. It is the central component to which Zabbix agents and proxies report data on availability and integrity of systems. The server can itself remotely check networked services (such as web servers and mail servers) using simple service checks.

## Docker
```
docker run --name zabbix-server-mysql -t \
             -e DB_SERVER_HOST=mysql_master \
             -e MYSQL_DATABASE=zabbix \
             -e MYSQL_USER=zabbix \
             -e MYSQL_PASSWORD=123456 \
             -e MYSQL_ROOT_PASSWORD=123456 \
             -e ZBX_JAVAGATEWAY=zabbix-java-gateway \
             -e ZBX_JAVAGATEWAY_ENABLE=true \
             -e ZBX_JAVAGATEWAYPORT=10052 \
             -e ZBX_STARTJAVAPOLLERS=5 \
             -e TZ=Asia/Shanghai \
             -v /etc/localtime:/etc/localtime:ro \
             --link zabbix-java-gateway:zabbix-java-gateway \
             -p 10051:10051 \
             -d zabbix/zabbix-server-mysql:alpine-5.4.8
```
主机 -> 模板：Generic Java JMX

## Enabling remote JMX monitoring for Java application
```
nohup java \
-Dcom.sun.management.jmxremote \
-Dcom.sun.management.jmxremote.port=12345 \
-Dcom.sun.management.jmxremote.authenticate=false \
-Dcom.sun.management.jmxremote.ssl=false \
-jar spring-boot-1.0.jar >/dev/null 2>&1 &
```
```
java \
       -Djava.rmi.server.hostname=192.168.3.14 \
       -Dcom.sun.management.jmxremote \
       -Dcom.sun.management.jmxremote.port=12345 \
       -Dcom.sun.management.jmxremote.authenticate=true \
       -Dcom.sun.management.jmxremote.password.file=/etc/java-6-openjdk/management/jmxremote.password \
       -Dcom.sun.management.jmxremote.access.file=/etc/java-6-openjdk/management/jmxremote.access \
       -Dcom.sun.management.jmxremote.ssl=true \
       -Djavax.net.ssl.keyStore=$YOUR_KEY_STORE \
       -Djavax.net.ssl.keyStorePassword=$YOUR_KEY_STORE_PASSWORD \
       -Djavax.net.ssl.trustStore=$YOUR_TRUST_STORE \
       -Djavax.net.ssl.trustStorePassword=$YOUR_TRUST_STORE_PASSWORD \
       -Dcom.sun.management.jmxremote.ssl.need.client.auth=true \
       -jar /usr/share/doc/openjdk-6-jre-headless/demo/jfc/Notepad/Notepad.jar
```

### catalina.sh
```
CATALINA_OPTS=”$CATALINA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false”
```

### catalina.bat
```
set JAVA_OPTS=%JAVA_OPTS% -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=12345 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false
```

## Environment Variables
- `cat /etc/zabbix/zabbix_server.conf`
- `ZBX_LOADMODULE`
- `ZBX_DEBUGLEVEL`
- `ZBX_TIMEOUT`
- `ZBX_JAVAGATEWAY_ENABLE`

### Other variables
```
ZBX_ALLOWUNSUPPORTEDDBVERSIONS=0 # Available since 6.0.0
ZBX_DBTLSCONNECT= # Available since 5.0.0
ZBX_DBTLSCAFILE= # Available since 5.0.0
ZBX_DBTLSCERTFILE= # Available since 5.0.0
ZBX_DBTLSKEYFILE= # Available since 5.0.0
ZBX_DBTLSCIPHER= # Available since 5.0.0
ZBX_DBTLSCIPHER13= # Available since 5.0.0
ZBX_VAULTDBPATH= # Available since 5.2.0
ZBX_VAULTURL=https://127.0.0.1:8200 # Available since 5.2.0
VAULT_TOKEN= # Available since 5.2.0
ZBX_LISTENIP=
ZBX_LISTENPORT=10051
ZBX_LISTENBACKLOG=
ZBX_STARTREPORTWRITERS=0 # Available since 5.4.0
ZBX_WEBSERVICEURL=http://zabbix-web-service:10053/report # Available since 5.4.0
ZBX_SERVICEMANAGERSYNCFREQUENCY=60 # Available since 6.0.0
ZBX_HISTORYSTORAGEURL= # Available since 3.4.0
ZBX_HISTORYSTORAGETYPES=uint,dbl,str,log,text # Available since 3.4.0
ZBX_STARTPOLLERS=5
ZBX_IPMIPOLLERS=0
ZBX_STARTPREPROCESSORS=3 # Available since 3.4.0
ZBX_STARTPOLLERSUNREACHABLE=1
ZBX_STARTTRAPPERS=5
ZBX_STARTPINGERS=1
ZBX_STARTDISCOVERERS=1
ZBX_STARTHISTORYPOLLERS=5 # Available since 5.4.0
ZBX_STARTHTTPPOLLERS=1
ZBX_STARTTIMERS=1
ZBX_STARTESCALATORS=1
ZBX_STARTALERTERS=3 # Available since 3.4.0
ZBX_JAVAGATEWAY=zabbix-java-gateway
ZBX_JAVAGATEWAYPORT=10052
ZBX_STARTJAVAPOLLERS=5
ZBX_STARTLLDPROCESSORS=2 # Available since 4.2.0
ZBX_STATSALLOWEDIP= # Available since 4.0.5
ZBX_STARTVMWARECOLLECTORS=0
ZBX_VMWAREFREQUENCY=60
ZBX_VMWAREPERFFREQUENCY=60
ZBX_VMWARECACHESIZE=8M
ZBX_VMWARETIMEOUT=10
ZBX_ENABLE_SNMP_TRAPS=false
ZBX_SOURCEIP=
ZBX_HOUSEKEEPINGFREQUENCY=1
ZBX_MAXHOUSEKEEPERDELETE=5000
ZBX_PROBLEMHOUSEKEEPINGFREQUENCY=60 # Available since 6.0.0
ZBX_SENDERFREQUENCY=30
ZBX_CACHESIZE=8M
ZBX_CACHEUPDATEFREQUENCY=60
ZBX_STARTDBSYNCERS=4
ZBX_EXPORTFILESIZE=1G # Available since 4.0.0
ZBX_EXPORTTYPE= # Available since 5.0.10 and 5.2.6
ZBX_AUTOHANODENAME=fqdn # Allowed values: fqdn, hostname. Available since 6.0.0
ZBX_HANODENAME= # Available since 6.0.0
ZBX_AUTONODEADDRESS=fqdn # Allowed values: fqdn, hostname. Available since 6.0.0
ZBX_NODEADDRESSPORT=10051 # Allowed to use with ZBX_AUTONODEADDRESS variable only. Available since 6.0.0
ZBX_NODEADDRESS=localhost # Available since 6.0.0
ZBX_HISTORYCACHESIZE=16M
ZBX_HISTORYINDEXCACHESIZE=4M
ZBX_HISTORYSTORAGEDATEINDEX=0 # Available since 4.0.0
ZBX_TRENDCACHESIZE=4M
ZBX_TRENDFUNCTIONCACHESIZE=4M
ZBX_VALUECACHESIZE=8M
ZBX_TRAPPERTIMEOUT=300
ZBX_UNREACHABLEPERIOD=45
ZBX_UNAVAILABLEDELAY=60
ZBX_UNREACHABLEDELAY=15
ZBX_LOGSLOWQUERIES=3000
ZBX_STARTPROXYPOLLERS=1
ZBX_PROXYCONFIGFREQUENCY=3600
ZBX_PROXYDATAFREQUENCY=1
ZBX_TLSCAFILE=
ZBX_TLSCRLFILE=
ZBX_TLSCERTFILE=
ZBX_TLSKEYFILE=
ZBX_TLSCIPHERALL= # Available since 4.4.7
ZBX_TLSCIPHERALL13= # Available since 4.4.7
ZBX_TLSCIPHERCERT= # Available since 4.4.7
ZBX_TLSCIPHERCERT13= # Available since 4.4.7
ZBX_TLSCIPHERPSK= # Available since 4.4.7
ZBX_TLSCIPHERPSK13= # Available since 4.4.7
```
Please use official documentation for [zabbix_server.conf](https://www.zabbix.com/documentation/current/manual/appendix/config/zabbix_server) to get more information about the variables.

## Screenshots
![](https://www.zabbix.com/documentation/current/assets/en/manual/config/items/itemtypes/jmx_interface.png)

![](https://www.zabbix.com/documentation/current/assets/en/manual/config/items/itemtypes/jmx_item.png)

## References
- [zabbix监控jvm内存](https://www.cnblogs.com/chywx/p/11496528.html)
- [zabbix/zabbix-server-mysql](https://hub.docker.com/r/zabbix/zabbix-server-mysql/)
- [Zabbix JMX monitoring](https://www.zabbix.com/documentation/current/en/manual/config/items/itemtypes/jmx_monitoring)