# Wireshark Docker

The world's most popular network protocol analyzer

## Docker
```sh
docker run -d --name=wireshark -p 3000:3000 linuxserver/wireshark
```
[http://localhost:3000/](http://localhost:3000/)

## Docker Compose
```
version: "2.1"
services:
  wireshark:
    image: lscr.io/linuxserver/wireshark:latest
    container_name: wireshark
    cap_add:
      - NET_ADMIN
    security_opt:
      - seccomp:unconfined #optional
    network_mode: host
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/config:/config
    ports:
      - 3000:3000 #optional
      - 3001:3001 #optional
    restart: unless-stopped
```

## Screenshots
![](https://www.wireshark.org/docs/wsug_html_chunked/images/ws-main.png)

## References
- [Wireshark](https://www.wireshark.org/)
- [Wireshark GitHub](https://github.com/wireshark/wireshark)
- [linuxserver/wireshark Docker](https://hub.docker.com/r/linuxserver/wireshark)
- [The Main window](https://www.wireshark.org/docs/wsug_html_chunked/ChUseMainWindowSection.html)
- [Wireshark Online Tools](https://www.wireshark.org/tools/)