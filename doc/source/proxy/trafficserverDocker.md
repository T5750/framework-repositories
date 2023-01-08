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

## Screenshots
![](https://docs.trafficserver.apache.org/en/latest/_images/records.jpg)

## References
- [Traffic Server](https://trafficserver.apache.org/)
- [Traffic Server GitHub](https://github.com/apache/trafficserver)
- [shaker/trafficserver Docker](https://hub.docker.com/r/shaker/trafficserver)
- [shaker/trafficserver GitHub](https://github.com/sqawasmi/trafficserver-docker)
- [Access Control Plugin](https://docs.trafficserver.apache.org/en/latest/admin-guide/plugins/access_control.en.html)