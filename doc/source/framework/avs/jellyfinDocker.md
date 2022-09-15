# Jellyfin Docker

The Free Software Media System

## Docker
```sh
mkdir -p $PWD/jellyfin/{config,cache}
docker run --name jellyfin -d -p 8096:8096 -v $PWD/jellyfin/config:/config -v $PWD/jellyfin/cache:/cache -v /media:/media jellyfin/jellyfin
```
[http://localhost:8096/](http://localhost:8096/)

## Docker Compose
```
version: "3.5"
services:
  jellyfin:
    image: jellyfin/jellyfin
    container_name: jellyfin
    user: uid:gid
    network_mode: "host"
    volumes:
      - /path/to/config:/config
      - /path/to/cache:/cache
      - /path/to/media:/media
      - /path/to/media2:/media2:ro
    restart: "unless-stopped"
    # Optional - alternative address used for autodiscovery
    environment:
      - JELLYFIN_PublishedServerUrl=http://example.com
```

## Screenshots
![](https://jellyfin.org/images/screenshots/home_full.png)

![](https://jellyfin.org/images/screenshots/library_full.png)

![](https://jellyfin.org/images/screenshots/movie_full.png)

![](https://jellyfin.org/images/screenshots/playback_full.png)

![](https://jellyfin.org/images/screenshots/epg_full.png)

## References
- [Jellyfin Docker](https://jellyfin.org/docs/general/administration/installing.html)
- [Jellyfin GitHub](https://github.com/jellyfin/jellyfin)
- [Jellyfin API](https://api.jellyfin.org/)