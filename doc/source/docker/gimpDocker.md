# GIMP Docker

The Free & Open Source Image Editor

## Docker
```sh
docker run -d \
  --name=gimp \
  --security-opt seccomp=unconfined `#optional` \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 3000:3000 \
  -p 3001:3001 \
  -v /path/to/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/gimp:latest
docker run -d --name=gimp -p 3000:3000 quay.io/linuxserver.io/gimp
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
---
services:
  gimp:
    image: lscr.io/linuxserver/gimp:latest
    container_name: gimp
    security_opt:
      - seccomp:unconfined #optional
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/config:/config
    ports:
      - 3000:3000
      - 3001:3001
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)

## Screenshots
![](https://developer.gimp.org/core/specifications/layer-effects-ui/advanced/edit_mask/3_result.jpg)

## References
- [GIMP](https://www.gimp.org/)
- [GIMP Gitlab](https://gitlab.gnome.org/Infrastructure/gimp-web)
- [linuxserver/GIMP Docker](https://docs.linuxserver.io/images/docker-gimp/)