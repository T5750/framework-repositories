# Qbittorrent Docker

A BitTorrent client in Qt

## Docker
```sh
docker run -d \
  --name=qbittorrent \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e WEBUI_PORT=8080 \
  -p 8080:8080 \
  -p 6881:6881 \
  -p 6881:6881/udp \
  -v /path/to/appdata/config:/config \
  -v /path/to/downloads:/downloads \
  --restart unless-stopped \
  lscr.io/linuxserver/qbittorrent:latest
docker run -d --name=qbittorrent -p 8080:8080 -p 6881:6881 -p 6881:6881/udp lscr.io/linuxserver/qbittorrent
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
---
services:
  qbittorrent:
    image: lscr.io/linuxserver/qbittorrent:latest
    container_name: qbittorrent
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - WEBUI_PORT=8080
    volumes:
      - /path/to/appdata/config:/config
      - /path/to/downloads:/downloads
    ports:
      - 8080:8080
      - 6881:6881
      - 6881:6881/udp
    restart: unless-stopped
```

## Runtime Environment
- C++

## References
- [Qbittorrent](https://www.qbittorrent.org/)
- [Qbittorrent GitHub](https://github.com/qbittorrent/qBittorrent)
- [linuxserver/qbittorrent Docker](https://docs.linuxserver.io/images/docker-qbittorrent/)