# Frigate Docker

Monitor your security cameras with locally processed AI

## Demo
[Demo](http://demo.frigate.video/)

## Configuring Frigate
`vi config/config.yml`
```
mqtt:
  enabled: False

cameras:
  dummy_camera: # <--- this will be changed to your actual camera later
    enabled: False
    ffmpeg:
      inputs:
        - path: rtsp://127.0.0.1:554/rtsp
          roles:
            - detect
```

## Docker
```sh
docker run -d \
  --name frigate \
  --restart=unless-stopped \
  --mount type=tmpfs,target=/tmp/cache,tmpfs-size=1000000000 \
  --device /dev/bus/usb:/dev/bus/usb \
  --device /dev/dri/renderD128 \
  --shm-size=64m \
  -v /path/to/your/storage:/media/frigate \
  -v /path/to/your/config:/config \
  -v /etc/localtime:/etc/localtime:ro \
  -e FRIGATE_RTSP_PASSWORD='password' \
  -p 5000:5000 \
  -p 8554:8554 \
  -p 8555:8555/tcp \
  -p 8555:8555/udp \
  ghcr.io/blakeblackshear/frigate:stable
```

## Docker Compose
- `frigate.yml`
- [http://localhost:5000/](http://localhost:5000/)

```
version: "3.9"
services:
  frigate:
    container_name: frigate
    privileged: true # this may not be necessary for all setups
    restart: unless-stopped
    image: ghcr.io/blakeblackshear/frigate:stable
    shm_size: "64mb" # update for your cameras based on calculation above
    devices:
      - /dev/bus/usb:/dev/bus/usb  # Passes the USB Coral, needs to be modified for other versions
      - /dev/apex_0:/dev/apex_0    # Passes a PCIe Coral, follow driver instructions here https://coral.ai/docs/m2/get-started/#2a-on-linux
      - /dev/video11:/dev/video11  # For Raspberry Pi 4B
      - /dev/dri/renderD128:/dev/dri/renderD128 # For intel hwaccel, needs to be updated for your hardware
    volumes:
      - /etc/localtime:/etc/localtime:ro
      - /path/to/your/config:/config
      - /path/to/your/storage:/media/frigate
      - type: tmpfs # Optional: 1GB of memory, reduces SSD/SD Card wear
        target: /tmp/cache
        tmpfs:
          size: 1000000000
    ports:
      - "5000:5000"
      - "8554:8554" # RTSP feeds
      - "8555:8555/tcp" # WebRTC over tcp
      - "8555:8555/udp" # WebRTC over udp
    environment:
      FRIGATE_RTSP_PASSWORD: "password"
```

## Runtime Environment
- [Python 3.9.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://frigate.video/images/ui.jpg)

![](https://frigate.video/images/detection.jpg)

![](https://frigate.video/images/events-mobile-cropped.jpg)

![](https://frigate.video/images/driveway_zones-min.png)

![](https://frigate.video/images/hass-opt.png)

![](https://frigate.video/images/birdseye-2-optimized.jpg)

## References
- [Frigate](https://frigate.video/)
- [Frigate GitHub](https://github.com/blakeblackshear/frigate)
- [Frigate Docker](https://docs.frigate.video/frigate/installation/#docker)
- [Frigate Getting Started](https://docs.frigate.video/guides/getting_started)