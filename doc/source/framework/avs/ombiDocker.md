# Ombi Docker

Want a Movie or TV Show on Plex/Emby/Jellyfin? Use Ombi!

## Demo
[Demo](https://app.ombi.io/)

## Docker
```sh
docker run -d --name=ombi -p 3579:3579 -v $PWD/ombi:/config lscr.io/linuxserver/ombi
```
[http://localhost:3579/](http://localhost:3579/)

## Docker Compose
```
version: "2.1"
services:
  ombi:
    image: lscr.io/linuxserver/ombi:latest
    container_name: ombi
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Europe/London
      - BASE_URL=/ombi #optional
    volumes:
      - /path/to/appdata/config:/config
    ports:
      - 3579:3579
    restart: unless-stopped
```

## Tips
### Failed to create CoreCLR, HResult: 0x80070008
[https://docs.linuxserver.io/faq#jammy](https://docs.linuxserver.io/faq#jammy)

## Runtime Environment
- [.NET 6.0](https://dotnet.microsoft.com/download/dotnet)

## Screenshots
![](https://i.imgur.com/kBXIqer.png)

![](https://ombi.io/img/screens/1.png)

![](https://ombi.io/img/screens/2.png)

![](https://ombi.io/img/screens/3.png)

## References
- [Ombi](http://ombi.io/)
- [Ombi GitHub](https://github.com/Ombi-app/Ombi)
- [Ombi Docker](https://hub.docker.com/r/linuxserver/ombi/)
- [Failed to create CoreCLR, HResult: 0x80070008](https://github.com/linuxserver/docker-ombi/issues/96)