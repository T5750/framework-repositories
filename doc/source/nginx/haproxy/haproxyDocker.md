# HAProxy Docker

The Reliable, High Performance TCP/HTTP Load Balancer

## Docker
```
docker run -d --name haproxy -v $PWD/haproxy:/usr/local/etc/haproxy:ro --sysctl net.ipv4.ip_unprivileged_port_start=0 -p 80:80 haproxy:2.3
```
Note that your host's `$PWD/haproxy` folder should be populated with a file named `haproxy.cfg`

## Docker Compose
- `haproxy.cfg`
- `haproxy.yml`

[http://localhost/](http://localhost/)

## References
- [Docker Compose部署Haproxy](https://www.ngui.cc/51cto/show-449516.html)
- [HAProxy Docker](https://hub.docker.com/_/haproxy)