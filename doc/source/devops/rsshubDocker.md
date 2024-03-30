# RSSHub Docker

Everything is RSSible

## Docker
```sh
docker run -d --name rsshub -p 1200:1200 diygod/rsshub
docker run -d --name rsshub -p 1200:1200 --privileged=true diygod/rsshub
```
[http://localhost:1200/](http://localhost:1200/)

## Docker Compose
```sh
wget https://raw.githubusercontent.com/DIYgod/RSSHub/master/docker-compose.yml
docker-compose up -d
```

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## References
- [RSSHub](https://docs.rsshub.app/)
- [RSSHub GitHub](https://github.com/DIYgod/RSSHub)
- [RSSHub Docker](https://rsshub-doc.pages.dev/install/)