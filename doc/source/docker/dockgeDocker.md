# Dockge Docker

Self-hosted - Docker compose.yaml - Stack-oriented Manager

## Docker
```sh
docker run -d --name dockge -p 5001:5001 --privileged=true louislam/dockge:1
```
[http://localhost:5001/](http://localhost:5001/)

## Docker Compose
```
version: "3.8"
services:
  dockge:
    image: louislam/dockge:1
    restart: unless-stopped
    ports:
      - 5001:5001
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
      - ./data:/app/data
      # Stacks Directory
      # ⚠️ READ IT CAREFULLY. If you did it wrong, your data could end up writing into a WRONG PATH.
      # ⚠️ 1. FULL path only. No relative path (MUST)
      # ⚠️ 2. Left Stacks Path === Right Stacks Path (MUST)
      - /opt/stacks:/opt/stacks
    environment:
      # Tell Dockge where to find the stacks
      - DOCKGE_STACKS_DIR=/opt/stacks
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)
- [Vue.js](https://github.com/vuejs/vue)

## Screenshots
![](https://dockge.kuma.pet/screenshot.png)

## References
- [Dockge](https://dockge.kuma.pet/)
- [Dockge GitHub](https://github.com/louislam/dockge)