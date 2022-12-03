# nginx Docker

Nginx (pronounced "engine-x") is an open source reverse proxy server for HTTP, HTTPS, SMTP, POP3, and IMAP protocols, as well as a load balancer, HTTP cache, and a web server (origin server). The nginx project started with a strong focus on high concurrency, high performance and low memory usage.
```sh
mkdir logs cert
docker cp nginx:/usr/share/nginx/html .
docker cp nginx:/etc/nginx/nginx.conf .
docker cp nginx:/etc/nginx/conf.d .
```

## Docker
```sh
docker run --name nginx -d nginx:stable
docker run --name nginx -d nginx:1.18
```

## Docker Compose
`nginx.yml`

[http://localhost/](http://localhost/)

## Tests
分别用 http://127.0.0.1/proxy/index.html 进行访问
```
location /proxy/ {
    proxy_pass http://127.0.0.1/;
}
```
代理到URL：http://127.0.0.1/index.html
```
location /proxy/ {
    proxy_pass http://127.0.0.1;
}
```
代理到URL：http://127.0.0.1/proxy/index.html
```
location /proxy/ {
    proxy_pass http://127.0.0.1/abc/;
}
```
代理到URL：http://127.0.0.1/abc/index.html
```
location /proxy/ {
    proxy_pass http://127.0.0.1/abc;
}
```
代理到URL：http://127.0.0.1/abcindex.html

## References
- [nginx Docker](https://hub.docker.com/_/nginx)
- [docker-compose nginx + ssl配置](https://blog.csdn.net/qq_31878883/article/details/94390860)
- [Nginx代理参数配置详解之proxy_pass方法](https://blog.csdn.net/weixin_48803304/article/details/107876414)