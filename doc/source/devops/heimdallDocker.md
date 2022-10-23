# Heimdall Docker

An Application dashboard and launcher

## Docker
```sh
docker run -d \
  --name=heimdall \
  -p 8080:80 \
  -v $PWD/config:/config \
  --restart unless-stopped \
  linuxserver/heimdall
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: "2.1"
services:
  heimdall:
    image: lscr.io/linuxserver/heimdall:latest
    container_name: heimdall
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Europe/London
    volumes:
      - </path/to/appdata/config>:/config
    ports:
      - 80:80
      - 443:443
    restart: unless-stopped
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)

## Screenshots
![](https://heimdall.site/img/mockup.jpg)

![](https://i.imgur.com/MrC4QpN.gif)

## References
- [Heimdall](https://heimdall.site/)
- [Heimdall GitHub](https://github.com/linuxserver/Heimdall)
- [Heimdall Docker](https://hub.docker.com/r/linuxserver/heimdall/)