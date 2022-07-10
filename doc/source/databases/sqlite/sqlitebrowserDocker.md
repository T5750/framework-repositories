# SQLite Browser Docker

[DB Browser for SQLite](https://sqlitebrowser.org/) is a high quality, visual, open source tool to create, design, and edit database files compatible with SQLite.

## Docker
```
docker run -d \
  --name=sqlitebrowser \
  -p 3000:3000 \
  lscr.io/linuxserver/sqlitebrowser
```
[http://yourhost:3000/](http://yourhost:3000/)

## Docker Compose
```
version: "2.1"
services:
  sqlitebrowser:
    image: lscr.io/linuxserver/sqlitebrowser:latest
    container_name: sqlitebrowser
    environment:
      - PUID=1000
      - PGID=1000
      - TZ=Europe/London
    volumes:
      - /path/to/config:/config
    ports:
      - 3000:3000
    restart: unless-stopped
```

### Environment variables from files (Docker secrets)
```
-e FILE__PASSWORD=/run/secrets/mysecretpassword
```

## References
- [linuxserver/sqlitebrowser Docker](https://hub.docker.com/r/linuxserver/sqlitebrowser)
- [linuxserver/docker-sqlitebrowser GitHub](https://github.com/linuxserver/docker-sqlitebrowser)
- [DB Browser for SQLite wiki](https://github.com/sqlitebrowser/sqlitebrowser/wiki)