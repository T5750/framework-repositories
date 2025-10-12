# Undb Docker

Private first self-hosted no code database & BaaS.

## Docker
```sh
docker run -p 3721:3721 ghcr.io/undb-io/undb:latest
docker run -d --name undb -p 3721:3721 ghcr.io/undb-io/undb
```
[http://localhost:3721/](http://localhost:3721/)

Run with docker with volume
```sh
docker run -d \
  -p 3721:3721 \
  -v $(pwd)/undb:/usr/src/app/.undb \
  --name undb \
  ghcr.io/undb-io/undb:latest
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://undb.io/images/views/grid-view.jpeg)

![](https://undb.io/images/views/kanban-view.jpeg)

![](https://undb.io/images/views/calendar.jpeg)

![](https://undb.io/images/views/gallery-view.jpeg)

## References
- [Undb](https://undb.io/)
- [Undb GitHub](https://github.com/undb-io/undb)
- [Self-Hosting undb](https://docs.undb.io/deployment/self-host/)
- [Environment variables](https://docs.undb.io/deployment/environment/)