# Pylon Docker

Pylon is a web based integrated development environment built with Node.js as a backend and with a supercharged JavaScript/HTML5 frontend, licensed under GPL version 3. This project originates from Cloud9 v2 project.

## Docker
```sh
docker run -d \
  --name=pylon \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e GITURL=https://github.com/linuxserver/docker-pylon.git `#optional` \
  -e PYUSER=myuser `#optional` \
  -e PYPASS=mypass `#optional` \
  -p 3131:3131 \
  -v /path/to/your/code:/code `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/pylon:latest
docker run -d --name=pylon -p 3131:3131 quay.io/linuxserver.io/pylon
```
[http://localhost:3131/](http://localhost:3131/)

## Docker Compose
```
---
services:
  pylon:
    image: lscr.io/linuxserver/pylon:latest
    container_name: pylon
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - GITURL=https://github.com/linuxserver/docker-pylon.git #optional
      - PYUSER=myuser #optional
      - PYPASS=mypass #optional
    volumes:
      - /path/to/your/code:/code #optional
    ports:
      - 3131:3131
    restart: unless-stopped
```

## Runtime Environment
- JavaScript

## Screenshots
![](https://github.com/pylonide/pylon/raw/master/doc/screenshot02.png)

![](https://github.com/pylonide/pylon/raw/master/doc/screenshot03.png)

## References
- [Pylon GitHub](https://github.com/pylonide/pylon)
- [linuxserver/pylon Docker](https://docs.linuxserver.io/images/docker-pylon/)