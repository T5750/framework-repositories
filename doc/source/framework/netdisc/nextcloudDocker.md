# Nextcloud Docker

A safe home for all your data.

## Docker
```sh
docker run -d --name=nextcloud -p 8080:80 nextcloud
docker run -d --name=nextcloud -p 8080:80 quay.io/linuxserver.io/nextcloud
docker run -d \
  --name=nextcloud \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -p 443:443 \
  -v /path/to/nextcloud/config:/config \
  -v /path/to/data:/data \
  --restart unless-stopped \
  lscr.io/linuxserver/nextcloud:latest
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
`nextcloud.yml`

## Update to a newer version
```sh
docker pull nextcloud
docker stop nextcloud
docker rm nextcloud
docker run -d --name=nextcloud -p 8080:80 nextcloud
```
```sh
docker compose pull
docker compose up -d
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)
- [Nextcloud](https://nextcloud.com/install/#instructions-server)

## Screenshots
![](https://docs.nextcloud.com/server/latest/user_manual/_images/users-files.png)

![](https://docs.nextcloud.com/server/latest/user_manual/_images/video_player_2.png)

![](https://docs.nextcloud.com/server/latest/admin_manual/_images/office.png)

![](https://docs.nextcloud.com/server/latest/user_manual/_images/talk-grid-view.png)

## References
- [Nextcloud](https://nextcloud.com/)
- [Nextcloud Docker](https://hub.docker.com/_/nextcloud)
- [Nextcloud GitHub](https://github.com/nextcloud)
- [Nextcloud Docker GitHub](https://github.com/nextcloud/docker)
- [Nextcloud All-in-One GitHub](https://github.com/nextcloud/all-in-one)
- [Nextcloud User manual](https://docs.nextcloud.com/server/latest/user_manual/zh_CN/contents.html)
- [Office Installation example with Docker](https://docs.nextcloud.com/server/latest/admin_manual/office/example-docker.html)