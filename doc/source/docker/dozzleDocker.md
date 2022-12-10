# Dozzle Docker

Dozzle is a real-time log viewer for docker containers.

## Docker
```sh
docker run -d --name dozzle --volume=/var/run/docker.sock:/var/run/docker.sock -p 8888:8080 amir20/dozzle
```
[http://localhost:8888/](http://localhost:8888/)

## Docker Compose
```
version: "3"
services:
  dozzle:
    container_name: dozzle
    image: amir20/dozzle:latest
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock
    ports:
      - 9999:8080
```

## Authentication
Dozzle supports a very simple authentication out of the box with just username and password. You should deploy using SSL to keep the credentials safe. See configuration to use `--username` and `--password`.

## Security
```sh
docker run --volume=/var/run/docker.sock:/var/run/docker.sock -p 8888:1224 amir20/dozzle:latest --addr localhost:1224
```

## Changing base URL
```sh
docker run --volume=/var/run/docker.sock:/var/run/docker.sock -p 8080:8080 amir20/dozzle:latest --base /foobar
```

## References
- [Dozzle Docker](https://hub.docker.com/r/amir20/dozzle)
- [Dozzle GitHub](https://github.com/amir20/dozzle)