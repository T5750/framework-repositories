# Traffic Server Docker

Apache Traffic Server™ software is a fast, scalable and extensible HTTP/1.1 and HTTP/2 compliant caching proxy server. Formerly a commercial product, Yahoo! donated it to the Apache Foundation, and currently used by several major CDNs and content owners.

## Docker
```sh
docker run -d --name trafficserver -p 8080:8080 shaker/trafficserver
```
[http://localhost:8080/](http://localhost:8080/)

## Configuration
```sh
docker run -d --name trafficserver -p 8080:8080 /MY-CONFIGS/trafficserver/:/etc/trafficserver/ shaker/trafficserver
```

## Tests
```sh
docker exec -it trafficserver bash
/opt/trafficserver/bin/traffic_logcat /opt/trafficserver/var/log/trafficserver/squid.blog
cat /opt/trafficserver/var/log/trafficserver/diags.log
apt-get update && apt-get install -y vim
vi /etc/trafficserver/remap.config
map http://localhost:8080/ https://www.baidu.com
docker restart trafficserver
```

## Screenshots
![](https://docs.trafficserver.apache.org/en/latest/_images/records.jpg)

## References
- [Traffic Server](https://trafficserver.apache.org/)
- [Traffic Server GitHub](https://github.com/apache/trafficserver)
- [shaker/trafficserver Docker](https://hub.docker.com/r/shaker/trafficserver)
- [shaker/trafficserver GitHub](https://github.com/sqawasmi/trafficserver-docker)
- [Access Control Plugin](https://docs.trafficserver.apache.org/en/latest/admin-guide/plugins/access_control.en.html)
- [Getting Started](https://docs.trafficserver.apache.org/en/latest/getting-started/index.en.html)