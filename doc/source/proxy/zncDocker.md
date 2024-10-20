# ZNC Docker

Znc is an IRC network bouncer or BNC. It can detach the client from the actual IRC server, and also from selected channels. Multiple clients from different locations can connect to a single ZNC account simultaneously and therefore appear under the same nickname on IRC.

## Docker
```sh
docker run -d \
  --name=znc \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 6501:6501 \
  -v /path/to/znc/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/znc:latest
docker run -d --name=znc -p 6501:6501 quay.io/linuxserver.io/znc
```
- [http://localhost:6501/](http://localhost:6501/)
- User: admin / admin

## Docker Compose
```
---
services:
  znc:
    image: lscr.io/linuxserver/znc:latest
    container_name: znc
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/znc/config:/config
    ports:
      - 6501:6501
    restart: unless-stopped
```

## Runtime Environment
- C++

## Network Scheme
![](https://wiki.znc.in/images/4/4f/Overview_network_scheme.png)

## Screenshots
![](https://wiki.znc.in/images/d/d5/Webadmin-settings-dark-clouds.png)

## References
- [ZNC](https://znc.in/)
- [ZNC GitHub](https://github.com/znc/znc)
- [linuxserver/znc Docker](https://docs.linuxserver.io/images/docker-znc/)