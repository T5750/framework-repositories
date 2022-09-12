# Emby Docker

TAKE YOUR MEDIA ANYWHERE WITH EMBY

## Docker
```sh
docker run -d \
    --name embyserver \
    --volume /path/to/programdata:/config \ # Configuration directory
    --volume /path/to/share1:/mnt/share1 \ # Media directory
    --volume /path/to/share2:/mnt/share2 \ # Media directory
    --net=host \ # Enable DLNA and Wake-on-Lan
    --device /dev/dri:/dev/dri \ # VAAPI/NVDEC/NVENC render nodes
    --device /dev/vchiq:/dev/vchiq \ # MMAL/OMX on Raspberry Pi
    --runtime=nvidia \ # Expose NVIDIA GPUs
    --publish 8096:8096 \ # HTTP port
    --publish 8920:8920 \ # HTTPS port
    --env UID=1000 \ # The UID to run emby as (default: 2)
    --env GID=100 \ # The GID to run emby as (default 2)
    --env GIDLIST=100 \ # A comma-separated list of additional GIDs to run emby as (default: 2)
    emby/embyserver:latest
docker run --name embyserver -p 8096:8096 -d emby/embyserver
```
[http://localhost:8096/](http://localhost:8096/)

## Docker Compose
```
version: "2.3"
services:
  emby:
    image: emby/embyserver
    container_name: embyserver
    runtime: nvidia # Expose NVIDIA GPUs
    network_mode: host # Enable DLNA and Wake-on-Lan
    environment:
      - UID=1000 # The UID to run emby as (default: 2)
      - GID=100 # The GID to run emby as (default 2)
      - GIDLIST=100 # A comma-separated list of additional GIDs to run emby as (default: 2)
    volumes:
      - /path/to/programdata:/config # Configuration directory
      - /path/to/tvshows:/mnt/share1 # Media directory
      - /path/to/movies:/mnt/share2 # Media directory
    ports:
      - 8096:8096 # HTTP port
      - 8920:8920 # HTTPS port
    devices:
      - /dev/dri:/dev/dri # VAAPI/NVDEC/NVENC render nodes
      - /dev/vchiq:/dev/vchiq # MMAL/OMX on Raspberry Pi
    restart: unless-stopped
```

## Screenshots
![](https://emby.media/resources/about_manage1.png)

![](https://emby.media/resources/Screenshot_2015-09-28-22-42-49123.png)

![](https://emby.media/resources/msg-3-0-39164600-1443639466.png)

## References
- [Emby](https://emby.media/about.html)
- [Emby GitHub](https://github.com/MediaBrowser/Emby)
- [Emby Docker](https://hub.docker.com/r/emby/embyserver)