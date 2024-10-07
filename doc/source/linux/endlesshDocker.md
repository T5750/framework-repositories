# Endlessh Docker

Endlessh is an SSH tarpit that very slowly sends an endless, random SSH banner. It keeps SSH clients locked up for hours or even days at a time. The purpose is to put your real SSH server on another port and then let the script kiddies get stuck in this tarpit instead of bothering a real server.

## Docker
```sh
docker run -d \
  --name=endlessh \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e MSDELAY=10000 `#optional` \
  -e MAXLINES=32 `#optional` \
  -e MAXCLIENTS=4096 `#optional` \
  -e LOGFILE=false `#optional` \
  -e BINDFAMILY= `#optional` \
  -p 22:2222 \
  -v /path/to/endlessh/appdata:/config `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/endlessh:latest
docker run -d --name=endlessh -p 22:2222 quay.io/linuxserver.io/endlessh
```

## Docker Compose
```
---
services:
  endlessh:
    image: lscr.io/linuxserver/endlessh:latest
    container_name: endlessh
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - MSDELAY=10000 #optional
      - MAXLINES=32 #optional
      - MAXCLIENTS=4096 #optional
      - LOGFILE=false #optional
      - BINDFAMILY= #optional
    volumes:
      - /path/to/endlessh/appdata:/config #optional
    ports:
      - 22:2222
    restart: unless-stopped
```

## Usage
```
Usage: endlessh [-vhs] [-d MS] [-f CONFIG] [-l LEN] [-m LIMIT] [-p PORT]
  -4        Bind to IPv4 only
  -6        Bind to IPv6 only
  -d INT    Message millisecond delay [10000]
  -f        Set and load config file [/etc/endlessh/config]
  -h        Print this help message and exit
  -l INT    Maximum banner line length (3-255) [32]
  -m INT    Maximum number of clients [4096]
  -p INT    Listening port [2222]
  -s        Print diagnostics to syslog instead of standard output
  -v        Print diagnostics (repeatable)
```

## Runtime Environment
- C

## References
- [Endlessh GitHub](https://github.com/skeeto/endlessh)
- [linuxserver/endlessh Docker](https://docs.linuxserver.io/deprecated_images/docker-endlessh/)
- [shizunge/endlessh-go GitHub](https://github.com/shizunge/endlessh-go/)