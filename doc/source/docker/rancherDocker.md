# Rancher Docker

## Docker
```
docker run -d --name rancher --restart=unless-stopped -p 8080:443 --privileged rancher/rancher:stable
```

[https://localhost:8080/](https://localhost:8080/)

## Rancher Server Architecture
![](https://rancher.com/docs/img/rancher/rancher-architecture-rancher-api-server.svg)

## Screenshots
![](https://cdn.rancher.com/wp-content/uploads/2017/10/02191654/16-ljWqMXX.png)

## References
- [Rancher GitHub](https://github.com/rancher/rancher)
- [Rancher Architecture](https://rancher.com/docs/rancher/v2.5/en/overview/architecture/)