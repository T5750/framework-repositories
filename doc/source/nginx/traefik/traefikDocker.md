# Traefik Docker

## Architecture

![](https://doc.traefik.io/traefik/v2.0/assets/img/traefik-architecture.png)

Traefik is an open-source Edge Router that makes publishing your services a fun and easy experience. It receives requests on behalf of your system and finds out which components are responsible for handling them.

## Docker Compose
- `traefik.yml`
- `traefik.yaml`

[http://localhost:8080/](http://localhost:8080/)

## Quick Start

![](https://doc.traefik.io/traefik/v2.0/assets/img/quickstart-diagram.png)

You can open a browser and go to [http://localhost:8080/api/rawdata](http://localhost:8080/api/rawdata) to see Traefik's API rawdata

`traefik-whoami.yml`
```
docker-compose -f traefik-whoami.yml up -d
curl -H Host:whoami.docker.localhost http://192.168.128.3
docker-compose -f traefik-whoami.yml up -d --scale whoami=2
curl -H Host:whoami.docker.localhost http://192.168.128.4
```

## References
- [traefik](https://hub.docker.com/_/traefik)
- [Traefik Welcome](https://doc.traefik.io/traefik/v2.0/)
- [Traefik Quick Start](https://doc.traefik.io/traefik/v2.0/getting-started/quick-start/)