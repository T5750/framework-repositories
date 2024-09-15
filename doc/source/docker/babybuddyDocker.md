# Baby Buddy Docker

A buddy for babies! Helps caregivers track sleep, feedings, diaper changes, tummy time and more to learn about and predict baby's needs without (as much) guess work.

## Demo
- [demo of Baby Buddy](http://demo.baby-buddy.net/)
- User: admin / admin

## Docker
```sh
docker run -d \
  --name=babybuddy \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e CSRF_TRUSTED_ORIGINS=http://127.0.0.1:8000,https://babybuddy.domain.com \
  -p 8000:8000 \
  -v /path/to/babybuddy/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/babybuddy:latest
docker run -d --name=babybuddy -p 8000:8000 quay.io/linuxserver.io/babybuddy
```
[http://localhost:8000/](http://localhost:8000/)

## Docker Compose
```
---
services:
  babybuddy:
    image: lscr.io/linuxserver/babybuddy:latest
    container_name: babybuddy
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - CSRF_TRUSTED_ORIGINS=http://127.0.0.1:8000,https://babybuddy.domain.com
    volumes:
      - /path/to/babybuddy/config:/config
    ports:
      - 8000:8000
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)
- [Node.js](https://nodejs.org/en/download)

## Screenshots
![](https://docs.baby-buddy.net/assets/images/dashboard.png)

![](https://docs.baby-buddy.net/assets/images/timeline.png)

## References
- [Baby Buddy](https://docs.baby-buddy.net/)
- [Baby Buddy GitHub](https://github.com/babybuddy/babybuddy)
- [linuxserver/babybuddy Docker](https://docs.linuxserver.io/images/docker-babybuddy/)