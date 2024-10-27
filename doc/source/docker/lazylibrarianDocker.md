# LazyLibrarian Docker

LazyLibrarian is a program to follow authors and grab metadata for all your digital reading needs.

## Docker
```sh
docker run -d \
  --name=lazylibrarian \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e DOCKER_MODS=linuxserver/mods:universal-calibre|linuxserver/mods:lazylibrarian-ffmpeg `#optional` \
  -p 5299:5299 \
  -v /path/to/lazylibrarian/data:/config \
  -v /path/to/downloads/:/downloads \
  -v /path/to/data/:/books `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/lazylibrarian:latest
docker run -d --name=lazylibrarian -p 5299:5299 quay.io/linuxserver.io/lazylibrarian
```
[http://localhost:5299/](http://localhost:5299/)

## Docker Compose
```
---
services:
  lazylibrarian:
    image: lscr.io/linuxserver/lazylibrarian:latest
    container_name: lazylibrarian
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - DOCKER_MODS=linuxserver/mods:universal-calibre|linuxserver/mods:lazylibrarian-ffmpeg #optional
    volumes:
      - /path/to/lazylibrarian/data:/config
      - /path/to/downloads/:/downloads
      - /path/to/data/:/books #optional
    ports:
      - 5299:5299
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.7.x](https://www.python.org/downloads/)

## Screenshots
![](https://lazylibrarian.gitlab.io/assets/screenshots/llmainpage.png)

![](https://lazylibrarian.gitlab.io/assets/screenshots/llauthdetail.png)

![](https://lazylibrarian.gitlab.io/assets/screenshots/llmags.png)

## References
- [LazyLibrarian](https://lazylibrarian.gitlab.io/)
- [LazyLibrarian GitLab](https://gitlab.com/LazyLibrarian/LazyLibrarian)
- [linuxserver/lazylibrarian Docker](https://docs.linuxserver.io/images/docker-lazylibrarian/)