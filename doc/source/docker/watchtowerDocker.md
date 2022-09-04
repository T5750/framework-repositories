# Watchtower Docker

A container-based solution for automating Docker container base image updates.

## Docker
```sh
docker run -d \
--name watchtower \
-v /var/run/docker.sock:/var/run/docker.sock \
containrrr/watchtower
```

## Docker Compose
```
version: "3"
services:
  watchtower:
    image: containrrr/watchtower
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
```

## References
- [Watchtower](https://containrrr.dev/watchtower/)
- [Watchtower GitHub](https://github.com/containrrr/watchtower/)