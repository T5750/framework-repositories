# Uptime Kuma Docker

A self-hosted monitoring tool

## Demo
- [Live Demo](https://demo.uptime.kuma.pet/)
- [Status Page](https://status.kuma.pet/)

## Docker
```sh
docker run -d --restart=always -p 3001:3001 -v uptime-kuma:/app/data --name uptime-kuma louislam/uptime-kuma:1
```
Please use a **local volume** only. Other types such as NFS are not supported.

[http://localhost:3001/](http://localhost:3001/)

## Windows Portable (x64)
[https://github.com/louislam/uptime-kuma/files/11886108/uptime-kuma-win64-portable-1.0.1.zip](https://github.com/louislam/uptime-kuma/files/11886108/uptime-kuma-win64-portable-1.0.1.zip)

## Screenshots
![](https://user-images.githubusercontent.com/1336778/212262296-e6205815-ad62-488c-83ec-a5b0d0689f7c.jpg)

## References
- [Uptime Kuma](https://uptime.kuma.pet/)
- [Uptime Kuma GitHub](https://github.com/louislam/uptime-kuma)