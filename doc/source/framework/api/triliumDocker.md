# Trilium Docker

Build your personal knowledge base with Trilium Notes

## Docker
```sh
docker run -d -p 0.0.0.0:8080:8080 -v ~/trilium-data:/home/node/trilium-data zadam/trilium:[VERSION]
docker run -d --name trilium -p 8080:8080 zadam/trilium
```
[http://localhost:8080/](http://localhost:8080/)

## Screenshots
![](https://raw.githubusercontent.com/wiki/zadam/trilium/images/screenshot.png)

## References
- [Trilium GitHub](https://github.com/zadam/trilium)
- [Trilium Docker server installation](https://github.com/zadam/trilium/wiki/Docker-server-installation)