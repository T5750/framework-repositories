# Diun Docker

Receive notifications when an image is updated on a Docker registry

Registry | Image
---|---
[Docker Hub](https://hub.docker.com/r/crazymax/diun/) | `crazymax/diun`
[GitHub Container Registry](https://github.com/users/crazy-max/packages/container/package/diun) | `ghcr.io/crazy-max/diun`

## Docker
```sh
docker run -d --name diun \
  -e "TZ=Europe/Paris" \
  -e "DIUN_WATCH_WORKERS=20" \
  -e "DIUN_WATCH_SCHEDULE=0 */6 * * *" \
  -e "DIUN_WATCH_JITTER=30s" \
  -e "DIUN_PROVIDERS_DOCKER=true" \
  -v "$PWD/data:/data" \
  -v "/var/run/docker.sock:/var/run/docker.sock" \
  -l "diun.enable=true" \
  crazymax/diun:latest
```

## Docker Compose
```
version: "3.5"
services:
  diun:
    image: crazymax/diun:latest
    command: serve
    volumes:
      - "./data:/data"
      - "/var/run/docker.sock:/var/run/docker.sock"
    environment:
      - "TZ=Europe/Paris"
      - "DIUN_WATCH_WORKERS=20"
      - "DIUN_WATCH_SCHEDULE=0 */6 * * *"
      - "DIUN_WATCH_JITTER=30s"
      - "DIUN_PROVIDERS_DOCKER=true"
    labels:
      - "diun.enable=true"
    restart: always

```

## Command Line
```sh
diun [global options] command [command or global options] [arguments...]
diun serve --config diun.yml --log-level debug
diun image list
diun image inspect --image alpine
diun image remove --image alpine:latest
diun image prune
diun notif test
```

## Runtime Environment
- [Go](https://golang.org/)

## References
- [Diun](https://crazymax.dev/diun/)
- [Diun GitHub](https://github.com/crazy-max/diun)
- [Diun Docker](https://crazymax.dev/diun/install/docker/)
- [Diun Command Line](https://crazymax.dev/diun/usage/command-line/)