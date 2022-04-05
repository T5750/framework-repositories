# nginx Docker

```
mkdir logs cert
docker cp nginx:/usr/share/nginx/html .
docker cp nginx:/etc/nginx/nginx.conf .
docker cp nginx:/etc/nginx/conf.d .
```

## Docker
```
docker run --name nginx -d nginx:stable
```

## Docker Compose
`nginx.yml`

[http://localhost/](http://localhost/)

## References
- [nginx Docker](https://hub.docker.com/_/nginx)
- [docker-compose nginx + ssl配置](https://blog.csdn.net/qq_31878883/article/details/94390860)