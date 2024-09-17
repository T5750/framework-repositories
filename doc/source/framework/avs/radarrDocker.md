# Radarr Docker

Movie organizer/manager for usenet and torrent users.

## Docker
```sh
docker run -d \
  --name=radarr \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 7878:7878 \
  -v /path/to/radarr/data:/config \
  -v /path/to/movies:/movies `#optional` \
  -v /path/to/download-client-downloads:/downloads `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/radarr:latest
docker run -d --name=radarr -p 7878:7878 quay.io/linuxserver.io/radarr
```
[http://localhost:7878/](http://localhost:7878/)

## Docker Compose
```
---
services:
  radarr:
    image: lscr.io/linuxserver/radarr:latest
    container_name: radarr
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/radarr/data:/config
      - /path/to/movies:/movies #optional
      - /path/to/download-client-downloads:/downloads #optional
    ports:
      - 7878:7878
    restart: unless-stopped
```

## Runtime Environment
- C#

## Screenshots
![](https://radarr.video/img/features/calendar.png)

![](https://radarr.video/img/features/blacklist.png)

## References
- [Radarr](https://radarr.video/)
- [Radarr GitHub](https://github.com/Radarr/Radarr)
- [linuxserver/radarr Docker](https://docs.linuxserver.io/images/docker-radarr/)