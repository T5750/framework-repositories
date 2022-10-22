# Dokuwiki Docker

DokuWiki is a simple to use and highly versatile Open Source wiki software that doesn't require a database.

## Docker
```sh
docker run -d \
  --name=dokuwiki \
  -p 8080:80 \
  -v $PWD/config:/config \
  --restart unless-stopped \
  linuxserver/dokuwiki
```
- [http://localhost:8080/](http://localhost:8080/)
- [http://localhost:8080/install.php](http://localhost:8080/install.php)

## Docker Compose
```
version: "2.1"
services:
  dokuwiki:
    image: lscr.io/linuxserver/dokuwiki:latest
    container_name: dokuwiki
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Europe/London
    volumes:
      - /path/to/appdata/config:/config
    ports:
      - 80:80
      - 443:443 #optional
    restart: unless-stopped
```

## Runtime Environment
- [PHP](https://www.php.net/downloads)

## Screenshots
![](https://www.dokuwiki.org/_media/dokuwikimainwindow.png)

![](https://www.dokuwiki.org/_media/editwindow.png?cache=&w=900&h=768&tok=dcbf05)

![](https://www.dokuwiki.org/_media/wiki:mediamanager.png?w=400&tok=56f33a)

## References
- [Dokuwiki](https://www.dokuwiki.org/dokuwiki/)
- [linuxserver/dokuwiki Docker](https://hub.docker.com/r/linuxserver/dokuwiki)
- [Dokuwiki GitHub](https://github.com/splitbrain/dokuwiki)