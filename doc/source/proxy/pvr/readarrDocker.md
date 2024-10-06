# Readarr Docker

Readarr is a ebook collection manager for Usenet and BitTorrent users. It can monitor multiple RSS feeds for new books from your favorite authors and will interface with clients and indexers to grab, sort, and rename them.

## Docker
```sh
docker run -d \
  --name=readarr \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 8787:8787 \
  -v /path/to/data:/config \
  -v /path/to/books:/books `#optional` \
  -v /path/to/downloadclient-downloads:/downloads `#optional` \
  --restart unless-stopped \
  lscr.io/linuxserver/readarr:develop
docker run -d --name=readarr -p 8787:8787 quay.io/linuxserver.io/readarr:develop
```
[http://localhost:8787/](http://localhost:8787/)

## Docker Compose
```
---
services:
  readarr:
    image: lscr.io/linuxserver/readarr:develop
    container_name: readarr
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
    volumes:
      - /path/to/data:/config
      - /path/to/books:/books #optional
      - /path/to/downloadclient-downloads:/downloads #optional
    ports:
      - 8787:8787
    restart: unless-stopped
```

## Settings
- 媒体管理 -> 根目录: `/downloads`
- 索引器 -> Torznab
- 下载客户端 -> 下载客户端 -> qBittorrent
  * 主机: `172.17.0.1`
  * 用户名: `admin`
- 下载客户端 -> 远程路径映射
  * 主机: `172.17.0.1`
  * 远程路径: `/downloads/`
  * 本地路径: `/downloads/`
- 通用 -> 安全 -> 认证: 表单（登陆页面）
- UI界面 -> 语言

## Runtime Environment
- C#

## Screenshots
![](https://readarr.com/img/slider/artistdetails.png)

![](https://readarr.com/img/features/calendar.png)

![](https://readarr.com/img/features/manualsearch.png)

## References
- [Readarr](https://readarr.com/)
- [Readarr GitHub](https://github.com/Readarr/Readarr)
- [linuxserver/readarr Docker](https://docs.linuxserver.io/images/docker-readarr/)