# WPS Office Docker

AI-Powered Office Suite for Everyone

## Docker
```sh
docker run -d \
  --name=wps-office \
  --security-opt seccomp=unconfined `#optional` \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 3000:3000 \
  -p 3001:3001 \
  -v /path/to/config:/config \
  --shm-size="1gb" \
  --restart unless-stopped \
  lscr.io/linuxserver/wps-office:latest
docker run -d --name=wps-office -p 3000:3000 -p 3001:3001 quay.io/linuxserver.io/wps-office
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
---
services:
  wps-office:
    image: lscr.io/linuxserver/wps-office:latest
    container_name: wps-office
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
    shm_size: "1gb"
    restart: unless-stopped
```

## Screenshots
![](https://website-prod.cache.wpscdn.com/img/intro_2.52bcfd4.png)

## References
- [WPS Office](https://www.wps.com/)
- [linuxserver/wps-office Docker](https://docs.linuxserver.io/images/docker-wps-office/)
- [linuxserver/docker-wps-office GitHub](https://github.com/linuxserver/docker-wps-office)