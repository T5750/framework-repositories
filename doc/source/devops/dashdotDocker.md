# dash. Docker

a modern server dashboard

## Demo
[Demo](https://dash.mauz.dev/)

## Docker
```sh
docker container run -it \
  -p 80:3001 \
  -v /:/mnt/host:ro \
  --privileged \
  mauricenino/dashdot
docker run -d --name dashdot -p 8080:3001 mauricenino/dashdot
```
[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
```
version: '3.5'

services:
  dash:
    image: mauricenino/dashdot:latest
    restart: unless-stopped
    privileged: true
    ports:
      - '80:3001'
    volumes:
      - /:/mnt/host:ro
```

## Screenshots
![](https://getdashdot.com/img/screenshot_darkmode.png)

## References
- [dash.](https://getdashdot.com/)
- [dash. GitHub](https://github.com/MauriceNino/dashdot)