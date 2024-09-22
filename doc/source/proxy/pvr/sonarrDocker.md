# Sonarr Docker

Smart PVR for newsgroup and bittorrent users.

## Docker
```sh
docker run -d \
  --name=sonarr \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 8989:8989 \
  -v /path/to/sonarr/data:/config \
  -v /path/to/tvseries:/tv `#optional` \
  -v /path/to/downloadclient-downloads:/downloads `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/sonarr:latest
docker run -d --name=sonarr -e PUID=1000 -e PGID=1000 -p 8989:8989 quay.io/linuxserver.io/sonarr
```
[http://localhost:8989/](http://localhost:8989/)

## Docker Compose
```
---
services:
  sonarr:
    image: lscr.io/linuxserver/sonarr:latest
    container_name: sonarr
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/sonarr/data:/config
      - /path/to/tvseries:/tv #optional
      - /path/to/downloadclient-downloads:/downloads #optional
    ports:
      - 8989:8989
    restart: unless-stopped
```

## Runtime Environment
- C#

## Screenshots
![](https://sonarr.tv/img/slider/seriesdetails.png)

![](https://sonarr.tv/img/slider/posters.png)

## References
- [Sonarr](https://sonarr.tv/)
- [Sonarr GitHub](https://github.com/Sonarr/Sonarr)
- [linuxserver/sonarr Docker](https://docs.linuxserver.io/images/docker-sonarr/)
- [Sonarr Wiki](https://wiki.servarr.com/sonarr)