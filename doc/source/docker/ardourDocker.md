# Ardour Docker

Record, Edit, and Mix
on Linux, macOS and Windows

## Docker
```sh
docker run -d \
  --name=ardour \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e SUBFOLDER=/ `#optional` \
  -p 3000:3000 `#optional` \
  -p 3001:3001 `#optional` \
  -v /path/to/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/ardour:latest
docker run -d --name=ardour -p 3000:3000 quay.io/linuxserver.io/ardour
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
---
services:
  ardour:
    image: lscr.io/linuxserver/ardour:latest
    container_name: ardour
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - SUBFOLDER=/ #optional
    volumes:
      - /path/to/config:/config
    ports:
      - 3000:3000 #optional
      - 3001:3001 #optional
    restart: unless-stopped
```

## Runtime Environment
- C++, C

## Screenshots
![](https://ardour.org/images/retina_no_plugs2.png)

![](https://ardour.org/images/editor4.png)

![](https://ardour.org/images/mixer4.png)

## References
- [Ardour](https://ardour.org/)
- [Ardour GitHub](https://github.com/Ardour/ardour)
- [linuxserver/ardour Docker](https://docs.linuxserver.io/images/docker-ardour/)
- [Ardour完全教程](https://wiki.ubuntu.org.cn/Ardour%E5%AE%8C%E5%85%A8%E6%95%99%E7%A8%8B)