# PairDrop Docker

最简单的跨设备传输方案

## Docker
```sh
docker run -d --name=pairdrop -p 3000:3000 lscr.io/linuxserver/pairdrop
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
---
services:
  pairdrop:
    image: lscr.io/linuxserver/pairdrop:latest
    container_name: pairdrop
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - RATE_LIMIT=false #optional
      - WS_FALLBACK=false #optional
      - RTC_CONFIG= #optional
      - DEBUG_MODE=false #optional
    ports:
      - 3000:3000
    restart: unless-stopped
```

## Runtime Environment
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://raw.githubusercontent.com/schlagmichdoch/PairDrop/refs/heads/master/docs/pairdrop_screenshot_mobile.gif)

## Snapdrop
A Progressive Web App for local file sharing

### Docker
```sh
docker run -d -p 8080:80 linuxserver/snapdrop
docker run -d --name=snapdrop -p 8080:80 quay.io/linuxserver.io/snapdrop
```
[http://localhost:8080/](http://localhost:8080/)

## References
- [Snapdrop](https://snapdrop.net)
- [Snapdrop GitHub](https://github.com/SnapDrop/snapdrop)
- [linuxserver/snapdrop Docker](https://docs.linuxserver.io/deprecated_images/docker-snapdrop/)
- [PairDrop](https://pairdrop.net/)
- [PairDrop GitHub](https://github.com/schlagmichdoch/PairDrop)
- [linuxserver/pairdrop Docker](https://docs.linuxserver.io/images/docker-pairdrop/)