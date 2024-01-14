# Homebox Docker

Homebox is the inventory and organization system built for the Home User

## Demo
[Demo](https://homebox.fly.dev/)

## Docker
```sh
mkdir -p /path/to/data/folder
chown 65532:65532 -R /path/to/data/folder
docker run -d \
  --name homebox \
  --restart unless-stopped \
  --publish 3100:7745 \
  --env TZ=Europe/Bucharest \
  --volume /path/to/data/folder/:/data \
  ghcr.io/hay-kot/homebox:latest
docker run -d --name homebox -p 3100:7745 ghcr.io/hay-kot/homebox
```
[http://localhost:3100/](http://localhost:3100/)

## Docker Compose
```
version: "3.4"

services:
  homebox:
    image: ghcr.io/hay-kot/homebox:latest
#   image: ghcr.io/hay-kot/homebox:latest-rootless
    container_name: homebox
    restart: always
    environment:
    - HBOX_LOG_LEVEL=info
    - HBOX_LOG_FORMAT=text
    - HBOX_WEB_MAX_UPLOAD_SIZE=10
    volumes:
      - homebox-data:/data/
    ports:
      - 3100:7745

volumes:
   homebox-data:
     driver: local
```

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Homebox Docs](https://hay-kot.github.io/homebox/)
- [Homebox GitHub](https://github.com/hay-kot/homebox)
- [Homebox Quick Start](https://hay-kot.github.io/homebox/quick-start/)