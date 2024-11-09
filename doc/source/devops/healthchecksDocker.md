# Healthchecks Docker

Simple and Effective Cron Job Monitoring

## Docker
```sh
docker run -d \
  --name=healthchecks \
  -e PUID=1000 \
  -e PGID=1000 \
  -e TZ=Etc/UTC \
  -e SITE_ROOT= \
  -e SITE_NAME= \
  -e SUPERUSER_EMAIL= \
  -e SUPERUSER_PASSWORD= \
  -e ALLOWED_HOSTS= `#optional` \
  -e APPRISE_ENABLED=False `#optional` \
  -e CSRF_TRUSTED_ORIGINS= `#optional` \
  -e DEBUG=True `#optional` \
  -e DEFAULT_FROM_EMAIL= `#optional` \
  -e EMAIL_HOST= `#optional` \
  -e EMAIL_PORT= `#optional` \
  -e EMAIL_HOST_USER= `#optional` \
  -e EMAIL_HOST_PASSWORD= `#optional` \
  -e EMAIL_USE_TLS= `#optional` \
  -e INTEGRATIONS_ALLOW_PRIVATE_IPS= `#optional` \
  -e PING_EMAIL_DOMAIN= `#optional` \
  -e RP_ID= `#optional` \
  -e SECRET_KEY= `#optional` \
  -e SITE_LOGO_URL= `#optional` \
  -p 8000:8000 \
  -p 2525:2525 `#optional` \
  -v /path/to/healthchecks/config:/config \
  --restart unless-stopped \
  lscr.io/linuxserver/healthchecks:latest
```
[http://localhost:8000/](http://localhost:8000/)

## Docker Compose
```
---
services:
  healthchecks:
    image: lscr.io/linuxserver/healthchecks:latest
    container_name: healthchecks
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Etc/UTC
      - SITE_ROOT=
      - SITE_NAME=
      - SUPERUSER_EMAIL=
      - SUPERUSER_PASSWORD=
      - ALLOWED_HOSTS= #optional
      - APPRISE_ENABLED=False #optional
      - CSRF_TRUSTED_ORIGINS= #optional
      - DEBUG=True #optional
      - DEFAULT_FROM_EMAIL= #optional
      - EMAIL_HOST= #optional
      - EMAIL_PORT= #optional
      - EMAIL_HOST_USER= #optional
      - EMAIL_HOST_PASSWORD= #optional
      - EMAIL_USE_TLS= #optional
      - INTEGRATIONS_ALLOW_PRIVATE_IPS= #optional
      - PING_EMAIL_DOMAIN= #optional
      - RP_ID= #optional
      - SECRET_KEY= #optional
      - SITE_LOGO_URL= #optional
    volumes:
      - /path/to/healthchecks/config:/config
    ports:
      - 8000:8000
      - 2525:2525 #optional
    restart: unless-stopped
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## Screenshots
![](https://healthchecks.io/static/img/my_checks.png?a98f)

![](https://healthchecks.io/static/img/period_grace.png?a98f)

![](https://healthchecks.io/static/img/cron.png?a98f)

![](https://healthchecks.io/static/img/check_details.png?a98f)

![](https://healthchecks.io/static/img/badges.png?a98f)

## References
- [Healthchecks](https://healthchecks.io/)
- [Healthchecks GitHub](https://github.com/healthchecks/healthchecks)
- [Healthchecks Docker](https://healthchecks.io/docs/self_hosted_docker/)
- [linuxserver/healthchecks Docker](https://docs.linuxserver.io/images/docker-healthchecks/)
- [Cron Expression Syntax Cheatsheet](https://healthchecks.io/docs/cron/)