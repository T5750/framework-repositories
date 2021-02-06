# Docker Dockerfile

## Dockerfile
`Dockerfile-nginx`

```
docker build -t t5750/nginx .
docker run -d --name nginx --restart=always -v /etc/localtime:/etc/localtime:ro -p 80:80 t5750/nginx
```

## References
- [Dockerfile reference](https://docs.docker.com/engine/reference/builder/)