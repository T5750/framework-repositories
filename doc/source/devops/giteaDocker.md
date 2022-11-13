# Gitea Docker

Gitea: Git with a cup of tea - A painless self-hosted Git service. 

## Docker
```sh
docker run -d --name gitea -p 3000:3000 -p 222:22 -v $PWD/gitea:/data gitea/gitea
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
version: "3"
networks:
  gitea:
    external: false
services:
  server:
    image: gitea/gitea:1.17.3
    container_name: gitea
    environment:
      - USER_UID=1000
      - USER_GID=1000
    restart: always
    networks:
      - gitea
    volumes:
      - ./gitea:/data
      - /etc/timezone:/etc/timezone:ro
      - /etc/localtime:/etc/localtime:ro
    ports:
      - "3000:3000"
      - "222:22"
```

## Runtime Environment
- [Go v1.13](https://github.com/golang/go)

## Screenshots
![](https://gitea.io/images/screenshot.png)

## References
- [Gitea Docker](https://docs.gitea.io/en-us/install-with-docker/)
- [Gitea GitHub](https://github.com/go-gitea)
- [Gitea](https://gitea.io/zh-cn/)