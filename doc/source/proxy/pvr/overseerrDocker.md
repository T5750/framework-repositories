# Overseerr Docker

Overseerr is a request management and media discovery tool built to work with your existing Plex ecosystem.

## Docker
```sh
docker run -d \
  --name=overseerr \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 5055:5055 \
  -v /path/to/overseerr/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/overseerr:latest
docker run -d --name=overseerr -p 5055:5055 quay.io/linuxserver.io/overseerr
```
[http://localhost:5055/](http://localhost:5055/)

## Docker Compose
```
---
services:
  overseerr:
    image: lscr.io/linuxserver/overseerr:latest
    container_name: overseerr
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/overseerr/config:/config
    ports:
      - 5055:5055
    restart: unless-stopped
```

## API Documentation
- [https://api-docs.overseerr.dev/](https://api-docs.overseerr.dev/)
- [http://localhost:5055/api-docs](http://localhost:5055/api-docs)

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://overseerr.dev/_next/image?url=%2Fscreenshots%2Fscreen_main.jpg&w=1920&q=75)

![](https://overseerr.dev/screenshots/screen_movie_details.jpg)

## References
- [Overseerr](https://overseerr.dev/)
- [Overseerr GitHub](https://github.com/sct/overseerr)
- [linuxserver/overseerr Docker](https://docs.linuxserver.io/images/docker-overseerr/)