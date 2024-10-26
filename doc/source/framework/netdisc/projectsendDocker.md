# ProjectSend Docker

ProjectSend is a free, secure and user friendly file sharing software

## Demo
- [ProjectSend demo](https://demo.projectsend.org/)
- User: admin / admin

## Docker
```sh
docker run -d \
  --name=projectsend \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e MAX_UPLOAD=5000 \
  -p 80:80 \
  -v /path/to/projectsend/config:/config \
  -v /path/to/data:/data \
  --restart unless-stopped \
  lscr.io/linuxserver/projectsend:latest
docker run -d --name=projectsend -p 8080:80 quay.io/linuxserver.io/projectsend
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
---
services:
  projectsend:
    image: lscr.io/linuxserver/projectsend:latest
    container_name: projectsend
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - MAX_UPLOAD=5000
    volumes:
      - /path/to/projectsend/config:/config
      - /path/to/data:/data
    ports:
      - 80:80
    restart: unless-stopped
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)
- [MySQL 5.x](http://www.mysql.com/)

## Screenshots
![](https://www.projectsend.org/wp-content/themes/projectsend/img/screenshots.png)

![](https://www.projectsend.org/wp-content/uploads/2018/06/default-template-laptop.png)

## References
- [ProjectSend](https://www.projectsend.org/)
- [ProjectSend GitHub](https://github.com/projectsend/projectsend)
- [linuxserver/projectsend Docker](https://docs.linuxserver.io/images/docker-projectsend/)