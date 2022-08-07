# nginx Docker

Nginx (pronounced "engine-x") is an open source reverse proxy server for HTTP, HTTPS, SMTP, POP3, and IMAP protocols, as well as a load balancer, HTTP cache, and a web server (origin server). The nginx project started with a strong focus on high concurrency, high performance and low memory usage. It is licensed under the 2-clause BSD-like license and it runs on Linux, BSD variants, Mac OS X, Solaris, AIX, HP-UX, as well as on other *nix flavors. It also has a proof of concept port for Microsoft Windows.
```
mkdir logs cert
docker cp nginx:/usr/share/nginx/html .
docker cp nginx:/etc/nginx/nginx.conf .
docker cp nginx:/etc/nginx/conf.d .
```

## Docker
```
docker run --name nginx -d nginx:stable
docker run --name nginx -d nginx:1.18
```

## Docker Compose
`nginx.yml`

[http://localhost/](http://localhost/)

## References
- [nginx Docker](https://hub.docker.com/_/nginx)
- [docker-compose nginx + ssl配置](https://blog.csdn.net/qq_31878883/article/details/94390860)