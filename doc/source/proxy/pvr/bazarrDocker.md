# Bazarr Docker

Bazarr is a companion application to Sonarr and Radarr that manages and downloads subtitles based on your requirements.

## Docker
```sh
docker run -d \
  --name=bazarr \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 6767:6767 \
  -v /path/to/bazarr/config:/config \
  -v /path/to/movies:/movies `#optional` \
  -v /path/to/tv:/tv `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/bazarr:latest
docker run -d --name=bazarr -p 6767:6767 quay.io/linuxserver.io/bazarr
```
[http://localhost:6767/](http://localhost:6767/)

## Docker Compose
```
---
services:
  bazarr:
    image: lscr.io/linuxserver/bazarr:latest
    container_name: bazarr
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/bazarr/config:/config
      - /path/to/movies:/movies #optional
      - /path/to/tv:/tv #optional
    ports:
      - 6767:6767
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.x](https://www.python.org/downloads/)

## Screenshots
![](https://github.com/morpheus65535/bazarr/raw/master/screenshot/bazarr-screenshot.png?raw=true)

## References
- [Bazarr](https://www.bazarr.media/)
- [Bazarr GitHub](https://github.com/morpheus65535/bazarr)
- [linuxserver/bazarr Docker](https://docs.linuxserver.io/images/docker-bazarr/)