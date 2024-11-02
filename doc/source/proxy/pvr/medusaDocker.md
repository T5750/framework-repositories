# Medusa Docker

Medusa is an automatic Video Library Manager for TV Shows.
It watches for new episodes of your favorite shows, and when they are posted it does its magic: automatic torrent/nzb searching, downloading, and processing at the qualities you want.

## Docker
```sh
docker run -d \
  --name=medusa \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 8081:8081 \
  -v /path/to/medusa/config:/config \
  -v /path/to/downloads:/downloads \
  -v /path/to/tv/shows:/tv \
  --restart unless-stopped \
  lscr.io/linuxserver/medusa:latest
docker run -d --name=medusa -p 8081:8081 quay.io/linuxserver.io/medusa
```
[http://localhost:8081/](http://localhost:8081/)

## Docker Compose
```
---
services:
  medusa:
    image: lscr.io/linuxserver/medusa:latest
    container_name: medusa
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/medusa/config:/config
      - /path/to/downloads:/downloads
      - /path/to/tv/shows:/tv
    ports:
      - 8081:8081
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.7.x](https://www.python.org/downloads/)

## Screenshots
![](https://pymedusa.com/images/screenshots/home.jpg)

![](https://pymedusa.com/images/screenshots/show.jpg)

![](https://pymedusa.com/images/screenshots/calendar.jpg)

## References
- [Medusa](https://pymedusa.com/)
- [Medusa GitHub](https://github.com/pymedusa/Medusa)
- [linuxserver/medusa Docker](https://docs.linuxserver.io/images/docker-medusa/)