# Jackett Docker

API Support for your favorite torrent trackers

## Docker
```sh
docker run -d \
  --name=jackett \
  -p 9117:9117 \
  -v $PWD/jackett/config:/config \
  -v $PWD/jackett/downloads:/downloads \
  --restart unless-stopped \
  linuxserver/jackett
docker run -d --name=jackett -p 9117:9117 quay.io/linuxserver.io/jackett
```
[http://localhost:9117/](http://localhost:9117/)

## Docker Compose
```
version: "2.1"
services:
  jackett:
    image: lscr.io/linuxserver/jackett:latest
    container_name: jackett
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Europe/London
      - AUTO_UPDATE=true #optional
      - RUN_OPTS=<run options here> #optional
    volumes:
      - <path to data>:/config
      - <path to blackhole>:/downloads
    ports:
      - 9117:9117
    restart: unless-stopped
```

## Settings
Add indexer -> Type: Public
- [52BT](https://www.529053.xyz/)
- [EXT Torrents](https://ext.to/)

## Runtime Environment
- [.NET 6.0](https://dotnet.microsoft.com/download/dotnet)

## Screenshots
![](https://raw.githubusercontent.com/Jackett/Jackett/master/.github/jackett-screenshot1.png)

![](https://raw.githubusercontent.com/Jackett/Jackett/master/.github/jackett-screenshot2.png)

![](https://raw.githubusercontent.com/Jackett/Jackett/master/.github/jackett-screenshot3.png)

## References
- [Jackett GitHub](https://github.com/Jackett/Jackett)
- [linuxserver/jackett Docker](https://hub.docker.com/r/linuxserver/jackett)